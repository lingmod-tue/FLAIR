package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.poi.hslf.blip.JPEG.ColorSpace;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.widgets.DocumentPreviewPane;
import com.flair.client.presentation.widgets.NumberSpinner;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.IExerciseSettings;
import com.flair.shared.exerciseGeneration.InstructionsProperties;
import com.flair.shared.exerciseGeneration.OutputFormat;
import com.flair.shared.exerciseGeneration.Pair;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.dtos.RankableDocument.ConstructionRange;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent.SelectComboHandler;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.RadioButtonType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
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
    MaterialIcon btnDelete;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnSelectDocumentPart;
    @UiField
    MaterialButton btnApplyDocumentSelection;
    @UiField
    MaterialButton btnApplyTargetSelection;
    @UiField
    MaterialButton btnReset;
    @UiField
    MaterialButton btnResetTargetSelection;
    @UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
    MaterialButton btnUpdateDocument;
    @UiField
    MaterialButton btnPreviewExercise;
    @UiField
    MaterialButton btnCloseExercisePreview;
    @UiField
    MaterialLabel lblNumberExercises;
    @UiField
    MaterialLabel lblSelectTypeTopic;
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
    MaterialCheckBox chkBracketsDistractorLemma;
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
    MaterialCheckBox chkBracketsProgressive;
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
    MaterialCheckBox chkDistractorsProgressive;
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
    MaterialCheckBox chkProgressive;
    @UiField
    MaterialCheckBox chkTenses;
    @UiField
    MaterialCheckBox chkLemmas;
    @UiField
    MaterialCheckBox chkNTargets;
    @UiField
    MaterialCheckBox chkOnlyText;
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
    MaterialDialog dlgExercisePreview;
    @UiField
    MaterialIcon icoValidity;
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
    MaterialRow grpInstructions;
    @UiField
    HTMLPanel htmlContent;
    @UiField 
    RichTextArea txtDocumentForSelection;
    @UiField
    MaterialDialog dlgTargetExclusion;
    @UiField
    MaterialButton btnSelectTargets;
    @UiField
    MaterialCheckBox chkUseConfig;
    @UiField
    MaterialRow pnlConfig;
    /*@UiField
    MaterialRow pnlFileUplaod;
    @UiField
    FileUpload btnFileUpload;*/
    @UiField
    MaterialColumn colType;
    
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
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsProgressive, DistractorProperties.PROGRESSIVE));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsIncorrectForms, DistractorProperties.INCORRECT_FORMS));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongConditional, DistractorProperties.WRONG_CONDITIONAL));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongClause, DistractorProperties.WRONG_CLAUSE));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongSuffixUse, DistractorProperties.WRONG_SUFFIX_USE));
        distractorOptions.add(new Pair<MaterialCheckBox, DistractorProperties>(chkDistractorsWrongSuffix, DistractorProperties.INCORRECT_FORMS));
                
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsLemma, BracketsProperties.LEMMA));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsDistractorLemma, BracketsProperties.DISTRACTOR_LEMMA));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsPos, BracketsProperties.POS));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsForm, BracketsProperties.COMPARISON_FORM));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsConditional, BracketsProperties.CONDITIONAL_TYPE));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsWill, BracketsProperties.WILL));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsSentenceType, BracketsProperties.SENTENCE_TYPE));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsTense, BracketsProperties.TENSE));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsProgressive, BracketsProperties.PROGRESSIVE));
        bracketsOptions.add(new Pair<MaterialCheckBox, BracketsProperties>(chkBracketsActiveSentence, BracketsProperties.ACTIVE_SENTENCE));

        instructionsOptions.add(new Pair<MaterialCheckBox, InstructionsProperties>(chkLemmas, InstructionsProperties.LEMMA));
        instructionsOptions.add(new Pair<MaterialCheckBox, InstructionsProperties>(chkTenses, InstructionsProperties.TENSE));
        instructionsOptions.add(new Pair<MaterialCheckBox, InstructionsProperties>(chkProgressive, InstructionsProperties.PROGRESSIVE));
        instructionsOptions.add(new Pair<MaterialCheckBox, InstructionsProperties>(chkNTargets, InstructionsProperties.N_TARGETS));
        
        formatOptions.add(new Pair<MaterialCheckBox, OutputFormat>(parent.chkH5p, OutputFormat.H5P));
        formatOptions.add(new Pair<MaterialCheckBox, OutputFormat>(parent.chkFeedbookXml, OutputFormat.FEEDBOOK_XML));

        settingsWidgets = new Widget[] {
        		grpBrackets, chkBracketsLemma, chkBracketsDistractorLemma, chkBracketsConditional, chkBracketsPos, chkBracketsForm, chkBracketsWill, 
        		chkBracketsSentenceType, chkBracketsTense, chkBracketsProgressive, chkBracketsActiveSentence, 
        		grpDistractors, chkDistractorsOtherForm, chkDistractorsOtherVariant, chkDistractorsOtherPast, chkDistractorsOtherTense, 
        		chkDistractorsProgressive,
        		chkDistractorsIncorrectForms, chkDistractorsWrongConditional, chkDistractorsWrongClause, chkDistractorsWrongSuffixUse,
        		chkDistractorsWrongSuffix, grpPos, grpCompForm, grpForms, grpVerbPerson, grpVerbForms, grpSentenceTypes, grpTenses,
        		lblTensesSentences, chkPresentSimple, chkFutureSimple, chkPresentProgressive, chkPastProgressive, chkFutureProgressive, 
        		chkFuturePerfect, chkPresentPerfectProg, chkPastPerfectProg, chkFuturePerfectProg, lblTensesWords, grpCondTypes, 
        		grpClauses, grpScope, grpPronouns, grpVerbSplitting, grpTargetWords, grpSentTypes, chkPastSimple, chkPresentPerfect, 
        		chkPastPerfect, chkScopeActive, chkWho, chkWhich, chkThat, chkOtherRelPron, chk3Pers, chkNot3Pers, chkAffirmativeSent, 
        		chkNegatedSent, chkQuestions, chkStatements, chkRegularVerbs, chkIrregularVerbs, chkscopeType1, chkscopeType2, 
        		chkPosAdj, chkPosAdv, chkFormComparatives, chkFormSuperlatives, chkFormSynthetic, chkFormAnalytic, grpInstructions,
        		chkLemmas, chkTenses, chkProgressive, chkNTargets
        };
        
        controlsToReset = new Widget[] {chkPosAdj, chkPosAdv, chkFormComparatives, chkFormSuperlatives, chkFormSynthetic, chkFormAnalytic, chkscopeType1,
    			chkscopeType2, rbtMainClause, chkScopeActive, rbt1Verb, chkPresentSimple, 
    			chkPastSimple, chkFutureSimple, chkPresentProgressive, chkPastProgressive, chkFutureProgressive, chkPresentPerfect, chkPastPerfect, 
    			chkFuturePerfect, chkPresentPerfectProg, chkPastPerfectProg, chkFuturePerfectProg, chkAffirmativeSent, chkNegatedSent, chkQuestions, 
    			chkStatements, chkRegularVerbs, chkIrregularVerbs, chk3Pers, chkNot3Pers, chkWho, chkWhich, chkThat, chkOtherRelPron, rbtPerSentence,
    			chkBracketsLemma, chkBracketsDistractorLemma, chkBracketsPos, chkBracketsForm, chkBracketsConditional, chkBracketsWill, chkBracketsSentenceType, chkBracketsTense,
    			chkBracketsActiveSentence, chkBracketsProgressive,
    			chkDistractorsOtherForm, chkDistractorsOtherVariant, chkDistractorsOtherPast, chkDistractorsOtherTense, chkDistractorsProgressive,
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
     * Checkboxes representing possible instructions options
     */
    private final ArrayList<Pair<MaterialCheckBox, InstructionsProperties>> instructionsOptions = new ArrayList<>();
    
    /**
     * Checkboxes representing possible outputFormats
     */
    private final ArrayList<Pair<MaterialCheckBox, OutputFormat>> formatOptions = new ArrayList<>();
    
    /**
     * Checkboxes and radiobuttons which need to be set to <code>true</code> when resetting the panel
     */
    final Widget[] controlsToReset;

    /**
     * The occurrences of constructions relevant to exercise generation in the current document.
     */
    public HashMap<String, ArrayList<TargetConstruction>> relevantConstructionsInEntireDocument = null;

    /**
     * The possible topics for the dropdown.
     */
    private ArrayList<Pair<String, String>> possibleTopics;
    
    private final VisibilityManagerCollection visibilityManagers;
    
    private class TargetConstruction {
    	private int startIndex;
    	private int endIndex;
    	private boolean toBeUsed = true;
    	private DetailedConstruction constructionType;
    	
		public TargetConstruction(int startIndex, int endIndex, DetailedConstruction constructionType) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.constructionType = constructionType;
		}
		
		public TargetConstruction(int startIndex, int endIndex) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		public int getStartIndex() { return startIndex; }
		public int getEndIndex() { return endIndex; }
		public boolean isToBeUsed() { return toBeUsed; }
		public DetailedConstruction getConstructionType() { return constructionType; }
		
		public void setToBeUsed(boolean toBeUsed) { this.toBeUsed = toBeUsed; }
		
    }
    
    
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
        dlgTargetExclusion.removeFromParent();
        RootPanel.get().add(dlgTargetExclusion);
        dlgExercisePreview.removeFromParent();
        RootPanel.get().add(dlgExercisePreview);

        btnApplyDocumentSelection.addClickHandler(event -> {
        	dlgDocumentSelection.close();
        	        	
        	removedParts = getNotOverlappingRemovedParts();
        	newlyRemovedParts.clear();
        	        	
        	btnApplyDocumentSelection.setEnabled(false);
    		btnReset.setEnabled(removedParts.size() > 0);    		
        });
        
        btnApplyTargetSelection.addClickHandler(event -> {
        	String html = txtDocumentForSelection.getHTML();
        	updateUsedConstructions(html);
	        
        	setNumberExercisesText(calculateNumberOfExercises());

        	dlgTargetExclusion.close();
        });
        
        btnCloseExercisePreview.addClickHandler(event -> {
        	dlgExercisePreview.close();       	        		
        });
        btnReset.addClickHandler(event -> {
        	lblDocumentForSelection.setText(doc.getText());
        	removedParts.clear();
        	newlyRemovedParts.clear();
        	setNumberExercisesText(calculateNumberOfExercises());
        	
        	btnApplyDocumentSelection.setEnabled(true);
        });
        
        btnResetTargetSelection.addClickHandler(event -> {
        	for(TargetConstruction usedConstruction : usedTargetConstructions) {
        		usedConstruction.setToBeUsed(true);
        	}
        	btnResetTargetSelection.setEnabled(false);
        	
        	setTargetExclusionText();
        	setNumberExercisesText(calculateNumberOfExercises());        	
        });
        
        lblDocumentForSelection.addKeyDownHandler(event -> {
        	if(event.getNativeKeyCode() == KeyCodes.KEY_DELETE || event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
        		removeSelectionEventHanlder();
        	}
        });
        
        txtDocumentForSelection.addKeyDownHandler(event -> {event.preventDefault();});
        
    	btnDelete.addClickHandler(event -> {
    		parent.deleteTask(this);
    	});
    	
    	btnSelectDocumentPart.addClickHandler(event -> {
    		dlgDocumentSelection.open();
    		lblDocumentForSelection.setFocus(true);
    	});
    	
    	btnSelectTargets.addClickHandler(event -> {
        	setTargetExclusionText();
        	
            btnResetTargetSelection.setEnabled(usedTargetConstructions.stream().anyMatch(c -> !c.isToBeUsed()));
    		dlgTargetExclusion.open();
    		txtDocumentForSelection.setFocus(true);
    	});
    	
    	btnPreviewExercise.addClickHandler(event -> {
    		dlgExercisePreview.open();
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
    	chkBracketsLemma.addClickHandler(e -> onLemmaClickedEventHandler());
    	
    	for(Pair<MaterialCheckBox, DistractorProperties> option : distractorOptions) {
    		option.first.addClickHandler(e -> setNumberExercisesText(calculateNumberOfExercises()));
    	}
    	
    	chkUseConfig.addClickHandler(e -> {
			pnlConfig.setVisible(!chkUseConfig.getValue());
			colType.setVisible(!chkUseConfig.getValue());
					
			if(chkUseConfig.getValue()) {				
				String selecteTopic = getTopic();
		    	drpTopic.clear();
		    	
		    	addOptionToTopic("---", "Topic", selecteTopic, 0);    			
		
		    	int i = 1;
		    	for(Pair<String, String> possibleTopic : possibleTopics) {
	    			addOptionToTopic(possibleTopic.first, possibleTopic.second, selecteTopic, i);  
	    			i++;
		    	}
		    			    	
		    	
				icoValidity.setVisible(true);
				
				if(doc.getFileExtension().equals(".xlsx") && !getTopic().equals("Topic")) {
					icoValidity.setIconType(IconType.CHECK_CIRCLE);
					icoValidity.setTextColor(Color.GREEN);
				} else {
					icoValidity.setIconType(IconType.ERROR);
					icoValidity.setTextColor(Color.RED);
				}
				parent.setGenerateExercisesEnabled();
	    	} else {
	    		initializeRelevantConstructions();
	    	}
			
			//pnlFileUpload.setVisible(chkUseConfig.getValue());
    	});
    	/*
    	btnFileUpload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				boolean isValidFile = !btnFileUpload.getFilename().isEmpty() && btnFileUpload.getFilename().endsWith(".xlsx");
				icoValidity.setVisible(isValidFile);
    			icoValidity.setIconType(IconType.CHECK_CIRCLE);
				icoValidity.setTextColor(Color.GREEN);
				if(isValidFile) {
					JavaScriptObject files = btnFileUpload.getElement().getPropertyJSO("files");
		    		readTextFile(files, instance);
				parent.setGenerateExercisesEnabled();
				}
			}
    	});*/
    }
    
    private void updateUsedConstructions(String htmlText) {
    	String[] parts = htmlText.split("<button");
    	ArrayList<Boolean> constructionEnabled = new ArrayList<>();
    	if(parts.length > 1) {
        	for(int i = 1; i < parts.length; i++) {
        		constructionEnabled.add(parts[i].contains("class=\"selected\"") ? true : false);
        	}
    	}             

    	for(int i = 0; i < usedTargetConstructions.size(); i++) {
        	usedTargetConstructions.get(i).setToBeUsed(constructionEnabled.get(i));
        }
    }
        
    private void onLemmaClickedEventHandler() {
    	if(!chkBracketsLemma.getValue()) {
    		chkBracketsDistractorLemma.setValue(false);
    		chkBracketsDistractorLemma.setEnabled(false);
    	} else {
    		chkBracketsDistractorLemma.setEnabled(true);
    	}
	}

	private void removeSelectionEventHanlder() {
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
    
		btnApplyDocumentSelection.setEnabled(true);
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
    	possibleTopics.add(new Pair<String, String>("Comparative", "Compare"));
    	possibleTopics.add(new Pair<String, String>("Simple Present", "Present"));
    	possibleTopics.add(new Pair<String, String>("Past tense", "Past"));
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
    		numberOfExercises = calculateGlobalNExercises();
    		visibleSettings = visibilityManager.getVisibleWidgets(numberOfExercises);
    	} else {
        	visibleSettings = new ArrayList<Widget>();
    	}
    	    	
    	setSettingsVisibility(visibleSettings);  	
    	
    	setNumberExercisesText(calculateNumberOfExercises());
    }
    

    /**
     * Re-calculates the constructions relevant to exercise generation for the entire document and for the selected document part.
     * Sets the text for document part selection to the previewed text and the selected range to the entire document.
     * Sets the possible topics options in the dropdown.
     */
    public void initializeRelevantConstructions() {    	
    	// We only use the document if it's in the selected language
    	if(!DocumentPreviewPane.getInstance().getCurrentlyPreviewedDocument().getDocument().getLanguage().equals(DocumentPreviewPane.getInstance().getCurrentLocale())) {
    		return;
    	}
    	
    	doc = DocumentPreviewPane.getInstance().getCurrentlyPreviewedDocument().getDocument();
    	lblDocTitle.setText(doc.getTitle());

    	if(chkUseConfig.getValue()) {
	    	if(doc.getFileExtension().equals(".xlsx")) {
				icoValidity.setIconType(IconType.CHECK_CIRCLE);
				icoValidity.setTextColor(Color.GREEN);	
	    	} else {
				icoValidity.setIconType(IconType.ERROR);
				icoValidity.setTextColor(Color.RED);
	    	}
    		icoValidity.setVisible(true);
			parent.setGenerateExercisesEnabled();
    	} else {
	        lblDocumentForSelection.setText(doc.getText());
	        
	        removedParts.clear();
	        newlyRemovedParts.clear();
	
			relevantConstructionsInEntireDocument = new HashMap<>();
	    	calculateConstructionsOccurrences(relevantConstructionsInEntireDocument);
	    	
	    	String selecteTopic = getTopic();
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
    	
    	parent.setResourceDownloadVisiblity();   
    }
    
    private void setTargetExclusionText() {
    	if(doc != null && usedTargetConstructions != null) {	        			        
	    	StringBuilder sb = new StringBuilder();
	    	ArrayList<TargetConstruction> usedConstructions = new ArrayList<>(usedTargetConstructions);

	    	sb.append("<div width='100%'><style>.selected { background-color: GoldenRod; border-radius: 6px;}</style>");
	    	ArrayList<Pair<Integer, Integer>> removedRanges = new ArrayList<>(removedParts);
	    	for(int n = 0; n < doc.getText().length(); n++) {
	    		if(usedConstructions.size() > 0 && usedConstructions.get(0).getEndIndex() == n) {
	    			usedConstructions.remove(0);
	    		}
	    		if(usedConstructions.size() > 0 && usedConstructions.get(0).getStartIndex() == n) {
	    			// it's the start of a construction
	    			if(usedConstructions.get(0).isToBeUsed()) {
	    				sb.append("<button style='cursor:pointer;' class='selected' onclick='this.classList.toggle(\"selected\");'>");
	    			} else {
	    				sb.append("<button style='cursor:pointer;' class='' onclick='this.classList.toggle(\"selected\");'>");
	    			}
	    		}  
	    		
	    		if(removedRanges.size() > 0 && removedRanges.get(0).second == n) {
	    			removedRanges.remove(0);
	    		}
	    		if(!(removedRanges.size() > 0 && removedRanges.get(0).first >= n && removedRanges.get(0).second < n)) {
	    			sb.append(doc.getText().charAt(n));
	    		} 
	    		if(usedConstructions.size() > 0 && usedConstructions.get(0).getEndIndex() == n + 1) {
	    			sb.append("</button>");
	    		}
	    	}
	    	sb.append("</div>");
	
	        txtDocumentForSelection.setHTML(sb.toString());
    	}
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
    private ArrayList<String> determineConfiguredConstructions(boolean checkForValue) {
    	String topic = getTopic();
    	String exerciseType = getExerciseType();
    	    	
    	ArrayList<String> constructionsToConsider = new ArrayList<String>();

    	if(topic.equals("Passive")) {
    		if(exerciseType.equals("FiB")) {    			    			    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPassiveFiBComponents(), checkForValue);    			
    		} else if(exerciseType.equals("Drag") || exerciseType.equals("Jumble")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPassiveDragComponents(), checkForValue);    	
    		}
    	} else if(topic.equals("Relatives")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Mark") || exerciseType.equals("Jumble") || exerciseType.equals("Drag") && rbtPerSentence.getValue()) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getRelativesFiBMarkComponents(), checkForValue);  
    		} else if (exerciseType.equals("Select")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getRelativesSelectComponents(), checkForValue);  
    		} else if(exerciseType.equals("Drag") && !rbtPerSentence.getValue()) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getRelativesDragComponents(), checkForValue);      			
    		}
    	} else if(topic.equals("Present")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Memory") || exerciseType.equals("Jumble")) {       			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPresentFiBSelectComponents(), checkForValue);
    		} else if(exerciseType.equals("Mark")) {     			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPresentMarkComponents(), checkForValue);   			
    		}
    	} else if(topic.equals("Past")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Memory") || exerciseType.equals("Jumble")) {    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPastFiBSelectComponents(), checkForValue);    			
    		} else if(exerciseType.equals("Mark") || exerciseType.equals("Drag")) {    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getPastMarkDragComponents(), checkForValue);    			
    		} 
    	} else if(topic.equals("'if'")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Drag") || exerciseType.equals("Jumble")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getConditionalComponents(), checkForValue);    	
    		} 
    	} else if(topic.equals("Compare")) {
			if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Mark") || exerciseType.equals("Drag") || 
					exerciseType.equals("Memory") || exerciseType.equals("Jumble")) {
    			constructionsToConsider = getConstructionNamesFromSettings(constructionComponents.getComparativeComponents(), checkForValue);  
    		}
    	}
    	
    	return constructionsToConsider;
    }
    
    /**
     * Calculates the maximum number of exercises that can be generated for the current document regardless of other settings configurations.
     * @return The maximum number of exercises for the current document
     */
    private int calculateGlobalNExercises() {
    	ArrayList<String> constructionsToConsider = determineConfiguredConstructions(false);
    	int nExercises = 0;
    	for(String constructionToConsider : constructionsToConsider) {
    		nExercises += relevantConstructionsInEntireDocument.get(constructionToConsider).size();
    	}
    	
    	// If we use both clauses of the conditional sentence as targets, we have double the amount of blanks
    	String topic = getTopic();
    	if(topic.equals("'if'") && rbtBothClauses.getValue()) {
    		nExercises = nExercises * 2;	
    	}
    	
    	return nExercises;
    }
    	
    private ArrayList<TargetConstruction> usedTargetConstructions = new ArrayList<>();

    /**
     * Calculates the number of exercises that can be generated for the current document and settings and selections.
     * @return The number of exercises that can be generated.
     */
    private int calculateNumberOfExercises() {
    	String topic = getTopic();
    	ArrayList<String> constructionsToConsider = determineConfiguredConstructions(true);    	
    			
		ArrayList<TargetConstruction> containedConstructions = new ArrayList<>();
		
		for(String constructionToConsider : constructionsToConsider) {
			ArrayList<TargetConstruction> possibleConstructions = relevantConstructionsInEntireDocument.get(constructionToConsider);
			for(TargetConstruction possibleConstruction : possibleConstructions) {
				boolean canBeUsed = true;
	    		for(Pair<Integer, Integer> removedPart : removedParts) {
	    			if(Math.max(possibleConstruction.getStartIndex(), removedPart.first) < Math.min(possibleConstruction.getEndIndex(), removedPart.second)) {
	    				// The construction overlaps with a removed part, so we cannot use it
	    				canBeUsed = false;
	    			}
	    		}
	    		if(canBeUsed) {
	    			// Copy the user's exclusion settings if possible
	    			for(TargetConstruction previouslyUsedConstruction : usedTargetConstructions) {
	    				if(previouslyUsedConstruction.getStartIndex() == possibleConstruction.getStartIndex() &&
	    						previouslyUsedConstruction.getEndIndex() == possibleConstruction.getEndIndex()) {
	    					possibleConstruction.setToBeUsed(previouslyUsedConstruction.isToBeUsed());
	    					break;
	    				}
	    			}
	    			
		    		containedConstructions.add(possibleConstruction);
	    		}
			}
    	}

		usedTargetConstructions.clear();
		
		// remove overlapping constructions
		Collections.sort(containedConstructions, (c1, c2) -> c1.getStartIndex() < c2.getStartIndex() ? -1 : 1);
		HashSet<TargetConstruction> constructionsToRemove = new HashSet<>();
        for(TargetConstruction construction : containedConstructions) {
        	for(TargetConstruction otherConstruction : containedConstructions) {
        		if(construction != otherConstruction && (Math.max(construction.getStartIndex(), otherConstruction.getStartIndex()) < 
						Math.min(construction.getEndIndex(), otherConstruction.getEndIndex()))) {
					if(construction.getEndIndex() - construction.getStartIndex() > otherConstruction.getEndIndex() - otherConstruction.getStartIndex()) {
						constructionsToRemove.add(otherConstruction);
					} else {
						constructionsToRemove.add(construction);
					}
        		}
        	}
        }        
    	
        int nExercises = 0;
    	for(TargetConstruction construction : containedConstructions) {
    		if(!constructionsToRemove.contains(construction)) {
    			usedTargetConstructions.add(construction);
    			if(construction.isToBeUsed()) {
    				nExercises++;
    			}
    		}
    	}
    	
    	// If we use both clauses of the conditional sentence as targets, we have double the amount of blanks
    	if(topic.equals("'if'") && rbtBothClauses.getValue()) {
    		nExercises *= 2;	
    	}
    	return nExercises;
    }
        
    /**
     * Creates all combinations of constructions for the checked settings.
     * @param 	constructionComponent A list of the individual constructions making up a more complex (more detailed) construction. 
     * 			Each construction consists of a list of possible values.
     * @return	The list of construction names corresponding to relevantConstructions names
     */
    private ArrayList<String> getConstructionNamesFromSettings(ArrayList<ArrayList<Pair<MaterialCheckBox, String>>> constructionLevels,
    		boolean checkForValue) {
		ArrayList<String> initialConstructionNames = new ArrayList<String>();
    	if(constructionLevels.size() > 0) {
    		for(Pair<MaterialCheckBox, String> firstLevelConstruction : constructionLevels.get(0)) {
    			if(firstLevelConstruction.first == null || !checkForValue ||  firstLevelConstruction.first.getValue()) {
    				initialConstructionNames.add(firstLevelConstruction.second);
    			}   		
    		}
		
    		if(constructionLevels.size() > 1) {
    			for(int i = 1; i < constructionLevels.size(); i++) {
    				if(initialConstructionNames.size() > 0) {
    					ArrayList<String> currentConstructionNames = new ArrayList<String>();
    					for(Pair<MaterialCheckBox, String> currentLevelConstruction : constructionLevels.get(i)) {
    						if(currentLevelConstruction.first == null || !checkForValue || currentLevelConstruction.first.getValue()) {
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
     * Hides the Generate feedback checkbox if the exercise text contains no constructions which support feedback.
     */
    private void setNumberExercisesText(int numberOfExercises) {
    	String topic = getTopic();
    	
    	icoValidity.setVisible(true);
    	icoValidity.setIconType(IconType.ERROR);
		icoValidity.setTextColor(Color.RED);

    	if(chkUseConfig.getValue()) {
    		if(!topic.equals("Topic") && doc.getFileExtension().equals(".xlsx")) {
    			icoValidity.setIconType(IconType.CHECK_CIRCLE);
    			icoValidity.setTextColor(Color.GREEN);
    		}
    	} else {
	    	String exerciseType = getExerciseType();
	
			lblNumberExercises.setVisible(true);
			lblSelectTypeTopic.setVisible(false);
			
			if(exerciseType.equals("Exercise Type") || topic.equals("Topic")) {
				lblSelectTypeTopic.setVisible(true);
	    		lblNumberExercises.setVisible(false);
	        	icoValidity.setVisible(false);
			} else if(exerciseType.equals("Memory") && !(topic.equals("Present") || topic.equals("Past") || topic.equals("Compare")) || 
					exerciseType.equals("Mark") && topic.equals("'if'") || 
					exerciseType.equals("Drag") && topic.equals("Present") || 
					exerciseType.equals("Select") && (topic.equals("Passive") || !topic.equals("Relatives") && !hasCheckedDistractors())) {
	
				lblNumberExercises.setText("No exercises can be generated for the current settings.");
	    		lblNumberExercises.setTextColor(Color.RED);
			} else {
	    		if(numberOfExercises == 0) {
	    			lblNumberExercises.setText("No exercises can be generated for the current settings.");
	        		lblNumberExercises.setTextColor(Color.RED);
	    		} else {
	        		lblNumberExercises.setTextColor(numberOfExercises >= 5 ? Color.BLACK : Color.ORANGE);
	        		if(numberOfExercises >= 5) {
	        			icoValidity.setIconType(IconType.CHECK_CIRCLE);
	        			icoValidity.setTextColor(Color.GREEN);
	        		} else {
	        			icoValidity.setIconType(IconType.WARNING);
	        			icoValidity.setTextColor(Color.ORANGE);
	        		}
	    			if(exerciseType.equals("FiB") || exerciseType.equals("Select")) {
	            		lblNumberExercises.setText("A maximum of " + numberOfExercises + " blanks can be generated for the current settings.");
	    			} else if(exerciseType.equals("Mark")) {
	            		lblNumberExercises.setText("A maximum of " + numberOfExercises + " target words can be generated for the current settings.");
	    			} else if(exerciseType.equals("Memory")) {
	            		lblNumberExercises.setText("A maximum of " + numberOfExercises + " pairs can be generated for the current settings.");
	    			} else if(exerciseType.equals("Jumble")) {
	            		lblNumberExercises.setText("A maximum of " + numberOfExercises + " jumbled sentences can be generated for the current settings.");
	    			} else if(exerciseType.equals("Drag")) {
	    				if((topic.equals("Relatives") || topic.equals("'if'")) && rbtPerSentence.getValue() || topic.equals("Passive")) {
	    					// 1 exercise per sentence
	                		lblNumberExercises.setText("A maximum of " + numberOfExercises + " exercises can be generated for the current settings.");
	    				} else {
	    					if(numberOfExercises < 2) {
	    						// We need at least 2 target words for drag & drop to make sense
	    						lblNumberExercises.setText("No exercises can be generated for the current settings.");
	    		        		lblNumberExercises.setTextColor(Color.RED);
	    		        		icoValidity.setIconType(IconType.ERROR);
	    	        			icoValidity.setTextColor(Color.RED);
	    					} else {
	    						lblNumberExercises.setText("A maximum of " + numberOfExercises + " target words can be generated for the current settings.");
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	
		parent.setGenerateExercisesEnabled();
		parent.setFeedbackGenerationVisiblity();
		btnPreviewExercise.setVisible(false);
		parent.icoDownload.setVisible(false);
    }
    
    /**
     * Checks if any contained construction supports feedback generation.
     * @return <c>true</c> if the selected text contains at least 1 construction which supports feedback generation; otherwise <c>false</c>.
     */
    public boolean supportsFeedbackGeneration() {
    	String type = getExerciseType();

    	// If the type doesn't support feedback or the settings always result in multi-word constructions, we don't need to check the constructions
    	if(type.equals("Mark") || type.equals("Drag") || type.equals("Memory") || type.equals("Jumble")) {
    		return false;
    	}
    	
    	if(!getSelectedOutputFormats().contains(OutputFormat.H5P)) {
			return false;
		}
    	
    	return true;
    }
    
    /**
     * Determines whether the document used for exercise generation is a web document.
     * @return	<c>true</c> if the document is a web document; otherwise <c>false</c>
     */
    public boolean usesWebDocument() {
    	return doc.getUrl().length() != 0;
    }
    
    /**
     * Filters those constructions that are within the selected part of the document.
     * @param construction	The construction under consideration
     * @param doc			The document containing the text and constructions
     * @return				The occurrences of the construction within the selected range
     */
    private ArrayList<TargetConstruction> getConstructionsWithinSelectedPart(GrammaticalConstruction construction, 
    		RankableDocument doc, String constructionName) {
    	ArrayList<TargetConstruction> containedConstructions = new ArrayList<>();
		
    	for(ConstructionRange range : doc.getConstructionOccurrences(construction)) {
    		boolean canBeUsed = true;
    		for(Pair<Integer, Integer> removedPart : removedParts) {
    			if(Math.max(range.getStart(), removedPart.first) < Math.min(range.getEnd(), removedPart.second)) {
    				// The construction overlaps with a removed part, so we cannot use it
    				canBeUsed = false;
    			}
    		}
    		if(canBeUsed) {
    			if(constructionName == null) {
    				containedConstructions.add(new TargetConstruction(range.getStart(), range.getEnd()));
    			} else {
	    			containedConstructions.add(new TargetConstruction(range.getStart(), range.getEnd(), 
	    					ConstructionNameEnumMapper.getEnum(constructionName)));
    			}
    		}
    	}
    	
    	return containedConstructions;
    }
    
    private ArrayList<TargetConstruction> getConstructionsWithinSelectedPart(GrammaticalConstruction construction, 
    		RankableDocument doc) {
    	return getConstructionsWithinSelectedPart(construction, doc, null);
    }
    
    /**
     * Calculates the occurrences of constructions in the combinations relevant to exercise generation.
     */
    public void calculateConstructionsOccurrences(HashMap<String, ArrayList<TargetConstruction>> relevantConstructions) {   
    	relevantConstructions.clear();
    	HashMap<String, ArrayList<TargetConstruction>> constructionOccurrences = getConstructionsOccurrences();
    	ArrayList<TargetConstruction> excludedConstructions = new ArrayList<>();
    	for(Entry<String, ArrayList<TargetConstruction>> constructions : relevantConstructions.entrySet()) {
    		for(TargetConstruction construction : constructions.getValue()) {
    			if(!construction.isToBeUsed()) {
    				excludedConstructions.add(construction);
    			}
    		}
    	}
    	
    	for (Entry<String, ArrayList<TargetConstruction>> entry : constructionOccurrences.entrySet()) {
    		ArrayList<TargetConstruction> usedConstructions = new ArrayList<>();
    		for(TargetConstruction tc : entry.getValue()) {
        		if(!excludedConstructions.stream().anyMatch(c -> c.getEndIndex() == tc.getEndIndex() && c.getStartIndex() == tc.getStartIndex()) ) {
        			usedConstructions.add(tc);
        		}
    		}
        	relevantConstructions.put(entry.getKey(), usedConstructions);
        }

    	setNumberExercisesText(calculateNumberOfExercises());
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
			numberOccurrences += relevantConstructionsInEntireDocument.get(name).size();
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
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_PROGRESSIVE"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PRESENT_PERFECT_PROGRESSIVE"));
			firstLevelConstructions.add(new Pair<MaterialCheckBox, String>(null, "TENSE_PAST_PERFECT_PROGRESSIVE"));
			
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
		
		return getConstructionNamesFromSettings(constructionLevels, true);    	
    }
    
//    public static native void readTextFile(JavaScriptObject files, TaskItem classInstance)
//	/*-{
//	    var reader = new FileReader();
//
//	    reader.onload = function(e) {
//	        classInstance.@com.flair.client.presentation.widgets.exerciseGeneration.TaskItem::fileLoaded(*)(String(new Uint8Array(reader.result)));
//	    }
//
//	    return reader.readAsArrayBuffer(files[0]);
//	}-*/;
    /*
    private String configFile;
    private String configFileName;
    
    public void fileLoaded(String fileContents) {
		configFile = fileContents;
		configFileName = btnFileUpload.getFilename();
	}*/
    
    /**
     * Generates settings for the server from the selected options
     */
    public IExerciseSettings generateExerciseSettings() {
    	String topic = getTopic();
    	String quiz = getQuiz();

    	if(chkUseConfig.getValue()) {
    		//return new ConfigExerciseSettings(configFile, "", configFileName, "", getSelectedOutputFormats());
    		return new ConfigExerciseSettings(doc.getTitle(), doc.getLinkingId(), getSelectedOutputFormats(),
    				topic, quiz, parent.chkGenerateFeedback.isVisible() && parent.chkGenerateFeedback.getValue());
    	} else {
	    	ArrayList<Construction> constructions = new ArrayList<>();
	    	    	
	    	ArrayList<DistractorProperties> distractorProperties = getSelectedDistractors();
			ArrayList<BracketsProperties> brackets = getSelectedBracketContents();
			ArrayList<InstructionsProperties> instructions = getSelectedInstructionsContents();
			ArrayList<OutputFormat> outputFormats = getSelectedOutputFormats();
	
	    	String type = getExerciseType();
	    	
			for(TargetConstruction c : usedTargetConstructions) {
				if(c.isToBeUsed()) {
	    			DetailedConstruction dc = type.equals("Jumble") ? DetailedConstruction.SENTENCE_PART : c.getConstructionType();
	    			Construction construction = new Construction(dc, new Pair<>(c.getStartIndex(), c.getEndIndex()));
	        		constructions.add(construction);
				}
			}
	
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
	    			ExerciseType.getEnum(type), quiz, distractorProperties, brackets, instructions, spnNDistractors.getValue() - 1, 
	    			lblName.getValue(), parent.chkDownloadResources.getValue(), chkOnlyText.getValue(), 
	    			parent.chkGenerateFeedback.isVisible() && parent.chkGenerateFeedback.getValue(), doc.getLinkingId(), doc.getTitle(), "",
	    			outputFormats, topic);
    	}
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
     * Determines the selected instructions content properties.
     * @return	The selected instructions properties
     */
    private ArrayList<InstructionsProperties> getSelectedInstructionsContents() {
    	ArrayList<InstructionsProperties> instructions = new ArrayList<>();
    	for(Pair<MaterialCheckBox, InstructionsProperties> option : instructionsOptions) {
    		if(option.first.isVisible() && option.first.getValue()) {
    			instructions.add(option.second);
    		}
    	}
    	
    	return instructions;
    }
    
    /**
     * Determines the selected output formats.
     * @return	The selected output formats
     */
    private ArrayList<OutputFormat> getSelectedOutputFormats() {
    	ArrayList<OutputFormat> formats = new ArrayList<>();
    	for(Pair<MaterialCheckBox, OutputFormat> option : formatOptions) {
    		if(option.first.getValue()) {
    			formats.add(option.second);
    		}
    	}
    	
    	return formats;
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
     * Adds constructions consisting of a single token to the list
     * @param relevantConstructions	The identified construction occurrences
     * @param gram					The grammatical construction
     * @param key					The name of the construction used as key in the has map
     */
    private void addSingleWordConstructions(HashMap<String, ArrayList<TargetConstruction>> relevantConstructions, 
    		GrammaticalConstruction gram, String key) {
    	ArrayList<TargetConstruction> findings = getConstructionsWithinSelectedPart(gram, doc, key);

		relevantConstructions.put(key, findings);
    }
    
	/**
	 * Determines the indices of occurrences of constructions in the combinations relevant to exercise generation.
	 * @return				The indices of occurrences of constructions relevant to exercise generation
	 */
    public HashMap<String, ArrayList<TargetConstruction>> getConstructionsOccurrences() {    
    	HashMap<String, ArrayList<TargetConstruction>> relevantConstructions = new HashMap<>();

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

        ArrayList<TargetConstruction> passiveOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.PASSIVE_VOICE, doc);
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<TargetConstruction> tenseOccurrences = 
            		getConstructionsWithinSelectedPart(tenseConstruction, doc);
    		ArrayList<TargetConstruction> passiveIndices = new ArrayList<>();
    		ArrayList<TargetConstruction> activeIndices = new ArrayList<>();
            for(TargetConstruction tenseOccurrence : tenseOccurrences) {
            	TargetConstruction correspondingPassiveOccurrence = null;
            	for(TargetConstruction passiveOccurrence : passiveOccurrences) {
            		if(passiveOccurrence.getStartIndex() >= tenseOccurrence.getStartIndex() && passiveOccurrence.getStartIndex() < tenseOccurrence.getEndIndex() || 
            				tenseOccurrence.getStartIndex() >= passiveOccurrence.getStartIndex() && tenseOccurrence.getStartIndex() < passiveOccurrence.getEndIndex()) {
            			// There is some overlap between the passive construction and the tense construction
            			correspondingPassiveOccurrence = passiveOccurrence;
            			break;
            		}
            	}
            	if(correspondingPassiveOccurrence != null) {
            		// The passive construction doesn't always contain all verbs of a verb cluster and the tense construction doesn't usually include the participle
            		// We therefore take the combined construction
            		passiveIndices.add(new TargetConstruction(Integer.min(correspondingPassiveOccurrence.getStartIndex(), tenseOccurrence.getStartIndex()), 
            				Integer.max(correspondingPassiveOccurrence.getEndIndex(), tenseOccurrence.getEndIndex()), 
            				ConstructionNameEnumMapper.getEnum("passive-" + tenseConstruction.name())));
            	} else {
            		// We just take the verb of the tense construction for active constructions
            		activeIndices.add(new TargetConstruction(tenseOccurrence.getStartIndex(), 
            				tenseOccurrence.getEndIndex(), ConstructionNameEnumMapper.getEnum("active-" + tenseConstruction.name())));
            	}
            }

			relevantConstructions.put("passive-" + tenseConstruction.name(), passiveIndices);
			relevantConstructions.put("active-" + tenseConstruction.name(), activeIndices);                               	
        }

        tenseConstructions = new GrammaticalConstruction[]{
        		GrammaticalConstruction.TENSE_PAST_SIMPLE,
        		GrammaticalConstruction.TENSE_PRESENT_PERFECT,
        		GrammaticalConstruction.TENSE_PAST_PERFECT,
        		GrammaticalConstruction.TENSE_PAST_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_PAST_PERFECT_PROGRESSIVE,
        		GrammaticalConstruction.TENSE_PRESENT_PERFECT_PROGRESSIVE
        };
        
        ArrayList<TargetConstruction> negationOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.NEGATION_NOT, doc);
        negationOccurrences.addAll(getConstructionsWithinSelectedPart(GrammaticalConstruction.NEGATION_NT, doc));
        ArrayList<TargetConstruction> questionOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.QUESTIONS_DIRECT, doc);
        ArrayList<TargetConstruction> irregularOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.VERBS_IRREGULAR, doc);

        // past tense combinations
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<TargetConstruction> tenseOccurrences = 
            		getConstructionsWithinSelectedPart(tenseConstruction, doc);
    		ArrayList<TargetConstruction> indicesQuestionAffirmativeRegular = new ArrayList<>();
    		ArrayList<TargetConstruction> indicesQuestionAffirmativeIrregular = new ArrayList<>();
    		ArrayList<TargetConstruction> indicesQuestionNegativeRegular = new ArrayList<>();
    		ArrayList<TargetConstruction> indicesQuestionNegativeIrregular = new ArrayList<>();
    		ArrayList<TargetConstruction> indicesStatementAffirmativeRegular = new ArrayList<>();
    		ArrayList<TargetConstruction> indicesStatementAffirmativeIrregular = new ArrayList<>();
    		ArrayList<TargetConstruction> indicesStatementNegativeRegular = new ArrayList<>();
    		ArrayList<TargetConstruction> indicesStatementNegativeIrregular = new ArrayList<>();
        	
            for(TargetConstruction tenseOccurrence : tenseOccurrences) {
            	boolean foundNegationOverlap = false;
            	TargetConstruction negation = null;
            	for(TargetConstruction negationOccurrence : negationOccurrences) {
            		if(negationOccurrence.getEndIndex() >= tenseOccurrence.getStartIndex() - 1 && negationOccurrence.getStartIndex() <= tenseOccurrence.getEndIndex() + 1) {
            			// The negation is at most the previous or succeeding token (only a single character in-between for a whitespace)
            			foundNegationOverlap = true;
            			negation = negationOccurrence;
            			break;
            		}
            	}
            	
            	boolean foundQuestionOverlap = false;
            	for(TargetConstruction questionOccurrence : questionOccurrences) {
            		if(tenseOccurrence.getStartIndex() >= questionOccurrence.getStartIndex() && tenseOccurrence.getEndIndex() <= questionOccurrence.getEndIndex()) {
            			// The verb is within a question
            			foundQuestionOverlap = true;
            			break;
            		}
            	}
            	
            	boolean foundIrregularOverlap = false;
            	for(TargetConstruction irregularOccurrence : irregularOccurrences) {
            		if(irregularOccurrence.getStartIndex() >= tenseOccurrence.getStartIndex() && irregularOccurrence.getEndIndex() <= tenseOccurrence.getEndIndex()) {
            			// The irregular verb is part of the verb (irregular forms consist of a single token)
            			foundIrregularOverlap = true;
            			break;
            		}
            	}
            	
            	if(foundNegationOverlap) {
            		if(foundQuestionOverlap) {
            			if(foundIrregularOverlap) {
            				indicesQuestionNegativeIrregular.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
            						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()), 
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-question-neg-irreg")));
            			} else {
            				indicesQuestionNegativeRegular.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
            						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()),
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-question-neg-reg")));
            			}
            		} else {
            			if(foundIrregularOverlap) {
            				indicesStatementNegativeIrregular.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
            						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()),
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-stmt-neg-irreg")));
            			} else {
            				indicesStatementNegativeRegular.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
            						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()),
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-stmt-neg-reg")));
            			}
            		}
            	} else {
            		if(foundQuestionOverlap) {
            			if(foundIrregularOverlap) {
            				indicesQuestionAffirmativeIrregular.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-question-affirm-irreg")));
            			} else {
            				indicesQuestionAffirmativeRegular.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-question-affirm-reg")));
            			}
            		} else {
            			if(foundIrregularOverlap) {
            				indicesStatementAffirmativeIrregular.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-stmt-affirm-irreg")));
            			} else {
            				indicesStatementAffirmativeRegular.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
            						ConstructionNameEnumMapper.getEnum(tenseConstruction.name() + "-stmt-affirm-reg")));
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
        ArrayList<TargetConstruction> presentOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, doc);
        ArrayList<TargetConstruction> indicesQuestionAffirmative3 = new ArrayList<TargetConstruction>();
        ArrayList<TargetConstruction> indicesQuestionAffirmativeNot3 = new ArrayList<TargetConstruction>();
    	ArrayList<TargetConstruction> indicesQuestionNegative3 = new ArrayList<TargetConstruction>();
    	ArrayList<TargetConstruction> indicesQuestionNegativeNot3 = new ArrayList<TargetConstruction>();
    	ArrayList<TargetConstruction> indicesStatementAffirmative3 = new ArrayList<TargetConstruction>();
    	ArrayList<TargetConstruction> indicesStatementAffirmativeNot3 = new ArrayList<TargetConstruction>();
    	ArrayList<TargetConstruction> indicesStatementNegative3 = new ArrayList<TargetConstruction>();
    	ArrayList<TargetConstruction> indicesStatementNegativeNot3 = new ArrayList<TargetConstruction>();
        
    	for(TargetConstruction tenseOccurrence : presentOccurrences) {
        	// we take the first token since the inflected present verb is usually at the beginning of the sequence.
        	// It's just an approximation, but the best we can get without proper NLP processing.
        	String occurrenceText = doc.getText().substring(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex()).split(" ")[0];
        	
        	boolean foundNegationOverlap = false;        	
        	TargetConstruction negation = null;
        	for(TargetConstruction negationOccurrence : negationOccurrences) {
        		if(negationOccurrence.getEndIndex() >= tenseOccurrence.getStartIndex() - 1 && negationOccurrence.getStartIndex() <= tenseOccurrence.getEndIndex() + 1) {
        			// The negation is at most the previous or succeeding token (only a single character in-between for a whitespace)
        			foundNegationOverlap = true;
        			negation = negationOccurrence;
        			break;
        		}
        	}

        	boolean foundQuestionOverlap = false;
        	for(TargetConstruction questionOccurrence : questionOccurrences) {
        		if(tenseOccurrence.getStartIndex() >= questionOccurrence.getStartIndex() && tenseOccurrence.getEndIndex() <= questionOccurrence.getEndIndex()) {
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
        				indicesQuestionNegative3.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
        						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()),
        						ConstructionNameEnumMapper.getEnum("present-question-neg-3")));
        			} else {
        				indicesQuestionNegativeNot3.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
        						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()),
        						ConstructionNameEnumMapper.getEnum("present-question-neg-not3")));
        			}
        		} else {
        			if(isThirdPersonSingular) {
        				indicesStatementNegative3.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
        						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()),
        						ConstructionNameEnumMapper.getEnum("present-stmt-neg-3")));
        			} else {
        				indicesStatementNegativeNot3.add(new TargetConstruction(Math.min(tenseOccurrence.getStartIndex(), negation.getStartIndex()), 
        						Math.max(tenseOccurrence.getEndIndex(), negation.getEndIndex()),
        						ConstructionNameEnumMapper.getEnum("present-stmt-neg-not3")));
        			}
        		}
        	} else {
        		if(foundQuestionOverlap) {
        			if(isThirdPersonSingular) {
        				indicesQuestionAffirmative3.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
        						ConstructionNameEnumMapper.getEnum("present-question-affirm-3")));
        			} else {
        				indicesQuestionAffirmativeNot3.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
        						ConstructionNameEnumMapper.getEnum("present-question-affirm-not3")));
        			}
        		} else {
        			if(isThirdPersonSingular) {
        				indicesStatementAffirmative3.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
        						ConstructionNameEnumMapper.getEnum("present-stmt-affirm-3")));
        			} else {
        				indicesStatementAffirmativeNot3.add(new TargetConstruction(tenseOccurrence.getStartIndex(), tenseOccurrence.getEndIndex(),
        						ConstructionNameEnumMapper.getEnum("present-stmt-affirm-not3")));
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
		ArrayList<TargetConstruction> relativeOccurrences = 
        		getConstructionsWithinSelectedPart(GrammaticalConstruction.PRONOUNS_RELATIVE, doc);
        ArrayList<TargetConstruction> indicesWhoOccurrences = new ArrayList<>();
        ArrayList<TargetConstruction> indicesWhichOccurrences = new ArrayList<>();
        ArrayList<TargetConstruction> indicesThatOccurrences = new ArrayList<>();
        ArrayList<TargetConstruction> indicesOtherOccurrences = new ArrayList<>();
        for(TargetConstruction relativeOccurrence : relativeOccurrences) {
        	String occurrenceText = doc.getText().substring(relativeOccurrence.getStartIndex(), relativeOccurrence.getEndIndex());
        	if(occurrenceText.equalsIgnoreCase("who")) {
        		indicesWhoOccurrences.add(new TargetConstruction(relativeOccurrence.getStartIndex(), relativeOccurrence.getEndIndex(),
        				ConstructionNameEnumMapper.getEnum("who")));
        	} else if(occurrenceText.equalsIgnoreCase("which")) {
        		indicesWhichOccurrences.add(new TargetConstruction(relativeOccurrence.getStartIndex(), relativeOccurrence.getEndIndex(),
        				ConstructionNameEnumMapper.getEnum("which")));
        	} else if(occurrenceText.equalsIgnoreCase("that")) {
        		indicesThatOccurrences.add(new TargetConstruction(relativeOccurrence.getStartIndex(), relativeOccurrence.getEndIndex(),
        				ConstructionNameEnumMapper.getEnum("that")));
        	} else {
        		indicesOtherOccurrences.add(new TargetConstruction(relativeOccurrence.getStartIndex(), relativeOccurrence.getEndIndex(),
        				ConstructionNameEnumMapper.getEnum("otherRelPron")));
        	}
        	
        }
		relevantConstructions.put("who", indicesWhoOccurrences);
		relevantConstructions.put("which", indicesWhichOccurrences);
		relevantConstructions.put("that", indicesThatOccurrences);
		relevantConstructions.put("otherRelPron", indicesOtherOccurrences);
        
		return relevantConstructions;
    }
    
}