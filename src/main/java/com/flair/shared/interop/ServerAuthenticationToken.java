package com.flair.shared.interop;

import java.io.Serializable;

/*
 * Authentication/identification token for client-server interop
 */
public class ServerAuthenticationToken implements AuthToken, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 411542448497805998L;
	
	private String				uuid;		// randomly generated
	private AuthToken.Status	status;
	
	public ServerAuthenticationToken()
	{
		uuid = "";
		status = AuthToken.Status.INVALID_SERVER_ERROR;
	}
	
	@Override
	public AuthToken.Status getStatus() {
		return status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setStatus(AuthToken.Status status) {
		this.status = status;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof ServerAuthenticationToken))
		{
			return false;
		}
		ServerAuthenticationToken other = (ServerAuthenticationToken) obj;
		if (uuid == null)
		{
			if (other.uuid != null)
			{
				return false;
			}
		} else if (!uuid.equals(other.uuid))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return uuid;
	}
}
