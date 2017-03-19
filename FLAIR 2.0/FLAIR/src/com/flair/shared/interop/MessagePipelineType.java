package com.flair.shared.interop;

/*
 * Supported message pipelines
 */
public enum MessagePipelineType
{
	PULL,			// client periodically polls the server for new messages
	PUSH			// server pushes messages to the client in realtime
}
