package com.flair.client.model.interfaces;


import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.utilities.GenericEventSource;

/*
 * Implements the core client-server communication and ranking presentation logic
 */
public interface AbstractWebRankerCore {
	/*
	 * Operations are of two types: Transient and non-transient
	 * Transient operations have explicit begin and end events
	 * Non-transient operations only have an end event
	 */
	enum OperationType {
		NONE,
		// Transient
		WEB_SEARCH,
		CUSTOM_CORPUS,
		// Non-transient
		COMPARE,            // compare documents
		RESTORE             // restore a previous operation
		;

		public static boolean isTransient(OperationType t) {
			switch (t) {
			case WEB_SEARCH:
			case CUSTOM_CORPUS:
				return true;
			default:
				return false;
			}
		}
	}

	class BeginOperation {
		public final OperationType op;
		public final Language lang;

		public BeginOperation(OperationType op, Language lang) {
			this.op = op;
			this.lang = lang;
		}
	}

	class EndOperation {
		public final OperationType op;
		public final Language lang;
		public final boolean success;

		public EndOperation(OperationType op, Language lang, boolean succ) {
			this.op = op;
			this.lang = lang;
			this.success = succ;
		}
	}

	void init(AuthToken token, AbstractWebRankerPresenter presenter);

	WebRankerAnalysis getCurrentOperation();
	void cancelCurrentOperation();
	boolean isOperationInProgress();

	void addBeginOperationHandler(GenericEventSource.EventHandler<BeginOperation> handler);
	void addEndOperationHandler(GenericEventSource.EventHandler<EndOperation> handler);
}
