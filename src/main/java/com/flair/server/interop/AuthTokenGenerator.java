package com.flair.server.interop;

import java.util.UUID;

import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerAuthenticationToken;

/*
 * Generates authentication tokens
 */
public final class AuthTokenGenerator
{
	public static ServerAuthenticationToken create()
	{
		ServerAuthenticationToken out = new ServerAuthenticationToken();
		out.setUuid(UUID.randomUUID().toString());
		out.setStatus(AuthToken.Status.VALID);
		
		return out;
	}
}
