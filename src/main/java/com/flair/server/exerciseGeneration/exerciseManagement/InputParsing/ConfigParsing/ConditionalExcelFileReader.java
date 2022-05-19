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

public class ConditionalExcelFileReader extends ExcelFileReader {
	
	@Override
	protected ExcelFileElements extractCellValues(XSSFSheet sheet, int nRows, int nCols) {
		XSSFRow row;
		XSSFCell cell;
		
		HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
		HashMap<Integer, String> columnHeaders = new HashMap<>();

		// get the column indices of clause positions
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
		
		int counter = 1;
		int r = 1;
		while(counter < nRows) {
		    row = sheet.getRow(r);
		    if(row != null) {    
		        for(int c = 0; c < nCols; c++) {
		            cell = row.getCell((short)c);
		            if(cell != null && sheet.getRow(1).getCell((short)c) != null && !sheet.getRow(1).getCell((short)c).toString().isEmpty() && row.getCell(3) != null && !row.getCell(3).getStringCellValue().isEmpty()) {
		            	cell.setCellType(CellType.STRING);
		            	String cellValue = xTrim(cell.toString());
	
			            if(!cellValue.isEmpty()) {
			            	if(r == 1) {
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
		            	if(r != 1 && sheet.getRow(1).getCell((short)c) != null && !sheet.getRow(1).getCell((short)c).toString().isEmpty() && row.getCell(3) != null && !row.getCell(3).getStringCellValue().isEmpty()) {
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
					// it's a new activity
					ExerciseConfigData cd = new ExerciseConfigData();
					cd.getItemData().add(new ConditionalExerciseItemConfigData());
					configData.add(cd);
				}
				ExerciseConfigData cd = configData.get(i);
				if(entry.getValue().equals("Aufgabennr.")) {
					cd.setActivity((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("stamp")) {
		    		cd.setStamp(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("item")) {
					((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setItem((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("toc nr.")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		if(value != null && !value.isEmpty()) {
		    			cd.setTocId(value);
		    		}
		    	} else if(entry.getValue().equals("type 1 or type 2")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setConditionalType((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("Übersetzung if-clause")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setTranslationIfClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("übersetzung main clause")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setTranslationMainClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("if-clause distractor 1")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDistractorsIfClause().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("if-clause distractor 2")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDistractorsIfClause().add(new Pair<>(2, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("if-clause distractor 3")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDistractorsIfClause().add(new Pair<>(3, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("main clause distractor 1")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDistractorsMainClause().add(new Pair<>(1, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("main clause distractor 2")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDistractorsMainClause().add(new Pair<>(2, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("main clause distractor 3")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDistractorsMainClause().add(new Pair<>(3, columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("given in brackets if-clause")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setLemmaIfClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("given in brackets main clause")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setLemmaMainClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("semantic distractor if clause")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setDistractorLemmaIfClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("semantic distractor main clause")) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setDistractorLemmaMainClause(columnValues.get(entry.getValue()).get(i));
		    	} else if(entry.getValue().equals("given words für halb-offene Aufgaben if-clause")) {
		    		String brackets = StringUtils.strip(columnValues.get(entry.getValue()).get(i), "()");
		    		String[] elements = brackets.split(",");
		    		for(String element : elements) {
		    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getBracketsIfClause().add(xTrim(element));
		    		}
		    	} else if(entry.getValue().equals("given words  für halb-offene Aufgaben main clause")) {
		    		String brackets = StringUtils.strip(columnValues.get(entry.getValue()).get(i), "()");
		    		String[] elements = brackets.split(",");
		    		for(String element : elements) {
		    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getBracketsMainClause().add(xTrim(element));
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
			    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getGapIfClause().add(new Pair<>(startIndex, endIndex));
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
			    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getUnderlineIfClause().add(new Pair<>(startIndex, endIndex));
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
			    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getGapMainClause().add(new Pair<>(startIndex, endIndex));
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
			    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getUnderlineMainClause().add(new Pair<>(startIndex, endIndex));
			    		}
		    		}
		    	} else if(entry.getValue().startsWith("if-clause position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		String[] v = columnValues.get(entry.getValue()).get(i).split("/");
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getPositionsIfClause().add(new Pair<>((int)Float.parseFloat(xTrim(entry.getValue().substring(19))), v[0].trim()));
		    		if(v.length > 1) {
		    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setAlternativeTarget(v[1].trim());
		    		}
		    	} else if(entry.getValue().startsWith("main-clause position ") && !columnValues.get(entry.getValue()).get(i).isEmpty()) {
		    		((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getPositionsMainClause().add(new Pair<>((int)Float.parseFloat(xTrim(entry.getValue().substring(21))), columnValues.get(entry.getValue()).get(i)));
		    	} else if(entry.getValue().equals("referent if")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		if(value != null) {
			    		String[] indices = value.split(",");
			    		for(String index : indices) {
			    			if(!index.trim().isEmpty()) {
			    				((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDifferingValuesIfClause().add(new Pair<>(Integer.parseInt(index.trim()), ""));
			    			}	
			    		}
		    		}
		    	} else if(entry.getValue().equals("referent main")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		if(value != null) {
			    		String[] indices = value.split(",");
			    		for(String index : indices) {
			    			if(!index.trim().isEmpty()) {
			    				((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).getDifferingValuesMainClause().add(new Pair<>(Integer.parseInt(index.trim()), ""));
			    			}
			    		}
		    		}
		    	} else if(entry.getValue().equals("only if first")) {
		    		String value = columnValues.get(entry.getValue()).get(i);
		    		if(value != null && value.equals("TRUE")) {
		    			((ConditionalExerciseItemConfigData)cd.getItemData().get(0)).setForceIfFirst(true);
		    		}
		    	} 
			}	
			isFirstCol = false;
		}
		
		for(ExerciseConfigData ecd : configData) {
			for(ExerciseItemConfigData d : ecd.getItemData()) {
				ConditionalExerciseItemConfigData cd = (ConditionalExerciseItemConfigData)d;
				Collections.sort(cd.getDistractorsIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getDistractorsMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getGapIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getGapMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getUnderlineIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getUnderlineMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getPositionsIfClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
				Collections.sort(cd.getPositionsMainClause(), (i1, i2) -> i1.first < i2.first ? -1 : 1);
			}
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
			d.setExerciseType(getExerciseTypes(d.getStamp()));
			d.setTocId(lastTocId);
			batchedExercises.add(d);
		}
		
		return batchedExercises;
	}

	private ArrayList<ExerciseTypeSpec> getExerciseTypes(String stamp) {
		ArrayList<ExerciseTypeSpec> types = new ArrayList<>();
		
		if(stamp.equals("conditinal 1 vs conditional 2")) {
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.SINGLE_CHOICE_4D, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_PARENTHESES, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.JUMBLED_SENTENCES, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.CATEGORIZATION, false, false, false, false, true));
		} else if(stamp.equals("Conditional negative if clause")) {
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.SINGLE_CHOICE_4D, false, true, false, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_PARENTHESES, false, true, false, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES, false, true, false, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.HALF_OPEN, false, true, false, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.MEMORY, false, true, false, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.JUMBLED_SENTENCES, false, true, false, false, true));
		} else if(stamp.equals("Conditional negative main clause")) {
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.SINGLE_CHOICE_4D, false, false, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_PARENTHESES, false, false, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES, false, false, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.HALF_OPEN, false, false, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.MEMORY, false, false, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.JUMBLED_SENTENCES, false, false, true, false, true));
		} else if(stamp.equals("Conditional negative main + if clause")) {
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.SINGLE_CHOICE_4D, false, true, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_PARENTHESES, false, true, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES, false, true, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.HALF_OPEN, false, true, true, false, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.MEMORY, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.JUMBLED_SENTENCES, false, false, false, true, true));
		} else if(stamp.equals("conditional question") || stamp.equals("Conditional affirmative")) {
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.SINGLE_CHOICE_4D, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_PARENTHESES, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.HALF_OPEN, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.MEMORY, false, false, false, true, true));
			types.add(new ConditionalExerciseTypeSpec(
					FeedBookExerciseType.JUMBLED_SENTENCES, false, false, false, true, true));
		}
		return types;
	}
}
