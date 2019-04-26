package com.flair.shared.interop.messages.client;

import com.flair.shared.interop.messages.BaseMessage;

public class CmSessionInitialize extends BaseMessage {
	public CmSessionInitialize() {}

	@Override
	public String toString() {
		return "CmSessionInitialize{" + identifier() + "}[]";
	}
}
