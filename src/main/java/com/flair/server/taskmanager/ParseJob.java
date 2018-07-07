package com.flair.server.taskmanager;

import com.flair.server.parser.*;
import com.flair.shared.grammar.Language;

import java.util.List;

/*
 * Parses a set of documents
 */
public class ParseJob extends AbstractJob<ParseJobOutput, ParseJobEvent> {
	private final ParseJobInput input;
	private final ParseJobOutput output;

	public ParseJob(ParseJobInput input) {
		this.input = input;
		this.output = new ParseJobOutput(input.sourceLanguage);
	}

	@Override
	public void begin() {
		if (isStarted())
			throw new IllegalStateException("Job has already begun");

		for (AbstractDocumentSource itr : input.sourceDocs) {
			DocumentParseTask newTask = new DocumentParseTask(this,
					itr,
					input.docParsingStrategy.create(),
					input.docParserPool,
					input.keywordSearcher.create(),
					input.keywordSearcherInput);

			registerTask(newTask);
			input.docParseExecutor.parse(newTask);
		}

		flagStarted();
	}

	@Override
	protected void handleTaskCompletion(AbstractTaskResult<?> completionResult) {
		switch (completionResult.getType()) {
		case PARSE_DOCUMENT: {
			// add the result to the doc collection
			DocumentParseTaskResult result = (DocumentParseTaskResult) completionResult.getResult();

			if (result.getOutput() != null) {
				notifyListeners(new ParseJobEvent(result.getOutput()));

				output.parsedDocs.add(result.getOutput(), true);
				output.parsedDocs.sort();
			}

			break;
		}
		}
	}

	@Override
	public ParseJobOutput getOutput() {
		waitForCompletion();
		return output;
	}

	@Override
	public String toString() {
		if (isStarted() == false)
			return "ParseJob has not started yet";
		else if (isCompleted() == false)
			return "ParseJob is still running";
		else if (isCancelled())
			return "ParseJob was cancelled";
		else
			return "ParseJob Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tSource Docs:"
					+ input.sourceDocs.size() + "\nOutput\n\t\n\tParsed Docs: " + output.parsedDocs.size();
	}

	@Override
	protected ParseJobEvent createCompletionEvent() {
		return new ParseJobEvent(output);
	}
}


final class ParseJobInput {
	public final Language sourceLanguage;
	public final List<AbstractDocumentSource> sourceDocs;

	public final DocumentParseTask.Executor docParseExecutor;

	public final DocumentParserPool docParserPool;
	public final AbstractParsingStrategyFactory docParsingStrategy;
	public final AbstractDocumentKeywordSearcherFactory keywordSearcher;
	public final KeywordSearcherInput keywordSearcherInput;

	public ParseJobInput(Language sourceLanguage,
	                     List<AbstractDocumentSource> sourceDocs,
	                     DocumentParseTask.Executor docParseExecutor,
	                     DocumentParserPool parserPool,
	                     AbstractParsingStrategyFactory strategy,
	                     AbstractDocumentKeywordSearcherFactory keywordSearcher,
	                     KeywordSearcherInput keywordSearcherInput) {
		this.sourceLanguage = sourceLanguage;
		this.sourceDocs = sourceDocs;
		this.docParseExecutor = docParseExecutor;

		this.docParserPool = parserPool;
		this.docParsingStrategy = strategy;
		this.keywordSearcher = keywordSearcher;
		this.keywordSearcherInput = keywordSearcherInput;
	}
}

final class ParseJobOutput {
	public final DocumentCollection parsedDocs;

	public ParseJobOutput(Language sourceLang) {
		this.parsedDocs = new DocumentCollection(sourceLang);
	}
}

final class ParseJobEvent implements AbstractJobEvent<ParseJobOutput> {
	enum Type {
		PARSE_COMPLETE,
		JOB_COMPLETE
	}

	public final Type type;
	public final AbstractDocument parsedDoc;
	public final ParseJobOutput jobOutput;


	public ParseJobEvent(AbstractDocument d) {
		type = Type.PARSE_COMPLETE;
		parsedDoc = d;
		jobOutput = null;
	}

	public ParseJobEvent(ParseJobOutput o) {
		type = Type.JOB_COMPLETE;
		parsedDoc = null;
		jobOutput = o;
	}

	@Override
	public boolean isCompletionEvent() {
		return type == Type.JOB_COMPLETE;
	}

	@Override
	public ParseJobOutput getOutput() {
		return jobOutput;
	}
}