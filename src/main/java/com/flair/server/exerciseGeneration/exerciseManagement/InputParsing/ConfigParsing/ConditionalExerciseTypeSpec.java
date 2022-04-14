package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public class ConditionalExerciseTypeSpec extends ExerciseTypeSpec {

	private boolean ifClauseFirst = false;
	private boolean targetIfClause = false;
	private boolean targetMainClause = false;
	private boolean randomTargetClause = false;
	
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
	
}
