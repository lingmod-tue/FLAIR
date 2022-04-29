package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public class RelativeExerciseTypeSpec extends ExerciseTypeSpec {

	private boolean practiceContactClauses = false;
	private boolean clause1First = true;
	private boolean randomClauseOrder = false;
		
	public RelativeExerciseTypeSpec(String feedbookType, boolean practiceContactClauses) {
		super(feedbookType);
		this.setPracticeContactClauses(practiceContactClauses);
	}
	
	public RelativeExerciseTypeSpec(String feedbookType) {
		super(feedbookType);
	}

	public RelativeExerciseTypeSpec() {
		super();
	}

	public boolean isPracticeContactClauses() {
		return practiceContactClauses;
	}

	public void setPracticeContactClauses(boolean practiceContactClauses) {
		this.practiceContactClauses = practiceContactClauses;
	}

	public boolean isClause1First() {
		return clause1First;
	}

	public void setClause1First(boolean clause1First) {
		this.clause1First = clause1First;
	}

	public boolean isRandomClauseOrder() {
		return randomClauseOrder;
	}

	public void setRandomClauseOrder(boolean randomClauseOrder) {
		this.randomClauseOrder = randomClauseOrder;
	}
	
	
}
