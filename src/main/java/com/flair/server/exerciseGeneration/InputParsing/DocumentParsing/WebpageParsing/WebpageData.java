package com.flair.server.exerciseGeneration.InputParsing.DocumentParsing.WebpageParsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.flair.server.exerciseGeneration.exerciseManagement.DownloadedResource;
import com.flair.shared.exerciseGeneration.Pair;

public class WebpageData {

    public WebpageData(Element document) {
        this.document = document;
    }

    private ArrayList<Boundaries> sentenceBoundaryElements;
    private HashMap<Integer, String> plainTextElements;
    private Pair<Node, Node> textBoundaries;
    private Element document;
    private ArrayList<Fragment> fragments;
    private Pair<Integer, Integer> textIndices;
    private ArrayList<DownloadedResource> downloadedResources = new ArrayList<>();
    private ArrayList<String> sentenceHtml = new ArrayList<>();
    
    public ArrayList<Boundaries> getSentenceBoundaryElements() { return sentenceBoundaryElements; }
    public HashMap<Integer, String> getPlainTextElements() { return plainTextElements; }
    public Pair<Node, Node> getTextBoundaries() { return textBoundaries; }
	public Element getDocument() { return document; }
	public ArrayList<Fragment> getFragments() { return fragments; }
	public Pair<Integer, Integer> getTextIndices() { return textIndices; }
	public ArrayList<DownloadedResource> getDownloadedResources() { return downloadedResources; }
	public ArrayList<String> getSentenceHtml() { return sentenceHtml; }
	
	public void setSentenceBoundaryElements(ArrayList<Boundaries> sentenceBoundaryElements) {
		this.sentenceBoundaryElements = sentenceBoundaryElements;
	}
	public void setPlainTextElements(HashMap<Integer, String> plainTextElements) {
		this.plainTextElements = plainTextElements;
	}
	public void setTextBoundaries(Pair<Node, Node> textBoundaries) {
		this.textBoundaries = textBoundaries;
	}
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}
	public void setTextIndices(Pair<Integer, Integer> textIndices) {
		this.textIndices = textIndices;
	}
	
}
