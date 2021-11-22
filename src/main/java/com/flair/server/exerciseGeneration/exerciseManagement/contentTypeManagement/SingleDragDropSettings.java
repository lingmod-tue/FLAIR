package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.SimpleExerciseXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.DragJsonManager;

public class SingleDragDropSettings extends ContentTypeSettings {

    public SingleDragDropSettings(String name) {
        super("drag_the_words_1task.h5p", new DragJsonManager(), true, new SimpleExerciseGenerator(), 
        		"H5P.XXDragText 0.1", name, new SimpleExerciseXmlManager());
    }

}
