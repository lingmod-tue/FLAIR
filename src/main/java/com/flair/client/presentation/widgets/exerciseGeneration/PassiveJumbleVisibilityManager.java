package com.flair.client.presentation.widgets.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.google.gwt.user.client.ui.Widget;

public class PassiveJumbleVisibilityManager extends VisibilityManager {

	public PassiveJumbleVisibilityManager(TaskItem taskItem) {
		super(taskItem);
	}
	
	@Override
	public ArrayList<Widget> getVisibleWidgets(int numberExercises) {
		ArrayList<Widget> visibleSettings = new ArrayList<Widget>();

		visibleSettings.add(taskItem.grpTenses);
		visibleSettings.add(taskItem.lblTensesSentences);
		addConstructionIfOccurs("TENSE_PRESENT_SIMPLE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPresentSimple, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_SIMPLE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkFutureSimple, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PROGRESSIVE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPresentProgressive, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PROGRESSIVE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPastProgressive, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_PROGRESSIVE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkFutureProgressive, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_PERFECT", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkFuturePerfect, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT_PROGRESSIVE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPresentPerfectProg, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PERFECT_PROGRESSIVE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPastPerfectProg, numberExercises);
		addConstructionIfOccurs("TENSE_FUTURE_PERFECT_PROGRESSIVE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkFuturePerfectProg, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_SIMPLE", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPastSimple, numberExercises);
		addConstructionIfOccurs("TENSE_PRESENT_PERFECT", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPresentPerfect, numberExercises);
		addConstructionIfOccurs("TENSE_PAST_PERFECT", ExerciseTopic.PASSIVE, 2, visibleSettings, taskItem.chkPastPerfect, numberExercises);

		return visibleSettings;
	}
}