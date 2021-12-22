package com.flair.server.exerciseGeneration.exerciseManagement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.InputParsing.ConfigParsing.ConditionalConfigParser;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.Item;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.SimpleExerciseXmlGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.XmlGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.XmlValues;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class ConfigBasedConditionalExerciseGenerator extends ConfigBasedExerciseGenerator {
	    
	private String generateCategorizationTask(ArrayList<ExerciseConfigData> configData, boolean ifClauseFirst,
			boolean randomizeClauseOrder) {
		XmlValues v = new XmlValues();
		v.instructions = "Read the sentences and decide if they are type 1 or type 2.";
		v.taskType = "CATEGORIZE";
		v.givenWordsDraggable = true;
		
		ArrayList<String> pool = new ArrayList<>();
		
		Item item = new Item();
		item.text = "Type 1";
		ArrayList<String> textElements = new ArrayList<>();
		for(ExerciseConfigData itemData : configData) {
			if(itemData.getConditionalType() == 1) {
				String ifClause = generateSentencesFromPositions(itemData.getPositionsIfClause());
				String mainClause = generateSentencesFromPositions(itemData.getPositionsMainClause());
				
				if(randomizeClauseOrder) {
					ifClauseFirst = Math.random() > 0.5;
				} 
				String firstClause = StringUtils.capitalize(ifClauseFirst ? ifClause : mainClause);
				String secondClause = ifClauseFirst ? mainClause : ifClause;
				String delimiter = ifClauseFirst ? ", " : " ";			
				
				String sentence = firstClause + delimiter + secondClause + ".";
				pool.add(sentence + "(1)");
				textElements.add(sentence);
			}
		}
				
		item.target = StringUtils.join(textElements, "|");
		item.inputType = "PHRASE";
		v.items.add(item);
		
		item = new Item();
		item.text = "Type 2";
		ArrayList<String> textElements2 = new ArrayList<>();
		for(ExerciseConfigData itemData : configData) {
			if(itemData.getConditionalType() == 2) {
				String ifClause = generateSentencesFromPositions(itemData.getPositionsIfClause());
				String mainClause = generateSentencesFromPositions(itemData.getPositionsMainClause());
				
				if(randomizeClauseOrder) {
					ifClauseFirst = Math.random() > 0.5;
				} 
				String firstClause = StringUtils.capitalize(ifClauseFirst ? ifClause : mainClause);
				String secondClause = ifClauseFirst ? mainClause : ifClause;
				String delimiter = ifClauseFirst ? ", " : " ";			
				
				String sentence = firstClause + delimiter + secondClause + ".";
				pool.add(sentence + "(2)");
				textElements2.add(sentence);			}
		}
				
		item.target = StringUtils.join(textElements2, "|");
		item.inputType = "PHRASE";
		v.items.add(item);
		
		Collections.shuffle(pool);
		v.givenWords = StringUtils.join(pool, "|");

		
		return new SimpleExerciseXmlGenerator().generateFeedBookInputXml(v);
	}
	
	
	private String generateMemoryTask(ArrayList<ExerciseConfigData> configData, boolean useIfClause) {
		XmlValues v = new XmlValues();
		v.instructions = "Find pairs.";
		v.support = "[12/3 1:49 PM] Florian Nuxoll\n"
				+ "<hr>Reminder:<ul><li>Simple past: often goes with <em>yesterday, ago, last</em>...</li><li>Present\n"
				+ " perfect: often goes with with <em>since, for, ever, yet</em>…</li></ul>\n"
				+ "\n"
				+ "";
		v.taskType = "MEMORY";
		Item item = new Item();
		item.target = "";
		ArrayList<String> textElements = new ArrayList<>();
		for(ExerciseConfigData itemData : configData) {
			String targetClause = useIfClause ? generateSentencesFromPositions(itemData.getPositionsIfClause()) : 
				generateSentencesFromPositions(itemData.getPositionsMainClause());
			String translationTargetClause = useIfClause ? itemData.getTranslationIfClause() : itemData.getTranslationMainClause();
			textElements.add(targetClause + " - " + translationTargetClause);
		}
		item.text = StringUtils.join(textElements, "|");
		item.inputType = "MAPPING";
		v.items.add(item);
		
		return new SimpleExerciseXmlGenerator().generateFeedBookInputXml(v);
	}
		
	private String generateUnderlineTask(ArrayList<ExerciseConfigData> configData, boolean targetClauseFirst, 
			boolean useIfClause, boolean randomizeClauseOrder) {
		XmlValues v = new XmlValues();
		v.taskType = "UNDERLINE";
		v.instructions = "Underline the verb in the if clause";
		Item item = new Item();
		item.inputType = "UNDERLINE";
		int offsetIndex = 0;
		
		ArrayList<String> lines = new ArrayList<>();
		ArrayList<Pair<Integer, Integer>> indices = new ArrayList<>();
		for(ExerciseConfigData itemData : configData) {
			ArrayList<Pair<Integer,String>> ifPositions = itemData.getPositionsIfClause();
			ArrayList<Pair<Integer,String>> positionsTargetedClause = useIfClause ? 
					ifPositions : itemData.getPositionsMainClause();
			ArrayList<Pair<Integer,String>> positionsOtherClause = useIfClause ? itemData.getPositionsMainClause() :
				ifPositions;
			ArrayList<Pair<Integer, Integer>> targetPositions = useIfClause ? itemData.getUnderlineIfClause() :
				itemData.getUnderlineMainClause();
			String targetedClause = generateSentencesFromPositions(positionsTargetedClause);
			String otherClause = generateSentencesFromPositions(positionsOtherClause);
			
			if(randomizeClauseOrder) {
				targetClauseFirst = Math.random() > 0.5;
			} 
			
			String firstClause = StringUtils.capitalize(targetClauseFirst ? targetedClause : otherClause);
			String secondClause = targetClauseFirst ? otherClause : targetedClause;
			String delimiter = targetClauseFirst && useIfClause || !targetClauseFirst && !useIfClause ? ", " : " ";
			lines.add(firstClause + delimiter + secondClause + ".");
			
			if(!targetClauseFirst) {
				offsetIndex += (firstClause + delimiter).length();
			}

			for(Pair<Integer, Integer> target : targetPositions) {
				int startIndex = -1;
				int endIndex = -1;
				int j = 0;
				
				Pair<Integer, String> pos = positionsTargetedClause.get(j);
				while(startIndex < targetedClause.length() && pos != null && pos.first <= target.second) {
					if(pos.first <= target.first) {
						startIndex = targetedClause.indexOf(pos.second, startIndex + 1);
					}
					endIndex = targetedClause.indexOf(pos.second, endIndex) + pos.second.length();
					if(positionsTargetedClause.size() > j + 1) {
						pos = positionsTargetedClause.get(++j);
					} else {
						pos = null;
					}
				}
				
				if(startIndex != -1) {						
					indices.add(new Pair<>(startIndex + offsetIndex, endIndex + offsetIndex));
				}
			}
			
			if(targetClauseFirst) {
				offsetIndex += (firstClause + delimiter).length();
			}
			offsetIndex += secondClause.length();

			offsetIndex += 6;
		}
		
		ArrayList<String> indexRanges = new ArrayList<>();
		for(Pair<Integer, Integer> index : indices) {
			indexRanges.add(index.first + "-" + index.second);
		}
		item.target = StringUtils.join(indexRanges, ",");
		String text = StringUtils.join(lines, " <br>");
		for(Pair<Integer, Integer> index : indices) {
			text = text.substring(0, index.first) + text.substring(index.first, index.second).replace(" ", "%") + text.substring(index.second);
		}
		item.text = text;
		
		v.items.add(item);

		return new SimpleExerciseXmlGenerator().generateFeedBookInputXml(v);
	}
	
	public boolean containsCaseInsensitive(ArrayList<String> list, String string) {
        for (String s : list) {
            if (s.equalsIgnoreCase(string)) {
            	return true;
            }
        }
        return false;
    }
	
	private String generateSCTask(ArrayList<ExerciseConfigData> configData, boolean ifClauseFirst, 
			boolean targetIfClause, boolean targetMainClause, int nDistractors, boolean randomizeClauseOrder) {
		XmlValues v = new XmlValues();
		v.instructions = "Pick the correct answer for each gap.";
		v.taskOrient = "Geschlossen";
		v.taskType = "FILL_IN_THE_BLANKS";

		String prompt = "";
		Item previousGap = null;
		ArrayList<Pair<Integer, String>> currentGaps = new ArrayList<>();

		for(ExerciseConfigData itemData : configData) {			
			ArrayList<Pair<Integer,String>> ifPositions = new ArrayList<>(itemData.getPositionsIfClause());
			ArrayList<Pair<Integer,String>> mainPositions = new ArrayList<>(itemData.getPositionsMainClause());
			
			ArrayList<Pair<Integer,String>> positions;
			ArrayList<Pair<Integer, Integer>> targetPositions = new ArrayList<>();
			ArrayList<ArrayList<Pair<Integer, String>>> targetDistractors = new ArrayList<>();
			
			if(randomizeClauseOrder) {
				ifClauseFirst = Math.random() > 0.5;
			} 
			
			if(ifClauseFirst) {
				positions = new ArrayList<>(ifPositions);
				positions.add(new Pair<>(positions.size() + 1, ","));
				for(Pair<Integer,String> p : mainPositions) {
					positions.add(new Pair<>(ifPositions.size() + 1 + p.first, p.second));
				}
				
				if(targetIfClause) {
					targetPositions.addAll(itemData.getGapIfClause());
					targetDistractors.add(itemData.getDistractorsIfClause());
				}
				if(targetMainClause) {
					for(Pair<Integer, Integer> tp : itemData.getGapMainClause()) {
						targetPositions.add(new Pair<>(ifPositions.size() + 1 + tp.first, ifPositions.size() + 1 + tp.second));
					}
					targetDistractors.add(itemData.getDistractorsMainClause());
				}

			} else {
				positions = new ArrayList<>(mainPositions);
				for(Pair<Integer,String> p : ifPositions) {
					positions.add(new Pair<>(mainPositions.size() + 1 + p.first, p.second));
				}
				
				if(targetMainClause) {
					targetPositions.addAll(itemData.getGapMainClause());
					targetDistractors.add(itemData.getDistractorsMainClause());
				}
				
				if(targetIfClause) {
					for(Pair<Integer, Integer> tp : itemData.getGapIfClause()) {
						targetPositions.add(new Pair<>(mainPositions.size() + 1 + tp.first, mainPositions.size() + 1 + tp.second));
					}
					targetDistractors.add(itemData.getDistractorsIfClause());
				}
			}
			
			positions.set(0, new Pair<>(positions.get(0).first, StringUtils.capitalize(positions.get(0).second)));
			
			ArrayList<Pair<Integer, String>> currentPositions = new ArrayList<>();
			for(Pair<Integer, Integer> gap : targetPositions) {
				ArrayList<Pair<Integer, String>> positionsToRemove = new ArrayList<>();
				for(Pair<Integer, String> position : positions) {
					if(position.first <= gap.second &&  position.first >= gap.first) {
						currentGaps.add(position);
					}
					
					// get everything before the current gap
					if(position.first < gap.first) {
						currentPositions.add(position);
					} else if(position.first == gap.second)  {
						if(currentPositions.size() > 0) {
							prompt += generateSentencesFromPositions(currentPositions);
							if(previousGap == null) {
								Item item = new Item();
								item.text = prompt;
								item.example = "null";
								v.items.add(item);
							} else {
								previousGap.text = prompt;
								v.items.add(previousGap);
							}
							
							previousGap = new Item();
							previousGap.inputType = "MUL_CHOICE_BLANK";
							
					    	Collections.shuffle(targetDistractors);
							ArrayList<String> distractors = new ArrayList<>();
							while(distractors.size() < nDistractors) {
								distractors.add(targetDistractors.get(0).get(distractors.size()).second);
							}
							targetDistractors.remove(0);
							distractors.add(generateSentencesFromPositions(currentGaps));
							previousGap.example = StringUtils.join(distractors, "|");
							previousGap.target = (distractors.indexOf(generateSentencesFromPositions(currentGaps)) + 1) + "";
							currentGaps.clear();
						
							prompt = "";
							currentPositions.clear();
						}
					} 

					if(position.first <= gap.second) {
						positionsToRemove.add(position);
					} else {
						break;
					}
				}
				
				// remove the positions so that they aren't added to the prompt again
				for(Pair<Integer, String> position : positionsToRemove) {
					positions.remove(position);
				}
			}
			
			// add everything behind the last gap to the prompt
			if(positions.size() > 0) {
				prompt += generateSentencesFromPositions(positions);
			}
					
			prompt += ".<br>";
		}
		
		if(prompt.endsWith("<br>")) {
			prompt = prompt.substring(0, prompt.length() - 4).trim();
		}
		if(!prompt.isEmpty() && previousGap != null) {			
			previousGap.text = prompt;
			v.items.add(previousGap);
		}
				
		return new SimpleExerciseXmlGenerator().generateFeedBookInputXml(v);
	}
	
	private String generateFiBTask(ArrayList<ExerciseConfigData> configData, boolean ifClauseFirst, 
			boolean targetIfClause, boolean targetMainClause, boolean lemmasInBrackets, boolean useDistractorLemma,
			boolean targetEntireClause, boolean randomizeClauseOrder) {
		XmlValues v = new XmlValues();
		v.instructions = targetEntireClause ? 
				"Use the verbs in brackets to form a if-clause." :
				!lemmasInBrackets ?
					"Read each sentence. Which verb fits? Fill in the gap with the correct form of the verb. " :
					useDistractorLemma ?
						"Read each sentence. Which verb fits? Fill in the gap with the correct form of the verb. " :
						"Fill in the gap to form a correct sentence.";
								
		v.taskType = "FILL_IN_THE_BLANKS";

		String prompt = "";
		Item previousGap = null;
		ArrayList<String> allLemmas = new ArrayList<>();
		ArrayList<String> allDistractorLemmas = new ArrayList<>();
		ArrayList<Pair<Integer, String>> currentGaps = new ArrayList<>();

		for(ExerciseConfigData itemData : configData) {			
			ArrayList<Pair<Integer,String>> ifPositions = new ArrayList<>(itemData.getPositionsIfClause());
			ArrayList<Pair<Integer,String>> mainPositions = new ArrayList<>(itemData.getPositionsMainClause());
			
			ArrayList<Pair<Integer,String>> positions;
			ArrayList<Pair<Integer, Integer>> targetPositions = new ArrayList<>();
			ArrayList<String> distractorLemmas = new ArrayList<>();
			ArrayList<String> lemmas = new ArrayList<>();
			ArrayList<ArrayList<String>> givenLemmas = new ArrayList<>();

			if(randomizeClauseOrder) {
				ifClauseFirst = Math.random() > 0.5;
			} 
			
			if(ifClauseFirst) {
				positions = new ArrayList<>(ifPositions);
				positions.add(new Pair<>(positions.size() + 1, ","));
				for(Pair<Integer,String> p : mainPositions) {
					positions.add(new Pair<>(ifPositions.size() + 1 + p.first, p.second));
				}
				
				if(targetIfClause) {
					if(targetEntireClause) {
						targetPositions.add(new Pair<>(2, ifPositions.size()));
					} else {
						targetPositions.addAll(itemData.getGapIfClause());
					}
					distractorLemmas.add(itemData.getDistractorLemmaIfClause());
					lemmas.add(itemData.getLemmaIfClause());
					givenLemmas.add(itemData.getBracketsIfClause());
				}
				if(targetMainClause) {
					if(targetEntireClause) {
						targetPositions.add(new Pair<>(ifPositions.size() + 2, ifPositions.size() + mainPositions.size() + 1));
					} else {
						for(Pair<Integer, Integer> tp : itemData.getGapMainClause()) {
							targetPositions.add(new Pair<>(ifPositions.size() + 1 + tp.first, ifPositions.size() + 1 + tp.second));
						}
					}
					distractorLemmas.add(itemData.getDistractorLemmaMainClause());
					lemmas.add(itemData.getLemmaMainClause());
					givenLemmas.add(itemData.getBracketsMainClause());
				}

			} else {
				positions = new ArrayList<>(mainPositions);
				for(Pair<Integer,String> p : ifPositions) {
					positions.add(new Pair<>(mainPositions.size() + 1 + p.first, p.second));
				}
				
				if(targetMainClause) {
					if(targetEntireClause) {
						targetPositions.add(new Pair<>(1, mainPositions.size()));
					} else {
						targetPositions.addAll(itemData.getGapMainClause());
					}
					distractorLemmas.add(itemData.getDistractorLemmaMainClause());
					lemmas.add(itemData.getLemmaMainClause());
					givenLemmas.add(itemData.getBracketsMainClause());
				}
				
				if(targetIfClause) {
					if(targetEntireClause) {
						targetPositions.add(new Pair<>(mainPositions.size() + 2, mainPositions.size() + ifPositions.size() + 1));
					} else {
						for(Pair<Integer, Integer> tp : itemData.getGapIfClause()) {
							targetPositions.add(new Pair<>(mainPositions.size() + 1 + tp.first, mainPositions.size() + 1 + tp.second));
						}
					}
					distractorLemmas.add(itemData.getDistractorLemmaIfClause());
					lemmas.add(itemData.getLemmaIfClause());
					givenLemmas.add(itemData.getBracketsIfClause());
				}
			}
			allLemmas.addAll(lemmas);
			allDistractorLemmas.addAll(distractorLemmas);

			positions.set(0, new Pair<>(positions.get(0).first, StringUtils.capitalize(positions.get(0).second)));
			
			ArrayList<Pair<Integer, String>> currentPositions = new ArrayList<>();

			for(Pair<Integer, Integer> gap : targetPositions) {
				ArrayList<Pair<Integer, String>> positionsToRemove = new ArrayList<>();
				for(Pair<Integer, String> position : positions) {
					if(position.first <= gap.second &&  position.first >= gap.first) {
						currentGaps.add(position);
					}
					
					// get everything before the current gap
					if(position.first < gap.first) {
						currentPositions.add(position);
					} else if(position.first == gap.second)  {
						prompt += generateSentencesFromPositions(currentPositions);
						
						if(previousGap == null) {
							if(!prompt.isEmpty()) {
								Item item = new Item();
								item.text = prompt;
								v.items.add(item);
							}
						} else {
							previousGap.text = prompt;
							v.items.add(previousGap);
						}
						
						previousGap = new Item();
						previousGap.target = generateSentencesFromPositions(currentGaps);
						currentGaps.clear();
						previousGap.inputType = previousGap.target.matches(".*?[\\s\\h\\v].*?") ? "PHRASE" : "WORD";
				
						prompt = "";
						if(targetEntireClause) {
							prompt = "(" + StringUtils.join(givenLemmas.get(0), ",") + ") ";
							givenLemmas.remove(0);
						} else {
							if(lemmasInBrackets) {
								ArrayList<String> bracketsLemmas = new ArrayList<>();
								bracketsLemmas.add(lemmas.get(0));
								lemmas.remove(0);
								if(useDistractorLemma) {
									bracketsLemmas.add(distractorLemmas.get(0));
									distractorLemmas.remove(0);
								}		
								
								Collections.shuffle(bracketsLemmas);
								
								prompt = "(" + StringUtils.join(bracketsLemmas, "/") + ") ";
							}
						}
						currentPositions.clear();
					} 

					if(position.first <= gap.second) {
						positionsToRemove.add(position);
					} else {
						break;
					}
				}
				
				// remove the positions so that they aren't added to the prompt again
				for(Pair<Integer, String> position : positionsToRemove) {
					positions.remove(position);
				}
			}
			
			// add everything behind the last gap to the prompt
			if(positions.size() > 0) {
				prompt += generateSentencesFromPositions(positions);
			}
					
			prompt += ".<br>";
		}
		
		if(prompt.endsWith("<br>")) {
			prompt = prompt.substring(0, prompt.length() - 4).trim();
		}
		if(!prompt.isEmpty() && previousGap != null) {			
			previousGap.text = prompt;
			v.items.add(previousGap);
		}
		
		if(!lemmasInBrackets) {
			HashSet<String> set = new HashSet<String>(allDistractorLemmas);
			allDistractorLemmas.clear();
			allDistractorLemmas.addAll(set);
			for(int i = 0; i < 2; i++) {
				Collections.shuffle(allDistractorLemmas);
				allLemmas.add(allDistractorLemmas.get(0));
				allDistractorLemmas.remove(0);
			}
			Collections.shuffle(allLemmas);
			v.instructionWords = StringUtils.join(allLemmas, " | ");
		}
				
		return new SimpleExerciseXmlGenerator().generateFeedBookInputXml(v);
	}
	
	private String generateJSTask(ArrayList<ExerciseConfigData> configData, boolean ifClauseFirst, 
			boolean targetIfClause, boolean targetMainClause, boolean randomizeClauseOrder) {
		XmlValues v = new XmlValues();
		v.instructions = "Put the parts of a sentence into a correct order.";
		v.taskType = "JUMBLED_SENTENCES";

		for(ExerciseConfigData itemData : configData) {			
			ArrayList<Pair<Integer,String>> ifPositions = new ArrayList<>(itemData.getPositionsIfClause());
			ArrayList<Pair<Integer,String>> mainPositions = new ArrayList<>(itemData.getPositionsMainClause());
			
			ArrayList<Pair<Integer,String>> positions = new ArrayList<>();

			if(randomizeClauseOrder) {
				ifClauseFirst = Math.random() > 0.5;
			} 
			
			if(ifClauseFirst) {
				ifPositions.add(new Pair<>(ifPositions.size() + 1, ","));
				mainPositions.add(new Pair<>(0, "."));
				if(targetIfClause) {
					positions.addAll(ifPositions);
				} else {
					positions.add(new Pair<>(0, generateSentencesFromPositions(ifPositions)));
				}
				
				if(targetMainClause) {
					positions.addAll(mainPositions);
				} else {
					positions.add(new Pair<>(0, generateSentencesFromPositions(mainPositions)));
				}
			} else {
				ifPositions.add(new Pair<>(0, "."));

				if(targetMainClause) {
					positions.addAll(mainPositions);
				} else {
					positions.add(new Pair<>(0, generateSentencesFromPositions(mainPositions)));
				}
				
				if(targetIfClause) {
					positions.addAll(ifPositions);
				} else {
					positions.add(new Pair<>(0, generateSentencesFromPositions(ifPositions)));
				}
			}
			
			positions.set(0, new Pair<>(positions.get(0).first, StringUtils.capitalize(positions.get(0).second)));
			
			ArrayList<String> parts = new ArrayList<>();
			for(Pair<Integer, String> position : positions) {
				parts.add(position.second);
			}
			
			Item item = new Item();
			item.target = StringUtils.join(parts, "|");
			Collections.shuffle(parts);
			item.text = StringUtils.join(parts, "|");
			item.inputType = "JUMBLED_SENTENCE_PARTS";
			v.items.add(item);
						
		}
						
		return new SimpleExerciseXmlGenerator().generateFeedBookInputXml(v);
	}
	
	private static final String punctuations = ".,:;!?";

	private String generateSentencesFromPositions(ArrayList<Pair<Integer, String>> positions) {
		StringBuilder sb = new StringBuilder();
		for(Pair<Integer, String> position : positions) {
			if(!position.second.isEmpty()) {
				if(!(position.first == 1) && !punctuations.contains(position.second.charAt(0) + "")) {
					sb.append(" ");
				}
				sb.append(position.second);
			}
		}
		
		return sb.toString().trim();
	}
	
	public static byte[] zipFiles(HashMap<String, ArrayList<HashMap<String, byte[]>>> activities) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        try {
        	for(Entry<String, ArrayList<HashMap<String, byte[]>>> blocks : activities.entrySet()) {
	        	int i = 0;
	        	for(HashMap<String, byte[]> block : blocks.getValue()) {
	        		for(Entry<String, byte[]> entry : block.entrySet()) {
	                    ZipEntry zipEntry = new ZipEntry(blocks.getKey() + "/" + i + "/" + entry.getKey() + ".xml");
	                    zipOutputStream.putNextEntry(zipEntry);
	                    zipOutputStream.write(entry.getValue());
	                    zipOutputStream.closeEntry();
	            	}
	        		i++;
	        	}
        	}
        } catch (IOException e) {
			ServerLogger.get().error(e, "File could not be zipped. Exception: " + e.toString());
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
    			ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
            }
        }
        
        byte[] outputArray = byteArrayOutputStream.toByteArray();
        try {
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return outputArray;
    }
	
	@Override
	public ArrayList<OutputComponents> generateExercise(ContentTypeSettings settings,
		CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
		
		ConfigExerciseSettings exerciseSettings = (ConfigExerciseSettings)settings.getExerciseSettings();
		
		ConditionalConfigParser p = new ConditionalConfigParser();
		HashMap<String,ArrayList<HashMap<String,ExerciseData>>> exerciseData = p.parseConfigFile(new ByteArrayInputStream(exerciseSettings.getFileStream()));
		
		HashMap<String, ArrayList<HashMap<String, byte[]>>> activities = new HashMap<>();
        ArrayList<OutputComponents> res = new ArrayList<>();


    	for(Entry<String, ArrayList<HashMap<String, ExerciseData>>> entry : exerciseData.entrySet()) { 
            ArrayList<HashMap<String, byte[]>> blocks = new ArrayList<>();
    		
    		for(HashMap<String, ExerciseData> block : entry.getValue()) {
        		HashMap<String, byte[]> files = new HashMap<>();

    			for(Entry<String, ExerciseData> exercise : block.entrySet()) {
        			SimpleExerciseXmlGenerator g = XmlGeneratorFactory.getXmlGenerator(exercise.getKey());
        			byte[] file = g.generateXMLFile(exercise.getValue(), exercise.getKey());
        			files.put(exercise.getKey(), file);
    			}
    			
    			blocks.add(files);
    		}
    		
    		activities.put(entry.getKey(), blocks);
    	}
		
		
		/*
    	ArrayList<ExerciseConfigData> configData = readExcelFile(new ByteArrayInputStream(exerciseSettings.getFileStream()));
    	
    	HashMap<String, ArrayList<ExerciseConfigData>> configs = new HashMap<>();
    	for(ExerciseConfigData cd : configData) {
    		String key = cd.getActivity() + (cd.isType1VsType2() ? "a" : "b");
    		if(!configs.containsKey(key)) {
    			configs.put(key, new ArrayList<>());
    		}
    		configs.get(key).add(cd);
    	}
    	
        ArrayList<OutputComponents> res = new ArrayList<>();
        HashMap<String, ArrayList<HashMap<String, byte[]>>> activities = new HashMap<>();


    	for(Entry<String, ArrayList<ExerciseConfigData>> entry : configs.entrySet()) { 
            ArrayList<HashMap<String, byte[]>> blocks = new ArrayList<>();
    		HashMap<String, byte[]> files = new HashMap<>();

    		if(entry.getValue().get(0).isType1VsType2()) {        
	    		files = new HashMap<>();
	    		// categorization if first
	    		files.put("" + 7, generateCategorizationTask(entry.getValue(), true, false).getBytes(StandardCharsets.UTF_8));
	    		//if-clause, if first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), true, true, false, 1, false).getBytes(StandardCharsets.UTF_8));    		
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), true, true, false, 3, false).getBytes(StandardCharsets.UTF_8));    		
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), true, true, false, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), true, true, false, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), true, true, false, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	                        
	    		files = new HashMap<>();
	    		// categorization main first
	    		files.put("" + 7, generateCategorizationTask(entry.getValue(), false, false).getBytes(StandardCharsets.UTF_8));
	    		// if-clause, main first
	    		// sc 1d 
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, false, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, false, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);

	    		files = new HashMap<>();
	    		// categorization random first
	    		files.put("" + 7, generateCategorizationTask(entry.getValue(), false, true).getBytes(StandardCharsets.UTF_8));
	    		// if-clause, random first
	    		// sc 1d 
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, false, 1, true).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, false, 3, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, true, false, true).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, true, true).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		//main clause, if first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), true, false, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), true, false, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), true, false, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), true, false, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), true, false, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	
	    		files = new HashMap<>();
	    		// main clause, main first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, false, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, false, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// main clause, random first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, false, true, 1, true).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, false, true, 3, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, true, false, true).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, true, true).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	            
	    		files = new HashMap<>();
	    		// both clauses, if first
	    		// sc 1d 
	    		files.put("" + 1, generateSCTask(entry.getValue(), true, true, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), true, true, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), true, true, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), true, true, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), true, true, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// both clauses, main first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// both clauses, random first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, true, 1, true).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, true, 3, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, true, false, true).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, true, true).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
    		} else {
	    		files = new HashMap<>();
	    		// memory if-clause
	    		files.put("" + 0, generateMemoryTask(entry.getValue(), true).getBytes(StandardCharsets.UTF_8));
	    		//if-clause, if first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), true, true, false, 1, false).getBytes(StandardCharsets.UTF_8));    		
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), true, true, false, 3, false).getBytes(StandardCharsets.UTF_8));    		
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), true, true, false, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), true, true, false, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib lemma instuctions
	    		files.put("" + 5, generateFiBTask(entry.getValue(), true, true, false, 
	    				false, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		// jumbled sentences
	    		files.put("" + 6, generateJSTask(entry.getValue(), true, true, false, false)
	    				.getBytes(StandardCharsets.UTF_8));
	    		// underline
	    		files.put("" + 8, generateUnderlineTask(entry.getValue(), true, true, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), true, true, false, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	            
	    		files = new HashMap<>();
	    		// if-clause, main first
	    		// sc 1d 
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, false, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, false, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib lemma instuctions
	    		files.put("" + 5, generateFiBTask(entry.getValue(), false, true, false, 
	    				false, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		// jumbled sentences
	    		files.put("" + 6, generateJSTask(entry.getValue(), false, true, false, false)
	    				.getBytes(StandardCharsets.UTF_8));
	    		// underline
	    		files.put("" + 8, generateUnderlineTask(entry.getValue(), false, true, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// if-clause, random first
	    		// sc 1d 
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, false, 1, true).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, false, 3, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, true, false, true).getBytes(StandardCharsets.UTF_8));
	    		// fib lemma instuctions
	    		files.put("" + 5, generateFiBTask(entry.getValue(), false, true, false, 
	    				false, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		// jumbled sentences
	    		files.put("" + 6, generateJSTask(entry.getValue(), false, true, false, true)
	    				.getBytes(StandardCharsets.UTF_8));
	    		// underline
	    		files.put("" + 8, generateUnderlineTask(entry.getValue(), false, true, true).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, false, 
	    				true, false, true, true).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// memory main clause
	    		files.put("" + 0, generateMemoryTask(entry.getValue(), false).getBytes(StandardCharsets.UTF_8));
	    		//main clause, if first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), true, false, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), true, false, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), true, false, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), true, false, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib lemma instuctions
	    		files.put("" + 5, generateFiBTask(entry.getValue(), true, false, true, 
	    				false, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		// jumbled sentences
	    		files.put("" + 6, generateJSTask(entry.getValue(), true, false, true, false)
	    				.getBytes(StandardCharsets.UTF_8));
	    		// underline 
	    		files.put("" + 8, generateUnderlineTask(entry.getValue(), false, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), true, false, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	            
	    		files = new HashMap<>();
	    		// main clause, main first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, false, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, false, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib lemma instuctions
	    		files.put("" + 5, generateFiBTask(entry.getValue(), false, false, true, 
	    				false, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		// jumbled sentences
	    		files.put("" + 6, generateJSTask(entry.getValue(), false, false, true, false)
	    				.getBytes(StandardCharsets.UTF_8));
	    		// underline 
	    		files.put("" + 8, generateUnderlineTask(entry.getValue(), true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// main clause, random first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, false, true, 1, true).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, false, true, 3, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, true, false, true).getBytes(StandardCharsets.UTF_8));
	    		// fib lemma instuctions
	    		files.put("" + 5, generateFiBTask(entry.getValue(), false, false, true, 
	    				false, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		// jumbled sentences
	    		files.put("" + 6, generateJSTask(entry.getValue(), false, false, true, true)
	    				.getBytes(StandardCharsets.UTF_8));
	    		// underline 
	    		files.put("" + 8, generateUnderlineTask(entry.getValue(), true, false, true).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, false, true, 
	    				true, false, true, true).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	            
	    		files = new HashMap<>();
	    		// both clauses, if first
	    		// sc 1d 
	    		files.put("" + 1, generateSCTask(entry.getValue(), true, true, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), true, true, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), true, true, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), true, true, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// jumbled sentences
	    		files.put("" + 6, generateJSTask(entry.getValue(), true, true, true, false)
	    				.getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), true, true, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// both clauses, main first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, true, 1, false).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, true, 3, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, false, false).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, true, false, false).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, true, false).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
	    		
	    		files = new HashMap<>();
	    		// both clauses, random first
	    		// sc 1d
	    		files.put("" + 1, generateSCTask(entry.getValue(), false, true, true, 1, true).getBytes(StandardCharsets.UTF_8));
	    		// sc 3d
	    		files.put("" + 2, generateSCTask(entry.getValue(), false, true, true, 3, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma blank
	    		files.put("" + 3, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, false, true).getBytes(StandardCharsets.UTF_8));
	    		//fib lemma/distractor blank
	    		files.put("" + 4, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, true, false, true).getBytes(StandardCharsets.UTF_8));
	    		// fib entire clause brackets
	    		files.put("" + 9, generateFiBTask(entry.getValue(), false, true, true, 
	    				true, false, true, true).getBytes(StandardCharsets.UTF_8));
	    		blocks.add(files);
    		}
           
    		
        		
    		
    		// for underline exercises evtl. separate exercise per line
    		//TODO: all support texts?
    		//TODO: generate feedback if requested

    		activities.put(entry.getKey(), blocks);
    		
        	
    	}
*/
    	HashMap<String, byte[]> hm = new HashMap<>();
    	hm.put("Generated_exercises", zipFiles(activities));
    	OutputComponents output = new OutputComponents(null, null, null, null, null, null, null, "feedbookExercises", null);
    	output.setZipFiles(hm);
        res.add(output);
        
        return res;
    }

	private ArrayList<ExerciseConfigData> readExcelFile(InputStream inputStream) {
		try (XSSFWorkbook wb = new XSSFWorkbook(inputStream)) {
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;

			int rows; // # rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // # columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it doesn't start from first few rows
			for(int i = 0; i < 10 || i < rows; i++) {
			    row = sheet.getRow(i);
			    if(row != null) {
			        tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			        if(tmp > cols) cols = tmp;
			    }
			}

			HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
			HashMap<Integer, String> columnHeaders = new HashMap<>();

			// get the column index of if clause positions and main clause positions
			row = sheet.getRow(0);
			int ifClausePositionsStart = 0;
			int mainClausePositionsStart = 0;
			for(int c = 0; c < cols; c++) {
				cell = row.getCell((short)c);
	            if(cell != null && cell.toString().equals("if-clause position")) {
	            	ifClausePositionsStart = c;
	            } else if(cell != null && cell.toString().equals("main-clause position")) {
	            	mainClausePositionsStart = c;
	            }
			}
			
			int counter = 0;
			int r = 0;
			int activityCounter = 1;
			String activityType = "";
			while(counter < rows) {
			    row = sheet.getRow(r);
			    if(row != null) {    
			        for(int c = 0; c < cols; c++) {
			            cell = row.getCell((short)c);

			            if(cell != null && !cell.toString().isEmpty()) {
			            	String cellValue = cell.toString();
		            		
		            		if(cellValue.trim().equals("If-clause") || cellValue.trim().startsWith("IV conditional type 1 vs conditional type 2")) {
		            			activityType = cellValue.trim().equals("If-clause") ? "if" : "1vs2";
		            			activityCounter = 1;
		            		}
		            		
			            	if(activityCounter == 1) {
			            		if(!columnValues.containsKey("Activity type")) {
			            			columnValues.put("Activity type", new ArrayList<String>());
					        		columnHeaders.put(-1, "Activity type");
			            		}
			            	} else if(activityCounter == 2) {
			            		if(c >= ifClausePositionsStart && c < mainClausePositionsStart) {
			            			cellValue = "if-clause position " + cellValue;
			            		} else if(c >= mainClausePositionsStart) {
			            			cellValue = "main-clause position " + cellValue;
			            		}
				        		// initialize the hash map with the column headers
			            		if(!columnValues.containsKey(cellValue)) {
					        		columnValues.put(cellValue, new ArrayList<String>());
					        		columnHeaders.put(c, cellValue);
			            		}
				        	} else {
				        		if(c == 0) {
				        			ArrayList<String> column = columnValues.get("Activity type");
					        		column.add(activityType);
				        		}
				        		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
				        		column.add(cell.toString());
				        	}
			            } else {
			            	if(activityCounter != 1 && sheet.getRow(1).getCell((short)c) != null && !sheet.getRow(1).getCell((short)c).toString().isEmpty() && row.getCell(0) != null) {
			            		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
				        		column.add("");
			            	}
			            }
			        }
			        
			        counter++;
			        activityCounter++;
			    }
			    r++;
			}
				    	
			ArrayList<ExerciseConfigData> configData = new ArrayList<>();
			boolean isFirstCol = true;
			for(Entry<Integer, String> entry : columnHeaders.entrySet()) {	// columns
				for(int i = 0; i < columnValues.get(entry.getValue()).size(); i++) {	// rows
					if(isFirstCol) {
						// it's a new activity
						configData.add(new ExerciseConfigData());
						configData.get(i).setType1VsType2(columnValues.get("Activity type").get(i).equals("1vs2"));
					}
					if(entry.getValue().trim().equals("Aufgabennr.")) {
						configData.get(i).setActivity((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i).trim()));
			    	} else if(entry.getValue().trim().equals("stamp")) {
						configData.get(i).setStamp(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("item")) {
						configData.get(i).setItem((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("type 1 or type 2")) {
						configData.get(i).setConditionalType((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("If clause")) {
						configData.get(i).setIfClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("Main Clause")) {
						configData.get(i).setMainClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("Übersetzung if-clause")) {
						configData.get(i).setTranslationIfClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("übersetzung main clause")) {
						configData.get(i).setTranslationMainClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("if-clause distractor 1")) {
			    		configData.get(i).getDistractorsIfClause().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("if-clause distractor 2")) {
			    		configData.get(i).getDistractorsIfClause().add(new Pair<>(2, columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("if-clause distractor 3")) {
			    		configData.get(i).getDistractorsIfClause().add(new Pair<>(3, columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("main clause distractor 1")) {
			    		configData.get(i).getDistractorsMainClause().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("main clause distractor 2")) {
			    		configData.get(i).getDistractorsMainClause().add(new Pair<>(2, columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("main clause distractor 3")) {
			    		configData.get(i).getDistractorsMainClause().add(new Pair<>(3, columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("given in brackets if-clause")) {
			    		configData.get(i).setLemmaIfClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("given in brackets main clause")) {
			    		configData.get(i).setLemmaMainClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("semantic distractor if clause")) {
			    		configData.get(i).setDistractorLemmaIfClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("semantic distractor main clause")) {
			    		configData.get(i).setDistractorLemmaMainClause(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("given words für halb-offene Aufgaben if-clause")) {
			    		String brackets = StringUtils.strip(columnValues.get(entry.getValue()).get(i).trim(), "()");
			    		String[] elements = brackets.split(",");
			    		for(String element : elements) {
				    		configData.get(i).getBracketsIfClause().add(element.trim());
			    		}
			    	} else if(entry.getValue().trim().equals("given words  für halb-offene Aufgaben main clause")) {
			    		String brackets = StringUtils.strip(columnValues.get(entry.getValue()).get(i).trim(), "()");
			    		String[] elements = brackets.split(",");
			    		for(String element : elements) {
				    		configData.get(i).getBracketsMainClause().add(element.trim());
			    		}
			    	} else if(entry.getValue().trim().equals("gap if-clause")) {
			    		String value = columnValues.get(entry.getValue()).get(i);
			    		String[] gapParts = value.split(",");
			    		for(String gapPart : gapParts) {
				    		int startIndex = 0; 
				    		int endIndex = 0;

				    		try {
				    			String[] parts = gapPart.split("-");
				    			if(parts.length == 2) {
				    				startIndex = (int)Float.parseFloat(parts[0].trim());
				    				endIndex = (int)Float.parseFloat(parts[1].trim());
				    			} else if (parts.length == 1) {
					    			startIndex = (int)Float.parseFloat(gapPart.trim());
					    			endIndex = startIndex;
					    		}
				    		} catch(Exception e) { }
				    		
				    		if(startIndex != 0 && endIndex != 0) {
					    		configData.get(i).getGapIfClause().add(new Pair<>(startIndex, endIndex));
				    		}
			    		}
			    	} else if(entry.getValue().trim().equals("if-clause underline")) {
			    		String value = columnValues.get(entry.getValue()).get(i);
			    		String[] gapParts = value.split(",");
			    		for(String gapPart : gapParts) {
				    		int startIndex = 0; 
				    		int endIndex = 0;

				    		try {
				    			String[] parts = gapPart.split("-");
				    			if(parts.length == 2) {
				    				startIndex = (int)Float.parseFloat(parts[0].trim());
				    				endIndex = (int)Float.parseFloat(parts[1].trim());
				    			} else if (parts.length == 1) {
					    			startIndex = (int)Float.parseFloat(gapPart.trim());
					    			endIndex = startIndex;
					    		}
				    		} catch(Exception e) { }
				    		
				    		if(startIndex != 0 && endIndex != 0) {
					    		configData.get(i).getUnderlineIfClause().add(new Pair<>(startIndex, endIndex));
				    		}
			    		}
			    	} else if(entry.getValue().trim().equals("gap main clause")) {
			    		String value = columnValues.get(entry.getValue()).get(i);
			    		String[] gapParts = value.split(",");
			    		for(String gapPart : gapParts) {
				    		int startIndex = 0; 
				    		int endIndex = 0;

				    		try {
				    			String[] parts = gapPart.split("-");
				    			if(parts.length == 2) {
				    				startIndex = (int)Float.parseFloat(parts[0].trim());
				    				endIndex = (int)Float.parseFloat(parts[1].trim());
				    			} else if (parts.length == 1) {
					    			startIndex = (int)Float.parseFloat(gapPart.trim());
					    			endIndex = startIndex;
					    		}
				    		} catch(Exception e) { }
				    		
				    		if(startIndex != 0 && endIndex != 0) {
					    		configData.get(i).getGapMainClause().add(new Pair<>(startIndex, endIndex));
				    		}
			    		}
			    	} else if(entry.getValue().trim().equals("main clause underline")) {
			    		String value = columnValues.get(entry.getValue()).get(i);
			    		String[] gapParts = value.split(",");
			    		for(String gapPart : gapParts) {
				    		int startIndex = 0; 
				    		int endIndex = 0;

				    		try {
				    			String[] parts = gapPart.split("-");
				    			if(parts.length == 2) {
				    				startIndex = (int)Float.parseFloat(parts[0].trim());
				    				endIndex = (int)Float.parseFloat(parts[1].trim());
				    			} else if (parts.length == 1) {
					    			startIndex = (int)Float.parseFloat(gapPart.trim());
					    			endIndex = startIndex;
					    		}
				    		} catch(Exception e) { }
				    		
				    		if(startIndex != 0 && endIndex != 0) {
					    		configData.get(i).getUnderlineMainClause().add(new Pair<>(startIndex, endIndex));
				    		}
			    		}
			    	} else if(entry.getValue().trim().startsWith("if-clause position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
			    		configData.get(i).getPositionsIfClause().add(new Pair<>((int)Float.parseFloat(entry.getValue().trim().substring(19).trim()), columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().startsWith("main-clause position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
			    		configData.get(i).getPositionsMainClause().add(new Pair<>((int)Float.parseFloat(entry.getValue().trim().substring(21).trim()), columnValues.get(entry.getValue()).get(i)));
			    	}
				}	
				isFirstCol = false;
			}
			
			for(ExerciseConfigData cd : configData) {
				Collections.sort(cd.getDistractorsIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getDistractorsMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getGapIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getGapMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getUnderlineIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getUnderlineMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getPositionsIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getPositionsMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
			}
			
			return configData;
		} catch(Exception e) { 
			ServerLogger.get().error("Excel config file could not be read.");
		}
		
		return null;
	}

	@Override
	public void cancelGeneration() {}
	

}