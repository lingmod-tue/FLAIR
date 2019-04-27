package com.flair.shared.interop.messaging.client;

import com.flair.shared.interop.messaging.Message;

public class CmActiveOperationCancel implements Message.Payload {
	public CmActiveOperationCancel() {}

	@Override
	public String name() {
		return "CmActiveOperationCancel";
	}
	@Override
	public String desc() {
		return "";
	}
}
