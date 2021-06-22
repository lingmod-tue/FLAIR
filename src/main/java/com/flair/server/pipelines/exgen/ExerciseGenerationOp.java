package com.flair.server.pipelines.exgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.FillInTheBlanksSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.FindSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.MultiDragDropSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.SingleChoiceSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.SingleDragDropSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

import edu.stanford.nlp.util.Pair;

public class ExerciseGenerationOp extends PipelineOp<ExerciseGenerationOp.Input, ExerciseGenerationOp.Output> {
	public interface ExGenComplete extends EventHandler<Pair<String, byte[]>> {}
	public interface JobComplete extends EventHandler<Pair<String, byte[]>> {}
	
	private final ReentrantLock lock = new ReentrantLock();
	private final ArrayList<Pair<ContentTypeSettings, Boolean>> settingsStates = new ArrayList<>();
	private final ArrayList<String> downloadedUrls = new ArrayList<>();
	
	static final class Input {
		final ArrayList<ExerciseSettings> settings;
		final AsyncExecutorService downloadExecutor;
		final AsyncExecutorService exGenExecutor;
		final ExGenComplete exGenComplete;
		final JobComplete jobComplete;
		final CoreNlpParser parser;
		final SimpleNlgParser generator;
		final OpenNlpParser lemmatizer;
		final ResourceDownloader resourceDownloader;
		
		Input(ArrayList<ExerciseSettings> settings,
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
		public final Pair<String, byte[]> file;

		Output() {
			this.file = new Pair<String, byte[]>("", new byte[] {});
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
			for(int i = 0; i < settingsStates.size(); i++) {
				Pair<ContentTypeSettings, Boolean> entry = settingsStates.get(i);
				if(!entry.second) {
					ContentTypeSettings contentTypeSettings = entry.first;
					if(contentTypeSettings instanceof QuizSettings) {
						boolean allDocumentsDownloaded = true;
						for(ContentTypeSettings settings : ((QuizSettings)contentTypeSettings).getExercises()) {
							if(settings.getExerciseSettings().getUrl().equals(r.url)) {
					            settings.setDoc(r.document);
					            settings.setResources(r.resources);
							}
							if(!downloadedUrls.contains(settings.getExerciseSettings().getUrl())) {
								allDocumentsDownloaded = false;
								break;
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
			safeInvoke(() -> input.exGenComplete.handle(new Pair<>(r.fileName, r.file)),
						"Exception in generation complete handler");
		});
	}

	ExerciseGenerationOp(Input input) {
		super("ExerciseGenerationOp", input, new Output());
				
		HashMap<String, ArrayList<ContentTypeSettings>> configs = new HashMap<>();
        for(ExerciseSettings settings : input.settings) {
            ContentTypeSettings contentTypeSettings;
            if(settings.getContentType().equals("FiB")) {
                contentTypeSettings = new FillInTheBlanksSettings(settings.getTaskName());
            } else if(settings.getContentType().equals("Select")) {
                contentTypeSettings = new SingleChoiceSettings(settings.getTaskName());
            } else if(settings.getContentType().equals("SingleDrag")) {
                contentTypeSettings = new SingleDragDropSettings(settings.getTaskName());
            } else if(settings.getContentType().equals("MultiDrag")) {
                contentTypeSettings = new MultiDragDropSettings(settings.getTaskName());
            } else if(settings.getContentType().equals("Mark")) {
                contentTypeSettings = new FindSettings(settings.getTaskName());
            } else {
                throw new IllegalArgumentException();
            }

            contentTypeSettings.setExerciseSettings(settings);

            if (!configs.containsKey(settings.getQuiz())) {
                configs.put(settings.getQuiz(), new ArrayList<>());
            }
            configs.get(settings.getQuiz()).add(contentTypeSettings);
        }
        
        for (HashMap.Entry<String, ArrayList<ContentTypeSettings>> entry : configs.entrySet()) {
            if(entry.getKey().equals("")) { // it's not a quiz
            	for(ContentTypeSettings settings : entry.getValue()) {
                	settingsStates.add(new Pair<>(settings, false));            	
            	}
            } else {
                ContentTypeSettings settings = new QuizSettings(entry.getValue(), "Quiz " + entry.getKey());
                settingsStates.add(new Pair<>(settings, false));  
            }
        }
        
		
		initTaskSyncHandlers();
	}

	@Override
	public PipelineOp<Input, Output> launch() {
		super.launch();

        HashMap<String, Element> documents = new HashMap<>();
        for(ExerciseSettings settings : input.settings) {
            if (!documents.containsKey(settings.getUrl())) {
                documents.put(settings.getUrl(), null);
            }
        }
        
        AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.newJob(j -> {
			if (j.isCancelled())
				return;
			
			safeInvoke(() -> input.jobComplete.handle(output.file),
					"Exception in job complete handler");
        });
        
        for (HashMap.Entry<String, Element> entry : documents.entrySet()) {
        	scheduler.newTask(WebsiteDownloadTask.factory(entry.getKey(), input.resourceDownloader))
			.with(input.downloadExecutor)
			.then(this::linkTasks)
			.queue();
        }
        		

		this.job = scheduler.fire();
		return this;
	}
}