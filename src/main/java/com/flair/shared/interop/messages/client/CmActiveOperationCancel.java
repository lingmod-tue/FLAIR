package com.flair.shared.interop.messages.client;

import com.flair.shared.interop.messages.BaseMessage;

public class CmActiveOperationCancel extends BaseMessage {
	public CmActiveOperationCancel() {}

	@Override
	public String toString() {
		return "CmActiveOperationCancel{" + identifier() + "}[]";
	}
}
