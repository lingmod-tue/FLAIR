package com.flair.shared.interop.messages.client;

import com.flair.shared.interop.messages.BaseMessage;

public class CmSessionTeardown extends BaseMessage {
	public CmSessionTeardown() {}

	@Override
	public String toString() {
		return "CmSessionTeardown{" + identifier() + "}[]";
	}
}
