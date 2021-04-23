package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.dtos.RankableDocument.ConstructionRange;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
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
    
    
    
    private static final String DOC_TEXT_PREVIEW_ID = "docTextPreview";

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
        addTask();
    }
    
    private void addTask() {
        wdgtTasks.add(new TaskItem());
    }
    
    private HashMap<String, Integer> relevantConstructions = null;

    /**
     * Calculates the occurrences of constructions in the combinations relevant to exercise generation.
     */
    public void initConstructionsOccurrences() {
        RankableDocument doc = documentPreviewPane.getCurrentlyPreviewedDocument().getDocument();
        
        relevantConstructions = new HashMap<String, Integer>();
        
        //non-combined constructions
		relevantConstructions.put("adj-comp-syn", doc.getConstructionOccurrences(GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT).size());
		relevantConstructions.put("adj-sup-syn", doc.getConstructionOccurrences(GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_SHORT).size());
		relevantConstructions.put("adj-comp-ana", doc.getConstructionOccurrences(GrammaticalConstruction.ADJECTIVE_COMPARATIVE_LONG).size());
		relevantConstructions.put("adj-sup-ana", doc.getConstructionOccurrences(GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_LONG).size());
		relevantConstructions.put("adv-comp-syn", doc.getConstructionOccurrences(GrammaticalConstruction.ADVERB_COMPARATIVE_SHORT).size());
		relevantConstructions.put("adv-sup-syn", doc.getConstructionOccurrences(GrammaticalConstruction.ADVERB_SUPERLATIVE_SHORT).size());
		relevantConstructions.put("adv-comp-ana", doc.getConstructionOccurrences(GrammaticalConstruction.ADVERB_COMPARATIVE_LONG).size());
		relevantConstructions.put("adv-sup-ana", doc.getConstructionOccurrences(GrammaticalConstruction.ADVERB_SUPERLATIVE_LONG).size());
		relevantConstructions.put("condReal", doc.getConstructionOccurrences(GrammaticalConstruction.CONDITIONALS_REAL).size());
		relevantConstructions.put("condUnreal", doc.getConstructionOccurrences(GrammaticalConstruction.CONDITIONALS_UNREAL).size());
        	
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
        
    	ArrayList<? extends ConstructionRange> passiveOccurrences = doc.getConstructionOccurrences(GrammaticalConstruction.PASSIVE_VOICE);
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<? extends ConstructionRange> tenseOccurrences = doc.getConstructionOccurrences(tenseConstruction);
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
        
        ArrayList<ConstructionRange> negationOccurrences = new ArrayList<ConstructionRange>();
        negationOccurrences.addAll(doc.getConstructionOccurrences(GrammaticalConstruction.NEGATION_NOT));
        negationOccurrences.addAll(doc.getConstructionOccurrences(GrammaticalConstruction.NEGATION_NT));
        ArrayList<? extends ConstructionRange> questionOccurrences = doc.getConstructionOccurrences(GrammaticalConstruction.QUESTIONS_DIRECT);
        ArrayList<? extends ConstructionRange> irregularOccurrences = doc.getConstructionOccurrences(GrammaticalConstruction.VERBS_IRREGULAR);

        // past tense combinations
        for(GrammaticalConstruction tenseConstruction : tenseConstructions) {
        	ArrayList<? extends ConstructionRange> tenseOccurrences = doc.getConstructionOccurrences(tenseConstruction);
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
        ArrayList<? extends ConstructionRange> presentOccurrences = doc.getConstructionOccurrences(GrammaticalConstruction.TENSE_PRESENT_SIMPLE);
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
		ArrayList<? extends ConstructionRange> relativeOccurrences = doc.getConstructionOccurrences(GrammaticalConstruction.CLAUSE_RELATIVE);
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
		
		for(Widget task : wdgtTasks.getChildren()) {
			if(task instanceof TaskItem) {
				((TaskItem)task).updateTask(relevantConstructions);
			}
		}
    }


}