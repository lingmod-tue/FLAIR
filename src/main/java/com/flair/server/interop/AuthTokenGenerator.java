package com.flair.server.interop;

import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerAuthenticationToken;

import java.util.UUID;

/*
 * Generates authentication tokens
 */
public final class AuthTokenGenerator {
	public static ServerAuthenticationToken create() {
		ServerAuthenticationToken out = new ServerAuthenticationToken();
		out.setUuid(UUID.randomUUID().toString());
		out.setStatus(AuthToken.Status.VALID);

		return out;
	}
}
