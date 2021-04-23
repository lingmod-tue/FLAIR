package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.dtos.RankableDocument.ConstructionRange;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialToast;

public class ExerciseGenerationWidget extends LocalizedComposite {

    interface ExerciseGenerationWidgetUiBinder extends UiBinder<Widget, ExerciseGenerationWidget> {
    }

    private static ExerciseGenerationWidgetUiBinder ourUiBinder = GWT.create(ExerciseGenerationWidgetUiBinder.class);

    interface ExerciseGenerationWidgetLocalizationBinder extends LocalizationBinder<ExerciseGenerationWidget> {
    }
    
    private static ExerciseGenerationWidget.ExerciseGenerationWidgetLocalizationBinder localeBinder = GWT.create(ExerciseGenerationWidget.ExerciseGenerationWidgetLocalizationBinder.class);


    
    @UiField
    MaterialCollapsible wdgtTasks;
    //TODO: disable button for German
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnAddTask;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnDeleteTasks;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnGenerateExercises;
    
        
    static DocumentPreviewPane documentPreviewPane = DocumentPreviewPane.getInstance();

    public ExerciseGenerationWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initLocale(localeBinder.bind(this));
        this.initHandlers();
    }

    /**
     * Initializes all handlers.
     */
    private void initHandlers() {
    	btnAddTask.addClickHandler(event -> {
            addTask();
    	});
    	btnDeleteTasks.addClickHandler(event -> {
            deleteAllTasks();
    	});
    }
    
    /**
     * Deletes all task widgets and removes them from the view.
     */
    private void deleteAllTasks() {
    	List<Widget> existingTasks = wdgtTasks.getChildrenList();
    	for(Widget existingTask : existingTasks) {
    		wdgtTasks.remove(existingTask);
    	}
    }
    
    /**
     * Creates a new task widget and adds it to the displayed tasks.
     */
    private void addTask() {
    	String name = "Task 1";
    	List<Widget> existingTasks = wdgtTasks.getChildrenList();
    	if(existingTasks.size() > 0) {
    		int lastNumber = Integer.parseInt(((TaskItem)existingTasks.get(existingTasks.size() - 1)).lblName.getText().split(" ")[1]);
    		name = "Task " + (lastNumber + 1);
    	}
    	TaskItem newTask = new TaskItem();
    	newTask.lblName.setText(name);
        wdgtTasks.add(newTask);
        updateSelectableQuizzes();
        newTask.calculateConstructionsOccurrences();
    }
    
    /**
     * Updates the construction counts when the selected document has been changed.
     */
    public void initConstructionsOccurrences() {
		for(Widget task : wdgtTasks.getChildren()) {
			if(task instanceof TaskItem) {
				((TaskItem)task).calculateConstructionsOccurrences();
			}
		}
    }

    /**
     * Re-sets the options in the quiz dropdown whenever the selection in one of the tasks has been changed, 
     * thus possibly changing the available quizzes
     */
    private void updateSelectableQuizzes() {
    	List<Widget> existingTasks = wdgtTasks.getChildrenList();
    	ArrayList<Integer> selectedQuizzes = new ArrayList<Integer>();
    	for(Widget existingTask : existingTasks) {
    		String selectedQuiz = ((TaskItem)existingTask).btnQuizDropdown.getText();
    		if(!selectedQuiz.equals("Quiz")) {
    			int selectedNumber = Integer.parseInt(selectedQuiz.split(" ")[1]);
    			if(!selectedQuizzes.contains(selectedNumber)) {
    				selectedQuizzes.add(selectedNumber);
    			}
    		}
    	}
    	
    	if(selectedQuizzes.size() > 0) {
    		Collections.sort(selectedQuizzes);
    		selectedQuizzes.add(selectedQuizzes.get(selectedQuizzes.size() - 1) + 1);
    	} else {
    		selectedQuizzes.add(1);
    	}
    	
    	for(Widget existingTask : existingTasks) {
    		// Remove all previous selections
    		List<Widget> oldSelections = ((TaskItem)existingTask).drpQuiz.getChildrenList();
    		for(Widget oldSelection : oldSelections) {
    			((TaskItem)existingTask).drpQuiz.remove(oldSelection);
    		}
    		
    		// Add the no quiz selection item
			MaterialLabel newSelection = new MaterialLabel();
			newSelection.setText("---");
			newSelection.setTextColor(Color.BLACK);
			newSelection.setFontSize("10pt");
			newSelection.addMouseDownHandler(new MouseDownHandler() 
	    	{
				@Override
				public void onMouseDown(MouseDownEvent event) {
					((TaskItem)existingTask).btnQuizDropdown.setText("Quiz");
					((TaskItem)existingTask).btnQuizDropdown.setFontWeight(FontWeight.BOLD);
					updateSelectableQuizzes();
				}
	    	});			
			((TaskItem)existingTask).drpQuiz.add(newSelection);
    		
    		// Add the newly determined selections
    		for(int selectedQuiz : selectedQuizzes) {
    			newSelection = new MaterialLabel();
    			newSelection.setText("Quiz " + selectedQuiz);
    			newSelection.setTextColor(Color.BLACK);
    			newSelection.setFontSize("10pt");
    			newSelection.addMouseDownHandler(new MouseDownHandler() 
    	    	{
    				@Override
    				public void onMouseDown(MouseDownEvent event) {
    					((TaskItem)existingTask).btnQuizDropdown.setText("Quiz " + selectedQuiz);
    					((TaskItem)existingTask).btnQuizDropdown.setFontWeight(FontWeight.NORMAL);
    					updateSelectableQuizzes();
    				}
    	    	});
    			
    			((TaskItem)existingTask).drpQuiz.add(newSelection);
    		}
    	}
    }

}