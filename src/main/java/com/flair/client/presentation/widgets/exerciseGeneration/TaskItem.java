package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.widgets.DocumentPreviewPane;
import com.flair.client.presentation.widgets.NumberSpinner;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;
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
import gwt.material.design.client.constants.RadioButtonType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialIcon;
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
    MaterialButton btnRemoveSelection;
    @UiField
    MaterialButton btnReset;
    @UiField
    MaterialButton btnDiscardDocumentSelection;
    @UiField
    MaterialButton btnUpdateDocument;
    @UiField
    MaterialLabel lblNumberExercises;
    @UiField
    MaterialLabel lblTensesSentences;
    @UiField
    MaterialLabel lblTensesWords;
    @UiField
    MaterialLabel lblDocTitle;
    @UiField
    MaterialLabel lblQuiz;
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
    MaterialCheckBox chkNTargets;
    MaterialRadioButton rbtPerSentence;
    MaterialRadioButton rbtSingleTask;
    MaterialRadioButton rbtMainClause;
    MaterialRadioButton rbtIfClause;
    MaterialRadioButton rbtEitherClause;
    MaterialRadioButton rbtBothClauses;
    MaterialRadioButton rbt1Verb;
    MaterialRadioButton rbt2Verbs;
    @UiField
    MaterialDialog dlgDocumentSelection;
    @UiField
    MaterialIcon icoOk;
    @UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
    MaterialIcon icoInvalid;
    @UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
    MaterialIcon icoWarning;
    @UiField
    NumberSpinner spnNDistractors;
    @UiField
    MaterialCollapsibleItem expTask;
    @UiField
    MaterialRow rowRbtPerSentence;
    @UiField
    MaterialRow rowRbtTargetClauses;
    @UiField
    MaterialRow rowRbtIfClause;
    @UiField
    MaterialRow rowRbtEitherClause;
    @UiField
    MaterialRow rowRbtBothClauses;
    @UiField
    MaterialRow rowRbt1Verb;
    @UiField
    MaterialRow rowRbt2Verbs;
    @UiField
    MaterialRow rowRbtSingleTask;
    @UiField
    MaterialRow grpNumberTargets;
    
    private ConstructionComponentsCollection constructionComponents;
    private ExerciseGenerationWidget parent;
    
    public TaskItem(ExerciseGenerationWidget parent, String name) {   
    	this.parent = parent;
    	
        initWidget(ourUiBinder.createAndBindUi(this));
        initLocale(localeBinder.bind(this));
                
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsOtherForm, DistractorProperties.OTHER_FORM));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsOtherVariant, DistractorProperties.OTHER_VARIANT));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsOtherPast, DistractorProperties.OTHER_PAST));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsOtherTense, DistractorProperties.OTHER_TENSE));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsIncorrectForms, DistractorProperties.INCORRECT_FORMS));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongConditional, DistractorProperties.WRONG_CONDITIONAL));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongClause, DistractorProperties.WRONG_CLAUSE));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongSuffixUse, DistractorProperties.WRONG_SUFFIX_USE));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongSuffix, DistractorProperties.INCORRECT_FORMS));
                
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsLemma, BracketsProperties.LEMMA));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsPos, BracketsProperties.POS));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsForm, BracketsProperties.COMPARISON_FORM));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsConditional, BracketsProperties.CONDITIONAL_TYPE));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsWill, BracketsProperties.WILL));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsSentenceType, BracketsProperties.SENTENCE_TYPE));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsTense, BracketsProperties.TENSE));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsActiveSentence, BracketsProperties.ACTIVE_SENTENCE));

        
        settingsWidgets = new Widget[] {
        		grpBrackets, chkBracketsLemma, chkBracketsConditional, chkBracketsPos, chkBracketsForm, chkBracketsWill, 
        		chkBracketsSentenceType, chkBracketsTense, chkBracketsActiveSentence, 
        		grpDistractors, chkDistractorsOtherForm, chkDistractorsOtherVariant, chkDistractorsOtherPast, chkDistractorsOtherTense, 
        		chkDistractorsIncorrectForms, chkDistractorsWrongConditional, chkDistractorsWrongClause, chkDistractorsWrongSuffixUse,
        		chkDistractorsWrongSuffix, grpPos, grpCompForm, grpForms, grpVerbPerson, grpVerbForms, grpSentenceTypes, grpTenses,
        		lblTensesSentences, chkPresentSimple, chkFutureSimple, chkPresentProgressive, chkPastProgressive, chkFutureProgressive, 
        		chkFuturePerfect, chkPresentPerfectProg, chkPastPerfectProg, chkFuturePerfectProg, lblTensesWords, grpCondTypes, 
        		grpClauses, grpScope, grpPronouns, grpVerbSplitting, grpTargetWords, grpSentTypes, chkPastSimple, chkPresentPerfect, 
        		chkPastPerfect, chkScopeActive, chkWho, chkWhich, chkThat, chkOtherRelPron, chk3Pers, chkNot3Pers, chkAffirmativeSent, 
        		chkNegatedSent, chkQuestions, chkStatements, chkRegularVerbs, chkIrregularVerbs, chkscopeType1, chkscopeType2, 
        		chkPosAdj, chkPosAdv, chkFormComparatives, chkFormSuperlatives, chkFormSynthetic, chkFormAnalytic, grpNumberTargets
        };
        
        controlsToReset = new Widget[] {chkPosAdj, chkPosAdv, chkFormComparatives, chkFormSuperlatives, chkFormSynthetic, chkFormAnalytic, chkscopeType1,
    			chkscopeType2, rbtMainClause, chkScopeActive, rbt1Verb, chkPresentSimple, 
    			chkPastSimple, chkFutureSimple, chkPresentProgressive, chkPastProgressive, chkFutureProgressive, chkPresentPerfect, chkPastPerfect, 
    			chkFuturePerfect, chkPresentPerfectProg, chkPastPerfectProg, chkFuturePerfectProg, chkAffirmativeSent, chkNegatedSent, chkQuestions, 
    			chkStatements, chkRegularVerbs, chkIrregularVerbs, chk3Pers, chkNot3Pers, chkWho, chkWhich, chkThat, chkOtherRelPron, rbtPerSentence,
    			chkBracketsLemma, chkBracketsPos, chkBracketsForm, chkBracketsConditional, chkBracketsWill, chkBracketsSentenceType, chkBracketsTense,
    			chkBracketsActiveSentence,
    			chkDistractorsOtherForm, chkDistractorsOtherVariant, chkDistractorsOtherPast, chkDistractorsOtherTense, 
        		chkDistractorsIncorrectForms, chkDistractorsWrongConditional, chkDistractorsWrongClause, chkDistractorsWrongSuffixUse, chkDistractorsWrongSuffix};
        
        lblName.setText(name);

        initRadiobuttons(name);
        initHandlers();
        visibilityManagers = new VisibilityManagerCollection(this);
        initUI();
    }
    
    private ArrayList<Pair<Integer, Integer>> removedParts = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> newlyRemovedParts = new ArrayList<>();
    private RankableDocument doc;
    

    /**
     * The widgets whose visibility depends on the settings.
     */
    final Widget[] settingsWidgets;
    
    /**
     * Checkboxes representing possible distractor options
     */
    private final ArrayList<Pair<MaterialCheckBox, DistractorProperties>> distractorOptions = new ArrayList<>();
    
    /**
     * Checkboxes representing possible brackets options
     */
    private final ArrayList<Pair<MaterialCheckBox, BracketsProperties>> bracketsOptions = new ArrayList<>();
    
    /**
     * Checkboxes and radiobuttons which need to be set to <code>true</code> when resetting the panel
     */
    final Widget[] controlsToReset;

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
     * Checks whether at least 2 of the given check boxes are visible and checked.
     * @param widgets	The checkboxes to take into account
     * @return 			<code>true</code> if at least 2 of the checkboxes are visible and checked; otherwise <code>false</code>
     */
    private boolean checkAtLeast2Checked(MaterialCheckBox[] widgets) {
    	int checked = 0;
    	for(MaterialCheckBox widget : widgets) {
    		if(widget.isVisible() && widget.getValue()) {
    			checked++;
    		}
    	}
    	
    	return checked >= 2;
    }
    
    /**
     * Generates the radiobuttons.
     * We cannot specify them in xml since the radiogroup names must be unique over all tasks.
     */
    private void initRadiobuttons(String taskName) {
        String groupName = "scope" + taskName.replace(" ", "");
        rbtPerSentence = new MaterialRadioButton(groupName, "1 exercise per sentence");
        rbtPerSentence.setType(RadioButtonType.GAP);       
        rowRbtPerSentence.add(rbtPerSentence);
        rbtSingleTask = new MaterialRadioButton(groupName, "Single exercise");
        rbtSingleTask.setType(RadioButtonType.GAP);       
        rowRbtSingleTask.add(rbtSingleTask);
        rbtPerSentence.setValue(true);

        groupName = "targetClauses" + taskName.replace(" ", "");
        rbtMainClause = new MaterialRadioButton(groupName, "Only main clause");
        rbtMainClause.setType(RadioButtonType.GAP);       
        rowRbtTargetClauses.add(rbtMainClause);
        rbtIfClause = new MaterialRadioButton(groupName, "Only if-clause");
        rbtIfClause.setType(RadioButtonType.GAP);       
        rowRbtIfClause.add(rbtIfClause);
        rbtEitherClause = new MaterialRadioButton(groupName, "Either clause");
        rbtEitherClause.setType(RadioButtonType.GAP);       
        rowRbtEitherClause.add(rbtEitherClause);
        rbtBothClauses = new MaterialRadioButton(groupName, "Both clauses");
        rbtBothClauses.setType(RadioButtonType.GAP);       
        rowRbtBothClauses.add(rbtBothClauses);
        rbtBothClauses.setValue(true);
        
        groupName = "verbSplitting" + taskName.replace(" ", "");
        rbt1Verb = new MaterialRadioButton(groupName, "Entire verb cluster as single element");
        rbt1Verb.setType(RadioButtonType.GAP);       
        rowRbt1Verb.add(rbt1Verb);
        rbt2Verbs = new MaterialRadioButton(groupName, "Form of 'be' and past participle as separate elements");
        rbt2Verbs.setType(RadioButtonType.GAP);       
        rowRbt2Verbs.add(rbt2Verbs);
        rbt1Verb.setValue(true);
    }
    
    /**
     * Determines all removed parts including the currently removed ones which are not contained in another removed part.
     * @return	The removed parts not contained in another removed part
     */
    private ArrayList<Pair<Integer, Integer>> getNotOverlappingRemovedParts() {
    	ArrayList<Pair<Integer, Integer>> allRemovedParts = new ArrayList<>(newlyRemovedParts);       	        	        	
		for(Pair<Integer, Integer> removedPart : removedParts) {
			boolean canUse = true;
			for(Pair<Integer, Integer> newlyRemovedPart : newlyRemovedParts) {
    			// A previously removed part may be entirely contained in a newly removed part.
    			// We then do not add it to the removed parts list
    			if(!(removedPart.first >= newlyRemovedPart.first && removedPart.second <= newlyRemovedPart.second)) {
    				canUse = false;
    				break;
    			}
    		}
			if(canUse) {
				allRemovedParts.add(removedPart);
			}
    	}       	        	        	
    	allRemovedParts.sort((c1, c2) -> c1.first < c2.first ? -1 : 1);
    	
    	return allRemovedParts;
    }
    
    /**
     * Initializes all handlers.
     */
    private void initHandlers() {
    	dlgDocumentSelection.removeFromParent();
        RootPanel.get().add(dlgDocumentSelection);

        btnApplyDocumentSelection.addClickHandler(event -> {
        	dlgDocumentSelection.close();
        	        	
        	removedParts = getNotOverlappingRemovedParts();
        	newlyRemovedParts.clear();
        	
        	relevantConstructionsInSelectedDocumentPart = new HashMap<String, Integer>();
        	calculateConstructionsOccurrences(relevantConstructionsInSelectedDocumentPart);
        	
        	btnApplyDocumentSelection.setEnabled(false);
    		btnDiscardDocumentSelection.setEnabled(false);
    		btnReset.setEnabled(removedParts.size() > 0);    		
        });
        btnRemoveSelection.addClickHandler(event -> {
        	int selectedPartStartIndex = lblDocumentForSelection.getCursorPos();
        	int selectedPartEndIndex = selectedPartStartIndex + lblDocumentForSelection.getSelectionLength();
        	
        	ArrayList<Pair<Integer, Integer>> allRemovedParts = getNotOverlappingRemovedParts();    	        	        	  		
        	for(Pair<Integer, Integer> removedPart : allRemovedParts) {
        		if(removedPart.first <= selectedPartStartIndex) {
        			selectedPartStartIndex += removedPart.second - removedPart.first;
        			selectedPartEndIndex += removedPart.second - removedPart.first;
        		} else if(removedPart.first < selectedPartEndIndex) {
        			selectedPartEndIndex += removedPart.second - removedPart.first;
        		} else {
        			break;
        		}
        	}
        	
        	newlyRemovedParts.add(new Pair<>(selectedPartStartIndex, selectedPartStartIndex + lblDocumentForSelection.getSelectionLength()));
        	lblDocumentForSelection.setText(lblDocumentForSelection.getText().substring(0, lblDocumentForSelection.getCursorPos()) + lblDocumentForSelection.getText().substring(lblDocumentForSelection.getCursorPos() + lblDocumentForSelection.getSelectionLength()));
        
    		btnRemoveSelection.setEnabled(false);
    		btnApplyDocumentSelection.setEnabled(true);
    		btnDiscardDocumentSelection.setEnabled(true);
        });
        btnReset.addClickHandler(event -> {
        	lblDocumentForSelection.setText(doc.getText());
        	removedParts.clear();
        	newlyRemovedParts.clear();
        	
        	btnApplyDocumentSelection.setEnabled(true);
    		btnDiscardDocumentSelection.setEnabled(true);
        });
        btnDiscardDocumentSelection.addClickHandler(event -> {
        	dlgDocumentSelection.close();
    		newlyRemovedParts.clear();
    		
    		// Sort them from highest to lowest number so later removals don't interfere with previous indices
        	removedParts.sort((c1, c2) -> c1.first < c2.first ? -1 : 1);
        	String text = doc.getText();
        	for(Pair<Integer, Integer> removedPart : removedParts) {
        		text = text.substring(0, removedPart.first) + text.substring(removedPart.second);
        	}
        	lblDocumentForSelection.setText(text);
        	
        	btnApplyDocumentSelection.setEnabled(false);
    		btnDiscardDocumentSelection.setEnabled(false);
        });
        
        lblDocumentForSelection.addClickHandler(event -> {
        	if(lblDocumentForSelection.getSelectedText().length() > 0) {
        		btnRemoveSelection.setEnabled(true);
        	} else {
        		btnRemoveSelection.setEnabled(false);
        	}
        });
        
    	btnDelete.addClickHandler(event -> {
    		parent.deleteTask(this);
    	});
    	
    	btnSelectDocumentPart.addClickHandler(event -> {
    		dlgDocumentSelection.open();
    		lblDocumentForSelection.setFocus(true);
    	});
    	
    	btnUpdateDocument.addClickHandler(event -> {
    		initializeRelevantConstructions();
    	});
    	    
    	drpTopic.addSelectionHandler(new SelectComboHandler<Option>()
    	{
			@Override
			public void onSelectItem(SelectItemEvent<Option> event) {
				setExerciseSettingsVisibilities();
				resetControlValues();
			}
    	});
    	
    	drpType.addSelectionHandler(new SelectComboHandler<Option>()
    	{
			@Override
			public void onSelectItem(SelectItemEvent<Option> event) {
				setExerciseSettingsVisibilities();
			}
    	});
    	    	    	
    	chkPosAdj.addClickHandler(e -> onPosClickedEventHandler(true));
    	chkPosAdv.addClickHandler(e -> onPosClickedEventHandler(true));
    	chkFormComparatives.addClickHandler(e -> onComparisonFormClickedEventHandler(true));
    	chkFormSuperlatives.addClickHandler(e -> onComparisonFormClickedEventHandler(true));
    	chkFormSynthetic.addClickHandler(e -> onFormClickedEventHandler(true));
    	chkFormAnalytic.addClickHandler(e -> onFormClickedEventHandler(true));
    	chkscopeType1.addClickHandler(e -> onConditionalTypeClickedEventHandler(true));
    	chkscopeType2.addClickHandler(e -> onConditionalTypeClickedEventHandler(true));
    	rbtBothClauses.addValueChangeHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	rbtMainClause.addValueChangeHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	rbtEitherClause.addValueChangeHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	rbtIfClause.addValueChangeHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkScopeActive.addClickHandler(e ->  onActiveSentenceClickedEventHandler(true));
    	chkPresentSimple.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkPastSimple.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkFutureSimple.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkPresentProgressive.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkPastProgressive.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkFutureProgressive.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkPresentPerfect.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkPastPerfect.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkFuturePerfect.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkPresentPerfectProg.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkPastPerfectProg.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkFuturePerfectProg.addClickHandler(e -> onTenseClickedEventHandler(true));
    	chkAffirmativeSent.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkNegatedSent.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkQuestions.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkStatements.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkRegularVerbs.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkIrregularVerbs.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chk3Pers.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkNot3Pers.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkWho.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkWhich.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkThat.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	chkOtherRelPron.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));  	
    	rbtSingleTask.addValueChangeHandler(e -> setExerciseSettingsVisibilities());
    	rbtPerSentence.addValueChangeHandler(e -> setExerciseSettingsVisibilities());
    	
    	for(Pair<MaterialCheckBox, DistractorProperties> option : distractorOptions) {
    		option.first.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	}
    }
    
    /**
     * Event handler method for when the Active sentence checkbox has been clicked.
     * Hides the brackets option for sentence type if only passive sentence are used and displays the Active sentence brackets option.
     */
    private void onActiveSentenceClickedEventHandler(boolean recalculateNumberExercises) {
    	if(recalculateNumberExercises) {
    		setNumberExercisesText(calculateNumberOfExercises());
    	}
    	chkBracketsSentenceType.setVisible(getExerciseType().equals("FiB") && checkAtLeast2Checked(new MaterialCheckBox[] {chkScopeActive, chkScopePassive}));
		chkBracketsActiveSentence.setVisible(grpSentTypes.isVisible() && (!chkScopeActive.isVisible() || !chkScopeActive.getValue()));
    }
    
    /**
     * Event handler method for when a tense checkbox has been clicked.
     * Hides the brackets option for tense if less than 2 relevant tenses are checked.
     */
    private void onTenseClickedEventHandler(boolean recalculateNumberExercises) {
    	if(recalculateNumberExercises) {
    		setNumberExercisesText(calculateNumberOfExercises());
    	}
    	chkBracketsTense.setVisible(getExerciseType().equals("FiB") && checkAtLeast2Checked(new MaterialCheckBox[] {chkPresentSimple, chkPastSimple, chkFutureSimple, chkPresentProgressive, chkPastProgressive,
				chkFutureProgressive, chkPresentPerfect, chkPastPerfect, chkFuturePerfect, chkPresentPerfectProg, chkPastPerfectProg, 
				chkFuturePerfectProg}));
    }
    
    /**
     * Event handler method for when a POS checkbox has been clicked.
     * Hides the brackets option for POS if less than 2 relevant tenses are checked.
     */
    private void onPosClickedEventHandler(boolean recalculateNumberExercises) {
    	if(recalculateNumberExercises) {
    		setNumberExercisesText(calculateNumberOfExercises());
    	}
		chkBracketsPos.setVisible(getExerciseType().equals("FiB") && checkAtLeast2Checked(new MaterialCheckBox[] {chkPosAdj, chkPosAdv}));
    }
    
    /**
     * Event handler method for when a comparison form checkbox has been clicked.
     * Hides the brackets and distractor options for comparison form if less than 2 relevant tenses are checked.
     */
    private void onComparisonFormClickedEventHandler(boolean recalculateNumberExercises) {
    	if(recalculateNumberExercises) {
    		setNumberExercisesText(calculateNumberOfExercises());
    	}
    	chkBracketsForm.setVisible(getExerciseType().equals("FiB") && checkAtLeast2Checked(new MaterialCheckBox[] {chkFormComparatives, chkFormSuperlatives}));
		chkDistractorsOtherForm.setVisible(getExerciseType().equals("Select") && checkAtLeast2Checked(new MaterialCheckBox[] {chkFormComparatives, chkFormSuperlatives}));
    }
    
    /**
     * Event handler method for when a form checkbox has been clicked.
     * Hides the brackets and distractor options for variant form if less than 2 relevant tenses are checked.
     */
    private void onFormClickedEventHandler(boolean recalculateNumberExercises) {
    	if(recalculateNumberExercises) {
    		setNumberExercisesText(calculateNumberOfExercises());
    	}
    	chkDistractorsOtherVariant.setVisible(getExerciseType().equals("Select") && checkAtLeast2Checked(new MaterialCheckBox[] {chkFormAnalytic, chkFormSynthetic}));
    }
    
    /**
     * Event handler method for when a conditional type checkbox has been clicked.
     * Hides the brackets and distractor options for conditional type if less than 2 relevant tenses are checked.
     */
    private void onConditionalTypeClickedEventHandler(boolean recalculateNumberExercises) {
    	if(recalculateNumberExercises) {
    		setNumberExercisesText(calculateNumberOfExercises());
    	}
    	chkBracketsConditional.setVisible(getExerciseType().equals("FiB") && checkAtLeast2Checked(new MaterialCheckBox[] {chkscopeType1, chkscopeType2}));
		chkDistractorsWrongConditional.setVisible(getExerciseType().equals("Select") && checkAtLeast2Checked(new MaterialCheckBox[] {chkscopeType1, chkscopeType2}));
    }
        
    /**
     * Initializes the UI components and sets their visibility.
     */
    private void initUI() {
    	possibleTopics = new ArrayList<Pair<String, String>>();
    	possibleTopics.add(new Pair<String, String>("Comparison", "Compare"));
    	possibleTopics.add(new Pair<String, String>("Present simple", "Present"));
    	possibleTopics.add(new Pair<String, String>("Past tenses", "Past"));
    	possibleTopics.add(new Pair<String, String>("Passive", "Passive"));
    	possibleTopics.add(new Pair<String, String>("Conditionals", "'if'"));
    	possibleTopics.add(new Pair<String, String>("Relative Pronouns", "Relatives"));
    	
        constructionComponents = new ConstructionComponentsCollection(this);
        
    	setExerciseSettingsVisibilities();    	
    }
    
    /**
     * Resets all controls that are initially <code>true</code> to that value.
     */
    private void resetControlValues() {
    	for(Widget widget : controlsToReset) {
    		if(widget instanceof MaterialRadioButton) {
    			((MaterialRadioButton) widget).setValue(true);
    		} else if(widget instanceof MaterialCheckBox) {
    			((MaterialCheckBox) widget).setValue(true);
    		}
    	}
    }
    
    /**
     * Sets the visibility of the settings parameters.
     * Only those widgets given in the visible settings list are displayed.
     * @param visibleSettings	The widgets to be displayed
     */
    private void setSettingsVisibility(ArrayList<Widget> visibleSettings) {
    	for(Widget settingsWidget : settingsWidgets) {
    		if(visibleSettings.contains(settingsWidget)) {
    			settingsWidget.setVisible(true);
    		} else {
    			settingsWidget.setVisible(false);
    		}
    	}
    	
    	onPosClickedEventHandler(false);
    	onComparisonFormClickedEventHandler(false);
    	onFormClickedEventHandler(false);
    	onConditionalTypeClickedEventHandler(false);
    	onActiveSentenceClickedEventHandler(false);
    	onTenseClickedEventHandler(false);
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
    	doc = DocumentPreviewPane.getInstance().getCurrentlyPreviewedDocument().getDocument();
    	lblDocTitle.setText(doc.getTitle());
        lblDocumentForSelection.setText(doc.getText());
        
        removedParts.clear();
        newlyRemovedParts.clear();

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
    		if(possibleTopic.second.equals("Passive")) {
    			if(getNumberOfConstructionOccurrences("passive", "Passive", 1) > 0) {
    				addOptionToTopic(possibleTopic.first, possibleTopic.second, selecteTopic, i);  
        			i++;
    			}
    		} else {
	    		if(checkConstructionOccurs(null, possibleTopic.second, 0)) {
	    			addOptionToTopic(possibleTopic.first, possibleTopic.second, selecteTopic, i);  
	    			i++;
	        	}
    		}
    	}
    	
    	setExerciseSettingsVisibilities();
    }
    
    /**
     * Gets the value of the currently selected topic.
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
     * Determines the constructions which are targeted by the exercise according to the current parameter settings.
     * @return	The list of targeted constructions
     */
    private ArrayList<String> determineConfiguredConstructions() {
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
    		if(exerciseType.equals("FiB") || exerciseType.equals("Mark") || exerciseType.equals("Drag") && rbtPerSentence.getValue()) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getRelativesFiBMarkComponents());  
    		} else if (exerciseType.equals("Select")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getRelativesSelectComponents());  
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
    	
    	return constructionsToConsider;
    }

    /**
     * Calculates the number of exercises that can be generated for the current document with the current parameter settings.
     * @return The number of exercises that can be generated.
     */
    private int calculateNumberOfExercises() {
    	String topic = getTopic();
    	ArrayList<String> constructionsToConsider = determineConfiguredConstructions();

    	int nExercises = 0;
    	
    	for(String constructionToConsider : constructionsToConsider) {
    		nExercises += relevantConstructionsInSelectedDocumentPart.get(constructionToConsider);
    	}
    	
    	// If we use both clauses of the conditional sentence as targets, we have double the amount of blanks
    	if(topic.equals("'if'") && rbtBothClauses.getValue()) {
    		nExercises = nExercises * 2;	
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
    			if(firstLevelConstruction.first == null ||  firstLevelConstruction.first.getValue()) {
    				initialConstructionNames.add(firstLevelConstruction.second);
    			}   		
    		}
		
    		if(constructionLevels.size() > 1) {
    			for(int i = 1; i < constructionLevels.size(); i++) {
    				if(initialConstructionNames.size() > 0) {
    					ArrayList<String> currentConstructionNames = new ArrayList<String>();
    					for(Pair<MaterialCheckBox, String> currentLevelConstruction : constructionLevels.get(i)) {
    						if(currentLevelConstruction.first == null || currentLevelConstruction.first.getValue()) {
    							for(String initialConstructionName : initialConstructionNames) {
    			    				currentConstructionNames.add(initialConstructionName + "-" + currentLevelConstruction.second);
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
     * Checks whether at least 1 visible distractor option is checked.
     * @return	<code>true</code> if at least 1 visible distractor option is checked; otherwise <code>false</code>
     */
    private boolean hasCheckedDistractors() {
    	for(Pair<MaterialCheckBox, DistractorProperties> option : distractorOptions) {
    		if(option.first.isVisible() && option.first.getValue()) {
    			return true;
    		}
    	}
    	
    	return false;
    }

	/**
     * Sets the text on whether and  how many exercises can be generated according to the selected topic and exercise type,
     * the individual parameter settings and the selected document.
     */
    private void setNumberExercisesText(int numberOfExercises) {
    	String topic = getTopic();
    	String exerciseType = getExerciseType();

    	icoOk.setVisible(false);
		icoInvalid.setVisible(true);
		icoWarning.setVisible(false);
		
		if(exerciseType.equals("Exercise Type") || topic.equals("Topic")) {
			lblNumberExercises.setText("Select a grammar topic and an exercise type to enable exercise generation.");
    		lblNumberExercises.setTextColor(Color.GREY);
		} else if(topic.equals("Passive") && exerciseType.equals("Select") || 
				topic.equals("'if'") && exerciseType.equals("Mark") || topic.equals("Present") && exerciseType.equals("Drag") || 
				exerciseType.equals("Select") && !topic.equals("Relatives") && !hasCheckedDistractors()) {
    		lblNumberExercises.setText("No exercises can be generated for the current settings.");
    		lblNumberExercises.setTextColor(Color.RED);
		} else {
    		if(numberOfExercises == 0) {
    			lblNumberExercises.setText("No exercises can be generated for the current settings.");
        		lblNumberExercises.setTextColor(Color.RED);
    		} else {
        		lblNumberExercises.setTextColor(numberOfExercises >= 5 ? Color.BLACK : Color.ORANGE);
        		if(numberOfExercises >= 5) {
        			icoOk.setVisible(true);
        		} else {
        			icoWarning.setVisible(true);
        		}
        		icoInvalid.setVisible(false);
    			if(exerciseType.equals("FiB") || exerciseType.equals("Select")) {
            		lblNumberExercises.setText("A maximum of " + numberOfExercises + " blanks can be generated for the current settings.");
    			} else if(exerciseType.equals("Mark")) {
            		lblNumberExercises.setText("A maximum of " + numberOfExercises + " target words can be generated for the current settings.");
    			} else if(exerciseType.equals("Drag")) {
    				if((topic.equals("Relatives") || topic.equals("'if'")) && rbtPerSentence.getValue() || topic.equals("Passive")) {
    					// 1 exercise per sentence
                		lblNumberExercises.setText("A maximum of " + numberOfExercises + " exercises can be generated for the current settings.");
    				} else {
    					if(numberOfExercises < 2) {
    						// We need at least 2 target words for drag & drop to make sense
    						lblNumberExercises.setText("No exercises can be generated for the current settings.");
    		        		lblNumberExercises.setTextColor(Color.RED);
    		        		icoOk.setVisible(false);
    		        		icoWarning.setVisible(false);
    		        		icoInvalid.setVisible(true);
    					} else {
    						lblNumberExercises.setText("A maximum of " + numberOfExercises + " target words can be generated for the current settings.");
    					}
    				}
    			}
    		}
    	}
		
		parent.setGenerateExercisesEnabled();
    }
    
    /**
     * Filters those constructions that are within the selected part of the document.
     * @param construction	The construction under consideration
     * @param doc			The document containing the text and constructions
     * @return				The occurrences of the construction within the selected range
     */
    private ArrayList<ConstructionRange> getConstructionsWithinSelectedPart(GrammaticalConstruction construction, RankableDocument doc) {
    	ArrayList<ConstructionRange> containedConstructions = new ArrayList<ConstructionRange>();
		
    	for(ConstructionRange range : doc.getConstructionOccurrences(construction)) {
    		boolean canBeUsed = true;
    		for(Pair<Integer, Integer> removedPart : removedParts) {
    			if(Math.max(range.getStart(), removedPart.first) < Math.min(range.getEnd(), removedPart.second)) {
    				// The construction overlaps with a removed part, so we cannot use it
    				canBeUsed = false;
    			}
    		}
    		if(canBeUsed) {
    			containedConstructions.add(range);
    		}
    	}
    	
    	return containedConstructions;
    }
    
    
    /**
     * Calculates the occurrences of constructions in the combinations relevant to exercise generation.
     */
    public void calculateConstructionsOccurrences(HashMap<String, Integer> relevantConstructions) {    
    	relevantConstructions.clear();
    	HashMap<String, ArrayList<Pair<Integer, Integer>>> constructionOccurrences = getConstructionsOccurrences();

    	for (HashMap.Entry<String, ArrayList<Pair<Integer, Integer>>> entry : constructionOccurrences.entrySet()) {
        	relevantConstructions.put(entry.getKey(), entry.getValue().size());
        }

		int numberOfExercises = calculateNumberOfExercises();
    	setNumberExercisesText(numberOfExercises);
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
    
    /**
     * Generates settings for the server from the selected options
     */
    public ExerciseSettings generateExerciseSettings() {
    	ArrayList<Construction> constructions = new ArrayList<>();

    	HashMap<String, ArrayList<Pair<Integer, Integer>>> constructionOccurrences = 
    			getConstructionsOccurrences();
    	ArrayList<String> configuredConstructions = determineConfiguredConstructions();
    	ArrayList<DistractorProperties> distractorProperties = getSelectedDistractors();
		ArrayList<BracketsProperties> brackets = getSelectedBracketContents();
		
    	for(String constructionToConsider : configuredConstructions) {
    		for(Pair<Integer, Integer> constructionIndices : constructionOccurrences.get(constructionToConsider)) {
    			Construction construction = new Construction(ConstructionNameEnumMapper.getEnum(constructionToConsider), 
    					constructionIndices);
        		constructions.add(construction);
    		}
    	}

    	String topic = getTopic();
    	String type = getExerciseType();
    	if(type.equals("Drag")) {
    		if(topic.equals("Passive")) {
    			type = "MultiDrag";
    			if(rbt2Verbs.getValue()) {
    				brackets.add(BracketsProperties.VERB_SPLITTING);
    			}
    		} else if(topic.equals("Past") || topic.equals("Compare")) {
				type = "SingleDrag";
    		} else {
    			if(rbtPerSentence.getValue()) {
    				type = "MultiDrag";
    			} else {
    				type = "SingleDrag";
    			}
    		}
    	} else if(type.equals("Mark")) {
    		if(grpNumberTargets.isVisible() && chkNTargets.getValue()) {
    			brackets.add(BracketsProperties.N_TARGETS);
    		}
    	}
    	
    	if(topic.equals("'if'")) {
    		if(rbtMainClause.getValue() ) {
    			brackets.add(BracketsProperties.MAIN_CLAUSE);
    		} else if(rbtBothClauses.getValue()) {
    			brackets.add(BracketsProperties.IF_CLAUSE);
    			brackets.add(BracketsProperties.MAIN_CLAUSE);
    		} else if(rbtIfClause.getValue()) {
    			brackets.add(BracketsProperties.IF_CLAUSE);
    		} else {
    			brackets.add(BracketsProperties.EITHER_CLAUSE);
    		}
    	} else if(topic.equals("Passive") && type.equals("FiB")) {
    		brackets.add(BracketsProperties.LEMMA);
    	}
    	
    	return new ExerciseSettings(constructions, doc.getUrl(), doc.getText(), removedParts, 
    			type, getQuiz(), distractorProperties, brackets, spnNDistractors.getValue() - 1, lblName.getValue(), parent.chkDownloadResources.getValue());
    }
    
    /**
     * Determines the selected distractor properties.
     * @return	The selected distractor properties
     */
    private ArrayList<DistractorProperties> getSelectedDistractors() {
    	ArrayList<DistractorProperties> distractors = new ArrayList<>();
    	for(Pair<MaterialCheckBox, DistractorProperties> option : distractorOptions) {
    		if(option.first.isVisible() && option.first.getValue()) {
    			distractors.add(option.second);
    		}
    	}
    	
    	return distractors;
    }
    
    /**
     * Determines the selected brackets content properties.
     * @return	The selected brackets properties
     */
    private ArrayList<BracketsProperties> getSelectedBracketContents() {
    	ArrayList<BracketsProperties> brackets = new ArrayList<>();
    	for(Pair<MaterialCheckBox, BracketsProperties> option : bracketsOptions) {
    		if(option.first.isVisible() && option.first.getValue()) {
    			brackets.add(option.second);
    		}
    	}
    	
    	return brackets;
    }
    
    /**
     * Gets the value of the currently selected quiz.
     * @return	The value of the currently selected quiz, if any; otherwise the empty string
     */
    public String getQuiz() {    	
    	//We get a ClassCastException when we try to access the first list element, so we just iterate over the (only) element
    	for(Object o : drpQuiz.getSelectedValue()) {
    		return o.toString();
    	}   		
    	
    	return "";
    }
    
    
    /**
     * Splits constructions consisting of multiple tokens at whitespaces and adds the parts as individual occurrences to the list
     * @param relevantConstructions	The identified construction occurrences
     * @param gram					The grammatical construction
     * @param key					The name of the construction used as key in the has map
     */
    private void addMultiWordConstructions(HashMap<String, ArrayList<ConstructionRange>> relevantConstructions, 
    		GrammaticalConstruction gram, String key) {
    	ArrayList<ConstructionRange> findings = getConstructionsWithinSelectedPart(gram, doc);
		ArrayList<Pair<Integer, Integer>> indices = new ArrayList<>();
		for(ConstructionRange finding : findings) {
			String entireConstruction = doc.getText().substring(finding.getStart(), finding.getEnd());
			String[] parts = entireConstruction.split("\\s\\h");
			int constructionStart = finding.getStart();
			for(String part : parts) {
				part = part.trim();
				if(!part.equals("")){
					int start = entireConstruction.indexOf(part);
					int end = part.length() + start;
					entireConstruction = entireConstruction.substring(end);
					indices.add(new Pair<Integer, Integer>(start + constructionStart, end + constructionStart));
					constructionStart += end;
				}
			}
		}
		relevantConstructions.put(key, findings);
    }
    
    
    /**
     * Adds constructions consisting of a single token to the list
     * @param relevantConstructions	The identified construction occurrences
     * @param gram					The grammatical construction
     * @param key					The name of the construction used as key in the has map
     */
    private void addSingleWordConstructions(HashMap<String, ArrayList<Pair<Integer, Integer>>> relevantConstructions, 
    		GrammaticalConstruction gram, String key) {
    	ArrayList<ConstructionRange> findings = getConstructionsWithinSelectedPart(gram, doc);
		ArrayList<Pair<Integer, Integer>> indices = new ArrayList<>();
		for(ConstructionRange finding : findings) {			
			indices.add(new Pair<>(finding.getStart(), finding.getEnd()));
		}
		relevantConstructions.put(key, indices);
    }
    
/**
 * Determines the indices of occurrences of constructions in the combinations relevant to exercise generation.
 * @return				The indices of occurrences of constructions relevant to exercise generation
 */
    public HashMap<String, ArrayList<Pair<Integer, Integer>>> getConstructionsOccurrences() {    
    	HashMap<String, ArrayList<Pair<Integer, Integer>>> relevantConstructions = 
    			new HashMap<String, ArrayList<Pair<Integer, Integer>>>();

    	// comparison
    	// comparatives and superlatives exclude elements like 'the', 'than'
    	//TODO: Maybe allow to specify in view whether to keep analytic forms as single markable or as separate
    	//we would have to multiply the occurrences of mark instances if this was selected
    	//for now, we keep them as one
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, "adj-comp-syn");
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_SHORT, "adj-sup-syn");		
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADJECTIVE_COMPARATIVE_LONG, "adj-comp-ana");
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_LONG, "adj-sup-ana");
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADVERB_COMPARATIVE_SHORT, "adv-comp-syn");		
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADVERB_SUPERLATIVE_SHORT, "adv-sup-syn");		
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADVERB_COMPARATIVE_LONG, "adv-comp-ana");
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.ADVERB_SUPERLATIVE_LONG, "adv-sup-ana");

		// for conditional sentences, we put the entire sentences
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.CONDITIONALS_REAL, "condReal");
    	addSingleWordConstructions(relevantConstructions, GrammaticalConstruction.CONDITIONALS_UNREAL, "condUnreal");

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
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.PASSIVE_VOICE, doc);
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<ConstructionRange> tenseOccurrences = 
            		getConstructionsWithinSelectedPart(tenseConstruction, doc);
    		ArrayList<Pair<Integer, Integer>> passiveIndices = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> activeIndices = new ArrayList<>();
            for(ConstructionRange tenseOccurrence : tenseOccurrences) {
            	ConstructionRange correspondingPassiveOccurrence = null;
            	for(ConstructionRange passiveOccurrence : passiveOccurrences) {
            		if(passiveOccurrence.getStart() >= tenseOccurrence.getStart() && passiveOccurrence.getStart() < tenseOccurrence.getEnd() || 
            				tenseOccurrence.getStart() >= passiveOccurrence.getStart() && tenseOccurrence.getStart() < passiveOccurrence.getEnd()) {
            			// There is some overlap between the passive construction and the tense construction
            			correspondingPassiveOccurrence = passiveOccurrence;
            			break;
            		}
            	}
            	if(correspondingPassiveOccurrence != null) {
            		// The passive construction doesn't always contain all verbs of a verb cluster and the tense construction doesn't usually include the participle
            		// We therefore take the combined construction
            		passiveIndices.add(new Pair<>(Integer.min(correspondingPassiveOccurrence.getStart(), tenseOccurrence.getStart()), 
            				Integer.max(correspondingPassiveOccurrence.getEnd(), tenseOccurrence.getEnd())));
            	} else {
            		// We just take the verb of the tense construction for active constructions
            		activeIndices.add(new Pair<>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
            	}
            }

			relevantConstructions.put("passive-" + tenseConstruction.name(), passiveIndices);
			relevantConstructions.put("active-" + tenseConstruction.name(), activeIndices);                               	
        }

        tenseConstructions = new GrammaticalConstruction[]{
        		GrammaticalConstruction.TENSE_PAST_SIMPLE,
        		GrammaticalConstruction.TENSE_PRESENT_PERFECT,
        		GrammaticalConstruction.TENSE_PAST_PERFECT
        };
        
        ArrayList<ConstructionRange> negationOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.NEGATION_NOT, doc);
        negationOccurrences.addAll(getConstructionsWithinSelectedPart(GrammaticalConstruction.NEGATION_NT, doc));
        ArrayList<ConstructionRange> questionOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.QUESTIONS_DIRECT, doc);
        ArrayList<ConstructionRange> irregularOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.VERBS_IRREGULAR, doc);

        // past tense combinations
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<ConstructionRange> tenseOccurrences = 
            		getConstructionsWithinSelectedPart(tenseConstruction, doc);
    		ArrayList<Pair<Integer, Integer>> indicesQuestionAffirmativeRegular = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> indicesQuestionAffirmativeIrregular = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> indicesQuestionNegativeRegular = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> indicesQuestionNegativeIrregular = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> indicesStatementAffirmativeRegular = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> indicesStatementAffirmativeIrregular = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> indicesStatementNegativeRegular = new ArrayList<>();
    		ArrayList<Pair<Integer, Integer>> indicesStatementNegativeIrregular = new ArrayList<>();
        	
            for(ConstructionRange tenseOccurrence : tenseOccurrences) {
            	boolean foundNegationOverlap = false;
            	ConstructionRange negation = null;
            	for(ConstructionRange negationOccurrence : negationOccurrences) {
            		if(negationOccurrence.getEnd() >= tenseOccurrence.getStart() - 1 && negationOccurrence.getStart() <= tenseOccurrence.getEnd() + 1) {
            			// The negation is at most the previous or succeeding token (only a single character in-between for a whitespace)
            			foundNegationOverlap = true;
            			negation = negationOccurrence;
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
            				indicesQuestionNegativeIrregular.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
            			} else {
            				indicesQuestionNegativeRegular.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
            			}
            		} else {
            			if(foundIrregularOverlap) {
            				indicesStatementNegativeIrregular.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
            			} else {
            				indicesStatementNegativeRegular.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
            			}
            		}
            	} else {
            		if(foundQuestionOverlap) {
            			if(foundIrregularOverlap) {
            				indicesQuestionAffirmativeIrregular.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
            			} else {
            				indicesQuestionAffirmativeRegular.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
            			}
            		} else {
            			if(foundIrregularOverlap) {
            				indicesStatementAffirmativeIrregular.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
            			} else {
            				indicesStatementAffirmativeRegular.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
            			}
            		}
            	}
            	           	
            }
            
			relevantConstructions.put(tenseConstruction.name() + "-question-neg-irreg", indicesQuestionNegativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-question-neg-reg", indicesQuestionNegativeRegular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-neg-irreg", indicesStatementNegativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-neg-reg", indicesStatementNegativeRegular);
			relevantConstructions.put(tenseConstruction.name() + "-question-affirm-irreg", indicesQuestionAffirmativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-question-affirm-reg", indicesQuestionAffirmativeRegular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-affirm-irreg", indicesStatementAffirmativeIrregular);
			relevantConstructions.put(tenseConstruction.name() + "-stmt-affirm-reg", indicesStatementAffirmativeRegular);
        }

        // present tense combinations
        ArrayList<ConstructionRange> presentOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, doc);
        ArrayList<Pair<Integer, Integer>> indicesQuestionAffirmative3 = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> indicesQuestionAffirmativeNot3 = new ArrayList<Pair<Integer, Integer>>();
    	ArrayList<Pair<Integer, Integer>> indicesQuestionNegative3 = new ArrayList<Pair<Integer, Integer>>();
    	ArrayList<Pair<Integer, Integer>> indicesQuestionNegativeNot3 = new ArrayList<Pair<Integer, Integer>>();
    	ArrayList<Pair<Integer, Integer>> indicesStatementAffirmative3 = new ArrayList<Pair<Integer, Integer>>();
    	ArrayList<Pair<Integer, Integer>> indicesStatementAffirmativeNot3 = new ArrayList<Pair<Integer, Integer>>();
    	ArrayList<Pair<Integer, Integer>> indicesStatementNegative3 = new ArrayList<Pair<Integer, Integer>>();
    	ArrayList<Pair<Integer, Integer>> indicesStatementNegativeNot3 = new ArrayList<Pair<Integer, Integer>>();
        
    	for(ConstructionRange tenseOccurrence : presentOccurrences) {
        	// we take the first token since the inflected present verb is usually at the beginning of the sequence.
        	// It's just an approximation, but the best we can get without proper NLP processing.
        	String occurrenceText = doc.getText().substring(tenseOccurrence.getStart(), tenseOccurrence.getEnd()).split(" ")[0];
        	
        	boolean foundNegationOverlap = false;        	
        	ConstructionRange negation = null;
        	for(ConstructionRange negationOccurrence : negationOccurrences) {
        		if(negationOccurrence.getEnd() >= tenseOccurrence.getStart() - 1 && negationOccurrence.getStart() <= tenseOccurrence.getEnd() + 1) {
        			// The negation is at most the previous or succeeding token (only a single character in-between for a whitespace)
        			foundNegationOverlap = true;
        			negation = negationOccurrence;
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

        	boolean isThirdPersonSingular = occurrenceText != null && (occurrenceText.endsWith("s") || occurrenceText.equals("doesn't") || 
        			occurrenceText.equals("hasn't") || occurrenceText.equals("isn't"));

        	if(foundNegationOverlap) {
        		if(foundQuestionOverlap) {
        			if(isThirdPersonSingular) {
        				indicesQuestionNegative3.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
        			} else {
        				indicesQuestionNegativeNot3.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
        			}
        		} else {
        			if(isThirdPersonSingular) {
        				indicesStatementNegative3.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
        			} else {
        				indicesStatementNegativeNot3.add(new Pair<Integer, Integer>(Math.min(tenseOccurrence.getStart(), negation.getStart()), Math.max(tenseOccurrence.getEnd(), negation.getEnd())));
        			}
        		}
        	} else {
        		if(foundQuestionOverlap) {
        			if(isThirdPersonSingular) {
        				indicesQuestionAffirmative3.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
        			} else {
        				indicesQuestionAffirmativeNot3.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
        			}
        		} else {
        			if(isThirdPersonSingular) {
        				indicesStatementAffirmative3.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
        			} else {
        				indicesStatementAffirmativeNot3.add(new Pair<Integer, Integer>(tenseOccurrence.getStart(), tenseOccurrence.getEnd()));
        			}
        		}
        	}
        }

        relevantConstructions.put("present-question-neg-3", indicesQuestionNegative3);
		relevantConstructions.put("present-question-neg-not3", indicesQuestionNegativeNot3);
		relevantConstructions.put("present-stmt-neg-3", indicesStatementNegative3);
		relevantConstructions.put("present-stmt-neg-not3", indicesStatementNegativeNot3);
		relevantConstructions.put("present-question-affirm-3", indicesQuestionAffirmative3);
		relevantConstructions.put("present-question-affirm-not3", indicesQuestionAffirmativeNot3);
		relevantConstructions.put("present-stmt-affirm-3", indicesStatementAffirmative3);
		relevantConstructions.put("present-stmt-affirm-not3", indicesStatementAffirmativeNot3);                              	

		// relative pronouns
		ArrayList<ConstructionRange> relativeOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.PRONOUNS_RELATIVE, doc);
        ArrayList<Pair<Integer, Integer>> indicesWhoOccurrences = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> indicesWhichOccurrences = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> indicesThatOccurrences = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> indicesOtherOccurrences = new ArrayList<Pair<Integer, Integer>>();
        for(ConstructionRange relativeOccurrence : relativeOccurrences) {
        	String occurrenceText = doc.getText().substring(relativeOccurrence.getStart(), relativeOccurrence.getEnd());
        	if(occurrenceText.equalsIgnoreCase("who")) {
        		indicesWhoOccurrences.add(new Pair<Integer, Integer>(relativeOccurrence.getStart(), relativeOccurrence.getEnd()));
        	} else if(occurrenceText.equalsIgnoreCase("which")) {
        		indicesWhichOccurrences.add(new Pair<Integer, Integer>(relativeOccurrence.getStart(), relativeOccurrence.getEnd()));
        	} else if(occurrenceText.equalsIgnoreCase("that")) {
        		indicesThatOccurrences.add(new Pair<Integer, Integer>(relativeOccurrence.getStart(), relativeOccurrence.getEnd()));
        	} else {
        		indicesOtherOccurrences.add(new Pair<Integer, Integer>(relativeOccurrence.getStart(), relativeOccurrence.getEnd()));
        	}
        	
        }
		relevantConstructions.put("who", indicesWhoOccurrences);
		relevantConstructions.put("which", indicesWhichOccurrences);
		relevantConstructions.put("that", indicesThatOccurrences);
		relevantConstructions.put("otherRelPron", indicesOtherOccurrences);
		
		return relevantConstructions;
    }
    
}