
package com.flair.shared.exerciseGeneration;

public enum ExerciseType {
	FIB("FiB"),
	SINGLE_CHOICE("Select"),
	MARK("Mark"),
	DRAG_SINGLE("SingleDrag"),
	DRAG_MULTI("MultiDrag"),
	MEMORY("Memory"),
	JUMBLED_SENTENCES("JumbledSentences");
	
	private final String name;       

    private ExerciseType(String name) {
        this.name = name;
    }

    public String toString() {
       return this.name;
    }
    
	public static ExerciseType getEnum(String name) {
		switch(name) {
		case "FiB":
			return ExerciseType.FIB;
		case "Select":
			return ExerciseType.SINGLE_CHOICE;
		case "Mark":
			return ExerciseType.MARK;
		case "SingleDrag":
			return ExerciseType.DRAG_SINGLE;
		case "MultiDrag":
			return ExerciseType.DRAG_MULTI;
		case "Memory":
			return ExerciseType.MEMORY;
		case "Jumble":
			return ExerciseType.JUMBLED_SENTENCES;
			default:
				throw new IllegalArgumentException();
		}
	}
}

