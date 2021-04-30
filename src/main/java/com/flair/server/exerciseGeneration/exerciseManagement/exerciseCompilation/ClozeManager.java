package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import com.flair.shared.exerciseGeneration.ExerciseSettings;

public class ClozeManager {

    public void prepareBlanks(ExerciseSettings exerciseSettings) {
        //TODO: in-place modification of construction indices (and bracketsText if relevant) for each Construction
        if(exerciseSettings.getContentType().equals("FiB")) {
            for(int i = 0; i < exerciseSettings.getConstructions().size(); i++) {
                exerciseSettings.getConstructions().get(i).setBracketsText("(Test)");
            }
        }
    }
}
