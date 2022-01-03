package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;

public class XmlValues {
	public int index = 1;
	public String instructions;
	public ArrayList<Item> items = new ArrayList<>();
	
	public String taskType;
	public String taskOrient = "Offen";
	public String taskFocus = "Form";
	public boolean givenWordsDraggable = false;
	public boolean feedbackDisabled = false;
	public String support = "null";
	public String givenWords = "null";
	
}
