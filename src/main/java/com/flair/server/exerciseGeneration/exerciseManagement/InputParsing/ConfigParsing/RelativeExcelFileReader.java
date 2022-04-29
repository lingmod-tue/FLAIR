package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flair.shared.exerciseGeneration.Pair;

public class RelativeExcelFileReader extends ExcelFileReader {
	
	@Override
	protected ExcelFileElements extractCellValues(XSSFSheet sheet, int nRows, int nCols) {
		XSSFRow row;
		XSSFCell cell;
		
		HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
		HashMap<Integer, String> columnHeaders = new HashMap<>();

		// get the column indices of clause positions
		row = sheet.getRow(0);
		int clause1PositionsStart = 0;
		int clause2PositionsStart = 0;
		int distractorsStart = 0;
		for(int c = 0; c < nCols; c++) {
			cell = row.getCell((short)c);
            if(cell != null && cell.toString().equals("Main clause 1 chunks")) {
            	clause1PositionsStart = c;
            } else if(cell != null && cell.toString().equals("Main clause 2 chunks")) {
            	clause2PositionsStart = c;
            } else if(cell != null && cell.toString().equals("Distractor")) {
            	distractorsStart = c;
            }
		}
		
		row = sheet.getRow(1);
		int clause1PositionsEnd = 0;	// exclusive
		int clause2PositionsEnd = 0;	// exclusive
		for(int c = 0; c < nCols; c++) {
			cell = row.getCell((short)c);
            if(cell != null && cell.toString().equals("Main clause 2")) {
            	clause1PositionsEnd = c;
            } else if(cell != null && cell.toString().equals("Relative pronoun")) {
            	clause2PositionsEnd = c;
            }
		}
		
		int counter = 1;
		int r = 1;
		while(counter < nRows) {
		    row = sheet.getRow(r);
		    if(row != null) {    
		        for(int c = 0; c < nCols; c++) {
		            cell = row.getCell((short)c);
		            if(cell != null) {
		            	cell.setCellType(CellType.STRING);
		            	String cellValue = xTrim(cell.toString());
	
			            if(!cellValue.isEmpty()) {
			            	if(r == 1) {
			            		if(c >= clause1PositionsStart && c < clause1PositionsEnd) {
			            			cellValue = "clause 1 position " + cellValue;
			            		} else if(c >= clause2PositionsStart && c < clause2PositionsEnd) {
			            			cellValue = "clause 2 position " + cellValue;
			            		} else if(c >= distractorsStart) {
			            			cellValue = "distractor " + cellValue;
			            		}
				        		// initialize the hash map with the column headers
			            		if(!columnValues.containsKey(cellValue)) {
					        		columnValues.put(cellValue, new ArrayList<String>());
					        		columnHeaders.put(c, cellValue);
			            		}
				        	} else {
				        		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
				        		if(column != null) {
				        			column.add(cellValue);
				        		}
				        	}
			            } else {
			            	if(r != 1 && sheet.getRow(1).getCell((short)c) != null && !sheet.getRow(1).getCell((short)c).toString().isEmpty() && row.getCell(3) != null) {
			            		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
			            		if(column != null) {
					        		column.add("");
				        		}
			            	}
			            }
		            } else {
		            	if(r != 1 && sheet.getRow(1).getCell((short)c) != null && !sheet.getRow(1).getCell((short)c).toString().isEmpty() && row.getCell(3) != null) {
		            		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
		            		if(column != null) {
				        		column.add("");
			        		}
		            	}
		            }
		        }
		        
		        counter++;
		    }
		    r++;
		}
		
		return new ExcelFileElements(columnHeaders, columnValues);
	}
	
	@Override
	protected ArrayList<ExerciseConfigData> readCellValues(ExcelFileElements excelElements) {
		HashMap<Integer, String> columnHeaders = excelElements.getColumnHeaders();
		HashMap<String, ArrayList<String>> columnValues = excelElements.getColumnValues();
				    	
		ArrayList<ExerciseConfigData> configData = new ArrayList<>();
		boolean isFirstCol = true;
		for(Entry<Integer, String> entry : columnHeaders.entrySet()) {	// columns
			for(int i = 0; i < columnValues.get(entry.getValue()).size(); i++) {	// rows
				if(isFirstCol) {
					// it's a new line
					ExerciseConfigData cd = new ExerciseConfigData();
					cd.getItemData().add(new RelativeExerciseItemConfigData());
					configData.add(cd);
				}
				ExerciseConfigData cd = configData.get(i);
				if(entry.getValue().equals("Aufgabennr.")) {
					cd.setActivity((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("stamp")) {
		    		cd.setStamp(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("toc nr.")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		if(value != null && !value.isEmpty()) {
		    			cd.setTocId(value);
		    		}
		    	} else if(entry.getValue().equals("item")) {
					cd.getItemData().get(0).setItem((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Relative pronoun")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getPronouns().add(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().startsWith("clause 1 position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setClause1(((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getClause1() + (int)Float.parseFloat(entry.getValue().substring(18)) + "~~~" +  columnValues.get(entry.getValue()).get(i) + "|");
		    	} else if(entry.getValue().startsWith("clause 2 position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setClause2(((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getClause2() + (int)Float.parseFloat(entry.getValue().substring(18)) + "~~~" +  columnValues.get(entry.getValue()).get(i) + "|");
		    	} else if(entry.getValue().startsWith("distractor ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getDistractors().add(columnValues.get(entry.getValue()).get(i));
		    	} 
			}	
			isFirstCol = false;
		}
		
		ArrayList<ExerciseConfigData> batchedExercises = new ArrayList<>();
		int lastActivity = 0;
		String lastStamp = "";
		String lastTocId = "";
		ArrayList<ExerciseItemConfigData> sentences = new ArrayList<>();
		for(ExerciseConfigData data: configData) {
			if(lastActivity != 0 && data.getActivity() != lastActivity) {
				ExerciseConfigData d = new ExerciseConfigData();
				d.setActivity(lastActivity);
				d.setStamp(lastStamp);
				d.setItemData(sentences);
				
				addRelativeSentences(sentences, lastStamp, lastActivity);
				
				d.setExerciseType(getExerciseTypes(d.getStamp()));
				d.setTocId(lastTocId);
				sentences = new ArrayList<>();
				batchedExercises.add(d);
			}
			
			sentences.add(data.getItemData().get(0));
			lastStamp = data.getStamp();
			lastActivity = data.getActivity();
			if(data.getTocId() != null) {
				lastTocId = data.getTocId();
			}
		}
		
		if(lastActivity != 0) {
			ExerciseConfigData d = new ExerciseConfigData();
			d.setActivity(lastActivity);
			d.setStamp(lastStamp);
			d.setItemData(sentences);

			addRelativeSentences(sentences, lastStamp, lastActivity);

			d.setExerciseType(getExerciseTypes(d.getStamp()));
			d.setTocId(lastTocId);
			batchedExercises.add(d);
		}
		
		for(ExerciseConfigData ecd : batchedExercises) {
			for(ExerciseItemConfigData d : ecd.getItemData()) {
				RelativeExerciseItemConfigData cd = (RelativeExerciseItemConfigData)d;
				String sentenceClause1 = StringUtils.capitalize(generateSentencesFromPositions(cd.getClause1()));
				if(!sentenceClause1.endsWith(".")) {
					sentenceClause1 += ".";
				}
				cd.setClause1("1|" + sentenceClause1);
				String sentenceClause2 = StringUtils.capitalize(generateSentencesFromPositions(cd.getClause2()));
				if(!sentenceClause2.endsWith(".")) {
					sentenceClause1 += ".";
				}
				cd.setClause2("1|" + sentenceClause2);
			}
		}
		
		return batchedExercises;
	}
		
	private ArrayList<ExerciseTypeSpec> getExerciseTypes(String stamp) {
		ArrayList<ExerciseTypeSpec> types = new ArrayList<>();
		
		if(stamp.equals("subject who/which")) {
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.MEMORY));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.UNDERLINE));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.SINGLE_CHOICE_2D));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.JUMBLED_SENTENCES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.HALF_OPEN));
		} else if(stamp.equals("object which/whom")) {
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.MEMORY));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.UNDERLINE));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.SINGLE_CHOICE_2D));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.JUMBLED_SENTENCES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.HALF_OPEN));
		} else if(stamp.equals("whose (+which, who and whom)")) {
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.MEMORY));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.UNDERLINE));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.SINGLE_CHOICE_4D));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.JUMBLED_SENTENCES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.HALF_OPEN));
		} else if(stamp.equals("where (+ which, whose, who)")) {
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.MEMORY));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.UNDERLINE));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.SINGLE_CHOICE_4D));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.JUMBLED_SENTENCES));
			types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.HALF_OPEN));
		}
		
		return types;
	}
	
	
	
	
	
	/**
	 * Assembles the positions of the two clauses into the order of the requested relative sentence.
	 * Enriches the positions with information on common referent and pronoun.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1AsRelativeClause	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @param extrapose				<code>true</code> if the extraposed order is required
	 * @return	The positions of the relative sentence
	 */
	private ArrayList<RelativeClausePosition> determineOrder(RelativeExerciseItemConfigData itemData, 
			boolean clause1AsRelativeClause, boolean extrapose, String stamp, int activity) {
		RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, clause1AsRelativeClause);
		if(clauseItems.getCommonReferentMainClause() == null || clauseItems.getCommonReferentRelativeClause() == null) {
			System.out.println("Line does not have common referent: " + stamp + ", " + activity + "." + itemData.getItem());
			return null;
		}
		
		ArrayList<RelativeClausePosition> positions = new ArrayList<>();
		
		if(extrapose) {
			// add everything in the main clause
			for(Pair<Integer, String> p : clauseItems.getPositionsMainClause()) {
				RelativeClausePosition position = new RelativeClausePosition(p.second);
				if(p.first >= clauseItems.getCommonReferentMainClause().first && 
						p.first <= clauseItems.getCommonReferentMainClause().second) {
					position.setCommonReferent(true);
				}
						
				positions.add(position);
			}
			
			// add the pronoun
			RelativeClausePosition position = new RelativeClausePosition(itemData.getPronouns().get(0));
			position.setPronoun(true);
			positions.add(position);
			
			// add everything in the relative clause except for the common referent
			for(Pair<Integer, String> p : clauseItems.getPositionsRelativeClause()) {
				if(!(p.first >= clauseItems.getCommonReferentRelativeClause().first && p.first <= clauseItems.getCommonReferentRelativeClause().second)) {
					positions.add(new RelativeClausePosition(p.second));
				}
			}
		} else {		
			// add everything in the main clause until after the common reference
			for(int i = 1; i <= clauseItems.getCommonReferentMainClause().second; i++) {
				Pair<Integer,String> p = clauseItems.getPositionsMainClause().get(i - 1);
				RelativeClausePosition position = new RelativeClausePosition(p.second);
				if(p.first >= clauseItems.getCommonReferentMainClause().first && 
						p.first <= clauseItems.getCommonReferentMainClause().second) {
					position.setCommonReferent(true);
				}
						
				positions.add(position);
			}
			
			// add the pronoun
			RelativeClausePosition position = new RelativeClausePosition(itemData.getPronouns().get(0));
			position.setPronoun(true);
			positions.add(position);
			
			// add everything from the relative clause except for the common reference
			for(Pair<Integer, String> p : clauseItems.getPositionsRelativeClause()) {
				if(!(p.first >= clauseItems.getCommonReferentRelativeClause().first && p.first <= clauseItems.getCommonReferentRelativeClause().second)) {
					positions.add(new RelativeClausePosition(p.second));
				}
			}
			
			// add the rest of the main clause
			for(int i = clauseItems.getCommonReferentMainClause().second; i < clauseItems.getPositionsMainClause().size(); i++) {
				positions.add(new RelativeClausePosition(clauseItems.getPositionsMainClause().get(i).second));
			}
		}
		
		if(!positions.get(positions.size() - 1).getValue().endsWith(".")) {
			positions.add(new RelativeClausePosition("."));
		}
		
		positions.get(0).setValue(StringUtils.capitalize(positions.get(0).getValue()));
		
		return positions;
	}
	
	/**
	 * Determines main clause and relative clause constituents based on the settings.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1AsRelativeClause	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return	The clause constituents of the main and relative clauses
	 */
	private RelativeTargetAndClauseItems determineClauseItems(RelativeExerciseItemConfigData itemData, boolean clause1AsRelativeClause) {
		String sentenceClause1 = StringUtils.capitalize(generateSentencesFromPositions(itemData.getClause1()));
		if(!sentenceClause1.endsWith(".")) {
			sentenceClause1 += ".";
		}
		String sentenceClause2 = StringUtils.capitalize(generateSentencesFromPositions(itemData.getClause2()));
		if(!sentenceClause2.endsWith(".")) {
			sentenceClause2 += ".";
		}
		
		Pair<Integer, Integer> c1AsRC = null;
		Pair<Integer, Integer> commonReferenceClause2 = null;
		ArrayList<Pair<Integer, String>> positions1 = getPositions(itemData.getClause1());
		ArrayList<Pair<Integer, String>> positions2 = getPositions(itemData.getClause2());

		for(Pair<Integer, String> position : positions1) {
			for(Pair<Integer, String> position2 : positions2) {
				if(position.first != 2 && position2.first != 2 && (position.second.equals(position2.second) ||
						itemData.getPronouns().get(0).equals("whose") && 
						(((position2.second.endsWith("'") || position2.second.endsWith("’")) && position.second.equals(position2.second.substring(0, position2.second.length() - 1)) || 
								(position2.second.endsWith("'s")  || position2.second.endsWith("’s")) && position.second.equals(position2.second.substring(0, position2.second.length() - 2))) || 
								((position.second.endsWith("'") || position.second.endsWith("’")) && position2.second.equals(position.second.substring(0, position.second.length() - 1)) || 
										(position.second.endsWith("'s") || position.second.endsWith("’s")) && position2.second.equals(position.second.substring(0, position.second.length() - 2)))))) {
					c1AsRC = new Pair<>(position.first, position.first);
					commonReferenceClause2 = new Pair<>(position2.first, position2.first);
					break;
				}
			}
		}
		
		if((c1AsRC == null || commonReferenceClause2 == null) && itemData.getPronouns().get(0).equals("where")) {
			// there could be a preposition with the common referent
			for(Pair<Integer, String> position : positions1) {
				String posWithoutFirstToken = position.second.substring(position.second.indexOf(" ") + 1);
				for(Pair<Integer, String> position2 : positions2) {
					String pos2WithoutFirstToken = position2.second.substring(position2.second.indexOf(" ") + 1);
					if(position.first != 2 && position2.first != 2 && (position.second.equals(pos2WithoutFirstToken) 
							|| position2.second.equals(posWithoutFirstToken) || posWithoutFirstToken.equals(pos2WithoutFirstToken))) {
						c1AsRC = new Pair<>(position.first, position.first);
						commonReferenceClause2 = new Pair<>(position2.first, position2.first);
						break;
					}
				}
			}
		}
		
		if(clause1AsRelativeClause) {
			return new RelativeTargetAndClauseItems(getPositions(itemData.getClause2()),
					getPositions(itemData.getClause1()), commonReferenceClause2,
					c1AsRC);
		} else {
			return new RelativeTargetAndClauseItems(getPositions(itemData.getClause1()),
					getPositions(itemData.getClause2()), c1AsRC,
					commonReferenceClause2);
		}
	}
	
	private ArrayList<Pair<Integer, String>> getPositions(String clause) {
		ArrayList<Pair<Integer, String>> positions = new ArrayList<>();
		String[] positionElements = clause.split("\\|");
		for(String p : positionElements) {
			if(!p.trim().isEmpty()) {
				String[] parts = p.trim().split("~~~");
				if(parts.length > 1) {
					positions.add(new Pair<>(Integer.parseInt(parts[0]), parts[1].trim()));
				} else {
					System.out.println("Invalid position string: " + clause);
				}
			}
		}
		
		Collections.sort(positions, (i1, i2) -> i1.first < i2.first ? -1 : 1);
		
		return positions;
	}
	
	/**
	 * Compiles a sentence from a list of positions with correct spacing.
	 * @param positions The list of positions
	 * @return The sentences string with correct spacing
	 */
	protected String generateSentencesFromPositions(String clause) {
		ArrayList<Pair<Integer, String>> positions = getPositions(clause);
		
		StringBuilder sb = new StringBuilder();
		for (Pair<Integer, String> position : positions) {
			if (!position.second.isEmpty()) {
				if (!(position.first == 1) && !punctuations.contains(position.second.charAt(0) + "")) {
					sb.append(" ");
				}
				sb.append(position.second);
			}
		}

		return sb.toString().trim();
	}
	
	/**
	 * Punctuation marks before which no space is to be inserted
	 */
	private static final String punctuations = ".,:;!?";
	
	private boolean randomizeClauseOrder(boolean allowC1AsRC) {
		boolean clause1AsRelativeClause = false;
		if(allowC1AsRC) {
			clause1AsRelativeClause = Math.random() < 0.5;
		}
		
		return clause1AsRelativeClause;
	}
	
	private boolean isIdenticalOrNull(ArrayList<RelativeSentence> relativeSentences, ArrayList<RelativeClausePosition> newSentence) {
		if(newSentence == null) {
			return true;
		}
		
		for(RelativeSentence rs : relativeSentences) {
			boolean allSame = true;
			if(newSentence.size() == rs.getChunks().size()) {
				for(int i = 0; i < newSentence.size(); i++) {
					if(!rs.getChunks().get(i).getValue().equals(newSentence.get(i).getValue())) {
						allSame = false;
						break;
					}
				}
				
				if(allSame) {
					return true;
				}
			}
		} 
		
		return false;
	}
	
	private void addIfDifferent(ArrayList<RelativeSentence> relativeSentences, ArrayList<RelativeClausePosition> newSentence,
			boolean mainSentence) {
		if(!isIdenticalOrNull(relativeSentences, newSentence)) {
			RelativeSentence rs = new RelativeSentence();
			rs.setUseToGenerateExercise(mainSentence);
			rs.setChunks(newSentence);
			relativeSentences.add(rs);
		}
	}
	
	private void addRelativeSentences(ArrayList<ExerciseItemConfigData> sentences, String lastStamp, int lastActivity) {
		for(ExerciseItemConfigData sentence : sentences) {
			ArrayList<RelativeSentence> relativeSentences = new ArrayList<>();
			boolean c1AsRC = randomizeClauseOrder(!((RelativeExerciseItemConfigData)sentence).getPronouns().get(0).equals("whose"));
								
			ArrayList<RelativeClausePosition> newRs = determineOrder((RelativeExerciseItemConfigData)sentence, c1AsRC, false, lastStamp, lastActivity);
			addIfDifferent(relativeSentences, newRs, true);
			
			newRs = determineOrder((RelativeExerciseItemConfigData)sentence, c1AsRC, true, lastStamp, lastActivity);
			addIfDifferent(relativeSentences, newRs, false);

			if(!((RelativeExerciseItemConfigData)sentence).getPronouns().get(0).equals("whose")) {
				newRs = determineOrder((RelativeExerciseItemConfigData)sentence, !c1AsRC, false, lastStamp, lastActivity);
				addIfDifferent(relativeSentences, newRs, false);
				
				newRs = determineOrder((RelativeExerciseItemConfigData)sentence, !c1AsRC, true, lastStamp, lastActivity);
				addIfDifferent(relativeSentences, newRs, false);
			}	
			
			for(RelativeSentence rs : relativeSentences) {
				rs.setChunks(splitAfterPrompt(rs.getChunks()));
			}
			((RelativeExerciseItemConfigData)sentence).setRelativeSentences(relativeSentences);
		}
	}
	
	private ArrayList<RelativeClausePosition> splitAfterPrompt(ArrayList<RelativeClausePosition> oldChunks) {
		ArrayList<RelativeClausePosition> chunks = new ArrayList<>();
		int indexFirstBlank = oldChunks.get(0).getValue().indexOf(" ");
		
		if(indexFirstBlank != -1) {
			// the first 2 tokens are entirely inside the first chunk
			int indexSecondBlank = oldChunks.get(0).getValue().indexOf(" ", indexFirstBlank + 1);
			
			if(indexSecondBlank != -1) {
				// the first chunk contains more than 2 tokens
				RelativeClausePosition promptChunk = new RelativeClausePosition(oldChunks.get(0).getValue().substring(0, indexSecondBlank).trim());
				promptChunk.setLastCommonReferent(true);
				chunks.add(promptChunk);
				chunks.add(new RelativeClausePosition(oldChunks.get(0).getValue().substring(indexSecondBlank).trim()));
				
				for(int i = 1; i < oldChunks.size(); i++) {
					chunks.add(oldChunks.get(i));
				}
			} else {
				// the first chunk contains exactly 2 tokens
				oldChunks.get(0).setLastCommonReferent(true);
				
				for(int i = 0; i < oldChunks.size(); i++) {
					chunks.add(oldChunks.get(i));
				}
			}
		} else {			
			// we also need to consider the first token of the second chunk
			int indexSecondBlank = oldChunks.get(1).getValue().indexOf(" ");
			
			if(indexSecondBlank != -1) {
				// the second chunk contains more than 1 token
				chunks.add(oldChunks.get(0));

				RelativeClausePosition promptChunk = new RelativeClausePosition(oldChunks.get(1).getValue().substring(0, indexSecondBlank).trim());
				promptChunk.setLastCommonReferent(true);
				chunks.add(promptChunk);
				chunks.add(new RelativeClausePosition(oldChunks.get(1).getValue().substring(indexSecondBlank).trim()));
				
				for(int i = 2; i < oldChunks.size(); i++) {
					chunks.add(oldChunks.get(i));
				}
			} else {
				// the second chunk contains exactly 1 token
				oldChunks.get(1).setLastCommonReferent(true);
				
				for(int i = 0; i < oldChunks.size(); i++) {
					chunks.add(oldChunks.get(i));
				}
			}
		}
		
		
		
		return chunks;
	}
	
	
	private final class RelativeTargetAndClauseItems {
		
		public RelativeTargetAndClauseItems(ArrayList<Pair<Integer, String>> positionsMainClause,
				ArrayList<Pair<Integer, String>> positionsRelativeClause, Pair<Integer, Integer> commonReferentMainClause,
				Pair<Integer, Integer> commonReferentRelativeClause) {
			this.positionsMainClause = positionsMainClause;
			this.positionsRelativeClause = positionsRelativeClause;
			this.commonReferentMainClause = commonReferentMainClause;
			this.commonReferentRelativeClause = commonReferentRelativeClause;
		}
		
		private ArrayList<Pair<Integer,String>> positionsMainClause;
		private ArrayList<Pair<Integer,String>> positionsRelativeClause;
		private Pair<Integer,Integer> commonReferentMainClause;
		private Pair<Integer,Integer> commonReferentRelativeClause;
		
		public ArrayList<Pair<Integer, String>> getPositionsMainClause() {
			return positionsMainClause;
		}
		public ArrayList<Pair<Integer, String>> getPositionsRelativeClause() {
			return positionsRelativeClause;
		}
		public Pair<Integer, Integer> getCommonReferentMainClause() {
			return commonReferentMainClause;
		}
		public Pair<Integer, Integer> getCommonReferentRelativeClause() {
			return commonReferentRelativeClause;
		}

	}

}
