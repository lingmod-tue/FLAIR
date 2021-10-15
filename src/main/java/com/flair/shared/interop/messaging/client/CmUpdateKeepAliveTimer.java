package com.flair.shared.interop.messaging.client;

import com.flair.shared.interop.messaging.Message;

public class CmUpdateKeepAliveTimer implements Message.Payload {

	public CmUpdateKeepAliveTimer() {}

	@Override
	public String name() {
		return "CmUpdateKeepAliveTimer";
	}
	@Override
	public String desc() {
		return "Keep alive";
	}
}
