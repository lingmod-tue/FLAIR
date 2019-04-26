package com.flair.client.model;

import com.flair.client.model.interfaces.AbstractWebRankerCore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

final class ProcessHistory {
	final LinkedList<ProcessData> stack;

	ProcessHistory() {
		stack = new LinkedList<>();
	}

	ProcessData poll() {
		if (stack.isEmpty())
			return null;
		else
			return stack.getFirst();
	}

	void push(ProcessData d) {
		stack.push(d);
	}

	ProcessData pop() {
		return stack.pop();
	}

	List<ProcessData> asList() {
		List<ProcessData> out = new ArrayList<>();
		for (ProcessData itr : stack) {
			if (AbstractWebRankerCore.OperationType.isTransient(itr.getType()) == false)
				continue;
			else if (itr.complete == false || itr.invalid)
				continue;

			out.add(itr);
		}
		return out;
	}

	boolean isEmpty() {
		return stack.isEmpty();
	}
}
