package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.AbstractDocumentResultsPane;
import com.flair.client.presentation.interfaces.AbstractResultItem;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.presentation.interfaces.CompletedResultItem;
import com.flair.client.presentation.interfaces.InProgressResultItem;
import com.flair.client.presentation.interfaces.OperationCancelService;
import com.flair.client.utilities.GlobalWidgetAnimator;
import com.flair.client.utilities.GwtUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDivider;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.jquery.client.api.Functions.Func;

public class DocumentResultsPane extends LocalizedComposite implements AbstractDocumentResultsPane, OperationCancelService
{
	private static DocumentResultsPaneUiBinder uiBinder = GWT.create(DocumentResultsPaneUiBinder.class);

	interface DocumentResultsPaneUiBinder extends UiBinder<Widget, DocumentResultsPane>
	{
	}
	
	private static DocumentResultsPaneLocalizationBinder localeBinder = GWT.create(DocumentResultsPaneLocalizationBinder.class);
	interface DocumentResultsPaneLocalizationBinder extends LocalizationBinder<DocumentResultsPane> {}
	
	
	@UiField
	MaterialPanel			pnlRootUI;
	@UiField
	MaterialTitle			lblTitleUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TEXT_BUTTON)
	MaterialButton			btnCancelOpUI;
	@UiField
	MaterialRow				pnlCompletedContainerUI;
	@UiField
	MaterialRow				pnlInProgressContainerUI;
	@UiField
	MaterialDivider			divDividerUI;
	@UiField
	MaterialRow				pnlSpinnerUI;
	
	State					state;
	CancelHandler			cancelHandler;
	
	private static class DisplayItem
	{
		AbstractResultItem			parent;
		DocumentResultDisplayItem	displayItem;
		
		DisplayItem(AbstractResultItem i)
		{
			parent = i;
			displayItem = new DocumentResultDisplayItem(parent);
		}
		
		DocumentResultDisplayItem getWidget() {
			return displayItem;
		}
	}
	
	private class State
	{
		Map<AbstractResultItem, DisplayItem>		completed;
		Map<AbstractResultItem, DisplayItem>		inprogress;
		DisplayItem									lastSelection;
	
		State()
		{
			completed = new HashMap<>();
			inprogress = new HashMap<>();
			lastSelection = null;
		}
		
		private void animate(Widget w, Transition t, int delay, int duration, Func callback)
		{
			MaterialAnimation anim = new MaterialAnimation(w);
			anim.setTransition(t);
			anim.setDelay(delay);
			anim.setDuration(duration);
			
			if (callback != null)
				anim.animate(callback);
			else
				anim.animate();
		}
		
		private void animate(Widget w, Transition t, int delay, int duration) {
			animate(w, t, delay, duration, null);
		}
		
		private void addDisplayItem(DisplayItem item, HasWidgets container)
		{
			container.add(item.getWidget());
			// only animate if its inside of the viewport
			if (GwtUtil.isScrolledIntoView(item.getWidget(), false))
				animate(item.getWidget(), Transition.ZOOMIN, 0, 600);
		}
		
		private void removeDisplayItem(DisplayItem item, HasWidgets container)
		{
			// immediately remove if not inside viewport
			if (GwtUtil.isScrolledIntoView(item.getWidget(), false) == false)
				container.remove(item.getWidget());
			else
				animate(item.getWidget(), Transition.ZOOMOUT, 0, 600, () -> container.remove(item.getWidget()));
		}
		
		private void validatePlaceholders()
		{
			// show the divider when there are items of both kind
			divDividerUI.setVisible(!inprogress.isEmpty() && !completed.isEmpty());
			
			// show the spinner when there are no results
			pnlSpinnerUI.setVisible(inprogress.isEmpty() && completed.isEmpty());
		}
		
		private Map<AbstractResultItem, DisplayItem> getMap(AbstractResultItem.Type type)
		{
			if (type == Type.IN_PROGRESS)
				return inprogress;
			else
				return completed;
		}
		
		private HasWidgets getContainer(AbstractResultItem.Type type)
		{
			if (type == Type.IN_PROGRESS)
				return pnlInProgressContainerUI;
			else
				return pnlCompletedContainerUI;
		}
	
		public void add(AbstractResultItem.Type type, AbstractResultItem item)
		{
			Map<AbstractResultItem, DisplayItem> map = getMap(type);
			HasWidgets container = getContainer(type);
			
			if ((type == Type.IN_PROGRESS && item instanceof InProgressResultItem == false) ||
				(type == Type.COMPLETED && item instanceof CompletedResultItem == false))
			{
				throw new RuntimeException("Item type mismatch");
			}
				
			if (map.containsKey(item))
				throw new RuntimeException("Item already exists");
			
			DisplayItem d = new DisplayItem(item);
			d.getWidget().addTextClickHandler(e -> {
				if (d.getWidget().isSelected() == false)
				{
					d.getWidget().setSelected(true);
					clearSelection();
					lastSelection = d;
				}
			});
			map.put(item, d);
				
			addDisplayItem(d, container);
			validatePlaceholders();
		}
		
		public void remove(AbstractResultItem.Type type, AbstractResultItem item)
		{
			Map<AbstractResultItem, DisplayItem> map = getMap(type);
			HasWidgets container = getContainer(type);
			
			if ((type == Type.IN_PROGRESS && item instanceof InProgressResultItem == false) ||
				(type == Type.COMPLETED && item instanceof CompletedResultItem == false))
			{
				throw new RuntimeException("Item type mismatch");
			}
				
			if (map.containsKey(item) == false)
				throw new RuntimeException("Item is yet to be added");
			
			DisplayItem d = map.get(item);
			map.remove(item);
			
			removeDisplayItem(d, container);
			validatePlaceholders();
		}
		
		public void clearAll(AbstractResultItem.Type type)
		{
			Map<AbstractResultItem, DisplayItem> map = getMap(type);
			if (map.size() > 0)
			{
				// copy to buffer as the remove operation will modify the map
				List<AbstractResultItem> buffer = new ArrayList<>(map.keySet());
				for (AbstractResultItem itr : buffer)
					remove(type, itr);
			}
			
			validatePlaceholders();
		}

		public void clearSelection()
		{
			if (lastSelection != null)
				lastSelection.getWidget().setSelected(false);
						
			lastSelection = null;
		}
	}
	
	private void initHandlers()
	{
		btnCancelOpUI.addClickHandler(e -> cancelOperation());
	}

	private void initUI()
	{
		pnlRootUI.setVisible(false);
	}
	
	public DocumentResultsPane()
	{
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
		
		state = new State();
		cancelHandler = null;

		initHandlers();
		initUI();
	}

	@Override
	public void setPanelTitle(String title) {
		lblTitleUI.setTitle(title);
	}

	@Override
	public void setPanelSubtitle(String subtitle) {
		lblTitleUI.setDescription(subtitle);
	}

	@Override
	public void addCompleted(CompletedResultItem item) {
		state.add(Type.COMPLETED, item);
	}

	@Override
	public void addInProgress(InProgressResultItem item) {
		state.add(Type.IN_PROGRESS, item);
	}

	@Override
	public void removeCompleted(CompletedResultItem item) {
		state.remove(Type.COMPLETED, item);
	}

	@Override
	public void removeInProgress(InProgressResultItem item) {
		state.remove(Type.IN_PROGRESS, item);
	}

	@Override
	public void clearCompleted() {
		state.clearAll(Type.COMPLETED);
	}

	@Override
	public void clearInProgress() {
		state.clearAll(Type.IN_PROGRESS);
	}
	
	@Override
	public void show()
	{
		GlobalWidgetAnimator.get().animateWithStart(pnlRootUI,
				Transition.FADEINDOWN, 0, 1000, () -> pnlRootUI.setVisible(true));
	}
	
	@Override
	public void hide()
	{
		GlobalWidgetAnimator.get().animateWithStop(pnlRootUI,
				Transition.FADEOUTUP, 0, 1000, () -> pnlRootUI.setVisible(false));
	}

	@Override
	public void cancelOperation()
	{
		if (cancelHandler != null)
			cancelHandler.handle();
	}

	@Override
	public void setCancelHandler(CancelHandler handler) {
		cancelHandler = handler;
	}
	
	public void setCancelVisible(boolean visible)
	{
		if (visible)
		{
			GlobalWidgetAnimator.get().animateWithStart(btnCancelOpUI,
					Transition.ZOOMIN, 0, 450, () -> btnCancelOpUI.setVisible(true));
		}
		else
		{
			GlobalWidgetAnimator.get().animateWithStop(btnCancelOpUI,
					Transition.ZOOMOUT, 0, 450, () -> btnCancelOpUI.setVisible(false));
		}
	}

	@Override
	public void clearSelection() {
		state.clearSelection();
	}
}
