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
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getPositionsClause1().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Main clause 2")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).getPositionsClause2().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Main clause 1 as relative clause")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setRelativeSentence(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("Main clause 1 as contact relative clause")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setContactRelativeSentence(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("Main clause 2 as contact relative clause")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setAlternativeRelativeSentence(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("richtig")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setContact(columnValues.get(entry.getValue()).get(i).equals("Yes"));
		    	} else if(entry.getValue().equals("Feedback bei falscher Antwort")) {
		    		((RelativeExerciseItemConfigData)cd.getItemData().get(0)).setFeedback(columnValues.get(entry.getValue()).get(i));
		    	} 
			}	
			isFirstCol = false;
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

	private static final String[] exerciseTypes = new String[] {
			"Contactclauses_Half-open",
			"Contactclauses_Open",
			"Contactclauses_Categorize"
	};
	
	private ArrayList<ExerciseTypeSpec> getExerciseTypes(String stamp) {
		ArrayList<ExerciseTypeSpec> types = new ArrayList<>();
		for(String type : exerciseTypes) {
			ExerciseTypeSpec t = new ExerciseTypeSpec();	
			t.setSubtopic("Contact clauses");
			if(type.equals("Contactclauses_Open")) {
				t.setFeedbookType(FeedBookExerciseType.HALF_OPEN);
			} else if(type.equals("Contactclauses_Half-open")) {
				t.setFeedbookType(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES);
			} else {
				t.setFeedbookType(FeedBookExerciseType.getContainedType(type));
			}
			types.add(t);
		}
		
		return types;
	}
	
}
