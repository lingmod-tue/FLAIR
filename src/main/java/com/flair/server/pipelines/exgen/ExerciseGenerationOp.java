package com.flair.server.pipelines.exgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.ResultComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ConfigBasedSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.IExerciseSettings;

import edu.stanford.nlp.util.Pair;

public class ExerciseGenerationOp extends PipelineOp<ExerciseGenerationOp.Input, ExerciseGenerationOp.Output> {
	public interface ExGenComplete extends EventHandler<ResultComponents> {}
	public interface JobComplete extends EventHandler<ResultComponents> {}
	
	private final ReentrantLock lock = new ReentrantLock();
	private final ArrayList<Pair<ContentTypeSettings, Boolean>> settingsStates = new ArrayList<>();
	private final ArrayList<String> downloadedUrls = new ArrayList<>();
	
	static final class Input {
		final ArrayList<IExerciseSettings> settings;
		final AsyncExecutorService downloadExecutor;
		final AsyncExecutorService exGenExecutor;
		final ExGenComplete exGenComplete;
		final JobComplete jobComplete;
		final CoreNlpParser parser;
		final SimpleNlgParser generator;
		final OpenNlpParser lemmatizer;
		final ResourceDownloader resourceDownloader;
		
		Input(ArrayList<IExerciseSettings> settings,
				AsyncExecutorService downloadExecutor,
		      AsyncExecutorService exGenExecutor,
		      ExGenComplete exGenComplete,
		      JobComplete jobComplete,
		      CoreNlpParser parser,
		      SimpleNlgParser generator, 
		      OpenNlpParser lemmatizer, 
		      ResourceDownloader resourceDownloader) {
			this.settings = settings;		
			this.downloadExecutor = downloadExecutor;
			this.exGenExecutor = exGenExecutor;
			this.exGenComplete = exGenComplete != null ? exGenComplete : e -> {};
			this.jobComplete = jobComplete != null ? jobComplete : e -> {};
			this.parser = parser;
			this.generator = generator;
			this.lemmatizer = lemmatizer;
			this.resourceDownloader = resourceDownloader;
		}
	}

	public static final class Output {
		public final ResultComponents file;

		Output() {
			this.file = new ResultComponents(new HashMap<String, byte[]>(), new HashMap<String, String>(), 
					new HashMap<String, byte[]>());
		}
	}

	@Override
	protected String desc() {
		return name;
	}
	
	private void initTaskSyncHandlers() {
		taskLinker.addHandler(WebsiteDownloadTask.Result.class, (j, r) -> {
			AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.existingJob(j);

			lock.lock();
			downloadedUrls.add(r.url);
			int index = 0;
			for(int i = 0; i < settingsStates.size(); i++) {
				Pair<ContentTypeSettings, Boolean> entry = settingsStates.get(i);
				if(!entry.second) {
					ContentTypeSettings contentTypeSettings = entry.first;
					if(contentTypeSettings instanceof QuizSettings) {
						boolean allDocumentsDownloaded = true;
						for(ContentTypeSettings settings : ((QuizSettings)contentTypeSettings).getExercises()) {
							if(settings.getExerciseSettings() instanceof ExerciseSettings) {
								if(settings.getExerciseSettings().getUrl().equals(r.url)) {
						            settings.setDoc(r.document);
						            settings.setResources(r.resources);
						            settings.setIndex(++index);
								}
								if(!downloadedUrls.contains(settings.getExerciseSettings().getUrl())) {
									allDocumentsDownloaded = false;
									break;
								}
							}
						}
						
						if(allDocumentsDownloaded) {
							settingsStates.set(i, new Pair<>(entry.first, true));	// set state of exercise to handled
							scheduler.newTask(ExGenTask.factory(contentTypeSettings, input.parser, input.generator, input.lemmatizer, input.resourceDownloader))
							.with(input.exGenExecutor)
							.then(this::linkTasks)
							.queue();
						}
					} else {
						if(contentTypeSettings.getExerciseSettings().getUrl().equals(r.url)) {
							settingsStates.set(i, new Pair<>(entry.first, true));	// set state of exercise to handled
							
							contentTypeSettings.setDoc(r.document);
				            contentTypeSettings.setResources(r.resources);
				            contentTypeSettings.setIndex(++index);

							scheduler.newTask(ExGenTask.factory(contentTypeSettings, input.parser, input.generator, input.lemmatizer, input.resourceDownloader))
							.with(input.exGenExecutor)
							.then(this::linkTasks)
							.queue();
						}
					}
				}
			}
			lock.unlock();

			if (scheduler.hasTasks())
				scheduler.fire();
		});
		taskLinker.addHandler(ExGenTask.Result.class, (j, r) -> {
			safeInvoke(() -> input.exGenComplete.handle(r.output),
						"Exception in generation complete handler");
		});
	}

	ExerciseGenerationOp(Input input) {
		super("ExerciseGenerationOp", input, new Output());
				
		HashMap<String, ArrayList<ContentTypeSettings>> configs = new HashMap<>();
        for(IExerciseSettings s : input.settings) {
    		ContentTypeSettings contentTypeSettings;

        	if(s instanceof ExerciseSettings) {
        		ExerciseSettings settings = (ExerciseSettings)s;
                contentTypeSettings = new ContentTypeSettings(settings.getContentType());
        	} else {
        		contentTypeSettings = new ConfigBasedSettings(((ConfigExerciseSettings)s).getTopic());
        	}
        	
        	contentTypeSettings.setExerciseSettings(s);

            if (!configs.containsKey(s.getQuiz())) {
                configs.put(s.getQuiz(), new ArrayList<>());
            }
            configs.get(s.getQuiz()).add(contentTypeSettings);
        }
        
        for (HashMap.Entry<String, ArrayList<ContentTypeSettings>> entry : configs.entrySet()) {
            if(entry.getKey().equals("")) { // it's not a quiz
            	for(ContentTypeSettings settings : entry.getValue()) {
                	settingsStates.add(new Pair<>(settings, false));            	
            	}
            } else {
            	QuizSettings settings = new QuizSettings("Quiz " + entry.getKey());
            	settings.setExercises(entry.getValue());
                settingsStates.add(new Pair<>(settings, false));  
            }
        }
        
		
		initTaskSyncHandlers();
	}

	@Override
	public PipelineOp<Input, Output> launch() {
		super.launch();

        HashMap<String, Element> documents = new HashMap<>();
        for(IExerciseSettings s : input.settings) {
        	if(s instanceof ExerciseSettings) {
        		ExerciseSettings settings = (ExerciseSettings)s;
        		
        		if(settings.getUrl().length() == 0) {
            		if (!documents.containsKey(settings.getFileName())) {
    	        		try {
    	        			if(settings.getFileContent().contains("<html") && settings.getFileContent().contains("</html>")) {
    	        				Document doc = Jsoup.parse(settings.getFileContent());
    			                documents.put(settings.getFileName(), doc);
    	        			} else {
    	        				settings.setWebPage(false);
    			                documents.put(settings.getFileName(), Jsoup.parse("<span>" + settings.getPlainText().trim().replace("\n", " <br> ") + "</span>"));
    	        			}
    	        		} catch(Exception e) {
	        				settings.setWebPage(false);
    		                documents.put(settings.getFileName(), Jsoup.parse("<span>" + settings.getPlainText().trim().replace("\n", " <br> ") + "</span>"));
    	        		}
            		}
            	} else {
    	            if (!documents.containsKey(settings.getUrl())) {
    	                documents.put(settings.getUrl(), null);
    	            }
            	}
        	} else {
                documents.put(s.getFileName(), Jsoup.parse(""));
        	}
        }
        
        AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.newJob(j -> {
			if (j.isCancelled())
				return;
			
			safeInvoke(() -> input.jobComplete.handle(output.file),
					"Exception in job complete handler");
        });
        
        for (HashMap.Entry<String, Element> entry : documents.entrySet()) {
        	scheduler.newTask(WebsiteDownloadTask.factory(entry.getValue() == null ? entry.getKey() : "", input.resourceDownloader, entry.getValue()))
			.with(input.downloadExecutor)
			.then(this::linkTasks)
			.queue();
        }
        		

		this.job = scheduler.fire();
		return this;
	}
}
