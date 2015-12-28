/*
 * To change this license header, choose License Headers inPrep Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template inPrep the editor.
 */
package parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static parsing.Constants.NUM_OF_RESULTS;
import reranking.Document;

/**
 * POS tagging for the top N results for 1(!) query.
 *
 * @author Maria
 */
public class DeepParserCoreNLP {

    private String query;

    private String directoryName;
    private String endFilePath;

    private int docNum;
    private String docText;

    // all indices to highlight 
    private ArrayList<Integer> highlights; // sorted in descending order to insert highlights starting from the end of text

    private int depCount; // count dependencies - correspond to token count without punctuation
    private int sentCount; // count sentences
    private int depthCount; // count tree depthCount
    private int charCount; // count characters in words

//    private static LexicalizedParser lp;
//    private static TreebankLanguagePack tlp;
//    private static GrammaticalStructureFactory gsf;
//    private static Tree parse;
//    
    private StanfordCoreNLP pipeline;

    // documents
    private List<Document> docs;

    // constructions
    private HashMap<String, Construction> constructions;

    /**
     * Default constructor.
     */
    public DeepParserCoreNLP() {
        query = "";
        directoryName = "";
        endFilePath = "";
        docNum = 0;
        docText = "";

        docs = new ArrayList<>();
        constructions = new HashMap<>();

        initializeConstructions();
        //initializeDocs(); - they're already initialized in the search module

        pipeline = null;

    }

    /**
     * Method to be accessed from the general FLAIR pipeline
     *
     * @param query
     * @param docs
     */
    public DeepParserCoreNLP(String query, List<Document> docs) {
        this();
        this.query = query;

        //endFilePath = Constants.PATH_TO_RESULTS + "total.csv";
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
        this.pipeline = new StanfordCoreNLP(props);

        this.docs = docs;

        // take case of extra docs
        if (this.docs.size() > NUM_OF_RESULTS) {
            List<Document> tmpDocs = new ArrayList<>();
            for (int i = 0; i < NUM_OF_RESULTS; i++) {
                tmpDocs.add(this.docs.get(i));
            }
            this.docs = tmpDocs;
        } else if (this.docs.size() < NUM_OF_RESULTS) {
            System.out.println("TOO FEW DOCS");
        }

        for (Document doc : this.docs) {
            docText = "";

            resetDocCounts(); // the overall counts for docs and counts for each construction

            if (doc.getText().trim().isEmpty()) { // to make it faster?
                updateDoc();
//                writeOneDoc(doc, doc.getPreRank()); // TODO: change to getPostRank()!
                docNum++;
                continue;
            }
            crawlText(doc.getText());
            updateDoc();

            System.out.println("Writing parsed doc#" + docNum + " (initially #" + doc.getPreRank() + ")");
//            writeOneDoc(doc, doc.getPreRank()); // TODO: change to getPostRank() in the main app

            docNum++;
        }

        if (this.docs.size() != NUM_OF_RESULTS) {
            System.out.println("More/less documents than expected: " + this.docs.size() + " instead of " + NUM_OF_RESULTS);
        }

        writeIntoFiles(); // write into name_constructions.csv and name_documents.csv - for visualization

//        // update in Construction: tf, df, idf, tf-idf and avRelFrew
//        for (Entry<String, Construction> entry : this.constructions.entrySet()) {
//            Construction c = entry.getValue();
//            c.calculateDocRelFrequencies(this.docs);
//            constructions.replace(entry.getKey(), c);
//        }
//        // update in Document (for every Document in docs): tfsNorm, docLength, tfsNorm (normalized), readabilityScore, gramScore, rankWeight, totalWeight
//        for (int j = 0; j < this.docs.size(); j++) {
//            Document d = this.docs.get(j).addConstructionsToDoc(constructions, j);
//            this.docs.set(j, d);
//        }
    }

    /**
     * Constructor for 1 query.
     *
     * @param directoryPath
     * @param pipeline
     */
    public DeepParserCoreNLP(String directoryPath, StanfordCoreNLP pipeline) {
        this();
        this.pipeline = pipeline;
        this.directoryName = directoryPath.substring(directoryPath.lastIndexOf("/") + 1);
        System.out.println(directoryPath);

        endFilePath = System.getProperty("user.dir") + "/constructs/total.csv";

        File f = new File(directoryPath);
        if (f.isDirectory()) {

            File[] allFiles = f.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return !name.toLowerCase().startsWith(".");
                }
            });

            for (File aFile : allFiles) {
                docText = "";
                resetDocCounts(); // the overall counts for docs and counts for each construction
                if (aFile.length() == 0) {
                    updateDoc();
                    docNum++;
                    continue;
                }

                crawlForEverything(aFile);
                updateDoc();
                docNum++;

            }

            if (docs.size() != NUM_OF_RESULTS) {
                System.out.println("More/less that 40 documents: " + directoryPath);
                return;
            }

            // update in Construction: tf, df, idf, tf-idf and avRelFrew
            for (Entry<String, Construction> entry : this.constructions.entrySet()) {
                Construction c = entry.getValue();
                c.calculateDocRelFrequencies(docs);
                constructions.replace(entry.getKey(), c);
            }
            // update in Document (for every Document in docs): tfsNorm, docLength, tfsNorm (normalized), readabilityScore, gramScore, rankWeight, totalWeight
            for (int j = 0; j < docs.size(); j++) {
                docs.get(j).addConstructionsToDoc(constructions, j);
            }
        }

        // write constructions into files
        // write docs into files (JSON!!!)
        writeIntoFiles();
    }

    // getter and setter
    public List<Document> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<Document> docs) {
        this.docs = docs;
    }

    public HashMap<String, Construction> getConstructions() {
        return constructions;
    }

    public void setConstructions(HashMap<String, Construction> constructions) {
        this.constructions = constructions;
    }

    private void writeOneDoc(Document d, int count) {
        String dirPath = Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/parsed";
        File dir = new File(dirPath);
        dir.mkdir();

        String outfile = dirPath + "/" + String.format("%03d", count) + ".json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSONString = gson.toJson(d);

        FileWriter fw = null;
        try {
            System.out.println("> Writing into file: " + outfile);
            fw = new FileWriter(outfile);

            fw.write(JSONString);
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initialize every construction using its name. (count and tf are set to 0
     * automatically)
     */
    private void initializeConstructions() {
        // (simple) constructions 
//        constructions.put("thisIsTheseAre", new Construction("thisIsTheseAre"));
//        constructions.put("itIsAdj", new Construction("itIsAdj"));
//        constructions.put("thisIsTheseAreSimple", new Construction("thisIsTheseAreSimple"));
//        constructions.put("itIsAdjSimple", new Construction("itIsAdjSimple"));
//        constructions.put("thereIsAreSimple", new Construction("thereIsAreSimple"));
        constructions.put("existentialThere", new Construction("existentialThere"));
        constructions.put("thereIsAre", new Construction("thereIsAre"));
        constructions.put("thereWasWere", new Construction("thereWasWere"));

        // conjunctions
        constructions.put("advancedConjunctions", new Construction("advancedConjunctions"));
        constructions.put("simpleConjunctions", new Construction("simpleConjunctions"));

        // prepositions
        constructions.put("prepositions", new Construction("prepositions"));
        constructions.put("simplePrepositions", new Construction("simplePrepositions"));
        constructions.put("complexPrepositions", new Construction("complexPrepositions"));
        constructions.put("advancedPrepositions", new Construction("advancedPrepositions"));
//        constructions.put("inPrep", new Construction("inPrep"));
//        constructions.put("onPrep", new Construction("onPrep"));
//        constructions.put("atPrep", new Construction("atPrep"));
//        constructions.put("toPrep", new Construction("toPrep"));
//        constructions.put("afterPrep", new Construction("afterPrep"));
//        constructions.put("withPrep", new Construction("withPrep")); // objective

        // sentence structure
        constructions.put("subordinateClause", new Construction("subordinateClause"));
        constructions.put("relativeClause", new Construction("relativeClause"));
        constructions.put("relativeClauseReduced", new Construction("relativeClauseReduced"));
        constructions.put("adverbialClause", new Construction("adverbialClause"));
        constructions.put("simpleSentence", new Construction("simpleSentence"));
        constructions.put("complexSentence", new Construction("complexSentence"));
        constructions.put("compoundSentence", new Construction("compoundSentence"));
        constructions.put("incompleteSentence", new Construction("incompleteSentence"));

//        constructions.put("simpleSentence", new Construction("simpleSentence"));
//        constructions.put("SVOA", new Construction("SVOA")); // simple structure
//        constructions.put("coordinateClause", new Construction("coordinateClause"));
//        constructions.put("defRelClause", new Construction("defRelClause")); // defining relative clause - rcmod() dependency
//        constructions.put("nonDefRelClause", new Construction("nonDefRelClause")); // non-defining rel clause (commas? "which?")
//        constructions.put("reportedSpeech", new Construction("reportedSpeech")); // from the list of reporting verbs
        constructions.put("directObject", new Construction("directObject")); // "give me"
        constructions.put("indirectObject", new Construction("indirectObject")); // "give it toPrep me"

        // pronouns
        constructions.put("pronouns", new Construction("pronouns"));
        constructions.put("pronounsSubjective", new Construction("pronounsSubjective")); // /PRP + I, you, they...
        constructions.put("pronounsObjective", new Construction("pronounsObjective")); // /PRP + me, you, them...
        constructions.put("pronounsPossessive", new Construction("pronounsPossessive")); // /PRP$ (my, your, their)
        constructions.put("pronounsPossessiveAbsolute", new Construction("pronounsPossessiveAbsolute")); // /JJ or PRP... (mine, yours, theirs)
        constructions.put("pronounsReflexive", new Construction("pronounsReflexive")); // /PRP + myself, themselves, etc.

        // quantifiers
        constructions.put("someDet", new Construction("someDet")); // "some"
        constructions.put("anyDet", new Construction("anyDet")); // "any"
        constructions.put("muchDet", new Construction("muchDet")); // "much"
        constructions.put("manyDet", new Construction("manyDet")); // "many"
        constructions.put("aLotOfDet", new Construction("aLotOfDet")); // "a lot of" - regex
//        constructions.put("aLittleDet", new Construction("aLittleDet")); // "a little"
//        constructions.put("aFewDet", new Construction("aFewDet")); // "a few"
//        constructions.put("littleDet", new Construction("littleDet")); // "little"
//        constructions.put("fewDet", new Construction("fewDet")); // "few"

        // articles
        constructions.put("articles", new Construction("articles"));
        constructions.put("theArticle", new Construction("theArticle"));
        constructions.put("aArticle", new Construction("aArticle"));
        constructions.put("anArticle", new Construction("anArticle"));

        // forms of nouns
        constructions.put("pluralRegular", new Construction("pluralRegular"));
        constructions.put("pluralIrregular", new Construction("pluralIrregular"));
        constructions.put("ingNounForms", new Construction("ingNounForms"));

        // negation
        constructions.put("negAll", new Construction("negAll")); // nobody, nowhere, etc.
        constructions.put("partialNegation", new Construction("partialNegation")); // rarely, barely, seldom, hardly, scarcely
        constructions.put("noNotNever", new Construction("noNotNever"));
        constructions.put("nt", new Construction("nt"));
        constructions.put("not", new Construction("not"));

        // questions
        constructions.put("directQuestions", new Construction("directQuestions"));
        constructions.put("indirectQuestions", new Construction("indirectQuestions"));
        constructions.put("yesNoQuestions", new Construction("yesNoQuestions")); // direct: "Are you ok?"
        constructions.put("whQuestions", new Construction("whQuestions")); // direct: "What do you do?"
        constructions.put("toBeQuestions", new Construction("toBeQuestions")); // direct: "What's this?"
        constructions.put("toDoQuestions", new Construction("toDoQuestions")); // direct: "What do you do?"
        constructions.put("toHaveQuestions", new Construction("toHaveQuestions")); // direct: "What have you done?"
        constructions.put("modalQuestions", new Construction("modalQuestions")); // direct: "Should I go?", "What should I do?"
        constructions.put("what", new Construction("what")); // direct
        constructions.put("who", new Construction("who")); // direct
        constructions.put("how", new Construction("how")); // direct
        constructions.put("why", new Construction("why")); // direct
        constructions.put("where", new Construction("where")); // direct
        constructions.put("when", new Construction("when")); // direct
        constructions.put("whose", new Construction("whose")); // direct
        constructions.put("whom", new Construction("whom")); // direct
        constructions.put("which", new Construction("which")); // direct

        constructions.put("tagQuestions", new Construction("tagQuestions")); // ", isn't it?"

        // conditionals - check first, before tenses
        constructions.put("conditionals", new Construction("conditionals"));
//        constructions.put("cond0", new Construction("cond0"));
//        constructions.put("condI", new Construction("condI"));
//        constructions.put("condII", new Construction("condII"));
//        constructions.put("condIII", new Construction("condIII"));
//        constructions.put("condMixed", new Construction("condMixed"));
        constructions.put("condReal", new Construction("condReal"));
        constructions.put("condUnreal", new Construction("condUnreal"));

        // tenses - only if not conditional
        constructions.put("presentSimple", new Construction("presentSimple"));
        constructions.put("presentProgressive", new Construction("presentProgressive"));
        constructions.put("presentPerfect", new Construction("presentPerfect"));
        constructions.put("presentPerfProg", new Construction("presentPerfProg"));
        constructions.put("pastSimple", new Construction("pastSimple"));
        constructions.put("pastProgressive", new Construction("pastProgressive"));
        constructions.put("pastPerfect", new Construction("pastPerfect"));
        constructions.put("pastPerfProg", new Construction("pastPerfProg"));
        constructions.put("futureSimple", new Construction("futureSimple"));
        constructions.put("futureProgressive", new Construction("futureProgressive"));
        constructions.put("futurePerfect", new Construction("futurePerfect"));
        constructions.put("futurePerfProg", new Construction("futurePerfProg"));

        // aspects
        constructions.put("simpleAspect", new Construction("simpleAspect"));
        constructions.put("progressiveAspect", new Construction("progressiveAspect"));
        constructions.put("perfectAspect", new Construction("perfectAspect"));
        constructions.put("perfProgAspect", new Construction("perfProgAspect"));

        // times
        constructions.put("presentTime", new Construction("presentTime"));
        constructions.put("pastTime", new Construction("pastTime"));
        constructions.put("futureTime", new Construction("futureTime"));

        // verb constructions
        constructions.put("goingTo", new Construction("goingTo")); // "going" + "to" + VB
        //constructions.put("presentProgForFuture", new Construction("presentProgForFuture")); // "I'm traveling toPrep the US tomorrow"
        constructions.put("usedTo", new Construction("usedTo")); // "used" immediately followed by "to"

        // verb forms 
        constructions.put("shortVerbForms", new Construction("shortVerbForms")); // 's, 're, 'm, 's, 've, 'd??!
        constructions.put("longVerbForms", new Construction("longVerbForms")); // is, are, am, has, have, had??!
        constructions.put("auxiliariesBeDoHave", new Construction("auxiliariesBeDoHave")); // be, do, have??! (got?), NOT modals!!! + V
        constructions.put("copularVerbs", new Construction("copularVerbs")); // be, stay, seem, etc. - CHECK the parser
        constructions.put("ingVerbForms", new Construction("ingVerbForms")); // gerund, participle, nouns 
        constructions.put("toInfinitiveForms", new Construction("toInfinitiveForms")); // "I want toPrep do it."
        constructions.put("emphaticDo", new Construction("emphaticDo")); // "I do realize it": do/did/VBP followed by /VB
//        constructions.put("causativeForms", new Construction("causativeForms")); // [let/make/have/get + person + verb] http://www.englishpage.com/minitutorials/let.html 

        // modals
        constructions.put("modals", new Construction("modals")); // all
        constructions.put("simpleModals", new Construction("simpleModals")); // can, must, need, may
        constructions.put("advancedModals", new Construction("advancedModals")); // the others
        constructions.put("can", new Construction("can")); // Klasse 6
        constructions.put("must", new Construction("must")); // Klasse 6
        constructions.put("need", new Construction("need")); // Klasse 6
        constructions.put("may", new Construction("may")); // Klasse 6
        constructions.put("could", new Construction("could")); // Klasse 10
        constructions.put("might", new Construction("might")); // Klasse 10
        constructions.put("ought", new Construction("ought")); // Klasse 10
        constructions.put("able", new Construction("able")); // Klasse 10 (annotated as JJ)
        constructions.put("haveTo", new Construction("haveTo")); // ??

        // irregular verbs
        constructions.put("irregularVerbs", new Construction("irregularVerbs")); // past tense or past participle not ending with -ed
        constructions.put("regularVerbs", new Construction("regularVerbs")); // past tense or past participle ending with -ed

        // phrasal verbs (& verbs with prepositions: look atPrep)
        constructions.put("phrasalVerbs", new Construction("phrasalVerbs"));

        // imperatives
        constructions.put("imperatives", new Construction("imperatives")); // start with a Verb, often end with "!": "Do it yourself!"

        // passive voice
        constructions.put("passiveVoice", new Construction("passiveVoice"));
        //constructions.put("activeVoice", new Construction("activeVoice"));

        // adjectives
        constructions.put("positiveAdj", new Construction("positiveAdj")); // "nice"
        constructions.put("comparativeAdjShort", new Construction("comparativeAdjShort")); // "nicer"
        constructions.put("superlativeAdjShort", new Construction("superlativeAdjShort")); // "nicest"
        constructions.put("comparativeAdjLong", new Construction("comparativeAdjLong")); // "more beautiful"
        constructions.put("superlativeAdjLong", new Construction("superlativeAdjLong")); // "most beautiful"
        // adverbs
        constructions.put("positiveAdv", new Construction("positiveAdv")); // "quickly"
        constructions.put("comparativeAdvShort", new Construction("comparativeAdvShort")); // "faster"
        constructions.put("superlativeAdvShort", new Construction("superlativeAdvShort")); // "fastest"
        constructions.put("comparativeAdvLong", new Construction("comparativeAdvLong")); // "more quickly"
        constructions.put("superlativeAdvLong", new Construction("superlativeAdvLong")); // "most quickly"
    }

    private void initializeDocs() {
        for (int i = 0; i < 40; i++) {
            docs.add(new Document(this.query, (i + 1)));
        }
    }

    /**
     * Reset current doc counts for each construction. (anew for each doc)
     */
    private void resetDocCounts() {
        if (constructions.isEmpty()) {
            System.out.println("Nothing to reset - no constructions yet.");
            return;
        }

        for (Entry<String, Construction> entry : constructions.entrySet()) {
            String name = entry.getKey();
            Construction c = entry.getValue();
            // reset the counts for current doc
            c.setCount(0);
            // don't reset "found" variable
            constructions.replace(name, c);
        }

        // reset numWords, numSents, depthCount, charCount
        depCount = 0;
        sentCount = 0;
        depthCount = 0;
        charCount = 0;
    }

    /**
     * Update the current doc (number docNum) after it's been processed.
     */
    private void updateDoc() {
        if (constructions.isEmpty()) {
            System.out.println("Nothing to update - no constructions yet.");
            return;
        }

        // update all 
        Document d = docs.get(docNum);

        if (sentCount == 0 || depCount == 0 || charCount == 0) {

            d.setText(docText);

            d.setAvSentLength(0);
            d.setAvTreeDepth(0);
            d.setAvWordLength(0);
            d.setDocLength(0);
            d.setNumDeps(0);
            d.setNumSents(0);
            d.setNumChars(0);

            d = d.addConstructionsToDoc(constructions, docNum);

            this.docs.set(docNum, d);

            return;
        }

        d.setAvSentLength((double) depCount / (double) sentCount);
        d.setAvTreeDepth((double) depthCount / (double) sentCount);
        d.setAvWordLength((double) charCount / (double) depCount);
        d.setDocLength(depCount);
        d.setNumDeps(depCount);
        d.setNumSents(sentCount);
        d.setNumChars(charCount);

        // update the constructions
        for (Entry<String, Construction> entry : constructions.entrySet()) {
            String name = entry.getKey();
            Construction c = entry.getValue();
            // calculate and update this-doc frequencies in Construction
            c.updateConstruction(docNum, d.getNumDeps());
            constructions.replace(name, c);
        }

        d = d.addConstructionsToDoc(constructions, docNum); // TODO problem?

        this.docs.set(docNum, d);

        // ??? calculate and update gramScore for Document
    }

    // private methods
    /**
     * Count occurrences of all linguistic phenomena in this doc.
     *
     * @param aFile
     */
    private void crawlForEverything(File aFile) {

        // sentCount, wordCount, depCount, depthCount are already reset in resetDocCounts()
        System.out.println("Read file into string: " + aFile.getAbsolutePath());

        String text = "";
        FileReader fr = null;
        BufferedReader br = null;

        // read the file into String
        try {
            fr = new FileReader(aFile);
            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                text += line + "\n";
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!text.trim().isEmpty()) {
            System.out.println("Nothing in the file. Adding zeros: " + aFile.getAbsolutePath());
        } else {
            crawlText(text);
        }

    }

    private void crawlText(String text) {
        if (!text.trim().isEmpty()) {

            Annotation document = new Annotation(text);
            pipeline.annotate(document);
            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

            for (CoreMap sentence : sentences) {
                if (sentence.size() > 0) {

                    sentCount++; // count sentences 

                    Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
                    List<CoreLabel> labeledWords = sentence.get(CoreAnnotations.TokensAnnotation.class);
                    SemanticGraph semGraph = sentence.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class);
                    Collection<TypedDependency> dependencies = semGraph.typedDependencies();
                    depCount += dependencies.size(); // count dependencies
                    depthCount += tree.depth();

                    // extract gram.structures 
                    inspectSentence(tree, labeledWords, dependencies);

                }
            }
        } else { // if text is empty
            System.out.println("Nothing in the file. Adding zeros: " + text);
        }
    }

    /**
     *
     * @param subStr
     * @param str
     * @return
     */
    private int countSubstring(String subStr, String str) {
        // the result of split() will contain one more element than the delimiter
        // the "-1" second argument makes it not discard trailing empty strings
        return str.split(Pattern.quote(subStr), -1).length - 1;
    }

    /**
     *
     * @param tree
     * @param labeledWords
     * @param dependencies
     */
    private void inspectSentence(Tree tree, List<CoreLabel> labeledWords, Collection<TypedDependency> dependencies) {

        if (labeledWords == null || labeledWords.size() == 0) {
            return;
        }

        boolean indirectQuestionFound = false;
        boolean conditional_found = false; // to find a conditional clause (the order can be inverted)

        // simple/complex sentence + subordinate clauses
        String treeString = tree.toString(); // don't use toLowerCase() here
        int startInd = labeledWords.get(0).beginPosition();
        int endInd = labeledWords.get(labeledWords.size() - 1).endPosition();

        int numOfSBAR = countSubstring("SBAR ", treeString); // the whitespace is important!
        if (numOfSBAR > 0) {
            constructions.get("complexSentence").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
            for (int i = 0; i < numOfSBAR; i++) {
                constructions.get("subordinateClause").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
            }

            int withCC = countSubstring("(SBAR (WHNP ", treeString) + countSubstring("(SBAR (IN that", treeString); // with a conjunction // TODO: can be an indirect question: "She asked me what I like" -> check for reporting words in dependency?
            int withoutCC = countSubstring("(SBAR (S ", treeString); // reduced relative clauses "The man I saw" // TODO: check
            for (int i = 0; i < withCC; i++) {
                constructions.get("relativeClause").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
            }
            for (int i = 0; i < withoutCC; i++) {
                constructions.get("relativeClauseReduced").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
            }

            int numOfAdvCl = countSubstring("(SBAR (IN", treeString);
            int numOfAdvClWhether = countSubstring("(SBAR (IN whether", treeString); // indirect questions

            int numOfRelClThat = countSubstring("(SBAR (IN that", treeString); // indirect questions

            int numOfAdvClIF = countSubstring("(SBAR (IN if", treeString); // TODO check: "if" is also IN! -> an indirect question or conditional
            int numOfAdvUnless = countSubstring("(SBAR (IN unless", treeString);

            for (int i = 0; i < numOfAdvCl - numOfRelClThat - numOfAdvClIF - numOfAdvUnless; i++) { // don't include the indirect questions and conditionals
                constructions.get("adverbialClause").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
            }
            if (numOfAdvClIF > 0 || numOfAdvClWhether > 0) {
                indirectQuestionFound = true;
            }

            if (numOfAdvClIF > 0 || numOfAdvUnless > 0) {
                conditional_found = true;
            }

        } else if (numOfSBAR == 0) {
            int numOfS = countSubstring("S ", treeString); // whitespace is important!
            if (numOfS == 1) {
                // incomplete sentence (no VP or NP)
                if (!(treeString.contains("VP") && treeString.contains("NP"))) {
                    constructions.get("incompleteSentence").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
                } else {
                    constructions.get("simpleSentence").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
                }
            } else if (numOfS > 1) {
                constructions.get("compoundSentence").incrementCount(docNum, treeString, startInd, endInd);
            } else { // numOfS == 0
                constructions.get("incompleteSentence").incrementCount(docNum, treeString, startInd, endInd); // highlight the whole sentence
            }

        } else {
            System.out.println("Check method countSubstring(): yields " + numOfSBAR);
        }

        // find the first and the last word (skip bullets, quotes, etc.)
        CoreLabel firstWord = null;
        for (CoreLabel w : labeledWords) {
            if (w.word() != null && w.word().toLowerCase().matches("[a-z]*")) { // first real word
                firstWord = w;
                break;
            }
        }
        if (firstWord == null) {
            return;
        }
        // find the last token (punctuation mark) (don't take quotes into account)
        CoreLabel lastWord = null;
        for (int i = labeledWords.size() - 1; i > 0; i--) {
            if (labeledWords.get(i).tag() != null && labeledWords.get(i).tag().equalsIgnoreCase(".")) {
                lastWord = labeledWords.get(i);
                break;
            }
        }
// There might be sentences that don't end with a punctuation mark
//        if (lastWord == null) {
//            return;
//        }

        //// find questions and imperatives
        if (lastWord != null && lastWord.word() != null) {
            // questions
            if (lastWord.word().toLowerCase().endsWith("?")) { // question (not indirect!)
                // indirectQuestions - ??? not here!
                inspectQuestion(labeledWords, dependencies);
            } // imperatives
            else if (lastWord.word().toLowerCase().endsWith("!") && lastWord.word().toLowerCase().startsWith("!")) {
                // imperative - do it here in line
                if (firstWord.tag() != null && firstWord.tag().equalsIgnoreCase("vb") || firstWord.tag().equalsIgnoreCase("vbp")) {
                    constructions.get("imperatives").incrementCount(docNum, labeledWords.toString(), startInd, endInd); // highlight the whole sentence
                }
            }
        }

        // degrees of comparison of long adjectives: "more/RBR beautiful/JJ", "most/RBS beautiful/JJ"
        boolean comparativeMoreFound = false;
        boolean superlativeMostFound = false;

        boolean usedFound = false;
        int goingToFound = 0;

        // independent of type of sentence
        // go through the words (CoreLabels) for POS tags
        for (CoreLabel label : labeledWords) {
            String labelTag = label.tag().toLowerCase();
            String labelWord = label.word().toLowerCase();
            if (labelTag != null && labelWord != null && (!(labelTag.equalsIgnoreCase(".") || labelTag.equalsIgnoreCase(",")))) {
                charCount += labelWord.length();

                // "used to"
                if (usedFound) {
                    if (labelTag.equalsIgnoreCase("to")) { // allows for elliptical structures, e.g., "yes, I used to."
                        constructions.get("usedTo").incrementCount(docNum, "used " + labelWord, labeledWords.get(label.index() - 1).beginPosition(), label.endPosition());
                    }
                    usedFound = false;
                }

                // "going to"
                if (goingToFound > 0) {
                    if (goingToFound == 1) { // only "going" found
                        if (labelTag.equalsIgnoreCase("to")) {
                            goingToFound++;
                            if (label.index() < labeledWords.size() - 2 && ".,!?;:)".contains(labeledWords.get(label.index() + 1).tag())) { // catches "yes, I'm going to."
                                constructions.get("goingTo").incrementCount(docNum, "going " + labelWord, labeledWords.get(label.index() - 1).beginPosition(), label.endPosition());
                                goingToFound = 0;
                            }
                        } else {
                            goingToFound = 0;
                        }
                    } else if (goingToFound == 2) { // "going to" found
                        if (labelTag.equalsIgnoreCase("vb")) { // can tell it from "I'm going to France" // can't catch "I'm going to _slowly_ start packing"
                            constructions.get("goingTo").incrementCount(docNum, "going to " + labelWord, labeledWords.get(label.index() - 3).beginPosition(), label.endPosition()); // "going to V"
                            goingToFound = 0;
                        }
                    } else {
                        goingToFound = 0;
                        System.out.println("Too many goingToFound! Check!");
                    }
                }

                // "a lot of" quantifier
                if (labelWord.equalsIgnoreCase("lot")) {
                    int lotInd = label.index();
                    if (lotInd > 0 && lotInd < labeledWords.size() - 1 && labeledWords.get(lotInd - 1).word().equalsIgnoreCase("a") && labeledWords.get(lotInd + 1).word().equalsIgnoreCase("of")) {
                        constructions.get("aLotOfDet").incrementCount(docNum, "a " + labelWord + " of", labeledWords.get(label.index() - 1).beginPosition(), labeledWords.get(label.index() + 1).endPosition()); // "going to V"
                    }
                }

                // degrees of comparison of long adjectives: "more/RBR beautiful/JJ", "most/RBS beautiful/JJ"
                if (comparativeMoreFound) {
                    if (labelTag.equalsIgnoreCase("jj") && labelWord.length() > 5) {
                        // add comparativeAdjLong
                        constructions.get("comparativeAdjLong").incrementCount(docNum, "more " + labelWord, labeledWords.get(label.index() - 1).beginPosition(), label.endPosition());
                    } else if (labelTag.equalsIgnoreCase("rb") && labelWord.length() > 5) {
                        // add comparativeAdvLong
                        constructions.get("comparativeAdvLong").incrementCount(docNum, "more " + labelWord, labeledWords.get(label.index() - 1).beginPosition(), label.endPosition());
                    }
                    comparativeMoreFound = false;
                } else if (superlativeMostFound) {
                    if (labelTag.equalsIgnoreCase("jj") && labelWord.length() > 5) {
                        // add superlativeAdjLong
                        constructions.get("superlativeAdjLong").incrementCount(docNum, "most " + labelWord, labeledWords.get(label.index() - 1).beginPosition(), label.endPosition());
                    } else if (labelTag.equalsIgnoreCase("rb") && labelWord.length() > 5) {
                        // add superlativeAdvLong
                        constructions.get("superlativeAdvLong").incrementCount(docNum, "most " + labelWord, labeledWords.get(label.index() - 1).beginPosition(), label.endPosition());
                    }
                    superlativeMostFound = false;
                } //// pronouns (reflexive or possessive)
                else if (labelTag.equalsIgnoreCase("prp") && Constants.reflexivePronouns.contains(labelWord)) {
                    constructions.get("pronounsReflexive").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    constructions.get("pronouns").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                } else if (labelTag.equalsIgnoreCase("prp$") && Constants.possessivePronouns.contains(labelWord)) {
                    // it can actually be either possessive or objective (stanford parser crashes on that!)
                    constructions.get("pronounsPossessive").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    constructions.get("pronouns").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                } else if (Constants.possessiveAbsolutePronouns.contains(labelWord)) {
                    constructions.get("pronounsPossessiveAbsolute").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    constructions.get("pronouns").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                } // conjunctions (all or simple)
                else if (labelTag.equalsIgnoreCase("in") || labelTag.equalsIgnoreCase("cc") || labelTag.equalsIgnoreCase("rb") || labelTag.equalsIgnoreCase("wrb")) {
                    if (Constants.advancedConjunctions.contains(labelWord)) {
                        constructions.get("advancedConjunctions").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    } else if (Constants.simpleConjunctions.contains(labelWord)) {
                        constructions.get("simpleConjunctions").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    }

                    if (labelTag.equalsIgnoreCase("rb")) {
                        constructions.get("positiveAdv").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    }
                } // NOT HERE : determiners (some, any) - see dependencies
                // HERE: articles (a, an, the)
                else if (labelTag.equalsIgnoreCase("dt")) {
                    switch (labelWord) {
                        case "a":
                            constructions.get("articles").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("aArticle").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "an":
                            constructions.get("articles").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("anArticle").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "the":
                            constructions.get("articles").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("theArticle").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        default:
                            break;
                    }
                } // degrees of comparison of long adjectives and adverbs
                else if (labelTag.equalsIgnoreCase("rbr")) {
                    if (labelWord.equalsIgnoreCase("more")) {
                        comparativeMoreFound = true;
                    }
                    constructions.get("comparativeAdvShort").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition()); // "more" is counted here
                } else if (labelTag.equalsIgnoreCase("rbs")) {
                    if (labelWord.equalsIgnoreCase("most")) {
                        superlativeMostFound = true;
                    }
                    constructions.get("superlativeAdvShort").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition()); // "most" is counted here
                } // ing noun forms
                else if (labelTag.startsWith("nn")) {
                    if (labelWord.endsWith("ing") && labelWord.length() > 4 && (!Constants.ingNouns.contains(labelWord))) {
                        constructions.get("ingNounForms").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    }
                    // plural noun forms
                    if (labelTag.equalsIgnoreCase("nns")) {
                        if (labelWord.endsWith("s")) {
                            constructions.get("pluralRegular").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                        } else {
                            constructions.get("pluralIrregular").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                        }
                    }

                } 
                //// modals
                else if (labelTag.equalsIgnoreCase("md")) { // includes "will"
                    constructions.get("modals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                    switch (labelWord) {
                        case "can":
                            constructions.get("simpleModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("can").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "must":
                            constructions.get("simpleModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("must").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "need":
                            constructions.get("simpleModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("need").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "may":
                            constructions.get("simpleModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("may").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "could":
                            constructions.get("advancedModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("could").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "might":
                            constructions.get("advancedModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("might").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "ought":
                            constructions.get("advancedModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            constructions.get("ought").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        default:
                            if (!(labelWord.equalsIgnoreCase("will") || labelWord.equalsIgnoreCase("shall"))) {
                                constructions.get("advancedModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            }
                            break;
                    }
                } //// forms of afjectives 
                else if (labelTag.startsWith("jj")) {  // already in lower case
                    switch (labelTag) {
                        case "jj":
                            if (labelWord.equalsIgnoreCase("much") || labelWord.equalsIgnoreCase("many")) {
                                break;
                            } else if (labelWord.equalsIgnoreCase("able") || labelWord.equalsIgnoreCase("unable")) {
                                int ableInd = label.sentIndex();
                                if (labeledWords.size() >= ableInd && labeledWords.size() > (ableInd + 1) && labeledWords.get(ableInd + 1).tag().equalsIgnoreCase("to")) {
                                    constructions.get("able").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                                    constructions.get("advancedModals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                                    constructions.get("modals").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                                }
                                // otherwise it's an adjective: He's an able student.
                            } else {
                                // if it doesn't contain numbers
                                if (!labelWord.matches(".*\\d.*")) {
                                    constructions.get("positiveAdj").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                                }
                            }
                            break;
                        case "jjr": // "more" is covered above -> not included here
                            constructions.get("comparativeAdjShort").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        case "jjs": // "most" is covered above -> not included here
                            constructions.get("superlativeAdjShort").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            break;
                        default:
                            break;
                    }
                } //// verb forms 
                else if (labelTag.startsWith("v") || labelTag.equalsIgnoreCase("md")) { // already in lower case // include modals "will" and "would" ++
                    if (labelWord.startsWith("'")) {
                        constructions.get("shortVerbForms").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                        // do NOT add auxiiaries here (can be  main verb as well)
                    } else {
                        switch (labelWord) {
                            case "are":
                            case "is":
                            case "am":
                                constructions.get("longVerbForms").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                                // do NOT add auxiiaries here (can be  main verb as well)
                                break;
                            case "has":
                            case "have":
                            case "had":
                                constructions.get("longVerbForms").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                                // add modal "have to" here
                                int thisInd = label.index();
                                if (labeledWords.size() > thisInd + 1) {
                                    if (labeledWords.get(thisInd + 1).tag().equalsIgnoreCase("to")) { // catch the elliptical "I have to." BUT not "I will give everything I have to John"
                                        constructions.get("modals").incrementCount(docNum, labelWord + " to", label.beginPosition(), labeledWords.get(thisInd + 1).endPosition());
                                        constructions.get("advancedModals").incrementCount(docNum, labelWord + " to", label.beginPosition(), labeledWords.get(thisInd + 1).endPosition());
                                        constructions.get("haveTo").incrementCount(docNum, labelWord + " to", label.beginPosition(), labeledWords.get(thisInd + 1).endPosition());
                                    }
                                }
                                break;
                            case "used":
                                if (labelTag.equalsIgnoreCase("vbd")) { // excludes passive "it is used to"
                                    usedFound = true;
                                }
                            default:
                                break;
                        }

                        if (labelTag.equalsIgnoreCase("vbg")) {
                            constructions.get("ingVerbForms").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            if (labelWord.equalsIgnoreCase("going") || labelWord.equalsIgnoreCase("gon")) {
                                goingToFound++;
                            }
                        } else if (labelTag.equalsIgnoreCase("vbd") || labelTag.equalsIgnoreCase("vbn")) {
                            if (!labelWord.endsWith("ed")) {
                                constructions.get("irregularVerbs").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            } else {
                                constructions.get("regularVerbs").incrementCount(docNum, labelWord, label.beginPosition(), label.endPosition());
                            }
                        }

                    }

                }
            }
        }

        // go through the dependencies
        for (TypedDependency dependency : dependencies) {
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
            if (depBegin < govBegin) {
                start = depBegin;
                end = govEnd;
            } else {
                start = govBegin;
                end = depEnd;
            }
            if (start < 0 || end < 0) {
                if (rel.equalsIgnoreCase("root")) {
                    start = dep.beginPosition();
                    end = dep.endPosition();
                }
                if (start < 0 || end < 0) {
                    System.out.println("Wrong highlight indices for dependencies!: rel " + rel);
                }
            }
            //// 

            if (gov.word() == null || gov.lemma() == null || gov.tag() == null || dep.word() == null || dep.lemma() == null || dep.tag() == null) {
                continue;
            }

            // negation
            if (Constants.negation.contains(gov.word().toLowerCase()) || Constants.negation.contains(dep.word().toLowerCase())) {
                constructions.get("negAll").incrementCount(docNum, dependency.toString(), start, end);
            } else if (Constants.partialNegation.contains(gov.word().toLowerCase()) || Constants.partialNegation.contains(dep.word().toLowerCase())) {
                constructions.get("partialNegation").incrementCount(docNum, dependency.toString(), gov.beginPosition(), dep.endPosition());
            }

            //// there is / there are: expl( is-3 , there-2 )
            if (rel.toLowerCase().equalsIgnoreCase("expl") && dep.word().equalsIgnoreCase("there")) {
                constructions.get("existentialThere").incrementCount(docNum, dependency.toString(), start, end);
                if (gov.tag().equalsIgnoreCase("vbz")) {
                    constructions.get("thereIsAre").incrementCount(docNum, dependency.toString(), start, end);
                } else if (gov.tag().equalsIgnoreCase("vbd")) {
                    constructions.get("thereWasWere").incrementCount(docNum, dependency.toString(), start, end);
                }

            } //// prepositions (incl. simple)
            else if (rel.toLowerCase().startsWith("prep")) {

                if (rel.contains("_")) { // collapsed dependencies

                    int startPrep = -1;
                    int endPrep = -1;

                    // identify the start and end indices of this preposition/these prepositions
                    if (rel.indexOf("_") == rel.lastIndexOf("_")) { // 1-word preposition
                        String prep = rel.substring(rel.indexOf("_") + 1).toLowerCase();

                        // go through labeledWords to find the indices
                        for (CoreLabel l : labeledWords) {

                            if (l.word().equalsIgnoreCase(prep)) {

                                startPrep = l.beginPosition();
                                endPrep = l.endPosition();

                                // add constructions here: in case of a duplicate, it'll not be added anyway
                                if (l.tag().equalsIgnoreCase("in") && Constants.simplePrepositions.contains(prep)) {
                                    constructions.get("simplePrepositions").incrementCount(docNum, dependency.toString(), startPrep, endPrep);
                                    constructions.get("prepositions").incrementCount(docNum, dependency.toString(), startPrep, endPrep);
                                } else {
                                    constructions.get("advancedPrepositions").incrementCount(docNum, dependency.toString(), startPrep, endPrep);
                                    constructions.get("prepositions").incrementCount(docNum, dependency.toString(), startPrep, endPrep);
                                }
                            }
                        }

                    } else if (rel.indexOf("_") > 0 && rel.indexOf("_") < rel.lastIndexOf("_")) { // n-word preposition

                        // take the begin poition of the 1st prep and the end position of the last prep
                        String prep1 = rel.substring(rel.indexOf("_") + 1, rel.indexOf("_", rel.indexOf("_") + 1));
                        String prep2 = rel.substring(rel.lastIndexOf("_") + 1);

                        for (CoreLabel l : labeledWords) {
                            if (l.word().equalsIgnoreCase(prep1)) {
                                // at first find the first prep - don't add constructions yet 
                                startPrep = l.beginPosition();
                            } else if (l.tag().equalsIgnoreCase("in") && l.word().equalsIgnoreCase(prep2)) {
                                endPrep = l.endPosition();
                                if (startPrep > -1 && endPrep > -1 && endPrep > startPrep) {
                                    // add constructions here: in case of a duplicate, it'll not be added anyway
                                    constructions.get("complexPrepositions").incrementCount(docNum, dependency.toString(), startPrep, endPrep);
                                    constructions.get("prepositions").incrementCount(docNum, dependency.toString(), startPrep, endPrep);
                                }
                            }
                        }

                    }
                }
                /// objective pronouns after a preposition
                if (dep.tag().equalsIgnoreCase("prp") && Constants.objectivePronouns.contains(dep.word().toLowerCase())) {
                    constructions.get("pronounsObjective").incrementCount(docNum, dep.word(), depBegin, depEnd);
                    constructions.get("pronouns").incrementCount(docNum, dep.word(), depBegin, depEnd);
                }
            } //// objective and subjective pronouns
            else if (rel.equalsIgnoreCase("iobj")) { // indirect object
                constructions.get("indirectObject").incrementCount(docNum, dependency.toString(), start, end);
                if (dep.tag().equalsIgnoreCase("prp") && Constants.objectivePronouns.contains(dep.word().toLowerCase())) {
                    constructions.get("pronounsObjective").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    constructions.get("pronouns").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                }
            } else if (rel.equalsIgnoreCase("dobj")) { // direct object
                constructions.get("directObject").incrementCount(docNum, dependency.toString(), start, end);
                if (dep.tag().equalsIgnoreCase("prp") && Constants.objectivePronouns.contains(dep.word().toLowerCase())) {
                    constructions.get("pronounsObjective").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    constructions.get("pronouns").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                }
            } else if (rel.equalsIgnoreCase("nsubj")) { // subjective pronouns
                if (dep.tag().equalsIgnoreCase("prp") && Constants.subjectivePronouns.contains(dep.word().toLowerCase())) {
                    constructions.get("pronounsSubjective").incrementCount(docNum, dep.word(), depBegin, depEnd);
                    constructions.get("pronouns").incrementCount(docNum, dep.word(), depBegin, depEnd);
                }
            } //// negation (noNotNever, nt, not)
            else if (rel.equalsIgnoreCase("neg")) {
                constructions.get("noNotNever").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                constructions.get("negAll").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                if (dep.word().equalsIgnoreCase("n't")) {
                    constructions.get("nt").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                } else if (dep.word().equalsIgnoreCase("not")) {
                    constructions.get("not").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                }
            } // determinets some, any
            else if (rel.equalsIgnoreCase("det")) {
                if (dep.lemma().equalsIgnoreCase("some")) {
                    constructions.get("someDet").incrementCount(docNum, dependency.toString(), dep.beginPosition(), gov.endPosition());
                } else if (dep.lemma().equalsIgnoreCase("any")) {
                    constructions.get("anyDet").incrementCount(docNum, dependency.toString(), dep.beginPosition(), gov.endPosition());
                }
            } else if (rel.equalsIgnoreCase("amod")) {
                if (dep.lemma().equalsIgnoreCase("many")) {
                    constructions.get("manyDet").incrementCount(docNum, dependency.toString(), dep.beginPosition(), gov.endPosition());
                } else if (dep.lemma().equalsIgnoreCase("much")) {
                    constructions.get("muchDet").incrementCount(docNum, dependency.toString(), dep.beginPosition(), gov.endPosition());
                }
            } //// verb forms
            else if (rel.equalsIgnoreCase("aux")) {
                // emphatic do (separately)
                if (dep.lemma().equalsIgnoreCase("do") && gov.tag().equalsIgnoreCase("vb") && (dep.index() == gov.index() - 1)) { // "do" is followed by a verb
                    constructions.get("emphaticDo").incrementCount(docNum, dependency.toString(), start, end);
                } else if (dep.lemma().equalsIgnoreCase("be") || dep.lemma().equalsIgnoreCase("have") || dep.lemma().equalsIgnoreCase("do")) {
                    constructions.get("auxiliariesBeDoHave").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                }

                // to-infinitives
                if (dep.tag().equalsIgnoreCase("to")) {
                    if (gov.tag().equalsIgnoreCase("vb")) {
                        // "He wants to sleep"
                        constructions.get("toInfinitiveForms").incrementCount(docNum, dependency.toString(), start, end);
                    } else {
                        // "He wants to be a pilot" -> aux ( pilot-6 *!!!* , to-3 ) + cop ( pilot-6 , be-4 ) ** only with "be" as copula **
                        int toInd = dep.index();
                        int govInd = gov.index();
                        if (govInd - toInd > 1 && labeledWords.get(toInd + 1).word().equalsIgnoreCase("be")) {
                            constructions.get("toInfinitiveForms").incrementCount(docNum, dependency.toString(), dep.beginPosition(), labeledWords.get(toInd + 1).endPosition());
                        }
                    }
                }
            } else if (rel.equalsIgnoreCase("cop")) {
                constructions.get("copularVerbs").incrementCount(docNum, dependency.toString(), start, end);
            } // phrasal verbs
            else if (rel.equalsIgnoreCase("prt")) {
                constructions.get("phrasalVerbs").incrementCount(docNum, dependency.toString(), start, end);
            }

        }

        //// second round for: conditionals
        if (conditional_found) {
            identifyConditionals(labeledWords, dependencies);
        } // look for tenses only 'outside' of conditionals!
        // after the first iteration over all dependencies 
        else { // if conditional not found -> look for tenses/times/aspects/voice
            identifyTenses(dependencies);
        }
    }

    /**
     *
     * @param tree
     * @param labeledWords
     * @param dependencies
     */
    private void identifyTenses(Collection<TypedDependency> dependencies) {
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

        for (TypedDependency dependency : dependencies) {
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
            if (depBegin < govBegin) {
                start = depBegin;
                end = govEnd;
            } else {
                start = govBegin;
                end = depEnd;
            }
            if (start < 0 || end < 0) {
                if (rel.equalsIgnoreCase("root")) {
                    start = dep.beginPosition();
                    end = dep.endPosition();
                }
                if (start < 0 || end < 0) {
                    System.out.println("Wrong highlight indices for dependencies - identifyTenses!");
                }
            }
            //// 

            // first examine the root (the main verb phrase)
            if (rel.equalsIgnoreCase("root") && verbIndex != dep.index()) { // roughly identify the aspect (any Time)
                if (dep.tag().toLowerCase().startsWith("v")) {
                    verbIndex = dep.index();
                    theVerb = dep;
                }

                if (dep.tag().equalsIgnoreCase("vbg")) { // check for Progressive aspect
                    // can still be: Present/Past/Future Progressive, Present/Past/Future Perfect Progressive
                    ingFound = true;
                    pastPartFound = false;
                    baseFormFound = false;
                } else if (dep.tag().equalsIgnoreCase("vbn")) { // check for Perfect aspect
                    // can still be: Past Perfect, Present Perfect, Future Perfect, Passive
                    pastPartFound = true;
                    ingFound = false;
                    baseFormFound = false;
                } else if (dep.tag().equalsIgnoreCase("vb")) { // base form
                    // can still be: Past Simple (negation), Future Simple, Present Simple (negation)
                    // cannot be Present Simple : VBP or VBZ instead
                    // examples: I didn't do it // I will do it // I don't know
                    constructions.get("simpleAspect").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    baseFormFound = true;
                    ingFound = false;
                    pastPartFound = false;
                } else if (dep.tag().equalsIgnoreCase("vbp") || dep.tag().equalsIgnoreCase("vbz")) { // single or plural present
                    constructions.get("simpleAspect").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    constructions.get("presentTime").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    constructions.get("presentSimple").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    ingFound = false;
                    pastPartFound = false;
                    baseFormFound = false;
                } else if (dep.tag().equalsIgnoreCase("vbd")) { // past form
                    constructions.get("simpleAspect").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    constructions.get("pastTime").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    constructions.get("pastSimple").incrementCount(docNum, dependency.toString(), depBegin, depEnd);
                    ingFound = false;
                    pastPartFound = false;
                    baseFormFound = false;
                }
            } // don't only look at the root, find other verb phrases
            else if ((rel.equalsIgnoreCase("nsubj") || rel.equalsIgnoreCase("nsubjpass")) && gov.tag().toLowerCase().startsWith("v") && gov.index() != verbIndex) { // != condition: don't count 1 verb twice: as root() and nsubj()
                verbIndex = gov.index();
                theVerb = gov;
                if (gov.tag().equalsIgnoreCase("vbg")) { // check for Progressive aspect
                    // can still be: Present/Past/Future Progressive, Present/Past/Future Perfect Progressive
                    ingFound = true;
                    pastPartFound = false;
                    baseFormFound = false;
                } else if (gov.tag().equalsIgnoreCase("vbn")) { // check for Perfect aspect
                    // can still be: Past Perfect, Present Perfect, Future Perfect, Passive
                    ingFound = false;
                    pastPartFound = true;
                    baseFormFound = false;
                } else if (gov.tag().equalsIgnoreCase("vb")) { // base form
                    // can still be: Past Simple (negation), Future Simple, Present Simple (negation)
                    // cannot be Present Simple : VBP or VBZ instead
                    // examples: I didn't do it // I will do it // I don't know
                    constructions.get("simpleAspect").incrementCount(docNum, dependency.toString(), govBegin, govEnd);
                    ingFound = false;
                    pastPartFound = false;
                    baseFormFound = true;
                } else if (gov.tag().equalsIgnoreCase("vbp") || gov.tag().equalsIgnoreCase("vbz")) { // single or plural present
                    constructions.get("simpleAspect").incrementCount(docNum, dependency.toString(), govBegin, govEnd);
                    constructions.get("presentTime").incrementCount(docNum, dependency.toString(), govBegin, govEnd);
                    constructions.get("presentSimple").incrementCount(docNum, dependency.toString(), govBegin, govEnd);
                    ingFound = false;
                    pastPartFound = false;
                    baseFormFound = false;
                } else if (gov.tag().equalsIgnoreCase("vbd")) { // past form
                    constructions.get("simpleAspect").incrementCount(docNum, dependency.toString(), govBegin, govEnd);
                    constructions.get("pastTime").incrementCount(docNum, dependency.toString(), govBegin, govEnd);
                    constructions.get("pastSimple").incrementCount(docNum, dependency.toString(), govBegin, govEnd);
                    ingFound = false;
                    pastPartFound = false;
                    baseFormFound = false;
                }
            } else if (theVerb != null && verbIndex > 0 && rel.equalsIgnoreCase("aux")) {

                if (dep.word().equalsIgnoreCase("will") && (!gov.tag().toLowerCase().startsWith("v"))) {
                    willCopulaFound = true;
                }

                // simple Aspect
                if (baseFormFound && verbIndex == gov.index()) {
                    // ! simpleAspect - already added above
                    // future
                    if (dep.word().equalsIgnoreCase("will") || (dep.word().equalsIgnoreCase("shall") && dep.index() > 1)) {
                        // Future Simple 
                        constructions.get("futureTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                        constructions.get("futureSimple").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                    } // past (negation or question)
                    else if (dep.word().equalsIgnoreCase("did")) {
                        // Past Simple negation or question
                        constructions.get("pastTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                        constructions.get("pastSimple").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                    } // present (negation or question)
                    // incl. emphatic do
                    else if (dep.word().equalsIgnoreCase("do") || dep.word().equalsIgnoreCase("does")) {  // no modals!
                        // Present Simple negation or question
                        constructions.get("presentTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                        constructions.get("presentSimple").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                    }
                    baseFormFound = false;
                    verbIndex = -1; // tense found - Future/Past/Present Simple
                    theVerb = null;
                }

                //// Perfect aspect
                // after aux(done,will) is found: Future Perfect, Passive
                if (willDoneFound) {
                    if (dep.word().equalsIgnoreCase("have")) { // Future Perfect, Passive (perfect)
                        willHaveDoneFound = true;
                    } // Passive (simple): just in case: normally under auxpass() dependency
                    else if (dep.word().equalsIgnoreCase("be") || dep.word().equalsIgnoreCase("get")) {
                        constructions.get("passiveVoice").incrementCount(docNum, dependency.toString(), start, end);
                    }
                    willDoneFound = false;
                } // after willHaveDoneFound is found : Future Perfect, Passive (perfect)
                else if (theVerb != null && willHaveDoneFound) {
                    // Passive (perfect): just in case: normally under auxpass() dependency
                    if (dep.word().equalsIgnoreCase("been")) {
                        constructions.get("passiveVoice").incrementCount(docNum, dependency.toString(), start, end);
                    } else {
                        constructions.get("futureTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                        constructions.get("perfectAspect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                        constructions.get("futurePerfect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                    }
                    willHaveDoneFound = false;
                    // TODO: check if it should be here
                    pastPartFound = false;
                    verbIndex = -1;
                    theVerb = null;
                } // after aux(done,had) is found : Past Perfect, Passive 
                // but we're at aux() dependency now: in case of Past Perfect, we wouldn't be here
                else if (hadDoneFound) {
                    // Passive (perfect) : just in case: normally under auxpass() dependency
                    if (dep.word().equalsIgnoreCase("been") || dep.word().equalsIgnoreCase("got") || dep.word().equalsIgnoreCase("gotten")) {
                        constructions.get("passiveVoice").incrementCount(docNum, dependency.toString(), start, end);
                    }
                } // after aux(done,have/has) is found : Present Perfect, Passive
                // but we're at aux() dependency now: in case of Present Perfect, we wouldn't be here
                else if (haveHasDoneFound) {
                    // Passive (perfect) : just in case: normally under auxpass() dependency
                    if (dep.word().equalsIgnoreCase("been") || dep.word().equalsIgnoreCase("got") || dep.word().equalsIgnoreCase("gotten")) {
                        constructions.get("passiveVoice").incrementCount(docNum, dependency.toString(), start, end);
                    }
                } // right after "done" as main verb is found
                // old version : "else if" instead of "if"
                else if (pastPartFound && (!(willDoneFound || hadDoneFound || haveHasDoneFound)) && gov.index() == verbIndex) {
                    // future
                    if (dep.word().equalsIgnoreCase("will") || (dep.word().equalsIgnoreCase("shall") && dep.index() > 1)) {
                        // can still be: Future Perfect, Passive 
                        willDoneFound = true;
                    } // past
                    else if (dep.word().equalsIgnoreCase("had")) {
                        // can still be: Past Perfect, Passive
                        hadDoneFound = true;
                    } // present
                    else if (dep.word().equalsIgnoreCase("have") || dep.word().equalsIgnoreCase("has")) {  // no modals!
                        // can still be: Present Perfect, Passive
                        haveHasDoneFound = true;
                    }
                    //pastPartFound = false;
                }

                //// Progressive aspect : Present/Past/Future Progressive, Present/Past/Future Perfect Progressive,
                if (theVerb != null & ingFound && gov.index() == verbIndex) {

                    if (willDoingFound) {
                        if (dep.word().equalsIgnoreCase("be")) {
                            // Future Progressive
                            constructions.get("progressiveAspect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("futureProgressive").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                        } else if (dep.word().equalsIgnoreCase("have")) {
                            constructions.get("perfProgAspect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("futurePerfProg").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                        }
                        willDoingFound = false;
                        verbIndex = -1;
                        theVerb = null;
                    }

                    if (theVerb != null) {
                        if (dep.word().equalsIgnoreCase("will")) {
                            // Future Progressive, Future Perfect Progressive
                            constructions.get("futureTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            willDoingFound = true;
                        } else if (dep.word().equalsIgnoreCase("am") || dep.word().equalsIgnoreCase("are") || dep.word().equalsIgnoreCase("is")) {
                            // Present Progressive
                            constructions.get("presentTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("progressiveAspect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("presentProgressive").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            verbIndex = -1;
                            theVerb = null;
                        } else if (dep.word().equalsIgnoreCase("was") || dep.word().equalsIgnoreCase("were")) {
                            // Past Progressive
                            constructions.get("pastTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("progressiveAspect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("pastProgressive").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            verbIndex = -1;
                            theVerb = null;
                        } else if ((!willDoingFound) && (dep.word().equalsIgnoreCase("have") || dep.word().equalsIgnoreCase("has"))) {
                            // Present Perfect Progressive
                            constructions.get("presentTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("perfProgAspect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("presentPerfProg").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            verbIndex = -1;
                            theVerb = null;
                        } else if ((!willDoingFound) && (dep.word().equalsIgnoreCase("had"))) {
                            // Past Perfect Progressive
                            constructions.get("pastTime").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("perfProgAspect").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            constructions.get("pastPerfProg").incrementCount(docNum, dependency.toString(), depBegin, theVerb.endPosition());
                            verbIndex = -1;
                            theVerb = null;
                        }
                    }
                    ingFound = false;
                }

//                    // if the sentence contains "will" or "shall"(but not as the first word: "Shall we go?")
//                    if (dep.word().equalsIgnoreCase("will") || (dep.word().equalsIgnoreCase("shall") && dep.index() > 1)) {
//                        futureFound = true;
//                        constructions.get("futureTime").incrementCount(docNum, dependency.toString());
//                        if (gov.tag().equalsIgnoreCase("vbg")) { // check for Progressive aspect
//                            // still can be Perfect Progressive
//                            ingFound = true;
//                            ingVerbIndex = gov.index();
//                        } else if (gov.tag().equalsIgnoreCase("vbn")) { // check for Perfect aspect
//                            // still can be Passive
//                            pastPartFound = true;
//                            pastPartIndex = gov.index();
//                        } // Future Simple (the only case)
//                        else if (gov.tag().equalsIgnoreCase("vb")) {
//                            constructions.get("futureSimple").incrementCount(docNum, dependency.toString());
//                            baseVerbIndices.add(gov.index()); // add the verb index to the list not to count it as Present Simple later
//                        }
//                    } 
            } // passive
            else if (theVerb != null && pastPartFound && verbIndex > 0 && rel.equalsIgnoreCase("auxpass") && gov.index() == verbIndex) {
                // only with verb==root! change!
                constructions.get("passiveVoice").incrementCount(docNum, dependency.toString(), start, end);
                pastPartFound = false;
                verbIndex = -1;
                theVerb = null;
                if (haveHasDoneFound) {
                    haveHasDoneFound = false;
                }
                if (hadDoneFound) {
                    hadDoneFound = false;
                }
                if (willHaveDoneFound) {
                    willHaveDoneFound = false;
                }
            } // TODO: check the start of highlight!!
            else if (theVerb != null && verbIndex > 0 && haveHasDoneFound && (!rel.equalsIgnoreCase("auxpass"))) {
                // Present Perfect
                constructions.get("presentTime").incrementCount(docNum, dependency.toString(), theVerb.beginPosition(), theVerb.endPosition());
                constructions.get("perfectAspect").incrementCount(docNum, dependency.toString(), theVerb.beginPosition(), theVerb.endPosition());
                constructions.get("presentPerfect").incrementCount(docNum, dependency.toString(), theVerb.beginPosition(), theVerb.endPosition());
                haveHasDoneFound = false;
                verbIndex = -1;
                theVerb = null;
            } else if (theVerb != null && verbIndex > 0 && hadDoneFound && (!rel.equalsIgnoreCase("auxpass"))) {
                // Past Perfect
                constructions.get("pastTime").incrementCount(docNum, dependency.toString(), theVerb.beginPosition(), theVerb.endPosition());
                constructions.get("perfectAspect").incrementCount(docNum, dependency.toString(), theVerb.beginPosition(), theVerb.endPosition());
                constructions.get("pastPerfect").incrementCount(docNum, dependency.toString(), theVerb.beginPosition(), theVerb.endPosition());
                hadDoneFound = false;
                verbIndex = -1;
                theVerb = null;
            } else if (rel.equalsIgnoreCase("cop")) {
                constructions.get("simpleAspect").incrementCount(docNum, dependency.toString(), start, end);
                switch (dep.tag().toLowerCase()) {
                    case "vbp":
                    case "vbz":
                        constructions.get("presentSimple").incrementCount(docNum, dependency.toString(), start, end);
                        constructions.get("presentTime").incrementCount(docNum, dependency.toString(), start, end);
                        break;
                    case "vbd":
                        constructions.get("pastSimple").incrementCount(docNum, dependency.toString(), start, end);
                        constructions.get("pastTime").incrementCount(docNum, dependency.toString(), start, end);
                        break;
                    case "vb":
                        if (willCopulaFound) {
                            constructions.get("futureSimple").incrementCount(docNum, dependency.toString(), start, end);
                            constructions.get("futureTime").incrementCount(docNum, dependency.toString(), start, end);
                        }
                        willCopulaFound = false;
                    default:
                        break;
                }
            }

        }
    }

    /**
     * Identify conditionals in case a conditional marker has already been
     * found.
     *
     * @param tree
     * @param labeledWords
     * @param dependencies
     */
    private void identifyConditionals(List<CoreLabel> labeledWords, Collection<TypedDependency> dependencies) {
        int verbBegin = -1;
        int verbInd = -1;
        int continuousInd = -1;
        boolean continuous = false;
        boolean mark_found = false;
        boolean would_found = false;
        int startInd = labeledWords.get(0).beginPosition();
        int endInd = labeledWords.get(labeledWords.size() - 1).endPosition();

        for (TypedDependency dependency : dependencies) {
            String rel = dependency.reln().getShortName().toLowerCase(); // dependency relation
            IndexedWord gov = dependency.gov();
            IndexedWord dep = dependency.dep();

            if ((dep.word() != null) && (gov.tag() != null)) {
                // retrieve all real conditionals using the conditional 'if' clause
                if (rel.equalsIgnoreCase("mark")) {
                    verbBegin = gov.beginPosition(); // mark(do-2,if-1)
                    verbInd = gov.index();
                    // e.g. If they call, ... // If it rains ... // If I should go ... 
                    // but: "If you didn't have a child now ..." -> mark(have,if) :/ - adds to condReal imediately, skips the would-clause
                    // TODO: but: "I don't know if he knows it." - treated as conditional!
                    if (gov.tag().equalsIgnoreCase("vb") || gov.tag().equalsIgnoreCase("vbp") || gov.tag().equalsIgnoreCase("vbz") || gov.tag().equalsIgnoreCase("should")) {
                        // fetch: "If you didn't have a child now, you wouldn't..." as well as "If you did know, you would..."
                        // i.e. don't skip the would-clause
                        if (!((verbInd >= 2 && labeledWords.get(verbInd - 2).word().equalsIgnoreCase("did")) || ((verbInd >= 3) && labeledWords.get(verbInd - 3).word().equalsIgnoreCase("did")))) {
                            constructions.get("condReal").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            constructions.get("conditionals").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break; // don't look at the 'would' clause 
                        }
                    } else if (gov.tag().equalsIgnoreCase("vbg")) { // continuous tense
                        continuous = true;
                        continuousInd = verbInd;
                    }
                    mark_found = true;
                } // retrieve all unreal conditionals using the 'would' clause
                else if (rel.equals("aux")) { // aux(watched-4, would-2) // aux(travel-4, to-3)

                    // conditionals
                    if (dep.word().equalsIgnoreCase("would") // or only would?
                            || dep.word().equalsIgnoreCase("should")
                            || dep.word().equalsIgnoreCase("could")
                            || dep.word().equalsIgnoreCase("might")
                            || dep.word().equalsIgnoreCase("may")) {
                        would_found = true;
                        if (gov.tag().equalsIgnoreCase("VBN")) { // past participle form: "would have gone"
                            //constructions.get("condIII").incrementCount(docNum, labeledWords.toString());
                            constructions.get("condUnreal").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            constructions.get("conditionals").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break; // with fetch only 1 conditional per sentence - which is fine
                            // but: (won't catch mixedConditionals: If you had done it, you wouldn't regret it now.)
                            // TODO
                        } else if (gov.tag().equalsIgnoreCase("VB")) { // base form: "would go"
                            //constructions.get("condII").incrementCount(docNum, labeledWords.toString());
                            constructions.get("condUnreal").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            constructions.get("conditionals").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break; // with fetch only 1 conditional per sentence - which is fine
                            // but: (won't catch mixedConditionals: If you had done it, you wouldn't regret it now.)
                            // TODO
                        }
                    } //  fetch Present Perfect: "If you have lost...", "If he's won..."
                    else if ((dep.tag().equalsIgnoreCase("vb") || dep.tag().equalsIgnoreCase("vbp") || dep.tag().equalsIgnoreCase("vbz")) && (dep.lemma().equalsIgnoreCase("have"))) {
                        if (gov.beginPosition() == verbBegin) {
                            mark_found = false;
                            constructions.get("condReal").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            constructions.get("conditionals").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break; // don't look at the 'would' clause 
                        }
                    } // fetch: continuous (present and past)
                    else if (continuous && (gov.index() == continuousInd)) {
                        if (dep.tag().equalsIgnoreCase("vbd") || dep.tag().equalsIgnoreCase("vbn")) { // past
                            mark_found = false;
                            constructions.get("condUnreal").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            constructions.get("conditionals").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break;
                        } else if (dep.tag().equalsIgnoreCase("vb") || dep.tag().equalsIgnoreCase("vbp") || dep.tag().equalsIgnoreCase("vbz")) { // present
                            mark_found = false;
                            constructions.get("condReal").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            constructions.get("conditionals").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break;
                        }
                    }
                }
            }
        }
        // fetch past real conditional: "If you didn't go there, you didn't see him"
        if (mark_found && !would_found) {
            constructions.get("condReal").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
            constructions.get("conditionals").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
        } else if (continuous) {
            // TODO ???
        }
    }

    /**
     * Identify the type of question.
     *
     * @param tree
     * @param labeledWords
     * @param dependencies
     */
    private void inspectQuestion(List<CoreLabel> labeledWords, Collection<TypedDependency> dependencies) {
        int startInd = labeledWords.get(0).beginPosition();
        int endInd = labeledWords.get(labeledWords.size() - 1).endPosition();

        //// direct question
        constructions.get("directQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);

        CoreLabel firstWord = null;
        for (CoreLabel w : labeledWords) {
            if (w.word() != null && w.word().toLowerCase().matches("[a-z]*")) { // first real word
                firstWord = w;
                break;
            }
        }
        if (firstWord == null) {
            return;
        } // if there are no words?
        String firstWordTag = firstWord.tag();
        String firstWordWord = firstWord.word().toLowerCase();

        //// tag questions
        // look at the word right before the "?" 
        CoreLabel lastWord = null;
        CoreLabel qMark = null; // it must be there
        int count = 0; // count the words between ? and ,
        for (int i = labeledWords.size() - 1; i > 0; i--) {
            CoreLabel label = labeledWords.get(i);
            String tag = label.tag();
            String word = label.word().toLowerCase();
            if (qMark == null && tag.equalsIgnoreCase(".") && word.contains("?")) {
                qMark = label;
                count++;
            } else if (qMark != null && (tag.equalsIgnoreCase("prp") || word.equalsIgnoreCase("not"))) { // check for the pronoun or the negation (do they not?)
                lastWord = label;
                count++;
            } else if (lastWord != null && (!tag.equalsIgnoreCase(","))) { // check for the negation or for the verb
                count++;
            } else if (lastWord != null && tag.equalsIgnoreCase(",")) {
                if (count < 5) {
                    constructions.get("tagQuestions").incrementCount(docNum, labeledWords.toString(), label.beginPosition(), endInd); // highlight only the tag
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //// wh-question
        if (firstWordTag.toLowerCase().startsWith("w")) {

            // only for wh-questions
            boolean rootFound = false;
            // in case there is a question phrase at the beginning ("What kind of music do you like?") - go from the ROOT to find the verb
            //  toBeQuestions; toDoQuestions; toHaveQuestions // direct: "What's this?" - look into the dependencies: the root should be a verb -> aux(verb, ??)
            int depStartInd = 0;
            for (TypedDependency dependency : dependencies) {
                String rel = dependency.reln().getShortName();
                IndexedWord dep = dependency.dep();
                IndexedWord gov = dependency.gov();

                if ((!rootFound) && rel.equalsIgnoreCase("root")) {
                    rootFound = true;
                    depStartInd = dep.beginPosition();
                }
                if (rootFound && (rel.equalsIgnoreCase("aux") || rel.equalsIgnoreCase("cop") || rel.equalsIgnoreCase("auxpass")) && (dep.beginPosition() == depStartInd || dependency.gov().beginPosition() == depStartInd)) {

                    // check the dep of aux()
                    if (dep.lemma().equalsIgnoreCase("be") || gov.lemma().equalsIgnoreCase("be")) { // it might not get here
                        addWhQuestion(firstWordWord, startInd, endInd);
                        constructions.get("toBeQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                        break;
                    } else if (dep.lemma().equalsIgnoreCase("do") || gov.lemma().equalsIgnoreCase("do")) {
                        addWhQuestion(firstWordWord, startInd, endInd);
                        constructions.get("toDoQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                        break;
                    } else if (dep.lemma().equalsIgnoreCase("have") || gov.lemma().equalsIgnoreCase("have")) {
                        addWhQuestion(firstWordWord, startInd, endInd);
                        constructions.get("toHaveQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                        break;
                    }
                    break;
                }
            }

        } //// yesNoQuestions (starts with a verb (or modal)) 
        else if (firstWordTag.toLowerCase().startsWith("vb")) {
            if (firstWord.lemma().equalsIgnoreCase("be")) { //  toBeQuestions;
                constructions.get("yesNoQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                constructions.get("toBeQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
            } else if (firstWord.lemma().equalsIgnoreCase("do")) { // simple
                constructions.get("yesNoQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                constructions.get("toDoQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
            } else if (firstWord.lemma().equalsIgnoreCase("have")) { // perfect
                constructions.get("yesNoQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                constructions.get("toHaveQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
            }
        } else if (firstWordTag.toLowerCase().contains("md")) {
            constructions.get("modalQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
            constructions.get("yesNoQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
        } else { // it can still be the case that it is a wh- or yes-no question but it doesn't start at the beginning of a sentence
            // e.g.: That person has to wonder 'What did I do wrong?' 
            // TODO: At ..., _do_ you know _what_ I mean?" 
            CoreLabel questionWord = null;
            for (CoreLabel aWord : labeledWords) {
                // look for a question word
                if ((questionWord == null) && (aWord.tag().toLowerCase().startsWith("w"))) {
                    questionWord = aWord;
                } // if question word found
                else if (questionWord != null) {
                    if (aWord.tag().toLowerCase().startsWith("vb")) {
                        if (aWord.lemma().equalsIgnoreCase("be")) { //  toBeQuestions;
                            addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
                            constructions.get("toBeQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break;
                        } else if (aWord.lemma().equalsIgnoreCase("do")) { // simple
                            addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
                            constructions.get("toDoQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break;
                        } else if (aWord.lemma().equalsIgnoreCase("have")) { // perfect
                            addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
                            constructions.get("toHaveQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                            break;
                        }
                    } else if (aWord.tag().toLowerCase().startsWith("md")) {
                        addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
                        constructions.get("modalQuestions").incrementCount(docNum, labeledWords.toString(), startInd, endInd);
                    }
                }
            }
        }
    }

    /**
     * Identify the type of wh- question.
     *
     * @param qWord
     * @param endInd endPosition of last word in the sentense
     */
    private void addWhQuestion(String qWord, int startInd, int endInd) {
        constructions.get("whQuestions").incrementCount(docNum, qWord, startInd, endInd);

        if (Constants.questionWords.contains(qWord)) {
            constructions.get(qWord.toLowerCase()).incrementCount(docNum, qWord, startInd, endInd);
        } else {
            System.out.println("Unknown wh- word in document " + docNum + ": " + qWord);
        }
    }

    /**
     * Write Docs into JSON files!!! Use GSON to efficiently read .json files. -
     * with URLs! Write Constructions csv files for storing the frequencies!
     */
    private void writeIntoFiles() {

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            System.out.println("\n\nWriting constructions into file...\n");
            File pathToFile = new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/all_constructions.csv");

            System.out.println(pathToFile.getAbsolutePath());

            fw = new FileWriter(pathToFile, true);//don't overwrite
            bw = new BufferedWriter(fw);
            // write all Constructions as lines into a file
            // header
            String header = "construction,";
            for (int i = 0; i < NUM_OF_RESULTS; i++) {
                header += i + 1 + ",";
            }
            header = header.substring(0, header.length() - 1); // get rid of the last comma
            // write the header into csv file
            bw.write(header);
            bw.newLine();
            System.out.println(header);

            ///// write each construction into a separate file (debugging)
            // json files for constructions
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fwJSON = null;

            for (Entry<String, Construction> entry : constructions.entrySet()) {
                // print first
                if (entry.getValue().isFound()) { // if it has occurred at least in one doc

                    // calculate relative frequencies for each construction, df and idf
                    Construction construct = entry.getValue();
                    int df = 0;
                    double idf = 0;
                    double avRelFreq = 0.0;
                    double[] docRelFreqs = construct.getDocRelFrequencies();
                    for (int k = 0; k < docRelFreqs.length; k++) {
                        avRelFreq += docRelFreqs[k];
                        if (docRelFreqs[k] > 0) {
                            df++;
                        }
                    }
                    avRelFreq = avRelFreq / NUM_OF_RESULTS;
                    if (df > 0) {
                        idf = Math.log10((1 + NUM_OF_RESULTS) / df);
                    }
                    construct.setAvRelFrequency(avRelFreq);
                    construct.setDf(df);
                    construct.setIdf(idf);

                    String toFile = construct.lineToFile();
                    // write into the csv file
                    bw.write(toFile);
                    bw.newLine();
                    System.out.println(toFile);
                    bw.flush();

                    try {
                        String outfile1 = Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/constructions";
                        File dir2 = new File(outfile1);
                        dir2.mkdir();

                        String outfile = Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/constructions/" + entry.getKey() + ".json";
                        String JSONString = gson.toJson(construct);
                        System.out.println("> Writing construction into JSON: " + outfile);
                        fwJSON = new FileWriter(outfile);
                        fwJSON.write(JSONString);
                        fwJSON.close();

                    } catch (IOException ex) {
                        Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println();
//
//        try {
//            System.out.println("\n\nWriting documents into file...\n");
//            File pathToFile = new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/all_documents.csv");
//
//            System.out.println(pathToFile.getAbsolutePath());
//
//            fw = new FileWriter(pathToFile, true);//don't overwrite
//            bw = new BufferedWriter(fw);
//            // write all Constructions as lines into a file
//            // header
//            String header = "document,";
//            for (int i = 0; i < docs.get(0).getConstructions().size(); i++) {
//                header += docs.get(0).getConstructions().get(i) + ",";
//            }
//            header = header.substring(0, header.length() - 1); // get rid of the last comma
//            // write the header into csv file
//            bw.write(header);
//            bw.newLine();
//            System.out.println(header);
//            for (Document doc : docs) {
//                String toFile = doc.lineToFile();
//                // write into the csv file
//                bw.write(toFile);
//                bw.newLine();
//                System.out.println(toFile);
//                bw.flush();
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fw.close();
//            } catch (IOException ex) {
//                Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }

    public static void main(String[] args) {
        String directoryName = Constants.PATH_TO_RESULTS + "Jennifer_Lawrence/parsed";
        //ArrayList<String> filepaths = new ArrayList<>();
        List<Document> documents = new ArrayList<>();
        File f = new File(directoryName);
        System.out.println(directoryName);

        if (f.isDirectory()) {

            File[] allFiles = f.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return !name.toLowerCase().startsWith(".");
                }
            });

            Gson gson = new Gson();
            for (File aFile : allFiles) {
                System.out.println(aFile.getAbsolutePath());
                if (!aFile.isDirectory()) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(aFile));
                        //convert the json string to object
                        Document doc = gson.fromJson(br, Document.class);

                        // reset all constructions and counts
                        doc.setConstructions(new ArrayList<String>());
                        doc.setTfIdf(new ArrayList<Double>());
                        doc.setTfNorm(new ArrayList<Double>());
                        doc.setRelFrequencies(new ArrayList<Double>());
			    doc.setFrequencies(new ArrayList<Integer>());

                        doc.setHighlights(new ArrayList<Occurrence>());
                        documents.add(doc);

//                        ArrayList<String> constr = new ArrayList<>();
//                        constr.add("relativeClause");
//                        constr.add("complexSentence");
//                        constr.add("subordinateClause");
//                        constr.add("prepositions");
//                        System.out.println("\n\n ---------- TEXT with complexSentence, subordinateClause and toInfinitiveForms ---------- \n\n");
//                        System.out.println(doc.highlightText(constr));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            br.close();
                        } catch (IOException ex) {
                            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        System.out.println(documents.size() + " documents.");

        DeepParserCoreNLP dp = new DeepParserCoreNLP("Jennifer Lawrence", documents);

//        Properties props = new Properties();
//        props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//        String filepath = System.getProperty("user.dir") + "/Aaron_Hernandez_test";
//        DeepParserCoreNLP dp = new DeepParserCoreNLP(filepath);
    }

}
