package com.flair.server.pipelines.gramparsing;

import com.flair.server.document.AbstractDocument;
import com.flair.server.document.AbstractDocumentFactory;
import com.flair.server.document.AbstractDocumentSource;
import com.flair.server.document.DocumentCollection;
import com.flair.server.parser.AbstractKeywordSearcher;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;
import com.flair.shared.grammar.Language;

import java.util.List;

public class ParseOp extends PipelineOp<ParseOp.Input, ParseOp.Output> {
	public interface ParseComplete extends EventHandler<AbstractDocument> {}

	public interface JobComplete extends EventHandler<DocumentCollection> {}

	static final class Input {
		final Language sourceLanguage;
		final List<AbstractDocumentSource> sourceDocs;

		final AsyncExecutorService docParseExecutor;

		final AbstractDocumentFactory docFactory;
		final CoreNlpParser docParser;
		final ParsingStrategy.Factory docParsingStrategy;
		final AbstractKeywordSearcher.Factory keywordSearcher;
		final KeywordSearcherInput keywordSearcherInput;

		final ParseOp.ParseComplete parseComplete;
		final ParseOp.JobComplete jobComplete;

		Input(Language sourceLanguage,
		      List<AbstractDocumentSource> sourceDocs,
		      AsyncExecutorService docParseExecutor,
		      AbstractDocumentFactory docFactory,
		      CoreNlpParser docParser,
		      ParsingStrategy.Factory strategy,
		      AbstractKeywordSearcher.Factory keywordSearcher,
		      KeywordSearcherInput keywordSearcherInput,
		      ParseComplete parseComplete,
		      JobComplete jobComplete) {
			this.sourceLanguage = sourceLanguage;
			this.sourceDocs = sourceDocs;

			this.docParseExecutor = docParseExecutor;

			this.docFactory = docFactory;
			this.docParser = docParser;
			this.docParsingStrategy = strategy;
			this.keywordSearcher = keywordSearcher;
			this.keywordSearcherInput = keywordSearcherInput;

			this.parseComplete = parseComplete != null ? parseComplete : e -> {};
			this.jobComplete = jobComplete != null ? jobComplete : e -> {};
		}
	}

	public static final class Output {
		public final DocumentCollection parsedDocs;

		Output(Language sourceLang) {
			this.parsedDocs = new DocumentCollection(sourceLang);
		}
	}

	@Override
	protected String desc() {
		return name + " Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tSource Docs:"
				+ input.sourceDocs.size() + "\nOutput\n\t\n\tParsed Docs: " + output.parsedDocs.size();
	}

	private void initTaskSyncHandlers() {
		taskLinker.addHandler(DocParseTask.Result.class, (j, r) -> {
			// add the result to the doc collection
			if (r.output != null) {
				input.parseComplete.handle(r.output);

				output.parsedDocs.add(r.output, true);
				output.parsedDocs.sort();
			}
		});
	}

	ParseOp(Input input) {
		super("ParseOp", input, new Output(input.sourceLanguage));
		initTaskSyncHandlers();

		AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.newJob(j -> {
			if (j.isCancelled())
				return;

			input.jobComplete.handle(output.parsedDocs);
		});

		for (AbstractDocumentSource itr : input.sourceDocs) {
			AbstractDocument docToParse = input.docFactory.create(itr);
			scheduler.newTask(
					DocParseTask.factory(input.docParsingStrategy.create(new ParserInput(docToParse)),
							input.docParser,
							input.keywordSearcher.create(),
							input.keywordSearcherInput)
			)
					.with(input.docParseExecutor)
					.then(this::linkTasks)
					.queue();
		}

		this.job = scheduler.fire();
	}
}
