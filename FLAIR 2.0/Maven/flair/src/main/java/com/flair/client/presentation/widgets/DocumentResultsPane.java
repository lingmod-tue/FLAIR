package com.flair.client.presentation.widgets;

import java.util.HashMap;
import java.util.Map;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.DocumentResultsPaneLocale;
import com.flair.client.presentation.interfaces.AbstractDocumentResultsPane;
import com.flair.client.presentation.interfaces.AbstractResultItem;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.presentation.interfaces.CompletedResultItem;
import com.flair.client.presentation.interfaces.InProgressResultItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.jquery.client.api.Functions.Func;

public class DocumentResultsPane extends LocalizedComposite implements AbstractDocumentResultsPane
{
	private static DocumentResultsPaneUiBinder uiBinder = GWT.create(DocumentResultsPaneUiBinder.class);

	interface DocumentResultsPaneUiBinder extends UiBinder<Widget, DocumentResultsPane>
	{
	}
	
	@UiField
	MaterialPanel			pnlRootUI;
	@UiField
	MaterialTitle			lblTitleUI;
	@UiField
	MaterialRow				pnlCompletedContainerUI;
	@UiField
	MaterialLabel			lblCompletedPlaceholderUI;
	@UiField
	MaterialRow				pnlInProgressContainerUI;
	@UiField
	MaterialLabel			lblInProgressPlaceholderUI;
	
	SimpleLocalizedTextWidget<MaterialLabel>		lblCompletedPlaceholderLC;
	SimpleLocalizedTextWidget<MaterialLabel>		lblInProgressPlaceholderLC;

	State					state;
	
	private static class DisplayItem
	{
		AbstractResultItem			parent;
		DocumentResultDisplayItem	displayItem;	
		SelectHandler				selectHandler;
		
		DisplayItem(AbstractResultItem i, SelectHandler h)
		{
			parent = i;
			selectHandler = h;
			displayItem = new DocumentResultDisplayItem(parent, e -> {
				if (selectHandler != null)
					selectHandler.handle(parent);
			});
		}
	
		
		Widget getWidget() {
			return displayItem.getWidget();
		}
	}
	
	private class State
	{
		Map<AbstractResultItem, DisplayItem>		completed;
		Map<AbstractResultItem, DisplayItem>		inprogress;
		SelectHandler								selectHandler;
		
		State()
		{
			completed = new HashMap<>();
			inprogress = new HashMap<>();
			selectHandler = null;
		}
		
		private void animate(Widget w, Transition t, int delay, int duration, Func callback)
		{
			MaterialAnimation anim = new MaterialAnimation(w);
			anim.setTransition(t);
			anim.setDelayMillis(delay);
			anim.setDurationMillis(duration);
			
			if (callback != null)
				anim.animate(callback);
			else
				anim.animate();
		}
		
		private void animate(Widget w, Transition t, int delay, int duration) {
			animate(w, t, delay, duration, null);
		}
		
		private void hidePlaceholder(Widget w) {
			animate(w, Transition.FADEOUTDOWN, 10, 500, () -> w.setVisible(false));
		}
		
		private void showPlaceholder(Widget w)
		{
			w.setVisible(true);
			animate(w, Transition.FADEIN, 10, 500);
		}
		
		private void addDisplayItem(DisplayItem item, HasWidgets container)
		{
			container.add(item.getWidget());
			animate(item.getWidget(), Transition.FADEINRIGHT, 10, 600);
		}
		
		private void removeDisplayItem(DisplayItem item, HasWidgets container) {
			animate(item.getWidget(), Transition.FADEOUTLEFT, 10, 600, () -> container.remove(item.getWidget()));
		}
		
		private void clearAllDisplayItems(HasWidgets container, Widget placeholder)
		{
			animate((Widget)container, Transition.FLIPOUTX, 10, 500, () -> {
				container.clear();
				
				animate((Widget)container, Transition.FLIPINX, 0, 500, () -> {
					container.add(placeholder);
					showPlaceholder(placeholder);
				});
			});
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
		
		private Widget getPlaceholder(AbstractResultItem.Type type)
		{
			if (type == Type.IN_PROGRESS)
				return lblInProgressPlaceholderUI;
			else
				return lblCompletedPlaceholderUI;
		}
		
		public void add(AbstractResultItem.Type type, AbstractResultItem item)
		{
			Map<AbstractResultItem, DisplayItem> map = getMap(type);
			Widget placeholder = getPlaceholder(type);
			HasWidgets container = getContainer(type);
			
			if ((type == Type.IN_PROGRESS && item instanceof InProgressResultItem == false) ||
				(type == Type.COMPLETED && item instanceof CompletedResultItem == false))
			{
				throw new RuntimeException("Item type mismatch");
			}
				
			if (map.containsKey(item))
				throw new RuntimeException("Item already exists");
			
			DisplayItem d = new DisplayItem(item, selectHandler);
			map.put(item, d);
			
			hidePlaceholder(placeholder);
			addDisplayItem(d, container);
		}
		
		public void remove(AbstractResultItem.Type type, AbstractResultItem item)
		{
			Map<AbstractResultItem, DisplayItem> map = getMap(type);
			Widget placeholder = getPlaceholder(type);
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
			if (map.isEmpty())
				showPlaceholder(placeholder);
		}
		
		public void clearAll(AbstractResultItem.Type type)
		{
			Map<AbstractResultItem, DisplayItem> map = getMap(type);
			Widget placeholder = getPlaceholder(type);
			HasWidgets container = getContainer(type);
			
			if (map.isEmpty())
				return;
			
			map.clear();
			clearAllDisplayItems(container, placeholder);
		}
		
		public void setSelectHandler(SelectHandler h) {
			selectHandler = h;
		}
	}
	
	private void initLocale()
	{
		lblCompletedPlaceholderLC = new SimpleLocalizedTextWidget<>(lblCompletedPlaceholderUI, DocumentResultsPaneLocale.DESC_lblCompletedPlaceholderUI);
		lblInProgressPlaceholderLC = new SimpleLocalizedTextWidget<>(lblInProgressPlaceholderUI, DocumentResultsPaneLocale.DESC_lblInProgressPlaceholderUI);
	
		registerLocale(DocumentResultsPaneLocale.INSTANCE.en);
		registerLocale(DocumentResultsPaneLocale.INSTANCE.de);
		
		registerLocalizedWidget(lblCompletedPlaceholderLC);
		registerLocalizedWidget(lblInProgressPlaceholderLC);
		
		refreshLocalization();
	}

	public DocumentResultsPane()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
		
		state = new State();
		
		initLocale();
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
	public void setSelectHandler(SelectHandler handler) {
		state.setSelectHandler(handler);
	}

}
