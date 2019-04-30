package com.flair.client.model;

interface ProcessCompletionEventHandler {
	void handle(ProcessData d, boolean success);
}
