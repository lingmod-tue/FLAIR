package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.trees.TypedDependency;

public class RelativeNlpManager extends NlpManager {

    public RelativeNlpManager(CoreNlpParser parser, SimpleNlgParser generator, String text, OpenNlpParser lemmatizer) {
    	super(parser, generator, text, lemmatizer);
    	this.plainText = text;
    }
    
    /**
     * Punctuation marks before which no space is to be inserted
     */
    private static final String punctuations = ".,:;!?'";

    private String plainText = null;
    
    
    public ArrayList<RelativeSentence> analyzeSentence(ExerciseData data) {
    	ArrayList<Pair<Integer, Integer>> constructionIndices = new ArrayList<>();
    	ArrayList<String> constructionTypes = new ArrayList<>();
    	constructionTypes.add(DetailedConstruction.WHO);
    	constructionTypes.add(DetailedConstruction.WHICH);
    	constructionTypes.add(DetailedConstruction.THAT);
    	constructionTypes.add(DetailedConstruction.OTHERPRN);
    	constructionTypes.add(DetailedConstruction.REL_CLAUSE);

    	for(TextPart d : data.getParts()) {
    		if(d instanceof ConstructionTextPart && (constructionTypes.contains(((ConstructionTextPart)d).getConstructionType()))) {
    			constructionIndices.add(((ConstructionTextPart)d).getIndicesInPlainText());
    		}
    	}
    	
    	ArrayList<RelativeSentence> relSentences = new ArrayList<>();
    	boolean previousSentenceWasAdded = false;
    	String contextBuffer = "";
    	for(int i = 0; i < sentences.size(); i++) {
    		boolean sentenceUsed = false;
    		SentenceAnnotations sentence = sentences.get(i);
    		if(constructionIndices.stream().anyMatch(idx -> idx.first >= sentence.getTokens().get(0).beginPosition() &&
    				idx.second <= sentence.getTokens().get(sentence.getTokens().size() - 1).endPosition())) {
    			try {
    	            RelativeSentence rs = getRelativeSentence(sentence);
    	
    	            if(rs != null) {
	    	            if(!contextBuffer.isEmpty()) {
	    	            	rs.contextBefore = contextBuffer;
	    		            contextBuffer = "";
	    	            }
	    	            
	    	            relSentences.add(rs);
	    	            
	    	            previousSentenceWasAdded = true;
	    	            sentenceUsed = true;
    	            }
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
    		} 

    		if(!sentenceUsed) {
    			// if we cannot use this sentences as target sentence, we check if we need it as context for the previous or succeeding sentence
    			// we add it as succeeding context to the previous sentences if the previous sentence was used as a target and there was no linebreak in-between
    			if(previousSentenceWasAdded && 
    					!plainText.substring(sentences.get(i - 1).getTokens().get(sentences.get(i - 1).getTokens().size() - 1).endPosition(), sentence.getTokens().get(0).beginPosition()).contains("\n")) {
    				relSentences.get(relSentences.size() - 1).contextAfter = plainText.substring(sentence.getTokens().get(0).beginPosition(), sentence.getTokens().get(sentence.getTokens().size() - 1).endPosition());
    			} else {
    				contextBuffer = plainText.substring(sentence.getTokens().get(0).beginPosition(), sentence.getTokens().get(sentence.getTokens().size() - 1).endPosition());
    			}
    			
    			previousSentenceWasAdded = false;
    		}
    	}
    	
    	return relSentences;
    }
    
    private RelativeSentence getRelativeSentence(SentenceAnnotations sentence) {
        ProcessedRelativeSentence rs = analyzeRelativeSentence(sentence.getDependencyGraph(), sentence.getTokens());
        rs.originalSentence = sentence.getSentence().toString();
        if(rs.pronoun == null) {
            return null;
        }

        analyzeClauses(sentence.getDependencyGraph(), sentence.getTokens(), rs, false);
        if(rs.clause1 == null || rs.clause2 == null) {
            return null;
        }
        
        ArrayList<RelativeSentenceAlternative> relativeSentences = generateRelativeSentences(rs, sentence);
        if(relativeSentences == null || relativeSentences.size() == 0) {
        	return null;
        }
                
        RelativeSentence relSent = new RelativeSentence();
        relSent.relativeSentences = relativeSentences;
        relSent.clause1 = rs.clause1;
        relSent.clause2 = rs.clause2;

        return relSent;
    }

    
    private ProcessedRelativeSentence analyzeRelativeSentence(Collection<TypedDependency> dependencyGraph, List<CoreLabel> tokens) {
    	ProcessedRelativeSentence rs = new ProcessedRelativeSentence();
        ArrayList<ArrayList<IndexedWord>> chunksMainClause = new ArrayList<>();
        ArrayList<ArrayList<IndexedWord>> chunksRelClause = new ArrayList<>();
        for(TypedDependency dep : dependencyGraph) {
            if(dep.reln().getShortName().equals("root")) {
                chunksMainClause = getRelativeChunks(dependencyGraph, dep.dep());
            }

            if(dep.reln().getShortName().equals("acl:relcl")) {
                rs.referentIsObject = dependencyGraph.stream().anyMatch(r -> r.dep().equals(dep.gov()) && r.reln().getShortName().equalsIgnoreCase("dobj"));
                chunksRelClause = getRelativeChunks(dependencyGraph, dep.dep());

                ArrayList<IndexedWord> relClDeps = getDependents(dependencyGraph, dep.dep());
                boolean foundPronoun = false;
                for(IndexedWord relClTok : relClDeps) {
                	if(relClTok.tag().startsWith("V")) {
                		int endPosition = relClTok.endPosition();
                        for(TypedDependency d : dependencyGraph) {
                        	// keep direct object and stranded preposition with the verb
                        	if(d.gov().equals(relClTok) && (d.reln().getShortName().equalsIgnoreCase("obj") || 
                        			d.reln().getShortName().equalsIgnoreCase("dobj") ||
                        			d.dep().tag().equalsIgnoreCase("IN") || d.dep().tag().equalsIgnoreCase("TO"))) {
                        		endPosition = Math.max(endPosition, d.dep().endPosition());
                        	}
                        }
                        if(rs.relVerb != null) {
                    		rs.relVerb = new Pair<>(Math.min(rs.relVerb.first, relClTok.beginPosition()), 
                    				Math.max(rs.relVerb.second, endPosition));
                        } else {
                        	rs.relVerb = new Pair<>(relClTok.beginPosition(), endPosition);
                        }
                	}
                    if(!foundPronoun && relClTok.tag().startsWith("W")) {
                        rs.pronoun = relClTok;
                        rs.pronIsObj = dependencyGraph.stream().anyMatch(r -> r.dep().equals(relClTok) && r.reln().getShortName().equalsIgnoreCase("dobj"));
                        rs.chunkIndices.add(new Pair<>(relClTok.beginPosition(), relClTok.endPosition()));
                        foundPronoun = true;
                        
                        // anything in the relative clause before the pronoun needs to be put after the verb in the clause later on
                        if(relClTok.beginPosition() > relClDeps.get(0).beginPosition()) {
                        	for(IndexedWord w : relClDeps) {
                        		if(w.beginPosition() < relClTok.beginPosition()) {
                                	rs.tokensBeforePronoun.add(w);
                        		} else {
                        			break;
                        		}
                        	}
                        }
                    } 
                }

                rs.chunkIndices = addRemainingChunks(rs.chunkIndices, relClDeps);
            }
        }
        
        ArrayList<ArrayList<IndexedWord>> chunksMainToKeep = new ArrayList<>();
        for(ArrayList<IndexedWord> cmc : chunksMainClause) {
        	ArrayList<IndexedWord> tokensToKeep = new ArrayList<>();
        	for(IndexedWord t : cmc) {
            	if(chunksRelClause.stream().noneMatch(cr -> cr.contains(t))) {
            		tokensToKeep.add(t);
            	}
        	}
        	if(tokensToKeep.size() > 0) {
        		chunksMainToKeep.add(tokensToKeep);
        	}
        }

        addChunkIfNoOverlap(chunksRelClause, rs.chunkIndices);
        addChunkIfNoOverlap(chunksMainToKeep, rs.chunkIndices);
        
        ArrayList<IndexedWord> ts = new ArrayList<>();
        for(CoreLabel t : tokens) {
        	ts.add(new IndexedWord(t));
        }
        rs.chunkIndices = addRemainingChunks(rs.chunkIndices, ts);
        
        ArrayList<Pair<Integer, Integer>> chunks = new ArrayList<>();
        for(Pair<Integer, Integer> chunkIndex : rs.chunkIndices) {
        	if(chunks.stream().noneMatch(c -> c.first.equals(chunkIndex.first) && c.second.equals(chunkIndex.second))) {
        		if(rs.relVerb != null && rs.relVerb.second > chunkIndex.first && rs.relVerb.second < chunkIndex.second) {
        			// break up the chunk after the verb
        			chunks.add(new Pair<>(chunkIndex.first, rs.relVerb.second));
        			chunks.add(new Pair<>(rs.relVerb.second, chunkIndex.second));
        		} else {
        			chunks.add(chunkIndex);
        		}
        	}
        }
        rs.chunkIndices = chunks;

        return rs;
    }
    
    private ArrayList<ArrayList<IndexedWord>> getRelativeChunks(Collection<TypedDependency> dependencyGraph, IndexedWord root) {
        HashSet<IndexedWord> rootParts = new HashSet<>();
        ArrayList<ArrayList<IndexedWord>> chunks = new ArrayList<>();
        HashSet<IndexedWord> nonRootParts = new HashSet<>();

        for(TypedDependency dep : dependencyGraph) {
            if(dep.gov().equals(root)) {
                if(dep.reln().getShortName().equalsIgnoreCase("det") || dep.reln().getShortName().equalsIgnoreCase("amod") ||
                        dep.reln().getShortName().equalsIgnoreCase("advmod") || dep.reln().getShortName().equalsIgnoreCase("case") ||
                        dep.reln().getShortName().equalsIgnoreCase("clf") || dep.reln().getShortName().equalsIgnoreCase("aux")) {
                    rootParts.addAll(getDependents(dependencyGraph, dep.dep()));
                } else {
                	nonRootParts.add(dep.dep());
                }
            }
        }

        rootParts.add(root);
        ArrayList<IndexedWord> parts = new ArrayList<>(rootParts);
		Collections.sort(parts, (i1, i2) -> i1.beginPosition() < i2.beginPosition() ? -1 : 1);
        chunks.add(parts);
        
        for(IndexedWord nonRootPart : nonRootParts) {
        	if(!parts.contains(nonRootPart)) {
        		chunks.add(getDependents(dependencyGraph, nonRootPart));
        	}
        }

        return chunks;
    }
    
    private void addChunkIfNoOverlap(ArrayList<ArrayList<IndexedWord>> chunks, ArrayList<Pair<Integer, Integer>> chunkIndices) {
        for(ArrayList<IndexedWord> chunk : chunks) {
    		Collections.sort(chunk, (i1, i2) -> i1.beginPosition() < i2.endPosition() ? -1 : 1);
            Pair<Integer, Integer> indices = new Pair<>(chunk.get(0).beginPosition(), chunk.get(chunk.size() - 1).endPosition());

            // keep it only if there is no overlap with another chunk
            if(chunkIndices.stream().noneMatch(idx -> Math.max(idx.first, indices.first) < Math.min(idx.second, indices.second))) {
                chunkIndices.add(indices);
            }
        }
    }
    
    private ArrayList<Pair<Integer, Integer>> addRemainingChunks(ArrayList<Pair<Integer, Integer>> chunkIndices, List<IndexedWord> tokens) {
        ArrayList<IndexedWord> currentSpan = new ArrayList<>();
        for(IndexedWord token : tokens) {
            if(chunkIndices.stream().noneMatch(chunk -> token.beginPosition() >= chunk.first && token.endPosition() <= chunk.second)) {
                // we need a new chunk if there was something in-between the last stranded token and this one
                if(currentSpan.size() > 0 && currentSpan.get(currentSpan.size() - 1).index() + 1 != token.index()) {
                    chunkIndices.add(new Pair<>(currentSpan.get(0).beginPosition(), currentSpan.get(currentSpan.size() - 1).endPosition()));
                    currentSpan.clear();
                }
                currentSpan.add(token);
            }
        }
        if(currentSpan.size() > 0) {
            chunkIndices.add(new Pair<>(currentSpan.get(0).beginPosition(), currentSpan.get(currentSpan.size() - 1).endPosition()));
        }

		Collections.sort(chunkIndices, (i1, i2) -> i1.first < i2.first ? -1 : 1);
        return chunkIndices;
    }
    
    private void analyzeClauses(Collection<TypedDependency> dependencyGraph, List<CoreLabel> tokens, ProcessedRelativeSentence rs,
    		boolean excludeVerbFromRel) {
    	ArrayList<Token> relToks = new ArrayList<>();
    	ArrayList<Token> mainToks = new ArrayList<>();
        
        if(!determineClauses(dependencyGraph, tokens, rs, false, relToks, mainToks)) {
        	return;	// we can't parse it correctly, so be better not use this sentence
        	/*
        	relToks.clear();
        	mainToks.clear();
        	determineClauses(dependencyGraph, tokens, rs, true, relToks, mainToks);*/
        }
        
        ArrayList<Pair<Integer, Integer>> chunks = new ArrayList<>();
        for(Pair<Integer, Integer> chunkIndex : rs.chunkIndices) {
    		if(rs.commonReferentInMain != null && rs.commonReferentInMain.get(0).beginPosition() > chunkIndex.first && 
    				rs.commonReferentInMain.get(0).beginPosition() < chunkIndex.second) {
    			// break up the chunk before the common referent
    			chunks.add(new Pair<>(chunkIndex.first, rs.commonReferentInMain.get(0).beginPosition()));
    			chunks.add(new Pair<>(rs.commonReferentInMain.get(0).beginPosition(), chunkIndex.second));
    		} else {
    			chunks.add(chunkIndex);
    		}
        }
        rs.chunkIndices = chunks;

        ArrayList<String> standardPronouns = new ArrayList<>();
        standardPronouns.add("who");
        standardPronouns.add("which");
        standardPronouns.add("that");
        standardPronouns.add("whose");
        standardPronouns.add("whom");
        
        // parts before the pronoun need to go after the verb
        ArrayList<Token> relToksAdjusted = new ArrayList<>();
        if(rs.tokensBeforePronoun.size() > 0 && rs.relVerb != null) {
	        for(Token t : relToks) {
	        	if(t.start >= rs.tokensBeforePronoun.get(rs.tokensBeforePronoun.size() - 1).endPosition()) {
	        		relToksAdjusted.add(t);

	        		if(t.end.equals(rs.relVerb.second)) {
	        			for(IndexedWord w : rs.tokensBeforePronoun) {
	        				relToksAdjusted.add(new Token(w.value(), w.beginPosition(), w.endPosition()));
	        			}
	        		}
	        	}
	        }
	        relToks = relToksAdjusted;
        }
                    
        if(rs.pronIsObj) {
        	Integer indexOfVerb = null;
        	if(rs.relVerb != null) {
	            for(int i = 0; i < relToks.size(); i++) {
	            	if(rs.relVerb.first >= relToks.get(i).start && rs.relVerb.second <= relToks.get(i).end) {
	            		indexOfVerb = i;
	            	}
	            }
        	}
        	if(indexOfVerb == null) {
            	indexOfVerb = relToks.size() - 1;
            }
        	
            if(!standardPronouns.contains(rs.pronoun.value())) {
            	relToks.add(new Token(rs.prepositionOfCommonReferent == null ? "at" : rs.prepositionOfCommonReferent.value(), null, 0));
            }
            
            // add the referent
        	for(IndexedWord w : rs.commonReferentInMain) {
            	relToks.add(++indexOfVerb, new Token(w.value(), w.beginPosition(), w.endPosition()));
            }
        	
        	if(rs.pronoun.value().equalsIgnoreCase("whose")) {
            	relToks.add(++indexOfVerb, new Token("'s", null, null));
            }
        } else {
        	// add the referent
        	for(int n = rs.commonReferentInMain.size() - 1; n >= 0; n--) {
            	IndexedWord w = rs.commonReferentInMain.get(n);
            	relToks.add(0, new Token(w.value(), w.beginPosition(), w.endPosition()));
            }
        }

        rs.clause1 = normalizeSentence(generateSentencesFromTokens(relToks), rs.originalSentence.substring(rs.originalSentence.length() - 1));
        rs.chunksClause1 = getClauseChunks(relToks, rs);

        rs.clause2 = normalizeSentence(generateSentencesFromTokens(mainToks), ".");
        rs.chunksClause2 = getClauseChunks(mainToks, rs);
    }
    
    private boolean determineClauses(Collection<TypedDependency> dependencyGraph, List<CoreLabel> tokens, ProcessedRelativeSentence rs,
    		boolean excludeVerbFromRel, ArrayList<Token> relToks, ArrayList<Token> mainToks) {
    	ArrayList<IndexedWord> relClDeps = null;
        IndexedWord relClauseReferent = null;
        for(TypedDependency dep : dependencyGraph) {
            if (dep.reln().getShortName().equals("acl:relcl")) {
                relClDeps = getDependents(dependencyGraph, dep.dep());
                relClauseReferent = dep.gov();
                
                if(excludeVerbFromRel) {
                	Integer si = null;
                	Integer ei = null;
                	for(int i = 0; i < relClDeps.size(); i++) {
                		Integer startIndex = null;
                    	Integer endIndex = null;
                    	
                		IndexedWord t = relClDeps.get(i);
                		if(t.tag().startsWith("V") || t.tag().equalsIgnoreCase("MD")) {
                			ArrayList<IndexedWord> verbDeps = getDependents(dependencyGraph, t);
                			for(int j = 0; j < verbDeps.size(); j++) {
                				if(relClDeps.contains(verbDeps.get(j))) {
                					int index = relClDeps.indexOf(verbDeps.get(j));
                					
                					if(verbDeps.get(j).tag().startsWith("V") || verbDeps.get(j).tag().equalsIgnoreCase("MD")) {
                            			if(startIndex == null || startIndex > index) {
                            				startIndex = index;
                            			}
                					}
                					if(endIndex == null || endIndex < index) {
                						endIndex = index;
                					}
                				}
                			}
                		}
                		
                		if(startIndex != null) {
                			si = startIndex;
                			ei = endIndex;
                		}
                	}
                	if(si != null) {
                		for(int i = ei; i >= si; i--) {
                			relClDeps.remove(i);
                		}
                	}
                }
                
                break;
            }
        }

        if(relClDeps == null) {
            return false;
        }

        ArrayList<IndexedWord> depsOfMainRef = getDependents(dependencyGraph, relClauseReferent);
        ArrayList<IndexedWord> depsToRemove = new ArrayList<>();
        for(IndexedWord w : depsOfMainRef) {
        	for(TypedDependency d : dependencyGraph) {
        		if(d.gov().equals(relClauseReferent) && d.dep().equals(w) && 
            			(d.reln().getShortName().equalsIgnoreCase("cop") || d.reln().getShortName().equalsIgnoreCase("nsubj"))) {
            		depsToRemove.addAll(getDependents(dependencyGraph, w));
            		break;
            	}
        	}
        }
        for(IndexedWord w : depsToRemove) {
        	depsOfMainRef.remove(w);
        }
        
        ArrayList<IndexedWord> commonReferentWithoutPreposition = new ArrayList<>();
        for(IndexedWord depOfMainRef : depsOfMainRef) {
            if(!relClDeps.contains(depOfMainRef) && !punctuations.contains(depOfMainRef.value())) {
            	if((commonReferentWithoutPreposition.size() > 0 || !depOfMainRef.tag().equalsIgnoreCase("TO") && !(depOfMainRef.tag().equalsIgnoreCase("IN") &&
                        dependencyGraph.stream().anyMatch(g -> g.dep().equals(depOfMainRef) && g.reln().getShortName().equalsIgnoreCase("case"))))) {
                    commonReferentWithoutPreposition.add(depOfMainRef);
            	} else {
                    rs.commonReferentInMainHasPreposition = true;
                    rs.prepositionOfCommonReferent = depOfMainRef;
            	}
            } 
        }
        rs.commonReferentInMain = commonReferentWithoutPreposition;

        Pair<Integer, Integer> relIndices = new Pair<>(relClDeps.get(0).beginPosition(), relClDeps.get(relClDeps.size() - 1).endPosition());
        ArrayList<CoreLabel> mainTokens = new ArrayList<>();
        for(CoreLabel token : tokens) {
            if(token.beginPosition() >= relIndices.first && token.endPosition() <= relIndices.second) {
                if(token.beginPosition() != rs.pronoun.beginPosition()) {
                	Token tok = new Token(decapitalizeIfRequired(token.value(), token.lemma()), token.beginPosition(), token.endPosition());
                	relToks.add(tok);
                }
            } else {
            	Token tok = new Token(decapitalizeIfRequired(token.value(), token.lemma()), token.beginPosition(), token.endPosition());
            	mainToks.add(tok);
                mainTokens.add(token);
                if(token.tag().equalsIgnoreCase("IN") && token.value().equalsIgnoreCase("that") && dependencyGraph.stream()
                		.anyMatch(d -> d.dep().beginPosition() == token.beginPosition() && d.reln().getShortName().equalsIgnoreCase("mark")) &&
                		!rs.commonReferentInMain.stream().anyMatch(t -> token.beginPosition() == t.beginPosition())) {
                	rs.mainToksToExcludeInRel.add(tok);
                }
            }
        }
        
        // check if the main clause has been assigned a verb.
        // if not, we need to assign one of the verbs of the relative clause to the main clause
        boolean mainClauseHasVerb = false;
        for(CoreLabel token : mainTokens) {
        	//VBN is technically incorrect, but sometimes the verb is incorrectly annotated as participle and it shouln't hurt to have it here
        	if(token.tag().equalsIgnoreCase("VBD") ||token.tag().equalsIgnoreCase("VBP") || token.tag().equalsIgnoreCase("VBZ") ||
        			token.tag().equalsIgnoreCase("VBN") || token.tag().equalsIgnoreCase("MD")) {
        		mainClauseHasVerb = true;
        	}
        }
        
        return mainClauseHasVerb;
    }
    
    /**
     * Checks if the lemma is lower-case and decapitalizes the token value if this is the case.
     * @param s		The string value of the token
     * @param lemma	The lemma of the token
     * @return The decapitalized string if the lemma is lower-case; otherwise, the string.
     */
    private String decapitalizeIfRequired(String s, String lemma) {
    	if(lemma.substring(0, 1).toLowerCase().equals(lemma.substring(0, 1))) {
        	return s.substring(0, 1).toLowerCase() + s.substring(1);
    	}
    	
    	return s;
    }
    
    private String normalizeSentence(String sentence, String mark) {
        sentence = StringUtils.capitalize(sentence);
        if(punctuations.contains(sentence.charAt(sentence.length() - 1) + "")) {
            sentence = sentence.substring(0, sentence.length() - 1);
        }

        return sentence + mark;
    }
    
    private ArrayList<Token> getClauseChunks(ArrayList<Token> tokens, ProcessedRelativeSentence rs) {
        ArrayList<Pair<Integer, ArrayList<Token>>> chunkTokens = new ArrayList<>();

        int previousChunkIndex = 0;
        Token previousPreposition = null;
        for(Token t : tokens) {
            if(t.end == null) {
                // add the possessive clitic to the previous chunk
            	for(Pair<Integer, ArrayList<Token>> ct : chunkTokens) {
            		if(ct.first.equals(previousChunkIndex)) {
            			ct.second.add(t);
            			break;
            		}
            	}
            } else if(t.end == 0) {
                // add the preposition to the following chunk
                previousPreposition = t;
            } else {
                for (int i = 0; i < rs.chunkIndices.size(); i++) {
                    // if the token is inside the chunk, we save it to the chunkindex-chunkStrings map
                    if (t.start >= rs.chunkIndices.get(i).first && t.end <= rs.chunkIndices.get(i).second) {
                    	ArrayList<Token> currentChunk = null;
                    	for(Pair<Integer, ArrayList<Token>> ct : chunkTokens) {
                    		if(ct.first.equals(i)) {
                    			currentChunk = ct.second;
                    			break;
                    		}
                    	}
                    	if(currentChunk == null) {
                    		currentChunk = new ArrayList<>();
                    		chunkTokens.add(new Pair<>(i, currentChunk));
                    	}
                        
                        if(previousPreposition != null) {
                        	currentChunk.add(previousPreposition);
                            previousPreposition = null;
                        }
                        currentChunk.add(t);
                        previousChunkIndex = i;
                    }
                }
            }
        }

        ArrayList<Token> chunks = new ArrayList<>();
        for(Pair<Integer, ArrayList<Token>> ct : chunkTokens) {
            chunks.add(new Token(generateSentencesFromTokens(ct.second), ct.second.get(0).start, ct.second.get(ct.second.size() - 1).end));
        }

        return chunks;
    }
    
    protected String generateSentencesFromTokens(ArrayList<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        boolean isFirstToken = true;
        for (int k = 0; k < tokens.size(); k++) {
            String token = tokens.get(k).value;
            if (!token.isEmpty() && !punctuations.contains(token.charAt(0) + "") && !token.equals("n't") && !isFirstToken) {
                sb.append(" ");
            }
            sb.append(token);
            isFirstToken = false;
        }

        return sb.toString();
    }
    
    private ArrayList<RelativeSentenceAlternative> generateRelativeSentences(ProcessedRelativeSentence rs, SentenceAnnotations sentence) {
    	ArrayList<RelativeSentenceAlternative> relativeSentences = new ArrayList<>();

        // It wouldn't normally make sense to have all those orderings, but we need to know which orders must be accepted for jumbled sentences.
        // we might just leave the decision to check that the chunking is unambiguous to the revisor
        // we need to give alternative exercises with the order of the clauses reversed (or random) to adjust difficulty

        // original order
        relativeSentences.add(getRelativeSentence(rs, false, rs.chunksClause2, rs.chunksClause1,
                rs.pronIsObj, sentence, false));
        addRelativeSentenceIfNotContained(relativeSentences, getRelativeSentence(rs, true, rs.chunksClause2, rs.chunksClause1,
                rs.pronIsObj, sentence, false));
        
        // add sentence with stranded preposition, but not for 'that' or contact clauses
        if(!rs.pronoun.value().equals("that")) {
	        addRelativeSentenceIfNotContained(relativeSentences, getRelativeSentence(rs, false, rs.chunksClause2, rs.chunksClause1,
	                rs.pronIsObj, sentence, true));
	        addRelativeSentenceIfNotContained(relativeSentences, getRelativeSentence(rs, true, rs.chunksClause2, rs.chunksClause1,
	                rs.pronIsObj, sentence, true));    
        }

        // reversed order
        // if both referents are objects or both are subjects, the pronoun is the same (object or subject) as in the original order; otherwise, it's reversed
        if(rs.pronoun.value().equals("who") || rs.pronoun.value().equals("which") || rs.pronoun.value().equals("that") || rs.pronoun.value().equals("whom") && rs.pronIsObj ||
                !rs.pronoun.value().equals("whom") && !rs.pronoun.value().equals("whose") && rs.commonReferentInMainHasPreposition) {
        	addRelativeSentenceIfNotContained(relativeSentences, getRelativeSentence(rs, false, rs.chunksClause1, rs.chunksClause2,
            		(rs.pronIsObj == rs.referentIsObject) == rs.pronIsObj, sentence, false));
        	addRelativeSentenceIfNotContained(relativeSentences, getRelativeSentence(rs, true, rs.chunksClause1, rs.chunksClause2,
                    (rs.pronIsObj == rs.referentIsObject) == rs.pronIsObj, sentence, false));
        	
            if(!rs.pronoun.value().equals("that")) {
	        	addRelativeSentenceIfNotContained(relativeSentences, getRelativeSentence(rs, false, rs.chunksClause1, rs.chunksClause2,
	            		(rs.pronIsObj == rs.referentIsObject) == rs.pronIsObj, sentence, true));
	        	addRelativeSentenceIfNotContained(relativeSentences, getRelativeSentence(rs, true, rs.chunksClause1, rs.chunksClause2,
	                    (rs.pronIsObj == rs.referentIsObject) == rs.pronIsObj, sentence, true));
            }
        }

       //TODO: remove 
        for(RelativeSentenceAlternative relS : relativeSentences) {
        	StringBuilder sb = new StringBuilder();
        	for(RelativeSentenceChunk c : relS.getChunks()) {
        		sb.append(c.getValue()).append("|");
        	}
        	System.out.println(sb.toString());
        }
        System.out.println("-------------------------");
        //TODO
        
        
        return relativeSentences;
    }
    
    private void addRelativeSentenceIfNotContained(ArrayList<RelativeSentenceAlternative> relativeSentences,
    		RelativeSentenceAlternative newSentence) {
    	for(RelativeSentenceAlternative oldSentence : relativeSentences) {
    		if(oldSentence.getChunks().size() == newSentence.getChunks().size()) {
    			boolean allSame = true;
    			for(int i = 0; i < newSentence.getChunks().size(); i++) {
    				if(!oldSentence.getChunks().get(i).getValue().equals(newSentence.getChunks().get(i).getValue())) {
    					allSame = false;
    					break;
    				}
    			}
    			if(allSame) {
    				return;
    			}
    		}
    	}
    	
    	// if we end up here, there was no identical relative sentence, so we add the new one
    	relativeSentences.add(newSentence);
    }
    
    
    private RelativeSentenceAlternative getRelativeSentence(ProcessedRelativeSentence rs, boolean extrapose,
            ArrayList<Token> chunksClause1,
            ArrayList<Token> chunksClause2,
            boolean referentClause2IsObject, SentenceAnnotations sentence, boolean prepositionStranding) {
    	ArrayList<RelativeSentenceChunk> chunks = new ArrayList<>();
    	
    	Pair<Integer, Integer> commonReferent = 
    			new Pair<>(rs.commonReferentInMain.get(0).beginPosition(), 
    					rs.commonReferentInMain.get(rs.commonReferentInMain.size() - 1).endPosition());
        if(extrapose) {
            // add everything in the main clause
            for(Token c : chunksClause1) {
                RelativeSentenceChunk chunk = new RelativeSentenceChunk(c.value);
                if(c.start >= commonReferent.first && c.end <= commonReferent.second) {
                    chunk.setCommonReferent(true);
                }

                chunks.add(chunk);
            }

            // add the pronoun
            RelativeSentenceChunk chunk = new RelativeSentenceChunk(rs.pronoun.value());
            chunk.setPronoun(true);
            chunks.add(chunk);

            // add everything in the relative clause except for the common referent
            String preposition = null;
            for(Token c : chunksClause2) {
            	if(prepositionStranding && rs.prepositionOfCommonReferent != null && rs.prepositionOfCommonReferent.beginPosition() >= c.start && rs.prepositionOfCommonReferent.endPosition() <= c.end) {
            		// we have a preposition in the relative clause, so if we need preposition stranding, we need to put it to the end
            		preposition = rs.prepositionOfCommonReferent.value();
            		if(rs.prepositionOfCommonReferent.beginPosition() > c.start) {
            			// we have something before the preposition, so it needs its own chunk
            			if(!(c.start >= commonReferent.first && c.start <= commonReferent.second) &&
                        		rs.mainToksToExcludeInRel.stream().noneMatch(t -> t.start.equals(c.start) && t.end.equals(c.end))) {
                            chunks.add(new RelativeSentenceChunk(c.value.substring(0, rs.prepositionOfCommonReferent.beginPosition() - c.start).trim()));
                        }
            		}
            		if(rs.prepositionOfCommonReferent.beginPosition() < c.end) {
            			// we have something after the preposition, so it needs its own chunk
            			if(!(c.start >= commonReferent.first && c.start <= commonReferent.second) &&
                        		rs.mainToksToExcludeInRel.stream().noneMatch(t -> t.start.equals(c.start) && t.end.equals(c.end))) {
                            chunks.add(new RelativeSentenceChunk(c.value.substring(c.value.length() - (c.end - rs.prepositionOfCommonReferent.endPosition())).trim()));
                        }
            		}
            	} else if(!(c.start >= commonReferent.first && c.start <= commonReferent.second) &&
                		rs.mainToksToExcludeInRel.stream().noneMatch(t -> t.start.equals(c.start) && t.end.equals(c.end))) {
                    chunks.add(new RelativeSentenceChunk(c.value));
                }
            }
            // add the preposition for preposition stranding
            if(preposition != null) {
                chunks.add(new RelativeSentenceChunk(preposition));
            }
        } else {
            // add everything in the main clause until after the common referent
            for(Token c : chunksClause1) {
                RelativeSentenceChunk chunk = new RelativeSentenceChunk(c.value);
                if(c.start >= commonReferent.first) {
                    chunk.setCommonReferent(true);
                }

                chunks.add(chunk);
                
                if(c.end.equals(commonReferent.second)) {
                    break;
                }
            }

            // add the preposition
            if(!prepositionStranding && rs.prepositionOfCommonReferent != null && 
            		rs.prepositionOfCommonReferent.beginPosition() >= chunksClause2.get(0).start && 
            		rs.prepositionOfCommonReferent.endPosition() <= chunksClause2.get(chunksClause2.size() - 1).end) {
            	chunks.add(new RelativeSentenceChunk(rs.prepositionOfCommonReferent.value()));
            }
            
            // add the pronoun
            RelativeSentenceChunk chunk = new RelativeSentenceChunk(rs.pronoun.value());
            chunk.setPronoun(true);
            chunks.add(chunk);
            
            // add everything in the relative clause except for the common referent and the preposition
            for(Token c : chunksClause2) {
                if(!(c.start >= commonReferent.first && c.start <= commonReferent.second) &&
                		rs.mainToksToExcludeInRel.stream().noneMatch(t -> t.start.equals(c.start) && t.end.equals(c.end))) {
                	if(rs.prepositionOfCommonReferent != null && c.start <= rs.prepositionOfCommonReferent.beginPosition() && c.end >= rs.prepositionOfCommonReferent.endPosition()) {
                		// we have to check whether there is something else in the chunk which we want to keep
                		if(c.start < rs.prepositionOfCommonReferent.beginPosition()) {
                			chunks.add(new RelativeSentenceChunk(c.value.substring(0, rs.prepositionOfCommonReferent.beginPosition() - c.start)));
                		}
                		if(c.end > rs.prepositionOfCommonReferent.beginPosition()) {
                			chunks.add(new RelativeSentenceChunk(c.value.substring(rs.prepositionOfCommonReferent.endPosition() - c.start)));
                		}
                	} else {
                		chunks.add(new RelativeSentenceChunk(c.value));
                	}
                }
            }
            
            // add the preposition for preposition stranding
            if(prepositionStranding && rs.prepositionOfCommonReferent != null && 
            		rs.prepositionOfCommonReferent.beginPosition() >= chunksClause2.get(0).start && 
            		rs.prepositionOfCommonReferent.endPosition() <= chunksClause2.get(chunksClause2.size() - 1).end) {
            	chunks.add(new RelativeSentenceChunk(rs.prepositionOfCommonReferent.value()));
            }

            // add the rest of the main clause
            boolean skip = true;
            for(Token c : chunksClause1) {
            	if(!skip) {
            		chunks.add(new RelativeSentenceChunk(c.value));
            	}

                if(c.end.equals(commonReferent.second)) {
                	skip = false;
                }
            }
        }
        
        // remove punctuation marks in the middle of the sentence
        ArrayList<RelativeSentenceChunk> chunksToRemove = new ArrayList<>();
        for(RelativeSentenceChunk chunk : chunks) {
        	if(sentenceFinalPunctuations.contains(chunk.getValue())) {
        		chunksToRemove.add(chunk);
        	} else if (sentenceFinalPunctuations.contains(chunk.getValue().substring(chunk.getValue().length() - 1))) {
        		chunk.setValue(chunk.getValue().substring(0, chunk.getValue().length() - 1));
        	}
        }
        for(RelativeSentenceChunk chunk : chunksToRemove) {
        	chunks.remove(chunk);
        }

        // normalize
        String mark = ".";
        String lastCharacter = rs.originalSentence.substring(rs.originalSentence.length() - 1);
        if(punctuations.contains(lastCharacter)) {
            mark = lastCharacter;
        }
        RelativeSentenceChunk lastChunk = chunks.get(chunks.size() - 1);
        if(punctuations.contains(lastChunk.getValue().substring(lastChunk.getValue().length() - 1))) {
            chunks.get(chunks.size() - 1).setValue(chunks.get(chunks.size() - 1).getValue().substring(0, lastChunk.getValue().length() - 1));
        }
        chunks.add(new RelativeSentenceChunk(mark));
        chunks.get(0).setValue(StringUtils.capitalize(chunks.get(0).getValue()));      

        RelativeSentenceAlternative s = new RelativeSentenceAlternative();
        s.setChunks(chunks);
        s.setPronounIsOptional(referentClause2IsObject && !prepositionStranding);    
        
        int j = 1;
        for(RelativeSentenceChunk chunk : chunks) {
        	if(chunk.isPronoun()) {
                s.setPronounIndex(j);
        	}
        	if(chunk.isCommonReferent()) {
        		s.setPromptEndIndex(j);
        	}
        	
        	j++;
        }

        return s;
    }
    
    public ArrayList<String> getDistractors(String pronoun, HashSet<String> distinctPronouns) {
        ArrayList<String> distractors = new ArrayList<>(distinctPronouns);
        if(distinctPronouns.contains(pronoun)) {
        	distractors.remove(pronoun);
        }
        
        return distractors;
    }
        
    private class ProcessedRelativeSentence {
        public IndexedWord pronoun = null;
        public boolean pronIsObj = false;
        public boolean referentIsObject = false;
        public ArrayList<Pair<Integer, Integer>> chunkIndices = new ArrayList<>();
        public String clause1 = null;
        public String clause2 = null;
        public boolean commonReferentInMainHasPreposition = false;
        public IndexedWord prepositionOfCommonReferent = null;
        public ArrayList<IndexedWord> commonReferentInMain = null;
        public ArrayList<Token> chunksClause1 = new ArrayList<>();
        public ArrayList<Token> chunksClause2 = new ArrayList<>();
        public String originalSentence = null;
        public Pair<Integer, Integer> relVerb = null;
        public ArrayList<IndexedWord> tokensBeforePronoun = new ArrayList<>();
        public ArrayList<Token> mainToksToExcludeInRel = new ArrayList<>();
    }
    
    private class Token {
    	public String value;
    	public Integer start;
    	public Integer end;
    	
		public Token(String value, Integer start, Integer end) {
			this.value = value;
			this.start = start;
			this.end = end;
		}
    	
    	
    }
    
}