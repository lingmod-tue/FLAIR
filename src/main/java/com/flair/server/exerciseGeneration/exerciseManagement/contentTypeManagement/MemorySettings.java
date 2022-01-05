package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.DocumentBasedExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.MemoryJsonManager;

public class MemorySettings extends ContentTypeSettings {

    public MemorySettings(String name) {
        super("memory.h5p", new MemoryJsonManager(), false, new DocumentBasedExerciseGenerator(), "H5P.XMemoryGame 0.1", 
        		name);
    }

}
