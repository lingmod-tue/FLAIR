package com.flair.shared.interop.messages.client;

import com.flair.shared.interop.messages.BaseMessage;

public class CmCustomCorpusUploadEnd extends BaseMessage {
	private boolean uploadSuccess = true;

	public CmCustomCorpusUploadEnd() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CmCustomCorpusUploadEnd{" + identifier() + "}[");
		sb.append("uploadSuccess=" + uploadSuccess);
		return sb.append("]").toString();
	}

	public boolean isUploadSuccess() {
		return uploadSuccess;
	}
	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}
}
