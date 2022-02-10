package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalExcelFileReader extends ExcelFileReader {
	
	@Override
	protected ExcelFileElements extractCellValues(XSSFSheet sheet, int nRows, int nCols) {
		XSSFRow row;
		XSSFCell cell;
		
		HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
		HashMap<Integer, String> columnHeaders = new HashMap<>();

		// get the column index of if clause positions and main clause positions
		row = sheet.getRow(0);
		int ifClausePositionsStart = 0;
		int mainClausePositionsStart = 0;
		for(int c = 0; c < nCols; c++) {
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
		while(counter < nRows) {
		    row = sheet.getRow(r);
		    if(row != null) {    
		        for(int c = 0; c < nCols; c++) {
		            cell = row.getCell((short)c);
		            
		            if(cell != null) {
		            	cell.setCellType(Cell.CELL_TYPE_STRING);
		            	String cellValue = xTrim(cell.toString());
	
			            if(!cellValue.isEmpty()) {
		            		
		            		if(cellValue.equals("If-clause") || cellValue.startsWith("IV conditional type 1 vs conditional type 2")) {
		            			activityType = cellValue.equals("If-clause") ? "if" : "1vs2";
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
				        		column.add(cellValue);
				        	}
			            } else {
			            	if(activityCounter != 1 && sheet.getRow(1).getCell((short)c) != null && !sheet.getRow(1).getCell((short)c).toString().isEmpty() && row.getCell(0) != null) {
			            		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
				        		column.add("");
			            	}
			            }
		            } 
		        }
		        
		        counter++;
		        activityCounter++;
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
					// it's a new activity
					ConditionalExerciseConfigData cd = new ConditionalExerciseConfigData();
					cd.setType1VsType2(columnValues.get("Activity type").get(i).equals("1vs2"));
					configData.add(cd);
				}
				ConditionalExerciseConfigData cd = (ConditionalExerciseConfigData)configData.get(i);
				if(entry.getValue().equals("Aufgabennr.")) {
					cd.setActivity((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("stamp")) {
		    		cd.setStamp(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("item")) {
					cd.setItem((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("type 1 or type 2")) {
		    		cd.setConditionalType((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Übersetzung if-clause")) {
		    		cd.setTranslationIfClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("übersetzung main clause")) {
		    		cd.setTranslationMainClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("if-clause distractor 1")) {
		    		cd.getDistractorsIfClause().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("if-clause distractor 2")) {
		    		cd.getDistractorsIfClause().add(new Pair<>(2, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("if-clause distractor 3")) {
		    		cd.getDistractorsIfClause().add(new Pair<>(3, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("main clause distractor 1")) {
		    		cd.getDistractorsMainClause().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("main clause distractor 2")) {
		    		cd.getDistractorsMainClause().add(new Pair<>(2, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("main clause distractor 3")) {
		    		cd.getDistractorsMainClause().add(new Pair<>(3, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("given in brackets if-clause")) {
		    		cd.setLemmaIfClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("given in brackets main clause")) {
		    		cd.setLemmaMainClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("semantic distractor if clause")) {
		    		cd.setDistractorLemmaIfClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("semantic distractor main clause")) {
		    		cd.setDistractorLemmaMainClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("given words für halb-offene Aufgaben if-clause")) {
		    		String brackets = StringUtils.strip(columnValues.get(entry.getValue()).get(i), "()");
		    		String[] elements = brackets.split(",");
		    		for(String element : elements) {
		    			cd.getBracketsIfClause().add(xTrim(element));
		    		}
		    	} else if(entry.getValue().equals("given words  für halb-offene Aufgaben main clause")) {
		    		String brackets = StringUtils.strip(columnValues.get(entry.getValue()).get(i), "()");
		    		String[] elements = brackets.split(",");
		    		for(String element : elements) {
		    			cd.getBracketsMainClause().add(xTrim(element));
		    		}
		    	} else if(entry.getValue().equals("gap if-clause")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		String[] gapParts = value.split(",");
		    		for(String gapPart : gapParts) {
			    		int startIndex = 0; 
			    		int endIndex = 0;

			    		try {
			    			String[] parts = gapPart.split("-");
			    			if(parts.length == 2) {
			    				startIndex = (int)Float.parseFloat(xTrim(parts[0]));
			    				endIndex = (int)Float.parseFloat(xTrim(parts[1]));
			    			} else if (parts.length == 1) {
				    			startIndex = (int)Float.parseFloat(xTrim(gapPart));
				    			endIndex = startIndex;
				    		}
			    		} catch(Exception e) { }
			    		
			    		if(startIndex != 0 && endIndex != 0) {
			    			cd.getGapIfClause().add(new Pair<>(startIndex, endIndex));
			    		}
		    		}
		    	} else if(entry.getValue().equals("if-clause underline")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		String[] gapParts = value.split(",");
		    		for(String gapPart : gapParts) {
			    		int startIndex = 0; 
			    		int endIndex = 0;

			    		try {
			    			String[] parts = gapPart.split("-");
			    			if(parts.length == 2) {
			    				startIndex = (int)Float.parseFloat(xTrim(parts[0]));
			    				endIndex = (int)Float.parseFloat(xTrim(parts[1]));
			    			} else if (parts.length == 1) {
				    			startIndex = (int)Float.parseFloat(xTrim(gapPart));
				    			endIndex = startIndex;
				    		}
			    		} catch(Exception e) { }
			    		
			    		if(startIndex != 0 && endIndex != 0) {
			    			cd.getUnderlineIfClause().add(new Pair<>(startIndex, endIndex));
			    		}
		    		}
		    	} else if(entry.getValue().equals("gap main clause")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		String[] gapParts = value.split(",");
		    		for(String gapPart : gapParts) {
			    		int startIndex = 0; 
			    		int endIndex = 0;

			    		try {
			    			String[] parts = gapPart.split("-");
			    			if(parts.length == 2) {
			    				startIndex = (int)Float.parseFloat(xTrim(parts[0]));
			    				endIndex = (int)Float.parseFloat(xTrim(parts[1]));
			    			} else if (parts.length == 1) {
				    			startIndex = (int)Float.parseFloat(xTrim(gapPart));
				    			endIndex = startIndex;
				    		}
			    		} catch(Exception e) { }
			    		
			    		if(startIndex != 0 && endIndex != 0) {
			    			cd.getGapMainClause().add(new Pair<>(startIndex, endIndex));
			    		}
		    		}
		    	} else if(entry.getValue().equals("main clause underline")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		String[] gapParts = value.split(",");
		    		for(String gapPart : gapParts) {
			    		int startIndex = 0; 
			    		int endIndex = 0;

			    		try {
			    			String[] parts = gapPart.split("-");
			    			if(parts.length == 2) {
			    				startIndex = (int)Float.parseFloat(xTrim(parts[0]));
			    				endIndex = (int)Float.parseFloat(xTrim(parts[1]));
			    			} else if (parts.length == 1) {
				    			startIndex = (int)Float.parseFloat(xTrim(gapPart));
				    			endIndex = startIndex;
				    		}
			    		} catch(Exception e) { }
			    		
			    		if(startIndex != 0 && endIndex != 0) {
				    		cd.getUnderlineMainClause().add(new Pair<>(startIndex, endIndex));
			    		}
		    		}
		    	} else if(entry.getValue().startsWith("if-clause position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		cd.getPositionsIfClause().add(new Pair<>((int)Float.parseFloat(xTrim(entry.getValue().substring(19))), columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().startsWith("main-clause position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		cd.getPositionsMainClause().add(new Pair<>((int)Float.parseFloat(xTrim(entry.getValue().substring(21))), columnValues.get(entry.getValue()).get(i)));
		    	}
			}	
			isFirstCol = false;
		}
		
		for(ExerciseConfigData ecd : configData) {
			ConditionalExerciseConfigData cd = (ConditionalExerciseConfigData)ecd;
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
	}
	
}
