package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;


public abstract class SimpleExerciseXmlGenerator {
		    
	public abstract byte[] generateXMLFile(ExerciseData exerciseDefinition);
	
    public String generateFeedBookInputXml(XmlValues values) {
		Element subTask = DocumentFactory.getInstance().createElement("SubTask");
    	addAttributes(values, subTask);
		Element prompts = DocumentFactory.getInstance().createElement("Prompts");
		generateTaskFields(values, prompts);
		subTask.add(prompts);
    	
    	try {
		    StringWriter writer = new StringWriter();
		    XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
		    xmlWriter.write(subTask);
		    xmlWriter.close();
		    return writer.toString();
    	} catch (IOException e) {
    		throw new RuntimeException();
    	}    	
    }
	
	protected void generateTaskFields(XmlValues values, Element prompts) {
    	int id = 0;
    	for(Item item : values.getItems()) {
    	    prompts.add(generateTaskFieldDefinition(++id, item));   
    	}
	}
		
	protected void addAttributes(XmlValues values, Element subTask) { 
		subTask.addAttribute("index", values.getIndex() + "");
		subTask.addAttribute("instruction", StringEscapeUtils.escapeXml(values.getInstructions()));
		subTask.addAttribute("non_en_input", "false");
		subTask.addAttribute("grammar_topics", "");
		subTask.addAttribute("task_topics", "");
		subTask.addAttribute("instruction", values.getInstructions());
		subTask.addAttribute("sort_index", values.getIndex() + "");
		subTask.addAttribute("given_words", values.getGivenWords());
		subTask.addAttribute("task_type", values.getTaskType());
		subTask.addAttribute("task_orient", values.getTaskOrient());
		subTask.addAttribute("given_words_draggable", String.valueOf(values.isGivenWordsDraggable()));
		subTask.addAttribute("feedback_disabled", String.valueOf(values.isFeedbackDisabled()));
		subTask.addAttribute("support", values.getSupport());
		subTask.addAttribute("evaluate_at_completion", String.valueOf(values.isEvaluateAtComplete()));
	}
	    
    /**
     * Generates a xml definition for a FeedBook TaskField
     * @param target	The prompt text of the task field
     * @param target	The correct target answer
     * @param index		The successive index of the task field within the (sub) task
     * @return	The generated xml definition
     */
    protected Element generateTaskFieldDefinition(int id, Item item) {
    	Element taskField = DocumentFactory.getInstance().createElement("TaskField");
    	
    	addTaskFieldAttributes(taskField, id, item);
    	    	
    	return taskField;
    }
    
    /**
     * Adds content type specific attributes for task fields.
     * @param target		The target construction
     * @param distractors	The list of distractors for the current target
     */
    protected void addTaskFieldAttributes(Element taskField, int id, Item item) { 
		taskField.addAttribute("text", item.text);
		taskField.addAttribute("sort_index", id + "");
		taskField.addAttribute("target", item.target);
		taskField.addAttribute("example", item.example);
		taskField.addAttribute("input_type", item.inputType);
    }
    
    

}
