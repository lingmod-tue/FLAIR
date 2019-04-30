package com.flair.shared.interop.messaging.client;

import com.flair.shared.interop.messaging.Message;

public class CmActiveOperationCancel implements Message.Payload {
	private boolean activeOperationExpected = true;

	public CmActiveOperationCancel() {}

	public boolean getActiveOperationExpected() {
		return activeOperationExpected;
	}
	public void setActiveOperationExpected(boolean activeOperationExpected) {
		this.activeOperationExpected = activeOperationExpected;
	}
	@Override
	public String name() {
		return "CmActiveOperationCancel";
	}
	@Override
	public String desc() {
		return "";
	}
}
