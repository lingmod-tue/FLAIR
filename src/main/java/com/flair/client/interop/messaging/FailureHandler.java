package com.flair.client.interop.messaging;

public interface FailureHandler {
	void onFailure(Throwable exception, String message);
}
