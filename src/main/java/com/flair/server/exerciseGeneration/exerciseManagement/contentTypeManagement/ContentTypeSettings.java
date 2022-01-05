package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import java.util.ArrayList;

import org.jsoup.nodes.Element;

import com.flair.server.exerciseGeneration.exerciseManagement.DownloadedResource;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.XmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.JsonManager;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.IExerciseSettings;

public abstract class ContentTypeSettings {

    public ContentTypeSettings(String resourceFolder, JsonManager jsonManager, boolean escapeAsterisksInHtml,
                               ExerciseGenerator exerciseGenerator, String contentTypeLibrary, String name) {
        this.resourceFolder = resourceFolder;
        this.jsonManager = jsonManager;
        this.escapeAsterisksInHtml = escapeAsterisksInHtml;
        this.exerciseGenerator = exerciseGenerator;
        this.contentTypeLibrary = contentTypeLibrary;
        this.name = name;
    }

    private String resourceFolder;
    private JsonManager jsonManager;
    private boolean escapeAsterisksInHtml;
    private ExerciseGenerator exerciseGenerator;
    private String contentTypeLibrary;
    private IExerciseSettings exerciseSettings;
    private Element doc;
    private ArrayList<DownloadedResource> resources;
    private String name;
    private int index;

	public String getResourceFolder() { return resourceFolder; }
    public JsonManager getJsonManager() { return jsonManager; }
    public boolean isEscapeAsterisksInHtml() {return escapeAsterisksInHtml; }
    public ExerciseGenerator getExerciseGenerator() { return exerciseGenerator; }
    public String getContentTypeLibrary() { return contentTypeLibrary; }
    public IExerciseSettings getExerciseSettings() { return exerciseSettings; }
    public Element getDoc() { return doc; }
    public ArrayList<DownloadedResource> getResources() { return resources; }
    public String getName() { return name; }
    public int getIndex() { return index; }

    public void setExerciseSettings(IExerciseSettings exerciseSettings) { this.exerciseSettings = exerciseSettings; }
    public void setDoc(Element doc) { this.doc = doc; }
	public void setResources(ArrayList<DownloadedResource> resources) { this.resources = resources; }
	public void setIndex(int index) { this.index = index; }

}
