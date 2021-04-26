package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.widgets.DocumentPreviewPane;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.dtos.RankableDocument.ConstructionRange;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent.SelectComboHandler;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.html.Option;

public class TaskItem extends LocalizedComposite {
	
	interface TaskItemUiBinder extends UiBinder<Widget, TaskItem> {
    }

    private static TaskItemUiBinder ourUiBinder = GWT.create(TaskItemUiBinder.class);


    private static TaskItem.TaskItemLocalizationBinder localeBinder = GWT.create(TaskItem.TaskItemLocalizationBinder.class);

    interface TaskItemLocalizationBinder extends LocalizationBinder<TaskItem> {
    }
    
    @UiField
    MaterialComboBox<Option> drpTopic;
    @UiField
    MaterialComboBox<Option> drpType;
    @UiField
    MaterialComboBox<Option> drpQuiz;
    @UiField
    Option optTypeDefault;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnDelete;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnSelectDocumentPart;
    @UiField
    MaterialButton btnApplyDocumentSelection;
    @UiField
    MaterialButton btnDiscardDocumentSelection;
    @UiField
    MaterialLabel lblSettings;
    @UiField
    MaterialLabel lblNumberExercises;
    @UiField
    MaterialLabel lblTensesSentences;
    @UiField
    MaterialLabel lblTensesWords;
    @UiField
    TextArea lblDocumentForSelection;
    @UiField
    MaterialLink lblName;
    @UiField
    MaterialRow grpPos;
    @UiField
    MaterialRow grpCompForm;
    @UiField
    MaterialRow grpForms;
    @UiField
    MaterialRow grpScope;
    @UiField
    MaterialRow grpCondTypes;
    @UiField
    MaterialRow grpClauses;
    @UiField
    MaterialRow grpSentTypes;
    @UiField
    MaterialRow grpTenses;
    @UiField
    MaterialRow grpVerbSplitting;
    @UiField
    MaterialRow grpTargetWords;
    @UiField
    MaterialRow grpSentenceTypes;
    @UiField
    MaterialRow grpVerbForms;
    @UiField
    MaterialRow grpVerbPerson;
    @UiField
    MaterialRow grpPronouns;
    @UiField
    MaterialRow grpBrackets;
    @UiField
    MaterialRow grpDistractors;
    @UiField
    MaterialCheckBox chkBracketsLemma;
    @UiField
    MaterialCheckBox chkBracketsPos;
    @UiField
    MaterialCheckBox chkBracketsForm;
    @UiField
    MaterialCheckBox chkBracketsConditional;
    @UiField
    MaterialCheckBox chkBracketsWill;
    @UiField
    MaterialCheckBox chkBracketsSentenceType;
    @UiField
    MaterialCheckBox chkBracketsTense;
    @UiField
    MaterialCheckBox chkBracketsActiveSentence;
    @UiField
    MaterialCheckBox chkBracketsKeywords;
    @UiField
    MaterialCheckBox chkDistractorsOtherForm;
    @UiField
    MaterialCheckBox chkDistractorsOtherVariant;
    @UiField
    MaterialCheckBox chkDistractorsOtherPast;
    @UiField
    MaterialCheckBox chkDistractorsOtherTense;
    @UiField
    MaterialCheckBox chkDistractorsIncorrectForms;
    @UiField
    MaterialCheckBox chkDistractorsWrongConditional;
    @UiField
    MaterialCheckBox chkDistractorsWrongClause;
    @UiField
    MaterialCheckBox chkDistractorsWrongSuffixUse;
    @UiField
    MaterialCheckBox chkDistractorsWrongSuffix;
    @UiField
    MaterialCheckBox chkRegularVerbs;
    @UiField
    MaterialCheckBox chkIrregularVerbs;
    @UiField
    MaterialCheckBox chk3Pers;
    @UiField
    MaterialCheckBox chkNot3Pers;
    @UiField
    MaterialCheckBox chkPresentSimple;
    @UiField
    MaterialCheckBox chkPastSimple;
    @UiField
    MaterialCheckBox chkFutureSimple;
    @UiField
    MaterialCheckBox chkPresentProgressive;
    @UiField
    MaterialCheckBox chkPastProgressive;
    @UiField
    MaterialCheckBox chkFutureProgressive;
    @UiField
    MaterialCheckBox chkPresentPerfect;
    @UiField
    MaterialCheckBox chkPastPerfect;
    @UiField
    MaterialCheckBox chkFuturePerfect;
    @UiField
    MaterialCheckBox chkPresentPerfectProg;
    @UiField
    MaterialCheckBox chkPastPerfectProg;
    @UiField
    MaterialCheckBox chkFuturePerfectProg;
    @UiField
    MaterialCheckBox chkScopeActive;
    @UiField
    MaterialCheckBox chkScopePassive;
    @UiField
    MaterialCheckBox chkWho;
    @UiField
    MaterialCheckBox chkWhich;
    @UiField
    MaterialCheckBox chkThat;
    @UiField
    MaterialCheckBox chkOtherRelPron;
    @UiField
    MaterialCheckBox chkAffirmativeSent;
    @UiField
    MaterialCheckBox chkNegatedSent;
    @UiField
    MaterialCheckBox chkQuestions;
    @UiField
    MaterialCheckBox chkStatements;
    @UiField
    MaterialCheckBox chkscopeType1;
    @UiField
    MaterialCheckBox chkscopeType2;
    @UiField
    MaterialCheckBox chkPosAdj;
    @UiField
    MaterialCheckBox chkPosAdv;
    @UiField
    MaterialCheckBox chkFormComparatives;
    @UiField
    MaterialCheckBox chkFormSuperlatives;
    @UiField
    MaterialCheckBox chkFormAnalytic;
    @UiField
    MaterialCheckBox chkFormSynthetic;
    @UiField
    MaterialRadioButton rbtPerSentence;
    @UiField
    MaterialRadioButton rbtSingleTask;
    @UiField
    MaterialRadioButton rbtMainClause;
    @UiField
    MaterialRadioButton rbtIfClause;
    @UiField
    MaterialRadioButton rbtEitherClause;
    @UiField
    MaterialRadioButton rbtBothClauses;
    @UiField
    MaterialRadioButton rbt1Verb;
    @UiField
    MaterialRadioButton rbt2Verbs;
    @UiField
    MaterialDialog dlgDocumentSelection;
    
    private ConstructionComponentsCollection constructionComponents;
    private ExerciseGenerationWidget parent;
    
    public TaskItem(ExerciseGenerationWidget parent) {   
    	this.parent = parent;
    	
        initWidget(ourUiBinder.createAndBindUi(this));
        initLocale(localeBinder.bind(this));
        initHandlers();
        
        settingsWidgets = new Widget[] {
        		grpBrackets, chkBracketsLemma, chkBracketsConditional, chkBracketsPos, chkBracketsForm, chkBracketsWill, 
        		chkBracketsSentenceType, chkBracketsTense, chkBracketsActiveSentence, chkBracketsKeywords, 
        		grpDistractors, chkDistractorsOtherForm, chkDistractorsOtherVariant, chkDistractorsOtherPast, chkDistractorsOtherTense, 
        		chkDistractorsIncorrectForms, chkDistractorsWrongConditional, chkDistractorsWrongClause, chkDistractorsWrongSuffixUse,
        		chkDistractorsWrongSuffix, grpPos, grpCompForm, grpForms, grpVerbPerson, grpVerbForms, grpSentenceTypes, grpTenses,
        		lblTensesSentences, chkPresentSimple, chkFutureSimple, chkPresentProgressive, chkPastProgressive, chkFutureProgressive, 
        		chkFuturePerfect, chkPresentPerfectProg, chkPastPerfectProg, chkFuturePerfectProg, lblTensesWords, grpCondTypes, 
        		grpClauses, grpScope, grpPronouns, grpVerbSplitting, grpTargetWords, grpSentTypes, chkPastSimple, chkPresentPerfect, 
        		chkPastPerfect, chkScopeActive, chkWho, chkWhich, chkThat, chkOtherRelPron, chk3Pers, chkNot3Pers, chkAffirmativeSent, 
        		chkNegatedSent, chkQuestions, chkStatements, chkRegularVerbs, chkIrregularVerbs, chkscopeType1, chkscopeType2, 
        		chkPosAdj, chkPosAdv, chkFormComparatives, chkFormSuperlatives, chkFormSynthetic, chkFormAnalytic
        };
        
        visibilityManagers = new VisibilityManagerCollection(this);
        initUI();
    }
    
    private int currentSelectionStartIndex = 0;
    private int currentSelectionLength = 0;
    

    /**
     * The widgets whose visibility depends on the settings.
     */
    final Widget[] settingsWidgets;

    /**
     * The occurrences of constructions relevant to exercise generation in the currently selected document part.
     */
    private HashMap<String, Integer> relevantConstructionsInSelectedDocumentPart = null;
    
    /**
     * The occurrences of constructions relevant to exercise generation in the current document.
     */
    public HashMap<String, Integer> relevantConstructionsInEntireDocument = null;
    
    /**
     * The possible topics for the dropdown.
     */
    private ArrayList<Pair<String, String>> possibleTopics;
    
    private final VisibilityManagerCollection visibilityManagers;
    
    /**
     * Calculates the start index and length of the selected document part within the previewed document.
     * @return	The start index and length of the current selection
     */
    private Pair<Integer, Integer> calculateSelectionIndices() {
    	RankableDocument doc = DocumentPreviewPane.getInstance().getCurrentlyPreviewedDocument().getDocument();
    	String selectedPart = lblDocumentForSelection.getSelectedText();
    	int startIndex;
    	int length;
    	if(selectedPart == null || selectedPart.length() == 0) {
    		selectedPart = doc.getText();
    		startIndex = 0;
    		length = selectedPart.length();
    	} else {
    		startIndex = doc.getText().indexOf(selectedPart);
    		length = selectedPart.length();
    	}
    	
    	return new Pair<Integer, Integer>(startIndex, length);
    }
    
    /**
     * Initializes all handlers.
     */
    private void initHandlers() {
    	dlgDocumentSelection.removeFromParent();
        RootPanel.get().add(dlgDocumentSelection);

        btnApplyDocumentSelection.addClickHandler(event -> {
        	dlgDocumentSelection.close();
        	relevantConstructionsInSelectedDocumentPart = new HashMap<String, Integer>();
        	calculateConstructionsOccurrences(relevantConstructionsInSelectedDocumentPart);
        });
        btnDiscardDocumentSelection.addClickHandler(event -> {
        	dlgDocumentSelection.close();
    		lblDocumentForSelection.setSelectionRange(currentSelectionStartIndex, currentSelectionLength);
        });
        
    	btnDelete.addClickHandler(event -> {
    		parent.deleteTask(this);
    	});
    	
    	btnSelectDocumentPart.addClickHandler(event -> {
    		Pair<Integer, Integer> selectionIndices = calculateSelectionIndices();
    		currentSelectionStartIndex = selectionIndices.getKey();
    		currentSelectionLength = selectionIndices.getValue();
    		    		
    		dlgDocumentSelection.open();
    	});
    	    
    	drpTopic.addSelectionHandler(new SelectComboHandler<Option>()
    	{
			@Override
			public void onSelectItem(SelectItemEvent<Option> event) {
				setExerciseSettingsVisibilities();
			}
    	});
    	
    	drpType.addSelectionHandler(new SelectComboHandler<Option>()
    	{
			@Override
			public void onSelectItem(SelectItemEvent<Option> event) {
				setExerciseSettingsVisibilities();
			}
    	});
    	    	    	    	
    	rbtSingleTask.addValueChangeHandler(e -> setExerciseSettingsVisibilities());
    	rbtPerSentence.addValueChangeHandler(e -> setExerciseSettingsVisibilities());

    }
        
    /**
     * Initializes the UI components and sets their visibility.
     */
    private void initUI() {
    	possibleTopics = new ArrayList<Pair<String, String>>();
    	possibleTopics.add(new Pair<String, String>("Comparatives", "Compare"));
    	possibleTopics.add(new Pair<String, String>("Present simple", "Present"));
    	possibleTopics.add(new Pair<String, String>("Past tenses", "Past"));
    	possibleTopics.add(new Pair<String, String>("Passive", "Passive"));
    	possibleTopics.add(new Pair<String, String>("Conditionals", "'if'"));
    	possibleTopics.add(new Pair<String, String>("Relative Clauses", "Relatives"));
    	
        constructionComponents = new ConstructionComponentsCollection(this);
        
    	setExerciseSettingsVisibilities();
    }
    
    /**
     * Sets the visibility of the settings parameters.
     * Only those widgets given in the visible settings list are displayed.
     * @param visibleSettings	The widgets to be displayed
     */
    private void setSettingsVisibility(ArrayList<Widget> visibleSettings) {
    	if(visibleSettings.size() == 0) {
    		lblSettings.setVisible(false);   		
    	} else {
    		lblSettings.setVisible(true);
    	}
    	
    	for(Widget settingsWidget : settingsWidgets) {
    		if(visibleSettings.contains(settingsWidget)) {
    			settingsWidget.setVisible(true);
    		} else {
    			settingsWidget.setVisible(false);
    		}
    	}
    }
    
    
    /**
     * Gets the value of the currently selected topic.
     * @return	The value of the currently selected topic, if any; otherwise the empty string
     */
    private String getTopic() {
    	for(Object o : drpTopic.getSelectedValue()) {
    		return o.toString();
    	}   		
    	
    	return "";
    }
    
    /**
     * Gets the value of the currently selected exercise type.
     * @return	The value of the currently selected exercise type, if any; otherwise the empty string
     */
    private String getExerciseType() {
    	for(Object o : drpType.getSelectedValue()) {
    		return o.toString();
    	}   		
    	
    	return "";
    }
    
    
    /**
     * Shows only those exercise parameters that are relevant for the topic and exercise type and hides all others
     */
    private void setExerciseSettingsVisibilities() {  
    	String topic = getTopic();
    	String exerciseType = getExerciseType();
    	int numberOfExercises = 0;

    	VisibilityManager visibilityManager = visibilityManagers.getVisibilityManger(topic, exerciseType);
    	ArrayList<Widget> visibleSettings;
    	if(visibilityManager != null) {
    		numberOfExercises = calculateNumberOfExercises();
    		visibleSettings = visibilityManager.getVisibleWidgets(numberOfExercises);
    	} else {
        	visibleSettings = new ArrayList<Widget>();
    	}
    	    	
    	setSettingsVisibility(visibleSettings);  	
    	
    	setNumberExercisesText(numberOfExercises);
    }
    

    /**
     * Re-calculates the constructions relevant to exercise generation for the entire document and for the selected document part.
     * Sets the text for document part selection to the previewed text and the selected range to the entire document.
     * Sets the possible topics options in the dropdown.
     */
    public void initializeRelevantConstructions() {
    	RankableDocument doc = DocumentPreviewPane.getInstance().getCurrentlyPreviewedDocument().getDocument();
        lblDocumentForSelection.setText(doc.getText());
		lblDocumentForSelection.setSelectionRange(0, doc.getText().length());
		
		relevantConstructionsInEntireDocument = new HashMap<String, Integer>();
    	calculateConstructionsOccurrences(relevantConstructionsInEntireDocument);
    	relevantConstructionsInSelectedDocumentPart = new HashMap<String, Integer>(relevantConstructionsInEntireDocument);  

    	String selecteTopic = getSelectedTopic();
    	drpTopic.clear();
    	
    	// Add the no topic option
    	addOptionToTopic("---", "Topic", selecteTopic, 0);    			

		// Add the newly determined options    	
    	int i = 1;
    	for(Pair<String, String> possibleTopic : possibleTopics) {
    		if(possibleTopic.getValue().equals("Passive")) {
    			if(getNumberOfConstructionOccurrences("passive", "Passive", 1) > 0) {
    				addOptionToTopic(possibleTopic.getKey(), possibleTopic.getValue(), selecteTopic, i);  
        			i++;
    			}
    		} else {
	    		if(checkConstructionOccurs(null, possibleTopic.getValue(), 0)) {
	    			addOptionToTopic(possibleTopic.getKey(), possibleTopic.getValue(), selecteTopic, i);  
	    			i++;
	        	}
    		}
    	}
    	
    	setExerciseSettingsVisibilities();
    }
    
    /**
     * Gets the value of the currently selected quiz.
     * @return	The value of the currently selected quiz, if any; otherwise the empty string
     */
    private String getSelectedTopic() {    	
    	//We get a ClassCastException when we try to access the first list element, so we just iterate over the (only) element
    	for(Object o : drpTopic.getSelectedValue()) {
    		return o.toString();
    	}   		
    	
    	return "";
    }
    
    /**
     * Adds an option to the specified dropdown.
     * @param name			The string of the option to display
     * @param value			The value of the option to use internally
     * @param selectedTopic	The value of the currently selected option
     * @param index			The index at which the element will be inserted
     */
    private void addOptionToTopic(String name, String value, String selectedTopic, int index) {
    	Option newSelection = new Option();
		newSelection.setText(name);
		newSelection.setValue(value);
		newSelection.setTextColor(Color.BLACK);
		newSelection.setFontSize("10pt");
		drpTopic.add(newSelection);	
		
		if(value.equals(selectedTopic)) {
			drpTopic.setSelectedIndex(index);
		}
    }

    /**
     * Calculates the number of exercises that can be generated for the current document with the current parameter settings.
     * @return The number of exercises that can be generated.
     */
    private int calculateNumberOfExercises() {
    	String topic = getTopic();
    	String exerciseType = getExerciseType();
    	
    	ArrayList<String> constructionsToConsider = new ArrayList<String>();

    	if(topic.equals("Passive")) {
    		if(exerciseType.equals("FiB")) {    			    			    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPassiveFiBComponents());    			
    		} else if(exerciseType.equals("Drag")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPassiveDragComponents());    	
    		}
    	} else if(topic.equals("Relatives")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Mark") || exerciseType.equals("Select") || exerciseType.equals("Drag") && rbtPerSentence.getValue()) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getRelativesFiBMarkSelectComponents());  
    		} else if(exerciseType.equals("Drag") && !rbtPerSentence.getValue()) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getRelativesDragComponents());      			
    		}
    	} else if(topic.equals("Present")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select")) {       			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPresentFiBSelectComponents());
    		} else if(exerciseType.equals("Mark")) {     			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPresentMarkComponents());   			
    		}
    	} else if(topic.equals("Past")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select")) {    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPastFiBSelectComponents());    			
    		} else if(exerciseType.equals("Mark") || exerciseType.equals("Drag")) {    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPastMarkDragComponents());    			
    		} 
    	} else if(topic.equals("'if'")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Drag")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getConditionalComponents());    	
    		} 
    	} else if(topic.equals("Compare")) {
			if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Mark") || exerciseType.equals("Drag")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getComparativeComponents());  
    		}
    	}

    	int nExercises = 0;
    	
    	for(String constructionToConsider : constructionsToConsider) {
    		nExercises += relevantConstructionsInSelectedDocumentPart.get(constructionToConsider);
    	}
    	    	
    	return nExercises;
    }
    
    /**
     * Creates all combinations of constructions for the checked settings.
     * @param 	constructionComponent A list of the individual constructions making up a more complex (more detailed) construction. 
     * 			Each construction consists of a list of possible values.
     * @return	The list of construction names corresponding to relevantConstructions names
     */
    private ArrayList<String> getConstructionNamesFromSettings(ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> constructionLevels) {
		ArrayList<String> initialConstructionNames = new ArrayList<String>();
    	if(constructionLevels.size() > 0) {
    		for(Pair<MaterialCheckBox, String> firstLevelConstruction : constructionLevels.get(0)) {
    			if(firstLevelConstruction.getKey() == null ||  firstLevelConstruction.getKey().getValue()) {
    				initialConstructionNames.add(firstLevelConstruction.getValue());
    			}   		
    		}
		
    		if(constructionLevels.size() > 1) {
    			for(int i = 1; i < constructionLevels.size(); i++) {
    				if(initialConstructionNames.size() > 0) {
    					ArrayList<String> currentConstructionNames = new ArrayList<String>();
    					for(Pair<MaterialCheckBox, String> currentLevelConstruction : constructionLevels.get(i)) {
    						if(currentLevelConstruction.getKey() == null || currentLevelConstruction.getKey().getValue()) {
    							for(String initialConstructionName : initialConstructionNames) {
    			    				currentConstructionNames.add(initialConstructionName + "-" + currentLevelConstruction.getValue());
    							}
    						}
    					}
    					initialConstructionNames = currentConstructionNames;
    				}
    			}
    		}
    	}

    	return initialConstructionNames;
    }
    

	/**
     * Sets the text on whether and  how many exercises can be generated according to the selected topic and exercise type,
     * the individual parameter settings and the selected document.
     */
    private void setNumberExercisesText(int numberOfExercises) {
    	String topic = getTopic();
    	String exerciseType = getExerciseType();

		if(exerciseType.equals("Exercise Type") || topic.equals("Topic")) {
			lblNumberExercises.setText("Select a grammar topic and an exercise type to enable exercise generation.");
    		lblNumberExercises.setTextColor(Color.GREY);
		} else if(topic.equals("Passive") && exerciseType.equals("Select") || 
				topic.equals("'if'") && exerciseType.equals("Mark") || topic.equals("Present") && exerciseType.equals("Drag")) {
    		lblNumberExercises.setText("No exercises can be generated for the current settings.");
    		lblNumberExercises.setTextColor(Color.RED);
		} else {
    		if(numberOfExercises == 0) {
    			lblNumberExercises.setText("No exercises can be generated for the current settings.");
        		lblNumberExercises.setTextColor(Color.RED);
    		} else {
        		lblNumberExercises.setTextColor(Color.BLACK);
    			if(exerciseType.equals("FiB") || exerciseType.equals("Select")) {
            		lblNumberExercises.setText(numberOfExercises + " blanks can be generated for the current settings.");
    			} else if(exerciseType.equals("Mark")) {
            		lblNumberExercises.setText(numberOfExercises + " target words can be generated for the current settings.");
    			} else if(exerciseType.equals("Drag")) {
    				if((topic.equals("Relatives") || topic.equals("'if'")) && rbtPerSentence.getValue() || topic.equals("Passive")) {
    					// 1 exercise per sentence
                		lblNumberExercises.setText(numberOfExercises + " exercises can be generated for the current settings.");
    				} else {
    					if(numberOfExercises < 2) {
    						// We need at least 2 target words for drag & drop to make sense
    						lblNumberExercises.setText("No exercises can be generated for the current settings.");
    		        		lblNumberExercises.setTextColor(Color.RED);
    					} else {
    						lblNumberExercises.setText(numberOfExercises + " target words can be generated for the current settings.");
    					}
    				}
    			}
    		}
    	}
		
    }
    
    /**
     * Filters those constructions that are within the selected part of the document.
     * @param startIndex 	The start index of the selected part within the document
     * @param endIndex		The end index of the selected part within the document
     * @param construction	The construction under consideration
     * @param doc			The document containing the text and constructions
     * @return				The occurrences of the construction within the selected range
     */
    private ArrayList<ConstructionRange> getConstructionsWithinSelectedPart(int startIndex, int endIndex, 
    		GrammaticalConstruction construction, RankableDocument doc) {
    	ArrayList<ConstructionRange> containedConstructions = new ArrayList<ConstructionRange>();
		
    	for(ConstructionRange range : doc.getConstructionOccurrences(construction)) {
    		if(range.getStart() >= startIndex && range.getEnd() <= endIndex) {
    			containedConstructions.add(range);
    		}
    	}
    	
    	return containedConstructions;
    }
    
    
    /**
     * Calculates the occurrences of constructions in the combinations relevant to exercise generation.
     */
    public void calculateConstructionsOccurrences(HashMap<String, Integer> relevantConstructions) {    
    	// Calculate indices of the selected document part
    	RankableDocument doc = DocumentPreviewPane.getInstance().getCurrentlyPreviewedDocument().getDocument();
    	
    	Pair<Integer, Integer> selectionIndices = calculateSelectionIndices();
    	int startIndex = selectionIndices.getKey();
    	int endIndex = selectionIndices.getValue() + startIndex;
		                
        //non-combined constructions
		relevantConstructions.put("adj-comp-syn", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("adj-sup-syn", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("adj-comp-ana", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("adj-sup-ana", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("adv-comp-syn", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("adv-sup-syn", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("adv-comp-ana", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("adv-sup-ana", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("condReal", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
		relevantConstructions.put("condUnreal", getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, doc).size());
        	
        // passive combinations
        GrammaticalConstruction[] tenseConstructions = new GrammaticalConstruction[]{
        		GrammaticalConstruction.TENSE_PRESENT_SIMPLE,
        		GrammaticalConstruction.TENSE_PAST_SIMPLE,
        		GrammaticalConstruction.TENSE_FUTURE_SIMPLE,
        		GrammaticalConstruction.TENSE_FUTURE_PERFECT,
        		GrammaticalConstruction.TENSE_PRESENT_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_PRESENT_PERFECT_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_PAST_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_PAST_PERFECT_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_FUTURE_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_FUTURE_PERFECT_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_PRESENT_PERFECT,
        		GrammaticalConstruction.TENSE_PAST_PERFECT,
        };
        
        ArrayList<ConstructionRange> passiveOccurrences = 
        		getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.PASSIVE_VOICE, doc);
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<ConstructionRange> tenseOccurrences = 
            		getConstructionsWithinSelectedPart(startIndex, endIndex, tenseConstruction, doc);
        	int nPassiveOccurrences = 0;
        	int nActiveOccurrences = 0;
            for(ConstructionRange tenseOccurrence : tenseOccurrences) {
            	boolean foundPassiveOverlap = false;
            	for(ConstructionRange passiveOccurrence : passiveOccurrences) {
            		if(passiveOccurrence.getStart() >= tenseOccurrence.getStart() && passiveOccurrence.getStart() < tenseOccurrence.getEnd() || 
            				tenseOccurrence.getStart() >= passiveOccurrence.getStart() && tenseOccurrence.getStart() < passiveOccurrence.getEnd()) {
            			// There is some overlap between the passive construction and the tense construction
            			foundPassiveOverlap = true;
            			break;
            		}
            	}
            	if(foundPassiveOverlap) {
        			nPassiveOccurrences++;
            	} else {
            		nActiveOccurrences++;
            	}
            }
			relevantConstructions.put("passive-" + tenseConstruction.name(), nPassiveOccurrences);
			relevantConstructions.put("active-" + tenseConstruction.name(), nActiveOccurrences);                               	
        }
        
        tenseConstructions = new GrammaticalConstruction[]{
        		GrammaticalConstruction.TENSE_PAST_SIMPLE,
        		GrammaticalConstruction.TENSE_PRESENT_PERFECT,
        		GrammaticalConstruction.TENSE_PAST_PERFECT
        };
        
        ArrayList<ConstructionRange> negationOccurrences = 
        		getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.NEGATION_NOT, doc);
        negationOccurrences.addAll(getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.NEGATION_NT, doc));
        ArrayList<ConstructionRange> questionOccurrences = 
        		getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.QUESTIONS_DIRECT, doc);
        ArrayList<ConstructionRange> irregularOccurrences = 
        		getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.VERBS_IRREGULAR, doc);

        // past tense combinations
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<ConstructionRange> tenseOccurrences = 
            		getConstructionsWithinSelectedPart(startIndex, endIndex, tenseConstruction, doc);
        	int nQuestionAffirmativeRegular = 0;
        	int nQuestionAffirmativeIrregular = 0;
        	int nQuestionNegativeRegular = 0;
        	int nQuestionNegativeIrregular = 0;
        	int nStatementAffirmativeRegular = 0;
        	int nStatementAffirmativeIrregular = 0;
        	int nStatementNegativeRegular = 0;
        	int nStatementNegativeIrregular = 0;
        	
            for(ConstructionRange tenseOccurrence : tenseOccurrences) {
            	boolean foundNegationOverlap = false;
            	for(ConstructionRange negationOccurrence : negationOccurrences) {
            		if(negationOccurrence.getEnd() >= tenseOccurrence.getStart() - 1 && negationOccurrence.getStart() <= tenseOccurrence.getEnd() + 1) {
            			// The negation is at most the previous or succeeding token (only a single character in-between for a whitespace)
            			foundNegationOverlap = true;
            			break;
            		}
            	}
            	
            	boolean foundQuestionOverlap = false;
            	for(ConstructionRange questionOccurrence : questionOccurrences) {
            		if(tenseOccurrence.getStart() >= questionOccurrence.getStart() && tenseOccurrence.getEnd() <= questionOccurrence.getEnd()) {
            			// The verb is within a question
            			foundQuestionOverlap = true;
            			break;
            		}
            	}
            	
            	boolean foundIrregularOverlap = false;
            	for(ConstructionRange irregularOccurrence : irregularOccurrences) {
            		if(irregularOccurrence.getStart() >= tenseOccurrence.getStart() && irregularOccurrence.getEnd() <= tenseOccurrence.getEnd()) {
            			// The irregular verb is part of the verb (irregular forms consist of a single token)
            			foundIrregularOverlap = true;
            			break;
            		}
            	}
            	
            	if(foundNegationOverlap) {
            		if(foundQuestionOverlap) {
            			if(foundIrregularOverlap) {
            				nQuestionNegativeIrregular++;
            			} else {
            				nQuestionNegativeRegular++;
            			}
            		} else {
            			if(foundIrregularOverlap) {
            				nStatementNegativeIrregular++;
            			} else {
            				nStatementNegativeRegular++;
            			}
            		}
            	} else {
            		if(foundQuestionOverlap) {
            			if(foundIrregularOverlap) {
            				nQuestionAffirmativeIrregular++;
            			} else {
            				nQuestionAffirmativeRegular++;
            			}
            		} else {
            			if(foundIrregularOverlap) {
            				nStatementAffirmativeIrregular++;
            			} else {
            				nStatementAffirmativeRegular++;
            			}
            		}
            	}
            	           	
            }
            
			relevantConstructions.put(tenseConstruction.name() + "-question-neg-irreg", nQuestionNegativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-question-neg-reg", nQuestionNegativeRegular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-neg-irreg", nStatementNegativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-neg-reg", nStatementNegativeRegular);
			relevantConstructions.put(tenseConstruction.name() + "-question-affirm-irreg", nQuestionAffirmativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-question-affirm-reg", nQuestionAffirmativeRegular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-affirm-irreg", nStatementAffirmativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-affirm-reg", nStatementAffirmativeRegular);
        }
        
        // present tense combinations
        ArrayList<ConstructionRange> presentOccurrences = 
        		getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.TENSE_PRESENT_SIMPLE, doc);
        int nQuestionAffirmative3 = 0;
    	int nQuestionAffirmativeNot3 = 0;
    	int nQuestionNegative3 = 0;
    	int nQuestionNegativeNot3 = 0;
    	int nStatementAffirmative3 = 0;
    	int nStatementAffirmativeNot3 = 0;
    	int nStatementNegative3 = 0;
    	int nStatementNegativeNot3 = 0;
        for(ConstructionRange tenseOccurrence : presentOccurrences) {
        	boolean foundNegationOverlap = false;
        	for(ConstructionRange negationOccurrence : negationOccurrences) {
        		if(negationOccurrence.getEnd() >= tenseOccurrence.getStart() - 1 && negationOccurrence.getStart() <= tenseOccurrence.getEnd() + 1) {
        			// The negation is at most the previous or succeeding token (only a single character in-between for a whitespace)
        			foundNegationOverlap = true;
        			break;
        		}
        	}
        	
        	boolean foundQuestionOverlap = false;
        	for(ConstructionRange questionOccurrence : questionOccurrences) {
        		if(tenseOccurrence.getStart() >= questionOccurrence.getStart() && tenseOccurrence.getEnd() <= questionOccurrence.getEnd()) {
        			// The verb is within a question
        			foundQuestionOverlap = true;
        			break;
        		}
        	}
        	
        	boolean isThirdPersonSingular = tenseOccurrence.toString().endsWith("s") || tenseOccurrence.equals("doesn't") || 
        			tenseOccurrence.equals("hasn't") || tenseOccurrence.equals("isn't");

        	if(foundNegationOverlap) {
        		if(foundQuestionOverlap) {
        			if(isThirdPersonSingular) {
        				nQuestionNegative3++;
        			} else {
        				nQuestionNegativeNot3++;
        			}
        		} else {
        			if(isThirdPersonSingular) {
        				nStatementNegative3++;
        			} else {
        				nStatementNegativeNot3++;
        			}
        		}
        	} else {
        		if(foundQuestionOverlap) {
        			if(isThirdPersonSingular) {
        				nQuestionAffirmative3++;
        			} else {
        				nQuestionAffirmativeNot3++;
        			}
        		} else {
        			if(isThirdPersonSingular) {
        				nStatementAffirmative3++;
        				nStatementAffirmativeNot3++;
        			}
        		}
        	}
        }
        
        relevantConstructions.put("present-question-neg-3", nQuestionNegative3);
		relevantConstructions.put("present-question-neg-not3", nQuestionNegativeNot3);
		relevantConstructions.put("present-stmt-neg-3", nStatementNegative3);
		relevantConstructions.put("present-stmt-neg-not3", nStatementNegativeNot3);
		relevantConstructions.put("present-question-affirm-3", nQuestionAffirmative3);
		relevantConstructions.put("present-question-affirm-not3", nQuestionAffirmativeNot3);
		relevantConstructions.put("present-stmt-affirm-3", nStatementAffirmative3);
		relevantConstructions.put("present-stmt-affirm-not3", nStatementAffirmativeNot3);                              	

		// relative pronouns
		ArrayList<ConstructionRange> relativeOccurrences = 
        		getConstructionsWithinSelectedPart(startIndex, endIndex, GrammaticalConstruction.CLAUSE_RELATIVE, doc);
    	int nWhoOccurrences = 0;
    	int nWhichOccurrences = 0;
    	int nThatOccurrences = 0;
    	int nOtherOccurrences = 0;
        for(ConstructionRange relativeOccurrence : relativeOccurrences) {
        	// We will only get an approximation like this, but without further NLP analysis, we cannot get a better estimate.
        	// By first looking for 'who' and 'which' we might filter out most occurrences of 'that' as conjunction or demonstrative pronoun
        	if(relativeOccurrence.toString().contains(" who ")) {
        		nWhoOccurrences++;
        	} else if(relativeOccurrence.toString().contains(" which ")) {
        		nWhichOccurrences++;
        	} else if(relativeOccurrence.toString().contains(" that ")) {
        		nThatOccurrences++;
        	} else {
        		nOtherOccurrences++;
        	}
        	
        }
		relevantConstructions.put("who", nWhoOccurrences);
		relevantConstructions.put("which", nWhichOccurrences);
		relevantConstructions.put("that", nThatOccurrences);
		relevantConstructions.put("otherRelPron", nOtherOccurrences);
		
		int numberOfExercises = calculateNumberOfExercises();
    	setNumberExercisesText(numberOfExercises);
    	lblDocumentForSelection.setText(doc.getText());
		lblDocumentForSelection.setSelectionRange(startIndex, endIndex - startIndex);
    }
    
    /**
     * Checks for a construction whether it occurs in the document.
     * @param constructionName	The name of the construction
     * @param topic				The topic to which the construction belongs
     * @param group				The index (in terms of first, second, third or fourth part) of the construction in the name
     * @return					<code>true</code> if the construction occurs in the text; otherwise <code>false</code>
     */
    private boolean checkConstructionOccurs(String constructionName, String topic, int group) {
		return getNumberOfConstructionOccurrences(constructionName, topic, group) > 0;
    }
    
    /**
     * Determines how often a construction occurs in the document.
     * @param constructionName	The name of the construction
     * @param topic				The topic to which the construction belongs
     * @param group				The index (in terms of first, second, third or fourth part) of the construction in the name
     * @return					The number of occurrences of the construction in the document
     */
    public int getNumberOfConstructionOccurrences(String constructionName, String topic, int group) {
    	int numberOccurrences = 0;
    	
		for(String name : getPartConstructionNames(topic, constructionName, group)) {
			numberOccurrences += relevantConstructionsInEntireDocument.get(name);
		}

		return numberOccurrences;
    }
    
    /**
     * Determines the names of all detailed constructions relevant to a topic with the value of one construction set.
     * @param topic	The topic to which the construction belongs
     * @param value	The value of the construction
     * @param group	The index (in terms of first, second, third or fourth part) of the construction in the name
     * @return The list of detailed construction names to which the construction belongs
     */
    public ArrayList<String> getPartConstructionNames(String topic, String value, int group) {
		ArrayList<Pair<MaterialCheckBox, String>> firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
		ArrayList<Pair<MaterialCheckBox, String>> secondLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
		ArrayList<Pair<MaterialCheckBox, String>> thirdLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
		ArrayList<Pair<MaterialCheckBox, String>> fourthLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();

    	if(topic.equals("Compare")) {
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "adj"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "adv"));
			
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "comp"));
			secondLevelConstructions. add(new Pair<MaterialCheckBox, String>(null, "sup"));

			thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "syn"));
			thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "ana"));
    	} else if(topic.equals("Present")) {
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "present"));
			
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "question"));
			secondLevelConstructions. add(new Pair<MaterialCheckBox, String>(null, "stmt"));

			thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "neg"));
			thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "affirm"));

			fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "3"));
			fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "not3"));			
    	} else if(topic.equals("Past")) {
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_SIMPLE"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PRESENT_PERFECT"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_PERFECT"));
			
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "question"));
			secondLevelConstructions. add(new Pair<MaterialCheckBox, String>(null, "stmt"));

			thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "neg"));
			thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "affirm"));

			fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "irreg"));
			fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "reg"));
    	} else if(topic.equals("Passive")) {
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "active"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "passive"));
			
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PRESENT_SIMPLE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_FUTURE_SIMPLE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PRESENT_PROGRESSIVE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_PROGRESSIVE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_FUTURE_PROGRESSIVE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_FUTURE_PERFECT"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PRESENT_PERFECT_PROGRESSIVE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_PERFECT_PROGRESSIVE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_FUTURE_PERFECT_PROGRESSIVE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_SIMPLE"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PRESENT_PERFECT"));
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_PERFECT"));
    	} else if(topic.equals("'if'")) {
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "condReal"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "condUnreal"));
    	}  else if(topic.equals("Relatives")) {
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "who"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "which"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "that"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "otherRelPron"));
    	}
    	
    	// For the construction part that we are interested in, we only use those constructions for our value.
    	if(group == 1) {
			firstLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, value));	    		
		} else if(group == 2) {
			secondLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
			secondLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, value));   			
		} else if(group == 3) {
			thirdLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
			thirdLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, value));
		}  else if(group == 4) {
			fourthLevelConstructions = new ArrayList<Pair<MaterialCheckBox, String>>();
			fourthLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, value));
		}	
    	
    	ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> constructionLevels = new ArrayList<ArrayList<Pair<MaterialCheckBox, String>>>();
		constructionLevels.add(firstLevelConstructions);
		if(secondLevelConstructions.size() > 0) {
			constructionLevels.add(secondLevelConstructions);
			
			if(thirdLevelConstructions.size() > 0) {
				constructionLevels.add(thirdLevelConstructions);

				if(fourthLevelConstructions.size() > 0) {
					constructionLevels.add(fourthLevelConstructions);
				}
			}
		}
		
		return getConstructionNamesFromSettings(constructionLevels);    	
    }
    
}