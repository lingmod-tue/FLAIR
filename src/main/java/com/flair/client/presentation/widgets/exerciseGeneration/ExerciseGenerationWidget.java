package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.ToastNotification;
import com.flair.client.presentation.interfaces.ExerciseGenerationService;
import com.flair.client.utilities.JSUtility;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.combobox.events.SelectItemEvent;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent.SelectComboHandler;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.html.Option;

public class ExerciseGenerationWidget extends LocalizedComposite implements ExerciseGenerationService {

    interface ExerciseGenerationWidgetUiBinder extends UiBinder<Widget, ExerciseGenerationWidget> {
    }

    private static ExerciseGenerationWidgetUiBinder ourUiBinder = GWT.create(ExerciseGenerationWidgetUiBinder.class);

    interface ExerciseGenerationWidgetLocalizationBinder extends LocalizationBinder<ExerciseGenerationWidget> {
    }
    
    private static ExerciseGenerationWidget.ExerciseGenerationWidgetLocalizationBinder localeBinder = GWT.create(ExerciseGenerationWidget.ExerciseGenerationWidgetLocalizationBinder.class);


    
    @UiField
    MaterialCollapsible wdgtTasks;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnAddTask;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnDeleteTasks;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnGenerateExercises;
    @UiField
    MaterialPreLoader spnGenerating;
    @UiField
    gwt.material.design.client.ui.MaterialDialog mdlCopyrightNoticeUI;
    @UiField
    MaterialCheckBox chkDontShowCopyrightNoticeUI;
    @UiField
    MaterialCheckBox chkDownloadResources;
    @UiField
    MaterialButton btnCloseCopyrightNoticeUI;
    @UiField
    MaterialTitle titleCopyrightNoticeUI;
    
        
    public ExerciseGenerationWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initLocale(localeBinder.bind(this));
        this.initHandlers();
    }

    /**
     * Initializes all handlers.
     */
    private void initHandlers() {
    	// Functionality for copyright notice copied from ExportDocument widget
    	mdlCopyrightNoticeUI.removeFromParent();
        RootPanel.get().add(mdlCopyrightNoticeUI);

        btnCloseCopyrightNoticeUI.addClickHandler(event -> {
            mdlCopyrightNoticeUI.close();
        });

    	btnAddTask.addClickHandler(event -> addTask());
    	btnDeleteTasks.addClickHandler(event -> deleteAllTasks());
    	btnGenerateExercises.addClickHandler(event -> generateExercises());
    }
    
    /**
     * Deletes all task widgets and removes them from the view.
     */
    private void deleteAllTasks() {
    	List<Widget> existingTasks = wdgtTasks.getChildrenList();
    	for(Widget existingTask : existingTasks) {
    		deleteTask((TaskItem)existingTask);
    	}
    }
    
    /**
     * Removes the given task from the UI
     * @param task	The task to remove
     */
    public void deleteTask(TaskItem task) {
    	wdgtTasks.remove(task);
    	if(wdgtTasks.getChildrenList().size() == 0) {
    		wdgtTasks.setVisible(false);
    	}
    	
    	setGenerateExercisesEnabled();
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
    	TaskItem newTask = new TaskItem(this, name);
    	newTask.drpQuiz.addSelectionHandler(new SelectComboHandler<Option>()
    	{
			@Override
			public void onSelectItem(SelectItemEvent<Option> event) {
				updateSelectableQuizzes();
			}
    	});
    	
    	newTask.expTask.setParent(wdgtTasks);
    	newTask.expTask.expand();

        wdgtTasks.add(newTask);

        updateSelectableQuizzes();
        newTask.initializeRelevantConstructions();

		wdgtTasks.setVisible(true);
    }
    
    /**
     * Updates the construction counts when the selected document has been changed.
     */
    public void initConstructionsOccurrences() {
    	if(wdgtTasks.getChildrenList().size() == 0) {
            addTask();
    	}
		/*for(Widget task : wdgtTasks.getChildren()) {
			if(task instanceof TaskItem) {
				((TaskItem)task).initializeRelevantConstructions();
			}
		}*/
    }
    
    /**
     * Determines if there is at least 1 valid task.
     * @return	<c>true</c> if there is at least 1 valid task; otherwise <c>false</c>
     */
    public boolean hasValidTasks() {
    	for(Widget task : wdgtTasks.getChildrenList()) {
            if(task instanceof TaskItem && ((TaskItem)task).icoOk.isVisible()) {
            	return true;
            }
    	}
    	
    	return false;
    }

    
    /**
     * Re-sets the options in the quiz dropdown whenever the selection in one of the tasks has been changed, 
     * thus possibly changing the available quizzes.
     */
    private void updateSelectableQuizzes() {
    	List<Widget> existingTasks = wdgtTasks.getChildrenList();
    	ArrayList<Integer> selectedQuizzes = new ArrayList<Integer>();
    	for(Widget existingTask : existingTasks) {
    		String selectedQuiz = ((TaskItem)existingTask).getQuiz();

    		if(!selectedQuiz.equals("")) {
    			int selectedNumber = Integer.parseInt(selectedQuiz);
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
    		String currentlySelectedQuiz = ((TaskItem)existingTask).getQuiz();
    		    		
    		// Remove all previous options
    		((TaskItem)existingTask).drpQuiz.clear();
    		
    		// Add the no quiz option
			addOptionToQuiz("---", "", (TaskItem)existingTask, currentlySelectedQuiz, 0);    			

    		// Add the newly determined options
    		for(int i = 0; i < selectedQuizzes.size(); i++) {
    			int selectedQuiz = selectedQuizzes.get(i);
    			addOptionToQuiz("Quiz " + selectedQuiz, "" + selectedQuiz, (TaskItem)existingTask, currentlySelectedQuiz, i + 1);    	
    			
    		}
    	}
    }
    
    /**
     * Adds an option to the specified dropdown.
     * @param name			The string of the option to display
     * @param value			The value of the option to use internally
     * @param task			The Task item to which the quiz belongs
     * @param selectedQuiz	The value of the currently selected option
     * @param index			The index at which the element will be inserted
     */
    private void addOptionToQuiz(String name, String value, TaskItem task, String selectedQuiz, int index) {
    	Option newSelection = new Option();
		newSelection.setText(name);
		newSelection.setValue(value);
		newSelection.setTextColor(Color.BLACK);
		newSelection.setFontSize("10pt");
		task.drpQuiz.add(newSelection);	
		
		if(value.equals(selectedQuiz)) {
			task.drpQuiz.setSelectedIndex(index);
		}
    }
    
    /**
     * Enables the Generate exercises button if there is at least 1 valid exercise.
     * Disables it otherwise.
     */
    public void setGenerateExercisesEnabled() {
    	boolean hasValidExercise = false;
    	for(Widget existingTask : wdgtTasks.getChildrenList()) {
    		if (((TaskItem)existingTask).icoOk.isVisible() || ((TaskItem)existingTask).icoWarning.isVisible()) {
    			hasValidExercise = true;
    		}
    	}
    	
    	btnGenerateExercises.setEnabled(hasValidExercise);
    }
    
    @Override
    public void enableButton() {
    	btnGenerateExercises.setText("Generate exercises");
    	btnGenerateExercises.setBackgroundColor(Color.ORANGE);
    	spnGenerating.setVisible(false);
    }
    
    /**
     * Generate H5P exercises for all valid tasks
     */
    private void generateExercises()
    {
    	if(btnGenerateExercises.getText().equals("Cancel")) {
    		interruptHandler.handle();
    	} else {
	    	btnGenerateExercises.setText("Cancel");
	    	btnGenerateExercises.setBackgroundColor(Color.RED);    	
	    	spnGenerating.setVisible(true);
	        ArrayList<ExerciseSettings> exerciseSettings = new ArrayList<>();
	    	for(Widget existingTask : wdgtTasks.getChildrenList()) {
	    		if (((TaskItem)existingTask).icoOk.isVisible()) {
	    			exerciseSettings.add(((TaskItem)existingTask).generateExerciseSettings());
	    		}
	    	}
	    	
	    	startGenerationHandler.handle(exerciseSettings);
    	}
    }

    GenerateHandler startGenerationHandler;
    InterruptHandler interruptHandler;
    
	@Override
	public void provideForDownload(byte[] file, String fileName) {	
		enableButton();
    	
		if(file != null && file.length > 0) {
			if (!chkDontShowCopyrightNoticeUI.getValue()) {
                titleCopyrightNoticeUI.setDescription("The configured exercises were generated successfully for the selected document. Please respect the copyright of the texts!");
                mdlCopyrightNoticeUI.open();
            }
			
	        JSUtility.exportToZip(file, fileName);
    	} else {
            ToastNotification.fire("We're sorry, no exercises could be generated. Please try with another document.");
    	}
	}

	@Override
	public void setGenerateHandler(GenerateHandler handler) {
		startGenerationHandler = handler;
	}

	@Override
	public void setInterruptHandler(InterruptHandler handler) {
		interruptHandler = handler;
	}

}