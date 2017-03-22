package com.flair.server.taskmanager;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.AbstractDocumentSource;
import com.flair.server.parser.DocumentCollection;

public interface CustomParseOperation extends AbstractPipelineOperation
{
	public interface JobBegin {
		public void handle(Iterable<AbstractDocumentSource> sources);
	}
	
	public interface ParseComplete {
		public void handle(AbstractDocument result);
	}
	
	public interface JobComplete {
		public void handle(DocumentCollection result);
	}

	public void			setJobBeginHandler(JobBegin handler);
	public void			setParseCompleteHandler(ParseComplete handler);
	public void			setJobCompleteHandler(JobComplete handler);
}


class CustomParseOperationImpl extends BasicPipelineOperation implements CustomParseOperation
{
	private ParseJobInput		input;
	private JobBegin			jobB;
	private ParseComplete		parseC;
	private JobComplete			jobC;
	
	public CustomParseOperationImpl(ParseJobInput input) 
	{
		super(new ParseJob(input), PipelineOperationType.CUSTOM_PARSE);

		this.input = input;
		jobB = null;
		parseC = null;
		jobC = null;
	}
	
	@Override
	public void begin()
	{
		// register listener
		ParseJob j = (ParseJob)job;
		j.addListener(e -> {
			switch (e.type)
			{
			case JOB_COMPLETE:
				if (jobC != null)
					jobC.handle(e.jobOutput.parsedDocs);
				
				break;
			case PARSE_COMPLETE:
				if (parseC != null)
					parseC.handle(e.parsedDoc);
				
				break;
			}
		});;
		
		// trigger the job begin event
		if (jobB != null)
			jobB.handle(input.sourceDocs);
			
		super.begin();
	}

	@Override
	public void setParseCompleteHandler(ParseComplete handler) {
		parseC = handler;	
	}

	@Override
	public void setJobCompleteHandler(JobComplete handler) {
		jobC = handler;
	}

	@Override
	public void setJobBeginHandler(JobBegin handler) {
		jobB = handler;
	}
}