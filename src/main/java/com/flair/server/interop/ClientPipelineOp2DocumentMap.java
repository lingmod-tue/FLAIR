package com.flair.server.interop;

import com.flair.server.document.AbstractDocument;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.interop.dtos.DocumentDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class ClientPipelineOp2DocumentMap {
	private final Map<PipelineOp<?, ?>, Map<Integer, AbstractDocument>> dataStore = new HashMap<>();      // op > (linking id > document)

	void put(PipelineOp<?, ?> op, AbstractDocument source, DocumentDTO dto) {
		Map<Integer, AbstractDocument> match = dataStore.computeIfAbsent(op, k -> new HashMap<>());
		if (match.containsKey(dto.getLinkingId()))
			ServerLogger.get().error("Linking ID collision for document '" + source.getDescription() + "'!");
		else
			match.put(dto.getLinkingId(), source);
	}

	AbstractDocument get(PipelineOp<?, ?> op, DocumentDTO dto) {
		Map<Integer, AbstractDocument> match = dataStore.get(op);
		if (match == null)
			return null;
		else
			return match.get(dto.getLinkingId());
	}

	boolean contains(PipelineOp<?, ?> op, DocumentDTO dto) {
		return get(op, dto) != null;
	}
	
	AbstractDocument getDocumentIfExists(int linkingId) {
		for (Entry<PipelineOp<?, ?>, Map<Integer, AbstractDocument>> entry : dataStore.entrySet()) {
			if(entry.getValue().containsKey(linkingId)) {
				return entry.getValue().get(linkingId);
			}
		}
		return null;
	}
}
