package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
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
					RelativeExerciseConfigData cd = new RelativeExerciseConfigData();
					configData.add(cd);
				}
				RelativeExerciseConfigData cd = (RelativeExerciseConfigData)configData.get(i);
				if(entry.getValue().equals("Aufgabennr.")) {
					cd.setActivity((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("stamp")) {
		    		cd.setStamp(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("item")) {
					cd.setItem((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Main clause 1")) {
		    		cd.getPositionsClause1().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Main clause 2")) {
		    		cd.getPositionsClause2().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Main clause 1 as relative clause")) {
		    		cd.setRelativeSentence(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("Main clause 1 as contact relative clause")) {
		    		cd.setContactRelativeSentence(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("Main clause 2 as contact relative clause")) {
		    		cd.setAlternativeRelativeSentence(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("richtig")) {
		    		cd.setContact(columnValues.get(entry.getValue()).get(i).equals("Yes"));
		    	} else if(entry.getValue().equals("Feedback bei falscher Antwort")) {
		    		cd.setFeedback(columnValues.get(entry.getValue()).get(i));
		    	} 
			}	
			isFirstCol = false;
		}

		return configData;
	}

}
