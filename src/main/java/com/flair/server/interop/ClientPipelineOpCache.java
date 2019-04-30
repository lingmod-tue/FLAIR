package com.flair.server.interop;

import com.flair.server.pipelines.PipelineOp;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exceptions.ServerRuntimeException;
import edu.stanford.nlp.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

final class ClientPipelineOpCache {
	private final Map<String, PipelineOp<?, ?>> id2op;      // string UUID > op
	private Pair<PipelineOp<?, ?>, String> currentOp;

	ClientPipelineOpCache() {
		id2op = new HashMap<>();
		currentOp = null;
	}

	String newOp(PipelineOp<?, ?> op) {
		if (hasActiveOp())
			throw new ServerRuntimeException("Previous state not cleared");

		String uuid = UUID.randomUUID().toString();
		if (id2op.containsKey(uuid)) {
			ServerLogger.get().warn("Pipeline op ID '" + uuid + "' has a hash collision!");
		}

		id2op.put(uuid, op);
		currentOp = new Pair<>(op, uuid);

		ServerLogger.get().info("Pipeline operation '" + op.name() + "' has begun");
		return uuid;
	}

	void endActiveOp(boolean cancel) {
		if (!hasActiveOp())
			throw new ServerRuntimeException("No operation running");

		if (cancel) {
			currentOp.first.cancel();
			ServerLogger.get().info("Pipeline operation '" + currentOp.first.name() + "' was cancelled");
		} else
			ServerLogger.get().info("Pipeline operation '" + currentOp.first.name() + "' has ended");

		currentOp = null;
	}

	PipelineOp<?, ?> lookupOp(String uuid) {
		return id2op.get(uuid);
	}

	PipelineOp<?, ?> activeOp() {
		if (currentOp != null)
			return currentOp.first;
		else
			return null;
	}

	String activeOpId() {
		if (currentOp != null)
			return currentOp.second;
		else
			return null;
	}

	boolean hasActiveOp() {
		return currentOp != null;
	}
}
