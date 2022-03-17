package com.flair.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.TextResource;

public interface ResourceManager extends ClientBundle {
	  public static final ResourceManager INSTANCE =  GWT.create(ResourceManager.class);

	  @Source("resources/ExerciseSpecification.html")
	  public TextResource specificationEditor();

}
