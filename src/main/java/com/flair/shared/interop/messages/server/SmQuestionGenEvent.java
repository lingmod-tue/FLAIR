package com.flair.shared.interop.messages.server;

import com.flair.shared.interop.QuestionDTO;
import com.flair.shared.interop.messages.BaseMessage;

import java.util.ArrayList;

public class SmQuestionGenEvent extends BaseMessage {
	public enum EventType {
		JOB_COMPLETE
	}

	private EventType event = EventType.JOB_COMPLETE;
	private ArrayList<QuestionDTO> generationResult = new ArrayList<>();

	public SmQuestionGenEvent() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SmQuestionGenEvent{" + identifier() + "}[");
		sb.append("event=" + event).append(" | ");
		sb.append("generatedQuestions=" + generationResult.size());
		return sb.append("]").toString();
	}

	public EventType getEvent() {
		return event;
	}
	public void setEvent(EventType event) {
		this.event = event;
	}
	public ArrayList<QuestionDTO> getGenerationResult() {
		return generationResult;
	}
	public void setGenerationResult(ArrayList<QuestionDTO> generationResult) {
		this.generationResult = generationResult;
	}
}
