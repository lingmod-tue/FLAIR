package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.CategorizeXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.DragJsonManager;

public class CategorizeSettings extends ContentTypeSettings {

    public CategorizeSettings(String name) {
        super("drag_the_words.h5p", new DragJsonManager(), true, new SimpleExerciseGenerator(), "H5P.XDragText 1.1", 
        		name, new CategorizeXmlManager());
    }

}
