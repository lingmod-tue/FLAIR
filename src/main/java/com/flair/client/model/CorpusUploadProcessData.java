package com.flair.client.model;

import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.UploadedDocument;

import java.util.ArrayList;
import java.util.List;

final class CorpusUploadProcessData extends ProcessData {
	final List<UploadedDocument> uploadedDocs;

	CorpusUploadProcessData(Language l) {
		super(AbstractWebRankerCore.OperationType.CUSTOM_CORPUS, l);
		uploadedDocs = new ArrayList<>();
	}

	@Override
	public String getName() {
		if (uploadedDocs.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder();
		for (UploadedDocument itr : uploadedDocs)
			sb.append(itr.getTitle()).append(", ");

		String out = sb.toString();
		return out.substring(0, out.length() - 2);
	}
}
