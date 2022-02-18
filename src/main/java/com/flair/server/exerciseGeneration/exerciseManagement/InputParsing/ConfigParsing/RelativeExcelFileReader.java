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
		    	} /*else if(entry.getValue().equals("Common reference in clause 1")) {
		    		String[] elements = columnValues.get(entry.getValue()).get(i).split("-");
		    		if(elements.length == 1) {
		    			cd.setCommonReferenceClause1(new Pair<>((int)Float.parseFloat(xTrim(elements[0])), (int)Float.parseFloat(elements[0])));
		    		} else if(elements.length == 2) {
		    			cd.setCommonReferenceClause1(new Pair<>((int)Float.parseFloat(xTrim(elements[0])), (int)Float.parseFloat(xTrim(elements[1]))));
		    		} 
		    	}  else if(entry.getValue().equals("Common reference in clause 2")) {
		    		String[] elements = columnValues.get(entry.getValue()).get(i).split("-");
		    		if(elements.length == 1) {
		    			cd.setCommonReferenceClause2(new Pair<>((int)Float.parseFloat(xTrim(elements[0])), (int)Float.parseFloat(xTrim(elements[0]))));
		    		} else if(elements.length == 2) {
		    			cd.setCommonReferenceClause2(new Pair<>((int)Float.parseFloat(xTrim(elements[0])), (int)Float.parseFloat(xTrim(elements[1]))));
		    		} 
		    	} */else if(entry.getValue().equals("Relative pronoun")) {
		    		cd.setPronoun(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().startsWith("clause 1 position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		cd.getPositionsClause1().add(new Pair<>((int)Float.parseFloat(entry.getValue().substring(18)), columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().startsWith("clause 2 position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		cd.getPositionsClause2().add(new Pair<>((int)Float.parseFloat(entry.getValue().substring(18)), columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().startsWith("distractor ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		cd.getDistractors().add(columnValues.get(entry.getValue()).get(i));
		    	} 
			}	
			isFirstCol = false;
		}
		
		for(ExerciseConfigData ecd : configData) {
			RelativeExerciseConfigData cd = (RelativeExerciseConfigData)ecd;
			Collections.sort(cd.getPositionsClause1(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
			Collections.sort(cd.getPositionsClause2(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
		}
		
		return configData;
	}
	
}
