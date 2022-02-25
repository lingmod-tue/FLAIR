package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public class ExerciseTypeSpec {

	private String subtopic = null;
	private boolean ifClauseFirst = false;
	private boolean randomClauseOrder = false;
	private boolean targetIfClause = false;
	private boolean targetMainClause = false;
	private String feedbookType;
	
	public String getSubtopic() {
		return subtopic;
	}
	public boolean isIfClauseFirst() {
		return ifClauseFirst;
	}
	public boolean isRandomClauseOrder() {
		return randomClauseOrder;
	}
	public boolean isTargetIfClause() {
		return targetIfClause;
	}
	public boolean isTargetMainClause() {
		return targetMainClause;
	}
	public String getFeedbookType() {
		return feedbookType;
	}
	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}
	public void setIfClauseFirst(boolean ifClauseFirst) {
		this.ifClauseFirst = ifClauseFirst;
	}
	public void setRandomClauseOrder(boolean randomClauseOrder) {
		this.randomClauseOrder = randomClauseOrder;
	}
	public void setTargetIfClause(boolean targetIfClause) {
		this.targetIfClause = targetIfClause;
	}
	public void setTargetMainClause(boolean targetMainClause) {
		this.targetMainClause = targetMainClause;
	}
	public void setFeedbookType(String feedbookType) {
		this.feedbookType = feedbookType;
	}
	
}
