package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flair.shared.exerciseGeneration.Pair;

public class RelativeContactExcelFileReader extends ExcelFileReader {
	
	@Override
	protected ExcelFileElements extractCellValues(XSSFSheet sheet, int nRows, int nCols) {
		XSSFRow row;
		XSSFCell cell;
		
		HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
		HashMap<Integer, String> columnHeaders = new HashMap<>();

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
		    	} else if(entry.getValue().equals("item")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setItem((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("toc nr.")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		if(value != null && !value.isEmpty()) {
		    			cd.setTocId(value);
		    		}
		    	} else if(entry.getValue().equals("Main clause 1")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setClause1(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("Main clause 2")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setClause2(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("Main clause 1 as relative clause")) {
		    		RelativeSentence relativeSentence = new RelativeSentence();
		    		relativeSentence.setUseToGenerateExercise(true);
		    		ArrayList<RelativeClausePosition> chunks = new ArrayList<>();
		    		chunks.add(new RelativeClausePosition(columnValues.get(entry.getValue()).get(i)));
		    		relativeSentence.setChunks(chunks);
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getRelativeSentences().add(relativeSentence);
		    	} else if(entry.getValue().equals("Main clause 1 as contact relative clause")) {
		    		String contactClause = columnValues.get(entry.getValue()).get(i);
		    		if(!contactClause.equals(((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getRelativeSentences().get(0).getChunks().get(0).getValue())) {
		    			ArrayList<RelativeClausePosition> chunks = splitAtPronoun(((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getRelativeSentences().get(0).getChunks().get(0).getValue(), contactClause);
			    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getRelativeSentences().get(0).setChunks(chunks);
		    		}
		    	} else if(entry.getValue().equals("Main clause 2 as contact relative clause")) {
		    		RelativeSentence relativeSentence = new RelativeSentence();
		    		ArrayList<RelativeClausePosition> chunks = new ArrayList<>();
		    		chunks.add(new RelativeClausePosition(columnValues.get(entry.getValue()).get(i)));
		    		relativeSentence.setChunks(chunks);
		    		relativeSentence.setPronounIsOptional(true);
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getRelativeSentences().add(relativeSentence);
		    	} else if(entry.getValue().equals("richtig")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getRelativeSentences().get(0).setPronounIsOptional(columnValues.get(entry.getValue()).get(i).equals("Yes"));
		    	} else if(entry.getValue().equals("Feedback bei falscher Antwort")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setFeedback(columnValues.get(entry.getValue()).get(i));
		    	} 
			}	
			isFirstCol = false;
		}
		
		for(ExerciseConfigData cd : configData) {
			for(ExerciseItemConfigData id : cd.getItemData()) {
				for(RelativeSentence rs : ((RelativeExerciseItemConfigData)id).getRelativeSentences()) {
					rs.setChunks(splitAfterPrompt(rs.getChunks()));
				}
			}
		}
		
		Collections.sort(configData, (i1, i2) -> i1.getActivity() < i2.getActivity() ? -1 : 1);
			
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
				d.setExerciseType(getExerciseTypes(d.getStamp()));
				d.setTocId(lastTocId);
				d.setItemData(sentences);
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
			d.setExerciseType(getExerciseTypes(d.getStamp()));
			d.setTocId(lastTocId);
			batchedExercises.add(d);
		}
		
		return batchedExercises;
	}

	private ArrayList<ExerciseTypeSpec> getExerciseTypes(String stamp) {
		ArrayList<ExerciseTypeSpec> types = new ArrayList<>();
		types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.HALF_OPEN, true));
		types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES, true));
		types.add(new RelativeExerciseTypeSpec(FeedBookExerciseType.CATEGORIZATION, true));

		return types;
	}
	
	private ArrayList<RelativeClausePosition> splitAtPronoun(String rs, String contactClause) {
		ArrayList<RelativeClausePosition> chunks = new ArrayList<>();
		for(int i = 1; i <= contactClause.length(); i++) {
			if(!rs.startsWith(contactClause.substring(0, i))) {
				// we have found the first difference, so this is the pronoun
				// now we need to find the start of the pronoun
				int startIndex = rs.substring(0, i).lastIndexOf(" ");
				int endIndex = rs.indexOf(" ", i);
				
				chunks.add(new RelativeClausePosition(rs.substring(0, startIndex).trim()));
				RelativeClausePosition chunk = new RelativeClausePosition(rs.substring(startIndex, endIndex).trim());
				chunk.setPronoun(true);
				chunks.add(chunk);
				chunks.add(new RelativeClausePosition(rs.substring(endIndex).trim()));

				return chunks;
			}
		}
		
		chunks.add(new RelativeClausePosition(contactClause));
		return chunks;
	}
	
	private ArrayList<RelativeClausePosition> splitAfterPrompt(ArrayList<RelativeClausePosition> oldChunks) {
		ArrayList<RelativeClausePosition> chunks = new ArrayList<>();
		int indexFirstBlank = oldChunks.get(0).getValue().indexOf(" ");
		int indexSecondBlank = oldChunks.get(0).getValue().indexOf(" ", indexFirstBlank + 1);
		
		RelativeClausePosition promptChunk = new RelativeClausePosition(oldChunks.get(0).getValue().substring(0, indexSecondBlank).trim());
		promptChunk.setLastCommonReferent(true);
		chunks.add(promptChunk);
		chunks.add(new RelativeClausePosition(oldChunks.get(0).getValue().substring(indexSecondBlank).trim()));
		
		for(int i = 1; i < oldChunks.size(); i++) {
			chunks.add(oldChunks.get(i));
		}
		
		return chunks;
	}
	
}
