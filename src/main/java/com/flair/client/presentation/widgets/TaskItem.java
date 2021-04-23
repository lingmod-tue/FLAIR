package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;

public class TaskItem extends LocalizedComposite {
	private class CheckBoxItem {
		public CheckBoxItem(MaterialCheckBox widget, String constructionName) {
			this.widget = widget;
			this.constructionName = constructionName;
		}
		
		private MaterialCheckBox widget;
		private String constructionName;
		
		public MaterialCheckBox getWidget() {
			return widget;
		}
		
		public String getConstructionName() {
			return constructionName;
		}
	}
	

    interface TaskItemUiBinder extends UiBinder<Widget, TaskItem> {
    }

    private static TaskItemUiBinder ourUiBinder = GWT.create(TaskItemUiBinder.class);


    private static TaskItem.TaskItemLocalizationBinder localeBinder = GWT.create(TaskItem.TaskItemLocalizationBinder.class);

    interface TaskItemLocalizationBinder extends LocalizationBinder<TaskItem> {
    }

    
    @UiField
    MaterialDropDown drpTopic;
    @UiField
    MaterialDropDown drpType;
    @UiField
    MaterialDropDown drpQuiz;
    @UiField
    MaterialButton btnTopicDropdown;
    @UiField
    MaterialButton btnQuizDropdown;
    @UiField
    MaterialButton btnTypeDropdown;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnDelete;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnSelectDocumentPart;
    @UiField
    MaterialLabel lblTopicPres;
    @UiField
    MaterialLabel lblTopicPass;
    @UiField
    MaterialLabel lblTopicComp;
    @UiField
    MaterialLabel lblTopicRel;
    @UiField
    MaterialLabel lblTopicCond;
    @UiField
    MaterialLabel lblTopicPast;
    @UiField
    MaterialLabel lblTypeFib;
    @UiField
    MaterialLabel lblTypeSelect;
    @UiField
    MaterialLabel lblTypeMark;
    @UiField
    MaterialLabel lblTypeDrag;
    @UiField
    MaterialLabel lblQuizNone;
    @UiField
    MaterialLabel lblQuiz1;
    @UiField
    MaterialLabel lblSettings;
    @UiField
    MaterialLabel lblNumberExercises;
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
    MaterialLabel lblTensesSentences;
    @UiField
    MaterialLabel lblTensesWords;
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
    
    public TaskItem() {    	
        initWidget(ourUiBinder.createAndBindUi(this));
        initLocale(localeBinder.bind(this));
        this.initHandlers();
        settingsWidgets = new Widget[] {
        		grpBrackets, chkBracketsLemma, chkBracketsConditional, chkBracketsPos, chkBracketsForm, chkBracketsWill, 
        		chkBracketsSentenceType, chkBracketsTense, chkBracketsActiveSentence, chkBracketsKeywords, 
        		grpDistractors, chkDistractorsOtherForm, chkDistractorsOtherVariant, chkDistractorsOtherPast, chkDistractorsOtherTense, 
        		chkDistractorsIncorrectForms, chkDistractorsWrongConditional, chkDistractorsWrongClause, chkDistractorsWrongSuffixUse,
        		chkDistractorsWrongSuffix, grpPos, grpCompForm, grpForms, grpVerbPerson, grpVerbForms, grpSentenceTypes, grpTenses,
        		lblTensesSentences, chkPresentSimple, chkFutureSimple, chkPresentProgressive, chkPastProgressive, chkFutureProgressive, 
        		chkFuturePerfect, chkPresentPerfectProg, chkPastPerfectProg, chkFuturePerfectProg, lblTensesWords, grpCondTypes, 
        		grpClauses, grpScope, grpPronouns, grpVerbSplitting, grpTargetWords, grpSentTypes
        };
    	setExerciseSettingsVisibilities();
    }

    /**
     * Initializes all handlers.
     */
    private void initHandlers() {
    	lblTopicPres.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTopicDropdown, "Present");
			}
    	});
    	
    	lblTopicPast.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTopicDropdown, "Past");
			}
    	});
    	
    	lblTopicCond.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTopicDropdown, "'if'");
			}
    	});
    	
    	lblTopicComp.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTopicDropdown, "Compare");
			}
    	});
    	
    	lblTopicRel.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTopicDropdown, "Relatives");
			}
    	});
    	
    	lblTopicPass.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTopicDropdown, "Passive");
			}
    	});
    	
    	lblTypeFib.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTypeDropdown, "FiB");
			}
    	});
    	
    	lblTypeSelect.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTypeDropdown, "Select");
			}
    	});
    	
    	lblTypeMark.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTypeDropdown, "Mark");
			}
    	});
    	
    	lblTypeDrag.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				handleDropdownSelection(btnTypeDropdown, "Drag");
			}
    	});
    	
    	lblQuizNone.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
    	    	btnQuizDropdown.setText("Quiz");
    	    	btnQuizDropdown.setFontWeight(FontWeight.BOLD);
			}
    	});
    	
    	lblQuiz1.addMouseDownHandler(new MouseDownHandler() 
    	{
			@Override
			public void onMouseDown(MouseDownEvent event) {
				btnQuizDropdown.setText("Quiz 1");
				btnQuizDropdown.setFontWeight(FontWeight.NORMAL);
			}
    	});
    	
    	rbtSingleTask.addValueChangeHandler(e -> setExerciseSettingsVisibilities());
    	rbtPerSentence.addValueChangeHandler(e -> setExerciseSettingsVisibilities());

    }
    
    
    /**
     * Sets the text of the selection button and the font weight when an item has been selected.
     * We need to use click handlers on the selection items instead of binding to the selection event
     * because that binding does not work.
     * @param btn 	The button of the dropdown whose text we want to set.
     * @param text 	The text we want to set.
     */
    private void handleDropdownSelection(MaterialButton btn, String text) {
    	btn.setText(text);
    	btn.setFontWeight(FontWeight.NORMAL);
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
     * The widgets whose visibility depends on the settings.
     */
    final Widget[] settingsWidgets;
    
    /**
     * Shows only those exercise parameters that are relevant for the topic and exercise type and hides all others
     */
    private void setExerciseSettingsVisibilities() {  
    	String topic = btnTopicDropdown.getText();
    	String exerciseType = btnTypeDropdown.getText();
    	ArrayList<Widget> visibleSettings = new ArrayList<Widget>();
    	
    	if(topic.equals("Passive")) {
    		if(exerciseType.equals("FiB")) {
    			visibleSettings.add(grpBrackets);
    			visibleSettings.add(chkBracketsSentenceType);
    			visibleSettings.add(chkBracketsTense);
    			visibleSettings.add(chkBracketsActiveSentence);
    			visibleSettings.add(chkBracketsKeywords);
    			visibleSettings.add(grpTenses);
    			visibleSettings.add(lblTensesSentences);
    			visibleSettings.add(chkPresentSimple);
    			visibleSettings.add(chkFutureSimple);
    			visibleSettings.add(chkPresentProgressive);
    			visibleSettings.add(chkPastProgressive);
    			visibleSettings.add(chkFutureProgressive);
    			visibleSettings.add(chkFuturePerfect);
    			visibleSettings.add(chkPresentPerfectProg);
    			visibleSettings.add(chkPastPerfectProg);
    			visibleSettings.add(chkFuturePerfectProg);
    			visibleSettings.add(grpSentTypes);
    		} else if(exerciseType.equals("Select")) {
    			
    		} else if(exerciseType.equals("Mark")) {
    			//visibleSettings.add(grpTargetWords);
    		} else if(exerciseType.equals("Drag")) {
    			visibleSettings.add(grpTenses);
    			visibleSettings.add(lblTensesSentences);
    			visibleSettings.add(chkPresentSimple);
    			visibleSettings.add(chkFutureSimple);
    			visibleSettings.add(chkPresentProgressive);
    			visibleSettings.add(chkPastProgressive);
    			visibleSettings.add(chkFutureProgressive);
    			visibleSettings.add(chkFuturePerfect);
    			visibleSettings.add(chkPresentPerfectProg);
    			visibleSettings.add(chkPastPerfectProg);
    			visibleSettings.add(chkFuturePerfectProg);
    			visibleSettings.add(grpVerbSplitting);
    		}
    	} else if(topic.equals("Relatives")) {
    		if(exerciseType.equals("FiB")) {
    			visibleSettings.add(grpPronouns);
    		} else if(exerciseType.equals("Select")) {
    			
    		} else if(exerciseType.equals("Mark")) {
    			visibleSettings.add(grpPronouns);
    		} else if(exerciseType.equals("Drag")) {
    			visibleSettings.add(grpScope);
    		}
    	} else if(topic.equals("Present")) {
    		if(exerciseType.equals("FiB")) {
    			visibleSettings.add(grpBrackets);
    			visibleSettings.add(chkBracketsLemma);   
    			visibleSettings.add(grpVerbPerson);  
    			visibleSettings.add(grpSentenceTypes);  
    		} else if(exerciseType.equals("Select")) {
    			visibleSettings.add(grpDistractors);    
    			visibleSettings.add(chkDistractorsWrongSuffixUse);  
    			visibleSettings.add(chkDistractorsWrongSuffix);  
    			visibleSettings.add(grpVerbPerson);  
    			visibleSettings.add(grpSentenceTypes);  
    		} else if(exerciseType.equals("Mark")) {
    			visibleSettings.add(grpVerbPerson);   
    		} else if(exerciseType.equals("Drag")) {
    			
    		}
    	} else if(topic.equals("Past")) {
    		if(exerciseType.equals("FiB")) {
    			visibleSettings.add(grpBrackets);
    			visibleSettings.add(chkBracketsLemma);
    			visibleSettings.add(chkBracketsTense);
    			visibleSettings.add(grpSentenceTypes);
    			visibleSettings.add(grpTenses);
    			visibleSettings.add(lblTensesWords);
    			visibleSettings.add(grpVerbForms);
    		} else if(exerciseType.equals("Select")) {
    			visibleSettings.add(grpDistractors);
    			visibleSettings.add(chkDistractorsOtherPast);
    			visibleSettings.add(chkDistractorsOtherTense);
    			visibleSettings.add(chkDistractorsIncorrectForms);
    			visibleSettings.add(grpSentenceTypes); 
    			visibleSettings.add(grpTenses);
    			visibleSettings.add(lblTensesWords);
    			visibleSettings.add(grpVerbForms);
    		} else if(exerciseType.equals("Mark")) {
    			visibleSettings.add(grpTenses);
    			visibleSettings.add(lblTensesWords);
    			visibleSettings.add(grpVerbForms);
    		} else if(exerciseType.equals("Drag")) {
    			visibleSettings.add(grpTenses);
    			visibleSettings.add(lblTensesWords);
    			visibleSettings.add(grpVerbForms);
    		}
    	} else if(topic.equals("'if'")) {
    		if(exerciseType.equals("FiB")) {
    			visibleSettings.add(grpBrackets);
    			visibleSettings.add(chkBracketsLemma);
    			visibleSettings.add(chkBracketsConditional);
    			visibleSettings.add(chkBracketsWill);
    			visibleSettings.add(grpCondTypes);
    			visibleSettings.add(grpClauses);
    		} else if(exerciseType.equals("Select")) {
    			visibleSettings.add(grpDistractors);
    			visibleSettings.add(chkDistractorsWrongConditional);
    			visibleSettings.add(chkDistractorsWrongClause);
    			visibleSettings.add(grpCondTypes);
    			visibleSettings.add(grpClauses);
    		} else if(exerciseType.equals("Mark")) {
    			
    		} else if(exerciseType.equals("Drag")) {
    			visibleSettings.add(grpCondTypes);
    			if(rbtSingleTask.getValue()) {
    				visibleSettings.add(grpClauses);
    			}
    			visibleSettings.add(grpScope);
    		}
    	}else if(topic.equals("Compare")) {
			if(exerciseType.equals("FiB")) {
    			visibleSettings.add(grpBrackets);
    			visibleSettings.add(chkBracketsLemma);
    			visibleSettings.add(chkBracketsPos);
    			visibleSettings.add(chkBracketsForm);
    			visibleSettings.add(grpPos);
    			visibleSettings.add(grpCompForm);
    			visibleSettings.add(grpForms);
    		} else if(exerciseType.equals("Select")) {
    			visibleSettings.add(grpDistractors);
    			visibleSettings.add(chkDistractorsOtherForm);
    			visibleSettings.add(chkDistractorsOtherVariant);
    			visibleSettings.add(chkDistractorsIncorrectForms);
    			visibleSettings.add(grpPos);
    			visibleSettings.add(grpCompForm);
    			visibleSettings.add(grpForms);
    		} else if(exerciseType.equals("Mark")) {
    			visibleSettings.add(grpPos);
    			visibleSettings.add(grpCompForm);
    			visibleSettings.add(grpForms);
    		} else if(exerciseType.equals("Drag")) {
    			visibleSettings.add(grpPos);
    			visibleSettings.add(grpCompForm);
    			visibleSettings.add(grpForms);
    		}
    	}
    	    	
    	setSettingsVisibility(visibleSettings);  	
    	
    	setNumberExercisesText();
    }
    
    /**
     * The occurrences of constructions relevant to exercise generation in the current document.
     */
    private HashMap<String, Integer> relevantConstructions = null;

    /**
     * Calculates the number of exercises that can be generated for the current document with the current parameter settings.
     * @return The number of exercises that can be generated.
     */
    private int calculateNumberOfExercises() {
    	String topic = btnTopicDropdown.getText();
    	String exerciseType = btnTypeDropdown.getText();
    	
    	ArrayList<String> constructionsToConsider = new ArrayList<String>();
    	
    	if(topic.equals("Passive")) {
    		if(exerciseType.equals("FiB")) {
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkScopeActive, "active"));
    				add(new CheckBoxItem(chkScopePassive, "passive"));
    			}};
    			ArrayList<CheckBoxItem> secondLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkPresentSimple, "presentSimple"));
    				add(new CheckBoxItem(chkFutureSimple, "futureSimple"));
    				add(new CheckBoxItem(chkPresentProgressive, "presentProgressive"));
    				add(new CheckBoxItem(chkPastProgressive, "pastProgressive"));
    				add(new CheckBoxItem(chkFutureProgressive, "futureProgressive"));
    				add(new CheckBoxItem(chkFuturePerfect, "futurePerfect"));
    				add(new CheckBoxItem(chkPresentPerfectProg, "presentPerfProg"));
    				add(new CheckBoxItem(chkPastPerfectProg, "pastPerfProg"));
    				add(new CheckBoxItem(chkFuturePerfectProg, "futurePerfProg"));
    				add(new CheckBoxItem(chkPastSimple, "pastSimple"));
    				add(new CheckBoxItem(chkPresentPerfect, "presentPerfect"));
    				add(new CheckBoxItem(chkPastPerfect, "pastPerfect"));
    			}};
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    				add(secondLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);    			
    		} else if(exerciseType.equals("Drag")) {
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkScopePassive, "passive"));
    			}};
    			ArrayList<CheckBoxItem> secondLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkPresentSimple, "presentSimple"));
    				add(new CheckBoxItem(chkFutureSimple, "futureSimple"));
    				add(new CheckBoxItem(chkPresentProgressive, "presentProgressive"));
    				add(new CheckBoxItem(chkPastProgressive, "pastProgressive"));
    				add(new CheckBoxItem(chkFutureProgressive, "futureProgressive"));
    				add(new CheckBoxItem(chkFuturePerfect, "futurePerfect"));
    				add(new CheckBoxItem(chkPresentPerfectProg, "presentPerfProg"));
    				add(new CheckBoxItem(chkPastPerfectProg, "pastPerfProg"));
    				add(new CheckBoxItem(chkFuturePerfectProg, "futurePerfProg"));
    				add(new CheckBoxItem(chkPastSimple, "pastSimple"));
    				add(new CheckBoxItem(chkPresentPerfect, "presentPerfect"));
    				add(new CheckBoxItem(chkPastPerfect, "pastPerfect"));
    			}};
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    				add(secondLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);    	
    		}
    	} else if(topic.equals("Relatives")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Mark")) {
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkScopePassive, "who"));
    				add(new CheckBoxItem(chkScopePassive, "which"));
    				add(new CheckBoxItem(chkScopePassive, "that"));
    				add(new CheckBoxItem(chkScopePassive, "otherRelPron"));
    			}};
    			    			
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);  
    		} else if(exerciseType.equals("Drag")) {
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(null, "who"));
    				add(new CheckBoxItem(null, "which"));
    				add(new CheckBoxItem(null, "that"));
    				add(new CheckBoxItem(null, "otherRelPron"));
    			}};   			 			
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);  
    			
    			//TODO maybe also offer choosing the pronoun for exercise per sentence
    		}
    	} else if(topic.equals("Present")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select")) {   
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(null, "present"));
    			}};
    			ArrayList<CheckBoxItem> secondLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkQuestions, "question"));
    				add(new CheckBoxItem(chkStatements, "stmt"));
    			}};
    			ArrayList<CheckBoxItem> thirdLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkNegatedSent, "neg"));
    				add(new CheckBoxItem(chkAffirmativeSent, "affirm"));
    			}};
    			
    			ArrayList<CheckBoxItem> fourthLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chk3Pers, "3"));
    				add(new CheckBoxItem(chkNot3Pers, "not3"));
    			}};
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    				add(secondLevelConstructions);
    				add(thirdLevelConstructions);
    				add(fourthLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);
    		} else if(exerciseType.equals("Mark")) {  
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(null, "present"));
    			}};
    			ArrayList<CheckBoxItem> secondLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(null, "question"));
    				add(new CheckBoxItem(null, "stmt"));
    			}};
    			ArrayList<CheckBoxItem> thirdLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(null, "neg"));
    				add(new CheckBoxItem(null, "affirm"));
    			}};
    			
    			ArrayList<CheckBoxItem> fourthLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chk3Pers, "3"));
    				add(new CheckBoxItem(chkNot3Pers, "not3"));
    			}};
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    				add(secondLevelConstructions);
    				add(thirdLevelConstructions);
    				add(fourthLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);   			
    		}
    	} else if(topic.equals("Past")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select")) {
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkPastSimple, "pastSimple"));
    				add(new CheckBoxItem(chkPresentPerfect, "presentPerfect"));
    				add(new CheckBoxItem(chkPastPerfect, "pastPerfect"));
    			}};    			
    			ArrayList<CheckBoxItem> secondLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkQuestions, "question"));
    				add(new CheckBoxItem(chkStatements, "stmt"));
    			}};
    			ArrayList<CheckBoxItem> thirdLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkNegatedSent, "neg"));
    				add(new CheckBoxItem(chkAffirmativeSent, "affirm"));
    			}};
    			ArrayList<CheckBoxItem> fourthLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkRegularVerbs, "reg"));
    				add(new CheckBoxItem(chkIrregularVerbs, "irreg"));
    			}};
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    				add(secondLevelConstructions);
    				add(thirdLevelConstructions);
    				add(fourthLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);    			
    		} else if(exerciseType.equals("Mark") || exerciseType.equals("Drag")) {
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkPastSimple, "pastSimple"));
    				add(new CheckBoxItem(chkPresentPerfect, "presentPerfect"));
    				add(new CheckBoxItem(chkPastPerfect, "pastPerfect"));
    			}};    			
    			ArrayList<CheckBoxItem> secondLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(null, "question"));
    				add(new CheckBoxItem(null, "stmt"));
    			}};
    			ArrayList<CheckBoxItem> thirdLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(null, "neg"));
    				add(new CheckBoxItem(null, "affirm"));
    			}};
    			ArrayList<CheckBoxItem> fourthLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkRegularVerbs, "reg"));
    				add(new CheckBoxItem(chkIrregularVerbs, "irreg"));
    			}};
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    				add(secondLevelConstructions);
    				add(thirdLevelConstructions);
    				add(fourthLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);    			
    		} 
    	} else if(topic.equals("'if'")) {
    		if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Drag")) {
    			ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkscopeType1, "condReal"));
    				add(new CheckBoxItem(chkscopeType2, "condUnreal"));
    			}};    			
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);    	
    		} 
    	} else if(topic.equals("Compare")) {
			if(exerciseType.equals("FiB") || exerciseType.equals("Select") || exerciseType.equals("Mark") || exerciseType.equals("Drag")) {
				ArrayList<CheckBoxItem> firstLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkPosAdj, "adj"));
    				add(new CheckBoxItem(chkPosAdv, "adv"));
    			}};    			
    			ArrayList<CheckBoxItem> secondLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkFormComparatives, "comp"));
    				add(new CheckBoxItem(chkFormSuperlatives, "sup"));
    			}};
    			ArrayList<CheckBoxItem> thirdLevelConstructions = new ArrayList<CheckBoxItem>(){{
    				add(new CheckBoxItem(chkFormSynthetic, "syn"));
    				add(new CheckBoxItem(chkFormAnalytic, "ana"));
    			}};
    			
    			ArrayList<ArrayList<CheckBoxItem>> constructionLevels = new ArrayList<ArrayList<CheckBoxItem>>() {{
    				add(firstLevelConstructions);
    				add(secondLevelConstructions);
    				add(thirdLevelConstructions);
    			}};
    			
    			constructionsToConsider = getConstructionNamesFromSettings(constructionLevels);  
    		}
    	}
    	
    	int nExercises = 0;
    	
    	for(String constructionToConsider : constructionsToConsider) {
    		nExercises += relevantConstructions.get(constructionToConsider);
    	}
    	    	
    	return nExercises;
    }
    
    /**
     * Creates all combinations of constructions for the checked settings.
     * @param 	constructionLevels A list of the individual constructions making up a more complex (more detailed) construction. 
     * 			Each construction consists of a list of possible values.
     * @return	The list of construction names corresponding to relevantConstructions names
     */
    private ArrayList<String> getConstructionNamesFromSettings(ArrayList<ArrayList<CheckBoxItem>> constructionLevels) {
		ArrayList<String> initialConstructionNames = new ArrayList<String>();

    	if(constructionLevels.size() > 0) {
    		for(CheckBoxItem firstLevelConstruction : constructionLevels.get(0)) {
    			if(firstLevelConstruction.getWidget().getValue()) {
    				initialConstructionNames.add(firstLevelConstruction.getConstructionName());
    			}
    		}
    		
    		if(constructionLevels.size() > 1) {
    			for(int i = 1; i < constructionLevels.size(); i++) {
    				if(initialConstructionNames.size() > 0) {
    					ArrayList<String> currentConstructionNames = new ArrayList<String>();
    					for(CheckBoxItem currentLevelConstruction : constructionLevels.get(i)) {
    						if(currentLevelConstruction.getWidget() == null || currentLevelConstruction.getWidget().getValue()) {
    							for(String initialConstructionName : initialConstructionNames) {
    			    				currentConstructionNames.add(initialConstructionName + "-" + currentLevelConstruction.getConstructionName());
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
     * Updates the relevant constructions and recalculates the possible exercises when a new document has been selected.
     * @param relevantConstructions	The occurrences relevant constructions for exercises generation in the new document.
     */
    public void updateTask(HashMap<String, Integer> relevantConstructions) {
    	this.relevantConstructions = relevantConstructions;
    	setNumberExercisesText();
    }
    
    /**
     * Sets the text on whether and  how many exercises can be generated according to the selected topic and exercise type,
     * the individual parameter settings and the selected document.
     */
    private void setNumberExercisesText() {
    	String topic = btnTopicDropdown.getText();
    	String exerciseType = btnTypeDropdown.getText();
    	   	
		if(exerciseType.equals("Exercise Type") || topic.equals("Topic")) {
			lblNumberExercises.setText("Select a grammar topic and an exercise type to enable exercise generation.");
    		lblNumberExercises.setTextColor(Color.GREY);
		} else if((topic.equals("Passive") || topic.equals("Relatives")) && exerciseType.equals("Select") || 
				topic.equals("'if'") && exerciseType.equals("Mark") || topic.equals("Present") && exerciseType.equals("Drag")) {
    		lblNumberExercises.setText("No exercises can be generated for the current settings.");
    		lblNumberExercises.setTextColor(Color.RED);
		} else {
    		int numberOfExercises = calculateNumberOfExercises();
    		if(numberOfExercises == 0) {
    			lblNumberExercises.setText("No exercises can be generated for the current settings.");
        		lblNumberExercises.setTextColor(Color.RED);
    		} else {
        		lblNumberExercises.setText(numberOfExercises + " exercises can be generated for the current settings.");
        		lblNumberExercises.setTextColor(Color.BLACK);
    		}
    	}
    }
    
}