package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public class ConditionalExerciseTypeSpec extends ExerciseTypeSpec {

	private boolean ifClauseFirst = false;
	private boolean targetIfClause = false;
	private boolean targetMainClause = false;
	private boolean randomTargetClause = false;
	private boolean randomClauseOrder = false;
		
	public ConditionalExerciseTypeSpec(String feedbookType, boolean ifClauseFirst,
			boolean targetIfClause, boolean targetMainClause, boolean randomTargetClause, boolean randomClauseOrder) {
		super(feedbookType);
		this.ifClauseFirst = ifClauseFirst;
		this.targetIfClause = targetIfClause;
		this.targetMainClause = targetMainClause;
		this.randomTargetClause = randomTargetClause;
		this.setRandomClauseOrder(randomClauseOrder);
	}

	public ConditionalExerciseTypeSpec() {
		super();
	}
	
	public boolean isIfClauseFirst() {
		return ifClauseFirst;
	}
	public boolean isTargetIfClause() {
		return targetIfClause;
	}
	public boolean isTargetMainClause() {
		return targetMainClause;
	}
	public void setIfClauseFirst(boolean ifClauseFirst) {
		this.ifClauseFirst = ifClauseFirst;
	}
	public void setTargetIfClause(boolean targetIfClause) {
		this.targetIfClause = targetIfClause;
	}
	public void setTargetMainClause(boolean targetMainClause) {
		this.targetMainClause = targetMainClause;
	}
	public boolean isRandomTargetClause() {
		return randomTargetClause;
	}
	public void setRandomTargetClause(boolean randomTargetClause) {
		this.randomTargetClause = randomTargetClause;
	}

	public boolean isRandomClauseOrder() {
		return randomClauseOrder;
	}

	public void setRandomClauseOrder(boolean randomClauseOrder) {
		this.randomClauseOrder = randomClauseOrder;
	}
	
}
