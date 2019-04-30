package com.flair.shared.interop;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Used to uniquely identify a client during client-server interop.
 */
public class ClientIdToken implements IsSerializable {
	private String uuid = "";

	public ClientIdToken(String uuid) {
		this.uuid = uuid;
	}
	public ClientIdToken() {}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClientIdToken that = (ClientIdToken) o;

		return uuid.equals(that.uuid);

	}
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public String toString() {
		return uuid;
	}
}
