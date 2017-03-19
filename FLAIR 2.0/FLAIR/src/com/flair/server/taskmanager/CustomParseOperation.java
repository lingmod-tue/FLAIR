package com.flair.server.taskmanager;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.DocumentCollection;

public interface CustomParseOperation extends AbstractPipelineOperation
{
	public interface ParseComplete {
		public void handle(AbstractDocument result);
	}
	
	public interface JobComplete {
		public void handle(DocumentCollection result);
	}

	public void			setParseCompleteHandler(ParseComplete handler);
	public void			setJobCompleteHandler(JobComplete handler);
}


class CustomParseOperationImpl extends BasicPipelineOperation implements CustomParseOperation
{
	private ParseComplete		parseC;
	private JobComplete			jobC;
	
	public CustomParseOperationImpl(ParseJobInput input) 
	{
		super(new ParseJob(input), PipelineOperationType.CUSTOM_PARSE);

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
}