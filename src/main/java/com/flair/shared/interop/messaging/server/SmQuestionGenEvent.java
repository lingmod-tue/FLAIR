package com.flair.shared.interop.messaging.server;

import com.flair.shared.interop.dtos.QuestionDTO;
import com.flair.shared.interop.messaging.Message;

import java.util.ArrayList;

public class SmQuestionGenEvent implements Message.Payload {
	public enum EventType {
		JOB_COMPLETE
	}

	private EventType event = EventType.JOB_COMPLETE;
	private ArrayList<QuestionDTO> generationResult = new ArrayList<>();

	public SmQuestionGenEvent() {}

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

	@Override
	public String name() {
		return "SmQuestionGenEvent";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		sb.append("event=" + event).append(" | ");
		sb.append("generatedQuestions=" + generationResult.size());
		return sb.toString();
	}
}
