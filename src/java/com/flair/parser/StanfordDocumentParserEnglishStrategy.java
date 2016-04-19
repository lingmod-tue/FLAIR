/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.grammar.EnglishGrammaticalConstants;
import com.flair.grammar.GrammaticalConstruction;
import com.flair.grammar.Language;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementation of English language parsing logic for the Stanford parser
 * @author shadeMe
 */
class StanfordDocumentParserEnglishStrategy extends BasicStanfordDocumentParserStrategy
{
    private AbstractDocument		workingDoc;
    
    private int				dependencyCount;    // count dependencies - correspond to token count without punctuation
    private int				sentenceCount;	    // count sentences
    private int				depthCount;	    // count tree depthCount
    private int				characterCount;	    // count characters in words
    
    public StanfordDocumentParserEnglishStrategy()
    {
	workingDoc = null;
	dependencyCount = sentenceCount = depthCount = characterCount = 0;
    }
    
    private void initializeState(AbstractDocument doc)
    {
	if (pipeline == null)
	    throw new IllegalStateException("Parser not set");
	else if (isLanguageSupported(doc.getLanguage()) == false)
	    throw new IllegalArgumentException("Document language " + doc.getLanguage() + " not supported (Strategy language: " + Language.ENGLISH +")");
	
	workingDoc = doc;
    }
    
    private void resetState()
    {
	dependencyCount = sentenceCount = depthCount = characterCount = 0;
	pipeline = null;
	workingDoc = null;
    }
    
    private int countSubstr(String substr, String str)
    {
	// ###TODO can be made faster?
	// the result of split() will contain one more element than the delimiter
        // the "-1" second argument makes it not discard trailing empty strings
        return str.split(Pattern.quote(substr), -1).length - 1;
    }
    
    private void addConstructionOccurrence(GrammaticalConstruction type, int start, int end, String expr) {
	workingDoc.getConstructionData(type).addOccurrence(start, end);
    }
    
     
    private void inspectSentence(Tree tree, List<CoreLabel> words, Collection<TypedDependency> deps)
    {
	if (words == null || words.isEmpty())
	    return;
	
	boolean indirectQuestionFound = false;
        boolean conditional_found = false; // to find a conditional clause (the order can be inverted)

        // simple/complex sentence + subordinate clauses
        String treeStr = tree.toString(); // don't use toLowerCase() here
        int startInd = words.get(0).beginPosition();
        int endInd = words.get(words.size() - 1).endPosition();

        int numSBAR = countSubstr("SBAR ", treeStr); // the whitespace is important!
	if (numSBAR > 0)
	{
	    addConstructionOccurrence(GrammaticalConstruction.SENTENCE_COMPLEX, startInd, endInd, treeStr);     // highlight the whole sentence
	    for (int i = 0; i < numSBAR; i++)
		addConstructionOccurrence(GrammaticalConstruction.CLAUSE_SUBORDINATE, startInd, endInd, treeStr);     // highlight the whole sentence

	    
	    int withCC = countSubstr("(SBAR (WHNP ", treeStr) + countSubstr("(SBAR (IN that", treeStr); // with a conjunction // TODO: can be an indirect question: "She asked me what I like" -> check for reporting words in dependency?
            int withoutCC = countSubstr("(SBAR (S ", treeStr); // reduced relative clauses "The man I saw" // TODO: check
            for (int i = 0; i < withCC; i++)
                addConstructionOccurrence(GrammaticalConstruction.CLAUSE_RELATIVE, startInd, endInd, treeStr); // highlight the whole sentence
            for (int i = 0; i < withoutCC; i++)
                addConstructionOccurrence(GrammaticalConstruction.CLAUSE_RELATIVE_REDUCED, startInd, endInd, treeStr); // highlight the whole sentence
	    
	    
	    int numOfAdvCl = countSubstr("(SBAR (IN", treeStr);
            int numOfAdvClWhether = countSubstr("(SBAR (IN whether", treeStr); // indirect questions

            int numOfRelClThat = countSubstr("(SBAR (IN that", treeStr); // indirect questions

            int numOfAdvClIF = countSubstr("(SBAR (IN if", treeStr); // TODO check: "if" is also IN! -> an indirect question or conditional
            int numOfAdvUnless = countSubstr("(SBAR (IN unless", treeStr);
	    
	    for (int i = 0; i < numOfAdvCl - numOfRelClThat - numOfAdvClIF - numOfAdvUnless; i++)		// don't include the indirect questions and conditionals
		addConstructionOccurrence(GrammaticalConstruction.CLAUSE_ADVERBIAL, startInd, endInd, treeStr); // highlight the whole sentence
           
	    if (numOfAdvClIF > 0 || numOfAdvClWhether > 0)
                indirectQuestionFound = true;

            if (numOfAdvClIF > 0 || numOfAdvUnless > 0)
                conditional_found = true;
	}
	else if (numSBAR == 0)
	{
	    int numOfS = countSubstr("S ", treeStr); // whitespace is important!
            if (numOfS == 1)
	    {
                // incomplete sentence (no VP or NP)
                if (!(treeStr.contains("VP") && treeStr.contains("NP")))
		    addConstructionOccurrence(GrammaticalConstruction.SENTENCE_INCOMPLETE, startInd, endInd, treeStr); // highlight the whole sentence
		else
		    addConstructionOccurrence(GrammaticalConstruction.SENTENCE_SIMPLE, startInd, endInd, treeStr); // highlight the whole sentence
            }
	    else if (numOfS > 1)
		addConstructionOccurrence(GrammaticalConstruction.SENTENCE_COMPOUND, startInd, endInd, treeStr);
            else
		addConstructionOccurrence(GrammaticalConstruction.SENTENCE_INCOMPLETE, startInd, endInd, treeStr);
	}
	
	
	// find the first and the last word (skip bullets, quotes, etc.)
        CoreLabel firstWord = null;
        for (CoreLabel w : words) {
            if (w.word() != null && w.word().toLowerCase().matches("[a-z]*"))
	    { 
		// first real word
                firstWord = w;
                break;
            }
        }
	
        if (firstWord == null)
            return;
	
        // find the last token (punctuation mark) (don't take quotes into account)
        CoreLabel lastWord = null;
        for (int i = words.size() - 1; i > 0; i--)
	{
            if (words.get(i).tag() != null && words.get(i).tag().equalsIgnoreCase("."))
	    {
                lastWord = words.get(i);
                break;
            }
        }
	
	//// find questions and imperatives
        if (lastWord != null && lastWord.word() != null)
	{
            // questions
            if (lastWord.word().toLowerCase().endsWith("?"))
	    { 
		// question (not indirect!)
                // indirectQuestions - ??? not here!
                inspectQuestion(words, deps);
            }
	    // imperatives
            else if (lastWord.word().toLowerCase().endsWith("!") && lastWord.word().toLowerCase().startsWith("!"))
	    {
                // imperative - do it here in line
                if (firstWord.tag() != null && firstWord.tag().equalsIgnoreCase("vb") || firstWord.tag().equalsIgnoreCase("vbp"))
		    addConstructionOccurrence(GrammaticalConstruction.IMPERATIVES, startInd, endInd, words.toString());
            }
        }
	
	 // degrees of comparison of long adjectives: "more/RBR beautiful/JJ", "most/RBS beautiful/JJ"
        boolean comparativeMoreFound = false;
        boolean superlativeMostFound = false;

        boolean usedFound = false;
        int goingToFound = 0;

        // independent of type of sentence
        // go through the words (CoreLabels) for POS tags
        for (CoreLabel label : words)
	{
            String labelTag = label.tag().toLowerCase();
            String labelWord = label.word().toLowerCase();
            if (labelTag != null && labelWord != null &&
		(!(labelTag.equalsIgnoreCase(".") || labelTag.equalsIgnoreCase(","))))
	    {
                characterCount += labelWord.length();

                // "used to"
                if (usedFound)
		{
                    if (labelTag.equalsIgnoreCase("to"))    // allows for elliptical structures, e.g., "yes, I used to."
			addConstructionOccurrence(GrammaticalConstruction.VERBCONST_USED_TO, words.get(label.index() - 1).beginPosition(), label.endPosition(), "used " + labelWord);
                   
		    usedFound = false;
                }
		
		// "going to"
                if (goingToFound > 0)
		{
		    switch (goingToFound)
		    {
		    	case 1:
			    // only "going" found
			    if (labelTag.equalsIgnoreCase("to"))
			    {
				goingToFound++;
				if (label.index() < words.size() - 2 && ".,!?;:)".contains(words.get(label.index() + 1).tag()))
				{
				    // catches "yes, I'm going to."
				    addConstructionOccurrence(GrammaticalConstruction.VERBCONST_GOING_TO, words.get(label.index() - 1).beginPosition(), label.endPosition(), "going " + labelWord);
				    goingToFound = 0;
				}
			    }
			    else
				goingToFound = 0;
			    break;
		    	case 2:
			    // "going to" found
			    if (labelTag.equalsIgnoreCase("vb"))
			    {
				// can tell it from "I'm going to France" // can't catch "I'm going to _slowly_ start packing"
				addConstructionOccurrence(GrammaticalConstruction.VERBCONST_GOING_TO, words.get(label.index() - 3).beginPosition(), label.endPosition(), "going to " + labelWord);  // "going to V"
				goingToFound = 0;
			    } 
			    break;
		    	default:
			    goingToFound = 0;
			    System.out.println("Too many goingToFound! Check!");
			    break;
		    }
                }
		
		 // "a lot of" quantifier
                if (labelWord.equalsIgnoreCase("lot"))
		{
                    int lotInd = label.index();
                    if (lotInd > 0 && lotInd < words.size() - 1 && words.get(lotInd - 1).word().equalsIgnoreCase("a") && words.get(lotInd + 1).word().equalsIgnoreCase("of")) // "going to V"
			addConstructionOccurrence(GrammaticalConstruction.DETERMINER_A_LOT_OF, words.get(label.index() - 1).beginPosition(), words.get(label.index() + 1).endPosition(), "a " + labelWord + " of");
                }
		
		 // degrees of comparison of long adjectives: "more/RBR beautiful/JJ", "most/RBS beautiful/JJ"
                if (comparativeMoreFound)
		{
                    if (labelTag.equalsIgnoreCase("jj") && labelWord.length() > 5)	    // add comparativeAdjLong
			addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_COMPARATIVE_LONG, words.get(label.index() - 1).beginPosition(), label.endPosition(), "more " + labelWord);
                    else if (labelTag.equalsIgnoreCase("rb") && labelWord.length() > 5)    // add comparativeAdvLong
			addConstructionOccurrence(GrammaticalConstruction.ADVERB_COMPARATIVE_LONG, words.get(label.index() - 1).beginPosition(), label.endPosition(), "more " + labelWord);
                    
                    comparativeMoreFound = false;
                }
		else if (superlativeMostFound)
		{
                    if (labelTag.equalsIgnoreCase("jj") && labelWord.length() > 5)  // add superlativeAdjLong
			addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_LONG, words.get(label.index() - 1).beginPosition(), label.endPosition(), "more " + labelWord);
		    else if (labelTag.equalsIgnoreCase("rb") && labelWord.length() > 5)	// add superlativeAdvLong
                        addConstructionOccurrence(GrammaticalConstruction.ADVERB_SUPERLATIVE_LONG, words.get(label.index() - 1).beginPosition(), label.endPosition(), "more " + labelWord);
                    
		    superlativeMostFound = false;
                } //// pronouns (reflexive or possessive)
		else if (labelTag.equalsIgnoreCase("prp") && EnglishGrammaticalConstants.REFLEXIVE_PRONOUNS.contains(labelWord))
		{
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_REFLEXIVE, label.beginPosition(), label.endPosition(), labelWord);
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, label.beginPosition(), label.endPosition(), labelWord);
                }
		else if (labelTag.equalsIgnoreCase("prp$") && EnglishGrammaticalConstants.POSSESSIVE_PRONOUNS.contains(labelWord))
		{
                    // it can actually be either possessive or objective (stanford parser crashes on that!)
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_POSSESSIVE, label.beginPosition(), label.endPosition(), labelWord);
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, label.beginPosition(), label.endPosition(), labelWord);
                } 
		else if (EnglishGrammaticalConstants.POSSESSIVE_ABSOLUTE_PRONOUNS.contains(labelWord))
		{
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_POSSESSIVE_ABSOLUTE, label.beginPosition(), label.endPosition(), labelWord);
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, label.beginPosition(), label.endPosition(), labelWord);
                }
		// conjunctions (all or simple)
                else if (labelTag.equalsIgnoreCase("in") || labelTag.equalsIgnoreCase("cc") || labelTag.equalsIgnoreCase("rb") || labelTag.equalsIgnoreCase("wrb"))
		{
                    if (EnglishGrammaticalConstants.ADVANCED_CONJUNCTIONS.contains(labelWord))
			addConstructionOccurrence(GrammaticalConstruction.CONJUNCTIONS_ADVANCED, label.beginPosition(), label.endPosition(), labelWord);
                    else if (EnglishGrammaticalConstants.SIMPLE_CONJUNCTIONS.contains(labelWord))
			addConstructionOccurrence(GrammaticalConstruction.CONJUNCTIONS_SIMPLE, label.beginPosition(), label.endPosition(), labelWord);

                    if (labelTag.equalsIgnoreCase("rb"))
			addConstructionOccurrence(GrammaticalConstruction.ADVERB_POSITIVE, label.beginPosition(), label.endPosition(), labelWord);
                   
                }
		// NOT HERE : determiners (some, any) - see dependencies
		// HERE: articles (a, an, the)
                else if (labelTag.equalsIgnoreCase("dt"))
		{
                    switch (labelWord)
		    {
                        case "a":
			    addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.ARTICLE_A, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "an":
			    addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.ARTICLE_AN, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "the":
			    addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.ARTICLE_THE, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                    }
                }
		// degrees of comparison of long adjectives and adverbs
                else if (labelTag.equalsIgnoreCase("rbr"))
		{
                    if (labelWord.equalsIgnoreCase("more"))
                        comparativeMoreFound = true;
                   
		    addConstructionOccurrence(GrammaticalConstruction.ADVERB_COMPARATIVE_SHORT, label.beginPosition(), label.endPosition(), labelWord);	// "more" is counted here
                }
		else if (labelTag.equalsIgnoreCase("rbs"))
		{
                    if (labelWord.equalsIgnoreCase("most"))
                        superlativeMostFound = true;
                   
		    addConstructionOccurrence(GrammaticalConstruction.ADVERB_SUPERLATIVE_SHORT, label.beginPosition(), label.endPosition(), labelWord);	// "most" is counted here
                } 
		// ing noun forms
                else if (labelTag.startsWith("nn"))
		{
                    if (labelWord.endsWith("ing") && labelWord.length() > 4 && (!EnglishGrammaticalConstants.ING_NOUNS.contains(labelWord)))
			addConstructionOccurrence(GrammaticalConstruction.NOUNFORMS_ING, label.beginPosition(), label.endPosition(), labelWord);
                    
                    // plural noun forms
                    if (labelTag.equalsIgnoreCase("nns"))
		    {
                        if (labelWord.endsWith("s"))
			    addConstructionOccurrence(GrammaticalConstruction.PLURAL_REGULAR, label.beginPosition(), label.endPosition(), labelWord);
                        else
			    addConstructionOccurrence(GrammaticalConstruction.PLURAL_IRREGULAR, label.beginPosition(), label.endPosition(), labelWord);
                    }
                }
		//// modals
                else if (labelTag.equalsIgnoreCase("md"))
		{   
		    // includes "will"
		    addConstructionOccurrence(GrammaticalConstruction.MODALS, label.beginPosition(), label.endPosition(), labelWord);
                    switch (labelWord) 
		    {
                        case "can":
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_CAN, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "must":
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_MUST, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "need":
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_NEED, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "may":
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_MAY, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "could":
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_COULD, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "might":
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_MIGHT, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "ought":
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(), label.endPosition(), labelWord);
			    addConstructionOccurrence(GrammaticalConstruction.MODALS_OUGHT, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        default:
                            if (!(labelWord.equalsIgnoreCase("will") || labelWord.equalsIgnoreCase("shall")))
                                addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(), label.endPosition(), labelWord);
                            
                            break;
                    }
                } 
		//// forms of adjectives 
		else if (labelTag.startsWith("jj"))
		{  
		    // already in lower case
                    switch (labelTag)
		    {
                        case "jj":
                            if (labelWord.equalsIgnoreCase("much") || labelWord.equalsIgnoreCase("many"))
                                break;
                            else if (labelWord.equalsIgnoreCase("able") || labelWord.equalsIgnoreCase("unable"))
			    {
                                int ableInd = label.sentIndex();
                                if (words.size() >= ableInd && words.size() > (ableInd + 1) && words.get(ableInd + 1).tag().equalsIgnoreCase("to"))
				{
				    addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(), label.endPosition(), labelWord);
				    addConstructionOccurrence(GrammaticalConstruction.MODALS, label.beginPosition(), label.endPosition(), labelWord);
				    addConstructionOccurrence(GrammaticalConstruction.MODALS_ABLE, label.beginPosition(), label.endPosition(), labelWord);
                                }
                                // otherwise it's an adjective: He's an able student.
                            }
			    else
			    {
                                // if it doesn't contain numbers
                                if (!labelWord.matches(".*\\d.*"))
				    addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_POSITIVE, label.beginPosition(), label.endPosition(), labelWord);
                            }
			    
                            break;
                        case "jjr": // "more" is covered above -> not included here
			    addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                        case "jjs": // "most" is covered above -> not included here
			    addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_SHORT, label.beginPosition(), label.endPosition(), labelWord);
                            break;
                    }
                } 
		//// verb forms 
                else if (labelTag.startsWith("v") || labelTag.equalsIgnoreCase("md"))
		{ 
		    // already in lower case // include modals "will" and "would" ++
                    if (labelWord.startsWith("'"))
		    {
			addConstructionOccurrence(GrammaticalConstruction.VERBFORM_SHORT, label.beginPosition(), label.endPosition(), labelWord);
                        // do NOT add auxiiaries here (can be  main verb as well)
                    } 
		    else
		    {
                        switch (labelWord)
			{
                            case "are":
                            case "is":
                            case "am":
				addConstructionOccurrence(GrammaticalConstruction.VERBFORM_LONG, label.beginPosition(), label.endPosition(), labelWord);
                                // do NOT add auxiiaries here (can be  main verb as well)
                                break;
                            case "has":
                            case "have":
                            case "had":
				addConstructionOccurrence(GrammaticalConstruction.VERBFORM_LONG, label.beginPosition(), label.endPosition(), labelWord);
                                // add modal "have to" here
                                int thisInd = label.index();
                                if (words.size() > thisInd + 1)
				{
                                    if (words.get(thisInd + 1).tag().equalsIgnoreCase("to"))
				    { 
					// catch the elliptical "I have to." BUT not "I will give everything I have to John"
					addConstructionOccurrence(GrammaticalConstruction.MODALS, label.beginPosition(), words.get(thisInd + 1).endPosition(), labelWord + " to");
					addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(), words.get(thisInd + 1).endPosition(), labelWord + " to");
					addConstructionOccurrence(GrammaticalConstruction.MODALS_HAVE_TO, label.beginPosition(), words.get(thisInd + 1).endPosition(), labelWord + " to");
                                    }
                                }
				
                                break;
                            case "used":
                                if (labelTag.equalsIgnoreCase("vbd"))	 // excludes passive "it is used to"
                                    usedFound = true;
                        }

                        if (labelTag.equalsIgnoreCase("vbg"))
			{
			    addConstructionOccurrence(GrammaticalConstruction.VERBFORM_ING, label.beginPosition(), label.endPosition(), labelWord);
                            if (labelWord.equalsIgnoreCase("going") || labelWord.equalsIgnoreCase("gon")) 
                                goingToFound++;
                        } 
			else if (labelTag.equalsIgnoreCase("vbd") || labelTag.equalsIgnoreCase("vbn")) 
			{
                            if (!labelWord.endsWith("ed"))
				addConstructionOccurrence(GrammaticalConstruction.VERBS_IRREGULAR, label.beginPosition(), label.endPosition(), labelWord);
                            else
				addConstructionOccurrence(GrammaticalConstruction.VERBS_REGULAR, label.beginPosition(), label.endPosition(), labelWord);
                        }

                    }

                }
            }
	}
	
	// go through the dependencies
        for (TypedDependency dependency : deps) 
	{
            String rel = dependency.reln().toString();

            IndexedWord dep = dependency.dep();
            IndexedWord gov = dependency.gov();

            // first gov is ROOT (no ind?)!
            // detect the order dep-gov or gov-dep for highlights
            int depBegin = dep.beginPosition();
            int govBegin = gov.beginPosition();
            int depEnd = dep.endPosition();
            int govEnd = gov.endPosition();
            int start = -1;
            int end = -1;
            if (depBegin < govBegin)
	    {
                start = depBegin;
                end = govEnd;
            }
	    else
	    {
                start = govBegin;
                end = depEnd;
            }
	    
            if (start < 0 || end < 0)
	    {
                if (rel.equalsIgnoreCase("root"))
		{
                    start = dep.beginPosition();
                    end = dep.endPosition();
                }
		
                if (start < 0 || end < 0)
                    System.out.println("Wrong highlight indices for dependencies!: rel " + rel);
            }

            if (gov.word() == null || gov.lemma() == null || gov.tag() == null || dep.word() == null || dep.lemma() == null || dep.tag() == null)
                continue;

            // negation
            if (EnglishGrammaticalConstants.NEGATION.contains(gov.word().toLowerCase()) || EnglishGrammaticalConstants.NEGATION.contains(dep.word().toLowerCase())) 
		addConstructionOccurrence(GrammaticalConstruction.NEGATION_ALL, start, end, dependency.toString());
            else if (EnglishGrammaticalConstants.PARTIAL_NEGATION.contains(gov.word().toLowerCase()) || EnglishGrammaticalConstants.PARTIAL_NEGATION.contains(dep.word().toLowerCase()))
		addConstructionOccurrence(GrammaticalConstruction.NEGATION_PARTIAL, gov.beginPosition(), dep.endPosition(), dependency.toString());
           

            //// there is / there are: expl( is-3 , there-2 )
            if (rel.toLowerCase().equalsIgnoreCase("expl") && dep.word().equalsIgnoreCase("there"))
	    {
		addConstructionOccurrence(GrammaticalConstruction.EXISTENTIAL_THERE, start, end, dependency.toString());
		
                if (gov.tag().equalsIgnoreCase("vbz"))
		    addConstructionOccurrence(GrammaticalConstruction.THERE_IS_ARE, start, end, dependency.toString());
                else if (gov.tag().equalsIgnoreCase("vbd"))
		    addConstructionOccurrence(GrammaticalConstruction.THERE_WAS_WERE, start, end, dependency.toString());
               

            }
	    //// prepositions (incl. simple)
            else if (rel.toLowerCase().startsWith("prep")) 
	    {
                if (rel.contains("_"))
		{ 
		    // collapsed dependencies
                    int startPrep = -1;
                    int endPrep = -1;

                    // identify the start and end indices of this preposition/these prepositions
                    if (rel.indexOf("_") == rel.lastIndexOf("_"))
		    { 
			// 1-word preposition
                        String prep = rel.substring(rel.indexOf("_") + 1).toLowerCase();

                        // go through words to find the indices
                        for (CoreLabel l : words)
			{
                            if (l.word().equalsIgnoreCase(prep)) 
			    {
                                startPrep = l.beginPosition();
                                endPrep = l.endPosition();

                                // add constructions here: in case of a duplicate, it'll not be added anyway
                                if (l.tag().equalsIgnoreCase("in") && EnglishGrammaticalConstants.SIMPLE_PREPOSITIONS.contains(prep)) 
				{
				    addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, startPrep, endPrep, dependency.toString());
				    addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_SIMPLE, startPrep, endPrep, dependency.toString());
                                } 
				else 
				{
				    addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, startPrep, endPrep, dependency.toString());
				    addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_ADVANCED, startPrep, endPrep, dependency.toString());
                                }
                            }
                        }

                    } 
		    else if (rel.indexOf("_") > 0 && rel.indexOf("_") < rel.lastIndexOf("_"))
		    { 
			// n-word preposition

                        // take the begin poition of the 1st prep and the end position of the last prep
                        String prep1 = rel.substring(rel.indexOf("_") + 1, rel.indexOf("_", rel.indexOf("_") + 1));
                        String prep2 = rel.substring(rel.lastIndexOf("_") + 1);

                        for (CoreLabel l : words)
			{
                            if (l.word().equalsIgnoreCase(prep1)) 
			    {
                                // at first find the first prep - don't add constructions yet 
                                startPrep = l.beginPosition();
                            } 
			    else if (l.tag().equalsIgnoreCase("in") && l.word().equalsIgnoreCase(prep2))
			    {
                                endPrep = l.endPosition();
                                if (startPrep > -1 && endPrep > -1 && endPrep > startPrep)
				{
                                    // add constructions here: in case of a duplicate, it'll not be added anyway
				    addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, startPrep, endPrep, dependency.toString());
				    addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_COMPLEX, startPrep, endPrep, dependency.toString());
                                }
                            }
                        }

                    }
                }
		
                /// objective pronouns after a preposition
                if (dep.tag().equalsIgnoreCase("prp") && EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase()))
		{
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dep.word());
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_OBJECTIVE, depBegin, depEnd, dep.word());
                }
            } 
	    //// objective and subjective pronouns
            else if (rel.equalsIgnoreCase("iobj"))
	    { 
		// indirect object
		addConstructionOccurrence(GrammaticalConstruction.OBJECT_INDIRECT, start, end, dependency.toString());
		
                if (dep.tag().equalsIgnoreCase("prp") && EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase()))
		{
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_OBJECTIVE, depBegin, depEnd, dependency.toString());
                }
            } 
	    else if (rel.equalsIgnoreCase("dobj")) 
	    { 
		// direct object
		addConstructionOccurrence(GrammaticalConstruction.OBJECT_DIRECT, start, end, dependency.toString());
		
                if (dep.tag().equalsIgnoreCase("prp") && EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase()))
		{
                    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_OBJECTIVE, depBegin, depEnd, dependency.toString());
                }
            } 
	    else if (rel.equalsIgnoreCase("nsubj")) 
	    { 
		// subjective pronouns
                if (dep.tag().equalsIgnoreCase("prp") && EnglishGrammaticalConstants.SUBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase()))
		{
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dep.word());
		    addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_SUBJECTIVE, depBegin, depEnd, dep.word());
                }
            } 
	    //// negation (noNotNever, nt, not)
            else if (rel.equalsIgnoreCase("neg"))
	    {
		addConstructionOccurrence(GrammaticalConstruction.NEGATION_NO_NOT_NEVER, depBegin, depEnd, dependency.toString());
		addConstructionOccurrence(GrammaticalConstruction.NEGATION_ALL, depBegin, depEnd, dependency.toString());
		
                if (dep.word().equalsIgnoreCase("n't"))
		    addConstructionOccurrence(GrammaticalConstruction.NEGATION_NT, depBegin, depEnd, dependency.toString());
                else if (dep.word().equalsIgnoreCase("not"))
		    addConstructionOccurrence(GrammaticalConstruction.NEGATION_NOT, depBegin, depEnd, dependency.toString());
            }
	    // determinets some, any
            else if (rel.equalsIgnoreCase("det"))
	    {
                if (dep.lemma().equalsIgnoreCase("some"))
		    addConstructionOccurrence(GrammaticalConstruction.DETERMINER_SOME, dep.beginPosition(), gov.endPosition(), dependency.toString());
                else if (dep.lemma().equalsIgnoreCase("any"))
		    addConstructionOccurrence(GrammaticalConstruction.DETERMINER_ANY, dep.beginPosition(), gov.endPosition(), dependency.toString());
            }
	    else if (rel.equalsIgnoreCase("amod"))
	    {
                if (dep.lemma().equalsIgnoreCase("many")) 
		    addConstructionOccurrence(GrammaticalConstruction.DETERMINER_MANY, dep.beginPosition(), gov.endPosition(), dependency.toString());
                else if (dep.lemma().equalsIgnoreCase("much"))
		    addConstructionOccurrence(GrammaticalConstruction.DETERMINER_MUCH, dep.beginPosition(), gov.endPosition(), dependency.toString());
            }
	    //// verb forms
            else if (rel.equalsIgnoreCase("aux"))
	    {
                // emphatic do (separately)
                if (dep.lemma().equalsIgnoreCase("do") && gov.tag().equalsIgnoreCase("vb") && (dep.index() == gov.index() - 1))
		{
		    // "do" is followed by a verb
		    addConstructionOccurrence(GrammaticalConstruction.VERBFORM_EMPATHIC_DO, start, end, dependency.toString());
                } 
		else if (dep.lemma().equalsIgnoreCase("be") || dep.lemma().equalsIgnoreCase("have") || dep.lemma().equalsIgnoreCase("do"))
		    addConstructionOccurrence(GrammaticalConstruction.VERBFORM_AUXILIARIES_BE_DO_HAVE, depBegin, depEnd, dependency.toString());

                // to-infinitives
                if (dep.tag().equalsIgnoreCase("to")) 
		{
                    if (gov.tag().equalsIgnoreCase("vb"))
		    {
                        // "He wants to sleep"
			addConstructionOccurrence(GrammaticalConstruction.VERBFORM_TO_INFINITIVE, start, end, dependency.toString());
                    } 
		    else 
		    {
                        // "He wants to be a pilot" -> aux ( pilot-6 *!!!* , to-3 ) + cop ( pilot-6 , be-4 ) ** only with "be" as copula **
                        int toInd = dep.index();
                        int govInd = gov.index();
                        if (govInd - toInd > 1 && words.get(toInd + 1).word().equalsIgnoreCase("be"))
			    addConstructionOccurrence(GrammaticalConstruction.VERBFORM_TO_INFINITIVE, dep.beginPosition(), words.get(toInd + 1).endPosition(), dependency.toString());
                    }
                }
            } 
	    else if (rel.equalsIgnoreCase("cop"))
		addConstructionOccurrence(GrammaticalConstruction.VERBFORM_COPULAR, start, end, dependency.toString());
            else if (rel.equalsIgnoreCase("prt"))
		addConstructionOccurrence(GrammaticalConstruction.VERBS_PHRASAL, start, end, dependency.toString());
        }

        //// second round for: conditionals
        if (conditional_found) 
            identifyConditionals(words, deps);
	// look for tenses only 'outside' of conditionals!
        // after the first iteration over all dependencies 
        else 
	{
	    // if conditional not found -> look for tenses/times/aspects/voice
            identifyTenses(deps);
        }
    }
    
    private void identifyTenses(Collection<TypedDependency> dependencies)
    {
	boolean pastPartFound = false;
	boolean ingFound = false;
	boolean willDoingFound = false;
	boolean baseFormFound = false; // to check in : Past Simple (negation), Future Simple, Present Simple (negation)
	boolean willDoneFound = false;
	boolean haveHasDoneFound = false;
	boolean hadDoneFound = false;
	boolean willHaveDoneFound = false;
	boolean willCopulaFound = false;
	int verbIndex = -1;
	IndexedWord theVerb = null;

	for (TypedDependency dependency : dependencies)
	{
	    String rel = dependency.reln().getShortName().toLowerCase(); // dependency relation
	    IndexedWord gov = dependency.gov();
	    IndexedWord dep = dependency.dep();

	    // detect the order dep-gov or gov-dep for highlights
	    int depBegin = dep.beginPosition();
	    int govBegin = gov.beginPosition();
	    int depEnd = dep.endPosition();
	    int govEnd = gov.endPosition();
	    int start = -1;
	    int end = -1;
	    if (depBegin < govBegin)
	    {
		start = depBegin;
		end = govEnd;
	    } 
	    else 
	    {
		start = govBegin;
		end = depEnd;
	    }

	    if (start < 0 || end < 0) 
	    {
		if (rel.equalsIgnoreCase("root"))
		{
		    start = dep.beginPosition();
		    end = dep.endPosition();
		}
		if (start < 0 || end < 0) 
		    System.out.println("Wrong highlight indices for dependencies - identifyTenses!");
	    }

	    // first examine the root (the main verb phrase)
	    if (rel.equalsIgnoreCase("root") && verbIndex != dep.index())
	    { 
		// roughly identify the aspect (any Time)
		if (dep.tag().toLowerCase().startsWith("v")) 
		{
		    verbIndex = dep.index();
		    theVerb = dep;
		}

		if (dep.tag().equalsIgnoreCase("vbg"))
		{ 
		    // check for Progressive aspect
		    // can still be: Present/Past/Future Progressive, Present/Past/Future Perfect Progressive
		    ingFound = true;
		    pastPartFound = false;
		    baseFormFound = false;
		}
		else if (dep.tag().equalsIgnoreCase("vbn")) 
		{ 
		    // check for Perfect aspect
		    // can still be: Past Perfect, Present Perfect, Future Perfect, Passive
		    pastPartFound = true;
		    ingFound = false;
		    baseFormFound = false;
		} 
		else if (dep.tag().equalsIgnoreCase("vb")) 
		{ 
		    // base form
		    // can still be: Past Simple (negation), Future Simple, Present Simple (negation)
		    // cannot be Present Simple : VBP or VBZ instead
		    // examples: I didn't do it // I will do it // I don't know
		    addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, depBegin, depEnd, dependency.toString());
		    baseFormFound = true;
		    ingFound = false;
		    pastPartFound = false;
		} 
		else if (dep.tag().equalsIgnoreCase("vbp") || dep.tag().equalsIgnoreCase("vbz"))
		{ 
		    // single or plural present
		    addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, depBegin, depEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, depBegin, depEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, depBegin, depEnd, dependency.toString());
		    ingFound = false;
		    pastPartFound = false;
		    baseFormFound = false;
		} 
		else if (dep.tag().equalsIgnoreCase("vbd"))
		{ 
		    // past form
		    addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, depBegin, depEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, depBegin, depEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, depBegin, depEnd, dependency.toString());
		    ingFound = false;
		    pastPartFound = false;
		    baseFormFound = false;
		}
	    } 
	    // don't only look at the root, find other verb phrases
	    else if ((rel.equalsIgnoreCase("nsubj") || rel.equalsIgnoreCase("nsubjpass")) && gov.tag().toLowerCase().startsWith("v") && gov.index() != verbIndex)
	    { 
		// != condition: don't count 1 verb twice: as root() and nsubj()
		verbIndex = gov.index();
		theVerb = gov;
		if (gov.tag().equalsIgnoreCase("vbg"))
		{ 
		    // check for Progressive aspect
		    // can still be: Present/Past/Future Progressive, Present/Past/Future Perfect Progressive
		    ingFound = true;
		    pastPartFound = false;
		    baseFormFound = false;
		} 
		else if (gov.tag().equalsIgnoreCase("vbn"))
		{ 
		    // check for Perfect aspect
		    // can still be: Past Perfect, Present Perfect, Future Perfect, Passive
		    ingFound = false;
		    pastPartFound = true;
		    baseFormFound = false;
		} 
		else if (gov.tag().equalsIgnoreCase("vb")) 
		{ 
		    // base form
		    // can still be: Past Simple (negation), Future Simple, Present Simple (negation)
		    // cannot be Present Simple : VBP or VBZ instead
		    // examples: I didn't do it // I will do it // I don't know
		    addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, govBegin, govEnd, dependency.toString());
		    ingFound = false;
		    pastPartFound = false;
		    baseFormFound = true;
		} 
		else if (gov.tag().equalsIgnoreCase("vbp") || gov.tag().equalsIgnoreCase("vbz")) 
		{ 
		    // single or plural present
		    addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, govBegin, govEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, govBegin, govEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, govBegin, govEnd, dependency.toString());
		    ingFound = false;
		    pastPartFound = false;
		    baseFormFound = false;
		} 
		else if (gov.tag().equalsIgnoreCase("vbd")) 
		{ 
		    // past form
		    addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, govBegin, govEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, govBegin, govEnd, dependency.toString());
		    addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, govBegin, govEnd, dependency.toString());
		    ingFound = false;
		    pastPartFound = false;
		    baseFormFound = false;
		}
	    } 
	    else if (theVerb != null && verbIndex > 0 && rel.equalsIgnoreCase("aux"))
	    {

		if (dep.word().equalsIgnoreCase("will") && (!gov.tag().toLowerCase().startsWith("v")))
		    willCopulaFound = true;

		// simple Aspect
		if (baseFormFound && verbIndex == gov.index()) 
		{
		    // ! simpleAspect - already added above
		    // future
		    if (dep.word().equalsIgnoreCase("will") || (dep.word().equalsIgnoreCase("shall") && dep.index() > 1))
		    {
			// Future Simple 
			addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, depBegin, theVerb.endPosition(), dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_SIMPLE, depBegin, theVerb.endPosition(), dependency.toString());
		    } 
		    // past (negation or question)
		    else if (dep.word().equalsIgnoreCase("did"))
		    {
			// Past Simple negation or question
			addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, depBegin, theVerb.endPosition(), dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, depBegin, theVerb.endPosition(), dependency.toString());
		    } 
		    // present (negation or question)
		    // incl. emphatic do
		    else if (dep.word().equalsIgnoreCase("do") || dep.word().equalsIgnoreCase("does")) 
		    {  
			// no modals!
			// Present Simple negation or question
			addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, depBegin, theVerb.endPosition(), dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, depBegin, theVerb.endPosition(), dependency.toString());
		    }

		    baseFormFound = false;
		    verbIndex = -1; // tense found - Future/Past/Present Simple
		    theVerb = null;
		}

		//// Perfect aspect
		// after aux(done,will) is found: Future Perfect, Passive
		if (willDoneFound)
		{
		    if (dep.word().equalsIgnoreCase("have")) 
		    { 
			// Future Perfect, Passive (perfect)
			willHaveDoneFound = true;
		    } 
		    // Passive (simple): just in case: normally under auxpass() dependency
		    else if (dep.word().equalsIgnoreCase("be") || dep.word().equalsIgnoreCase("get"))
			addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end, dependency.toString());

		    willDoneFound = false;
		}
		// after willHaveDoneFound is found : Future Perfect, Passive (perfect)
		else if (theVerb != null && willHaveDoneFound)
		{
		    // Passive (perfect): just in case: normally under auxpass() dependency
		    if (dep.word().equalsIgnoreCase("been"))
			addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end, dependency.toString());
		    else 
		    {
			addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, depBegin, theVerb.endPosition(), dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PERFECT, depBegin, theVerb.endPosition(), dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, depBegin, theVerb.endPosition(), dependency.toString());
		    }

		    willHaveDoneFound = false;
		    // TODO: check if it should be here
		    pastPartFound = false;
		    verbIndex = -1;
		    theVerb = null;
		} 
		// after aux(done,had) is found : Past Perfect, Passive 
		// but we're at aux() dependency now: in case of Past Perfect, we wouldn't be here
		else if (hadDoneFound)
		{
		    // Passive (perfect) : just in case: normally under auxpass() dependency
		    if (dep.word().equalsIgnoreCase("been") || dep.word().equalsIgnoreCase("got") || dep.word().equalsIgnoreCase("gotten"))
			addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end, dependency.toString());
		}
		// after aux(done,have/has) is found : Present Perfect, Passive
		// but we're at aux() dependency now: in case of Present Perfect, we wouldn't be here
		else if (haveHasDoneFound) 
		{
		    // Passive (perfect) : just in case: normally under auxpass() dependency
		    if (dep.word().equalsIgnoreCase("been") || dep.word().equalsIgnoreCase("got") || dep.word().equalsIgnoreCase("gotten")) 
			addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end, dependency.toString());
		} 
		// right after "done" as main verb is found
		// old version : "else if" instead of "if"
		else if (pastPartFound && (!(willDoneFound || hadDoneFound || haveHasDoneFound)) && gov.index() == verbIndex)
		{
		    // future
		    if (dep.word().equalsIgnoreCase("will") || (dep.word().equalsIgnoreCase("shall") && dep.index() > 1))
		    {
			// can still be: Future Perfect, Passive 
			willDoneFound = true;
		    } 
		    // past
		    else if (dep.word().equalsIgnoreCase("had"))
		    {
			// can still be: Past Perfect, Passive
			hadDoneFound = true;
		    } 
		    // present
		    else if (dep.word().equalsIgnoreCase("have") || dep.word().equalsIgnoreCase("has")) 
		    {  
			// no modals!
			// can still be: Present Perfect, Passive
			haveHasDoneFound = true;
		    }
		}

		//// Progressive aspect : Present/Past/Future Progressive, Present/Past/Future Perfect Progressive,
		if (theVerb != null & ingFound && gov.index() == verbIndex) 
		{
		    if (willDoingFound)
		    {
			if (dep.word().equalsIgnoreCase("be"))
			{
			    // Future Progressive
			    addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.ASPECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			} 
			else if (dep.word().equalsIgnoreCase("have"))
			{
			    addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PERFECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			}

			willDoingFound = false;
			verbIndex = -1;
			theVerb = null;
		    }

		    if (theVerb != null) 
		    {
			if (dep.word().equalsIgnoreCase("will")) 
			{
			    // Future Progressive, Future Perfect Progressive
			    addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, depBegin, theVerb.endPosition(), dependency.toString());
			    willDoingFound = true;
			} 
			else if (dep.word().equalsIgnoreCase("am") || dep.word().equalsIgnoreCase("are") || dep.word().equalsIgnoreCase("is")) 
			{
			    // Present Progressive
			    addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.ASPECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    verbIndex = -1;
			    theVerb = null;
			} 
			else if (dep.word().equalsIgnoreCase("was") || dep.word().equalsIgnoreCase("were")) 
			{
			    // Past Progressive
			    addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.ASPECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    verbIndex = -1;
			    theVerb = null;
			} 
			else if ((!willDoingFound) && (dep.word().equalsIgnoreCase("have") || dep.word().equalsIgnoreCase("has")))
			{
			    // Present Perfect Progressive
			    addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    verbIndex = -1;
			    theVerb = null;
			} 
			else if ((!willDoingFound) && (dep.word().equalsIgnoreCase("had"))) 
			{
			    // Past Perfect Progressive
			    addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT_PROGRESSIVE, depBegin, theVerb.endPosition(), dependency.toString());
			    verbIndex = -1;
			    theVerb = null;
			}
		    }

		    ingFound = false;
		}
	    } 
	    // passive
	    else if (theVerb != null && pastPartFound && verbIndex > 0 && rel.equalsIgnoreCase("auxpass") && gov.index() == verbIndex) 
	    {
		// only with verb==root! change!
		addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end, dependency.toString());
		pastPartFound = false;
		verbIndex = -1;
		theVerb = null;

		if (haveHasDoneFound) 
		    haveHasDoneFound = false;

		if (hadDoneFound) 
		    hadDoneFound = false;

		if (willHaveDoneFound)
		    willHaveDoneFound = false;

	    }
	    // TODO: check the start of highlight!!
	    else if (theVerb != null && verbIndex > 0 && haveHasDoneFound && (!rel.equalsIgnoreCase("auxpass"))) 
	    {
		// Present Perfect
		addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, theVerb.beginPosition(), theVerb.endPosition(), dependency.toString());
		addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, theVerb.beginPosition(), theVerb.endPosition(), dependency.toString());
		addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT, theVerb.beginPosition(), theVerb.endPosition(), dependency.toString());
		haveHasDoneFound = false;
		verbIndex = -1;
		theVerb = null;
	    } 
	    else if (theVerb != null && verbIndex > 0 && hadDoneFound && (!rel.equalsIgnoreCase("auxpass")))
	    {
		// Past Perfect
		addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, theVerb.beginPosition(), theVerb.endPosition(), dependency.toString());
		addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, theVerb.beginPosition(), theVerb.endPosition(), dependency.toString());
		addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT, theVerb.beginPosition(), theVerb.endPosition(), dependency.toString());
		hadDoneFound = false;
		verbIndex = -1;
		theVerb = null;
	    } 
	    else if (rel.equalsIgnoreCase("cop"))
	    {
		addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, start, end, dependency.toString());
		switch (dep.tag().toLowerCase())
		{
		    case "vbp":
		    case "vbz":
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, start, end, dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, start, end, dependency.toString());
			break;
		    case "vbd":
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, start, end, dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, start, end, dependency.toString());
			break;
		    case "vb":
			if (willCopulaFound) 
			{
			    addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_SIMPLE, start, end, dependency.toString());
			    addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, start, end, dependency.toString());
			}

			willCopulaFound = false;
			break;
		}
	    }
	}
    }

    private void identifyConditionals(List<CoreLabel> labeledWords, Collection<TypedDependency> dependencies)
    {
	int verbBegin = -1;
        int verbInd = -1;
        int continuousInd = -1;
        boolean continuous = false;
        boolean mark_found = false;
        boolean would_found = false;
        int startInd = labeledWords.get(0).beginPosition();
        int endInd = labeledWords.get(labeledWords.size() - 1).endPosition();

        for (TypedDependency dependency : dependencies)
	{
            String rel = dependency.reln().getShortName().toLowerCase(); // dependency relation
            IndexedWord gov = dependency.gov();
            IndexedWord dep = dependency.dep();

            if ((dep.word() != null) && (gov.tag() != null))
	    {
                // retrieve all real conditionals using the conditional 'if' clause
                if (rel.equalsIgnoreCase("mark"))
		{
                    verbBegin = gov.beginPosition(); // mark(do-2,if-1)
                    verbInd = gov.index();
                    // e.g. If they call, ... // If it rains ... // If I should go ... 
                    // but: "If you didn't have a child now ..." -> mark(have,if) :/ - adds to condReal imediately, skips the would-clause
                    // TODO: but: "I don't know if he knows it." - treated as conditional!
                    if (gov.tag().equalsIgnoreCase("vb") || gov.tag().equalsIgnoreCase("vbp") || gov.tag().equalsIgnoreCase("vbz") || gov.tag().equalsIgnoreCase("should"))
		    {
                        // fetch: "If you didn't have a child now, you wouldn't..." as well as "If you did know, you would..."
                        // i.e. don't skip the would-clause
                        if (!((verbInd >= 2 && labeledWords.get(verbInd - 2).word().equalsIgnoreCase("did")) || 
			      ((verbInd >= 3) && labeledWords.get(verbInd - 3).word().equalsIgnoreCase("did")))) 
			{
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd, labeledWords.toString());
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
                            break; // don't look at the 'would' clause 
                        }
                    }
		    else if (gov.tag().equalsIgnoreCase("vbg"))
		    { 
			// continuous tense
                        continuous = true;
                        continuousInd = verbInd;
                    }
		    
                    mark_found = true;
                } 
		// retrieve all unreal conditionals using the 'would' clause
                else if (rel.equals("aux"))
		{ 
		    // aux(watched-4, would-2) // aux(travel-4, to-3)
                    // conditionals
                    if (dep.word().equalsIgnoreCase("would") // or only would?
                            || dep.word().equalsIgnoreCase("should")
                            || dep.word().equalsIgnoreCase("could")
                            || dep.word().equalsIgnoreCase("might")
                            || dep.word().equalsIgnoreCase("may"))
		    {
                        would_found = true;
                        if (gov.tag().equalsIgnoreCase("VBN"))
			{ 
			    // past participle form: "would have gone"
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_UNREAL, startInd, endInd, labeledWords.toString());
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
                            break; // with fetch only 1 conditional per sentence - which is fine
                            // but: (won't catch mixedConditionals: If you had done it, you wouldn't regret it now.)
                            // TODO
                        }
			else if (gov.tag().equalsIgnoreCase("VB")) 
			{ 
			    // base form: "would go"
                            //constructions.get("condII").incrementCount(docNum, labeledWords.toString());
                            addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_UNREAL, startInd, endInd, labeledWords.toString());
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
                            break; // with fetch only 1 conditional per sentence - which is fine
                            // but: (won't catch mixedConditionals: If you had done it, you wouldn't regret it now.)
                            // TODO
                        }
                    } 
		    //  fetch Present Perfect: "If you have lost...", "If he's won..."
                    else if ((dep.tag().equalsIgnoreCase("vb") || dep.tag().equalsIgnoreCase("vbp") || dep.tag().equalsIgnoreCase("vbz")) && (dep.lemma().equalsIgnoreCase("have")))
		    {
                        if (gov.beginPosition() == verbBegin)
			{
                            mark_found = false;
                            addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd, labeledWords.toString());
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
                            break; // don't look at the 'would' clause 
                        }
                    } 
		    // fetch: continuous (present and past)
                    else if (continuous && (gov.index() == continuousInd))
		    {
                        if (dep.tag().equalsIgnoreCase("vbd") || dep.tag().equalsIgnoreCase("vbn"))
			{ 
			    // past
                            mark_found = false;
                            addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_UNREAL, startInd, endInd, labeledWords.toString());
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
                            break;
                        } 
			else if (dep.tag().equalsIgnoreCase("vb") || dep.tag().equalsIgnoreCase("vbp") || dep.tag().equalsIgnoreCase("vbz")) 
			{ 
			    // present
                            mark_found = false;
                            addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd, labeledWords.toString());
			    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
                            break;
                        }
                    }
                }
            }
        }
	
        // fetch past real conditional: "If you didn't go there, you didn't see him"
        if (mark_found && !would_found) 
	{
            addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd, labeledWords.toString());
	    addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
        } 
	else if (continuous)
	{
            // TODO ???
        }
    }
    
    private void inspectQuestion(List<CoreLabel> labeledWords, Collection<TypedDependency> dependencies)
    {
	int startInd = labeledWords.get(0).beginPosition();
        int endInd = labeledWords.get(labeledWords.size() - 1).endPosition();

        //// direct question
	addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_DIRECT, startInd, endInd, labeledWords.toString());

        CoreLabel firstWord = null;
        for (CoreLabel w : labeledWords) 
	{
            if (w.word() != null && w.word().toLowerCase().matches("[a-z]*")) 
	    { 
		// first real word
                firstWord = w;
                break;
            }
        }
	
        if (firstWord == null)
            return;
        
        String firstWordTag = firstWord.tag();
        String firstWordWord = firstWord.word().toLowerCase();

        //// tag questions
        // look at the word right before the "?" 
        CoreLabel lastWord = null;
        CoreLabel qMark = null; // it must be there
        int count = 0; // count the words between ? and ,
        for (int i = labeledWords.size() - 1; i > 0; i--)
	{
            CoreLabel label = labeledWords.get(i);
            String tag = label.tag();
            String word = label.word().toLowerCase();
	    
            if (qMark == null && tag.equalsIgnoreCase(".") && word.contains("?"))
	    {
                qMark = label;
                count++;
            } 
	    else if (qMark != null && (tag.equalsIgnoreCase("prp") || word.equalsIgnoreCase("not"))) 
	    { 
		// check for the pronoun or the negation (do they not?)
                lastWord = label;
                count++;
            } 
	    else if (lastWord != null && (!tag.equalsIgnoreCase(","))) // check for the negation or for the verb
                count++;
            else if (lastWord != null && tag.equalsIgnoreCase(","))
	    {
                if (count < 5)
		    addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TAG, label.beginPosition(), endInd, labeledWords.toString());  // highlight only the tag
                else 
                    break;
            } 
	    else 
                break;
        }

        //// wh-question
        if (firstWordTag.toLowerCase().startsWith("w"))
	{
            // only for wh-questions
            boolean rootFound = false;
            // in case there is a question phrase at the beginning ("What kind of music do you like?") - go from the ROOT to find the verb
            //  toBeQuestions; toDoQuestions; toHaveQuestions // direct: "What's this?" - look into the dependencies: the root should be a verb -> aux(verb, ??)
            int depStartInd = 0;
            for (TypedDependency dependency : dependencies)
	    {
                String rel = dependency.reln().getShortName();
                IndexedWord dep = dependency.dep();
                IndexedWord gov = dependency.gov();

                if ((!rootFound) && rel.equalsIgnoreCase("root"))
		{
                    rootFound = true;
                    depStartInd = dep.beginPosition();
                }
		
                if (rootFound && 
		    (rel.equalsIgnoreCase("aux") || rel.equalsIgnoreCase("cop") || rel.equalsIgnoreCase("auxpass")) &&
		    (dep.beginPosition() == depStartInd || dependency.gov().beginPosition() == depStartInd))
		{
                    // check the dep of aux()
                    if (dep.lemma().equalsIgnoreCase("be") || gov.lemma().equalsIgnoreCase("be")) 
		    { 
			// it might not get here
                        addWhQuestion(firstWordWord, startInd, endInd);
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_BE, startInd, endInd, labeledWords.toString());
                    } 
		    else if (dep.lemma().equalsIgnoreCase("do") || gov.lemma().equalsIgnoreCase("do")) 
		    {
                        addWhQuestion(firstWordWord, startInd, endInd);
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_DO, startInd, endInd, labeledWords.toString());
                    } 
		    else if (dep.lemma().equalsIgnoreCase("have") || gov.lemma().equalsIgnoreCase("have")) 
		    {
                        addWhQuestion(firstWordWord, startInd, endInd);
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_HAVE, startInd, endInd, labeledWords.toString());
                    }
		    
                    break;
                }
            }

        } 
	//// yesNoQuestions (starts with a verb (or modal)) 
        else if (firstWordTag.toLowerCase().startsWith("vb"))
	{
            if (firstWord.lemma().equalsIgnoreCase("be"))
	    { 
		//  toBeQuestions;
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd, labeledWords.toString());
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_BE, startInd, endInd, labeledWords.toString());
            } 
	    else if (firstWord.lemma().equalsIgnoreCase("do"))
	    { 
		// simple
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd, labeledWords.toString());
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_DO, startInd, endInd, labeledWords.toString());
            } 
	    else if (firstWord.lemma().equalsIgnoreCase("have")) 
	    { 
		// perfect
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd, labeledWords.toString());
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_HAVE, startInd, endInd, labeledWords.toString());
            }
        } 
	else if (firstWordTag.toLowerCase().contains("md"))
	{
	    addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd, labeledWords.toString());
	    addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_MODAL, startInd, endInd, labeledWords.toString());
        } 
	else
	{ 
	    // it can still be the case that it is a wh- or yes-no question but it doesn't start at the beginning of a sentence
            // e.g.: That person has to wonder 'What did I do wrong?' 
            // TODO: At ..., _do_ you know _what_ I mean?" 
            CoreLabel questionWord = null;
            for (CoreLabel aWord : labeledWords)
	    {
                // look for a question word
                if ((questionWord == null) && (aWord.tag().toLowerCase().startsWith("w")))
                    questionWord = aWord;
                else if (questionWord != null) 
		{
		    // if question word found
                    if (aWord.tag().toLowerCase().startsWith("vb")) 
		    {
                        if (aWord.lemma().equalsIgnoreCase("be")) 
			{ 
			    //  toBeQuestions;
                            addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
			    addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_BE, startInd, endInd, labeledWords.toString());
                            break;
                        } 
			else if (aWord.lemma().equalsIgnoreCase("do")) 
			{ 
			    // simple
                            addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
			    addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_DO, startInd, endInd, labeledWords.toString());
                            break;
                        } 
			else if (aWord.lemma().equalsIgnoreCase("have")) 
			{ 
			    // perfect
                            addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
			    addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_HAVE, startInd, endInd, labeledWords.toString());
                            break;
                        }
                    } 
		    else if (aWord.tag().toLowerCase().startsWith("md"))
		    {
                        addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_MODAL, startInd, endInd, labeledWords.toString());
                    }
                }
            }
        }
    }
    
    private void addWhQuestion(String qWord, int startInd, int endInd)
    {
	addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WH, startInd, endInd, qWord);
	switch (qWord.toLowerCase())
	{
	    case "what":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHAT, startInd, endInd, qWord);
		break;
	    case "how":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_HOW, startInd, endInd, qWord);
		break;
	    case "why":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHY, startInd, endInd, qWord);
		break;
	    case "who":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHO, startInd, endInd, qWord);
		break;
	    case "where":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHERE, startInd, endInd, qWord);
		break;
	    case "when":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHEN, startInd, endInd, qWord);
		break;
	    case "whose":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHOSE, startInd, endInd, qWord);
		break;
	    case "whom":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHOM, startInd, endInd, qWord);
		break;
	    case "which":
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHICH, startInd, endInd, qWord);
		break;
	    default:
		System.out.println("Unknown wh- word: " + qWord);
   
	}
    }
    
    @Override
    public boolean isLanguageSupported(Language lang) {
	return lang == Language.ENGLISH;
    }
    
    @Override
    public boolean apply(AbstractDocument docToParse)
    {
	assert docToParse != null;
	try
	{
	    initializeState(docToParse);
	    
	    Annotation docAnnotation = new Annotation(workingDoc.getText());
	    pipeline.annotate(docAnnotation);
	    
	    List<CoreMap> sentences = docAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
            for (CoreMap itr : sentences)
	    {
		if (itr.size() > 0)
		{
		    Tree tree = itr.get(TreeCoreAnnotations.TreeAnnotation.class);
                    List<CoreLabel> words = itr.get(CoreAnnotations.TokensAnnotation.class);
                    Collection<TypedDependency> dependencies = itr.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class).typedDependencies();
		    
		    sentenceCount++;
                    dependencyCount += dependencies.size();
                    depthCount += tree.depth();
		    
		    // extract gram.structures 
                    inspectSentence(tree, words, dependencies);
		}
	    }
	    
	    // update doc properties
	    workingDoc.setAvgSentenceLength((double)dependencyCount / (double)sentenceCount);
	    workingDoc.setAvgTreeDepth((double)depthCount / (double)sentenceCount);
	    workingDoc.setAvgWordLength((double)characterCount / (double)dependencyCount);
	    workingDoc.setLength(dependencyCount);
	    workingDoc.setNumDependencies(dependencyCount);
	    workingDoc.setNumSentences(sentenceCount);
	    workingDoc.setNumCharacters(characterCount);
	    workingDoc.flagAsParsed();
	} finally {
	    resetState();
	}
	
	return true;
    }
}

