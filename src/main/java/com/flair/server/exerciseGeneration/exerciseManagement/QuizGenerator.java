package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;

import edu.stanford.nlp.util.Pair;

public class QuizGenerator extends ExerciseGenerator {

    @Override
    public byte[] generateExercise(ContentTypeSettings settings, ArrayList<Pair<String, byte[]>> resources) {
        ArrayList<JsonComponents> exerciseComponents = new ArrayList<>();
        for(ContentTypeSettings taskSettings : ((QuizSettings)settings).getExercises()) {
            SimpleExerciseGenerator generator = new SimpleExerciseGenerator();
            JsonComponents exercise = generator.prepareExercise(taskSettings);
            if(exercise != null) {
            	exerciseComponents.add(exercise);
            }
        }

        return createH5pPackage(settings, exerciseComponents, resources);
    }

}