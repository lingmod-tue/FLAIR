package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.DocumentCollectionVisualizerLocale;
import com.flair.client.localization.locale.GrammaticalConstructionLocale;
import com.flair.client.presentation.interfaces.VisualizerService;
import com.flair.client.presentation.widgets.sliderbundles.ConstructionSliderBundleEnglish;
import com.flair.client.presentation.widgets.sliderbundles.ConstructionSliderBundleGerman;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.RankableDocument;
import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.D3Event;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.behaviour.Drag.DragEventType;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.dsv.DsvRow;
import com.github.gwtd3.api.dsv.DsvRows;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.svg.Brush;
import com.github.gwtd3.api.svg.Brush.BrushEvent;
import com.github.gwtd3.api.svg.Line;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.splitpanel.MaterialSplitPanel;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.animate.MaterialAnimation;

public class DocumentCollectionVisualizer extends LocalizedComposite implements VisualizerService
{
	private static final Margin			MARGINS = new Margin(55, 5, 10, 10);
	private static final int			WIDTH = 990;
	private static final int			HEIGHT = 660;
	
	enum DefaultDimension
	{
		RESULT,
		NUM_WORDS,
		NUM_SENTENCES,
		COMPLEXITY,
		KEYWORDS,
	}
	
	final class State
	{
		Input							input;
		int								width;
		int								height;
		OrdinalScale					x;
		Map<String, LinearScale>		y;				// axis -> scale
		Map<String, Brush>				brushes;		// axis -> brush
		Map<String, Coords>				dragCoords;		// axis -> X coord
		Line							line;
		Axis							axis;
		Selection						background;
		Selection						foreground;
		Array<String>					dimensions;		// as read in from the CSV header, used as y axes
		Selection						svg;
		Selection						group;
		DsvRows<DsvRow>					cache;			// the parsed CSV string
		Set<String>						selectedAxes;	// axes displayed in the visualization
		
		LanguageSpecificConstructionSliderBundle	toggles;
		VisualizerService.ApplyFilterHandler		filterHandler;
		VisualizerService.ResetFilterHandler		resetHandler;
		
		RankableDocument getDoc(int rank)
		{
			for (RankableDocument itr : input.getDocuments())
			{
				if (itr.getRank() == rank)
					return itr;
			}
			
			return null;
		}
		
		void toggleAxis(String dim, boolean state, boolean reload)
		{
			if (state)
				selectedAxes.add(dim);
			else
				selectedAxes.remove(dim);
			
			if (reload)
				reload(false);
		}
		
		String getConstructionDimesionId(GrammaticalConstruction gram) {
			return gram.getID();
		}
		
		String getDefaultDimensionId(DefaultDimension dim)
		{
			switch (dim)
			{
			case COMPLEXITY:
				return getLocalizedString(DocumentCollectionVisualizerLocale.DESC_axisComplexity);
			case NUM_SENTENCES:
				return getLocalizedString(DocumentCollectionVisualizerLocale.DESC_axisSentences);
			case NUM_WORDS:
				return getLocalizedString(DocumentCollectionVisualizerLocale.DESC_axisWords);
			case RESULT:
				return getLocalizedString(DocumentCollectionVisualizerLocale.DESC_axisResult);
			case KEYWORDS:
				return getLocalizedString(DocumentCollectionVisualizerLocale.DESC_axisKeywords);
			default:
				return "";
			}
		}
		
		String generateFrequencyTable()
		{
			// construct CSV string
			StringBuilder writer = new StringBuilder();
			
			// the header first
			// the dimensions need to be mapped to unique IDs/names as we need to look them up later
			// we can use localized names as long as the interface language doesn't change (and they don't contain commas)
			writer.append(getDefaultDimensionId(DefaultDimension.RESULT)).append(",");
			for (GrammaticalConstruction itr : input.getConstructions())
					writer.append(getConstructionDimesionId(itr)).append(",");
			
			writer.append(getDefaultDimensionId(DefaultDimension.KEYWORDS))
				.append(",")
				.append(getDefaultDimensionId(DefaultDimension.NUM_SENTENCES))
				.append(",")
				.append(getDefaultDimensionId(DefaultDimension.NUM_WORDS))
				.append(",")
				.append(getDefaultDimensionId(DefaultDimension.COMPLEXITY))
				.append("\n");

			// the rest comes next
			for (RankableDocument itr : input.getDocuments())
			{
				int id = itr.getRank();
				writer.append("" + id + ",");
				for (GrammaticalConstruction gramConst : input.getConstructions())
				{
					Double relFreq = itr.getConstructionRelFreq(gramConst);
					writer.append(relFreq.toString() + ",");
				}

				double keywordRelFreq = itr.getKeywordRelFreq();
				writer.append("" + keywordRelFreq + "," + itr.getNumSentences() + "," + itr.getNumWords() + ","
						+ itr.getReadablilityScore());
				
				writer.append("\n");
			}

			return writer.toString();
		}
		
		int getXPosition(String dim)
		{
			int out = 0;
			if (dragCoords.containsKey(dim))
				out = (int)dragCoords.get(dim).x();
			else
				out = x.apply(dim).asInt();
			
			return out;
		}
		
		Transition getTransition(Selection in) {
			return in.transition().duration(500);
		}
		
		String getPathString(Value val)
		{
			// returns the path for a given data point
			Array<?> out = dimensions.map((t, e, i, a) -> {
				String dim = e.asString();
				return Array.fromDoubles(getXPosition(dim),
							y.get(dim).apply(+val.getProperty(dim).asDouble()).asDouble());
			});
			
			String path = line.generate(out);
			return path;
		}
		
		void onBrushStart()
		{
			D3Event d3Event = D3.event();
			d3Event.sourceEvent().stopPropagation();
		}
		
		void onBrush()
		{
			// handles a brush event, toggling the display of foreground lines
			Array<String> actives = dimensions.filter((t, e, i, a) -> {
				String dim = e.asString();
				return !brushes.get(dim).empty();
			});
			
			Array<Array<Double>> extents = actives.map((t,e,i,a) -> {
				String dim = e.asString();
				return brushes.get(dim).extent();
			});
			
			foreground.style("display", (c,d,x) -> {
				return actives.every((t,e,i,a) -> {
					String dim = e.asString();
					double e1 = d.getProperty(dim).asDouble();
					return extents.get(i).getNumber(0) <= e1 && e1 <= extents.get(i).getNumber(1);
				}) ? "initial" : "none";
			});
		}
		
		void reload(boolean full)
		{
			// clear up previous state
			if (svg != null)
				svg.selectAll("*").remove();
			else
			{
				svg = D3.select(pnlSVGContainerUI.getElement())
						.append("svg")
						.attr("width", width + MARGINS.left + MARGINS.right)
						.attr("height", height + MARGINS.top + MARGINS.bottom)
						.append("g")
						.attr("transform", "translate(" + MARGINS.left + "," + MARGINS.top + ")");
			}
			
			group = null;
			brushes.clear();
			y.clear();
			brushes.clear();
			dragCoords.clear();
			
			// generate CSV data from the input and reset cache if necessary
			if (full)
				cache = D3.csv().parse(generateFrequencyTable());
			
			// extract the list of dimensions and create a scale for each
			dimensions = D3.keys(cache.get(0)).filter((t, e, i, a) -> {
				String dim = e.asString();
				
				// skip if the dimension isn't selected
				if (selectedAxes.contains(dim) == false)
					return false;
				
				// add scale
				JsArrayMixed extents = Arrays.extent(cache, (d, idx) -> {
					DsvRow row = (DsvRow)d;
					return +row.get(dim).asDouble();
				});
				
				y.put(dim, D3.scale.linear()
								.domain(extents)
								.range(0, height));
				
				return true;
			});
			
			x.domain(dimensions);
			
			// add grey background lines for context
			background = svg.append("g")
							.attr("class", "background")
							.selectAll("path")
							.data(cache)
							.enter()
							.append("path")
							.attr("d", (c,d,i) -> getPathString(d))
							.attr("stroke-width", 2);
			
			// add blue foreground lines for focus
			foreground = svg.append("g")
							.attr("class", "foreground")
							.selectAll("path")
							.data(cache)
							.enter()
							.append("path")
							.attr("d", (c,d,i) -> getPathString(d))
							.attr("stroke-width", 2);
			
			// add a group element for each dimension
			group = svg.selectAll(".dimension")
							  .data(dimensions)
							  .enter()
							  .append("g")
							  .attr("class", "dimension")
							  .attr("transform", (c,d,i) -> {
								  int pos = getXPosition(d.asString());
								  return "translate(" + pos + ")";
							  })
							  .call(D3.behavior().drag()
									  			 .origin((c,d,i) -> {
									  				 int startX = getXPosition(d.asString());
									  				 return Coords.create(startX, 0);
									  			 })
									  			 .on(DragEventType.DRAGSTART, (c,d,i) -> {
									  				String dim = d.asString();
									  				
									  				int startX = getXPosition(dim);
									  				dragCoords.put(dim, Coords.create(startX, 0));
									  				background.attr("visibility", "hidden");
									  				return null;
									  			 })
									  			 .on(DragEventType.DRAG, (c,d,i) -> {
									  				String dim = d.asString();
									  				Coords coords = dragCoords.get(dim);
									  				
									  				// update x coords and sort dimension based on position
									  				D3Event d3Event = D3.event();
									  				int delta = (int) D3.eventAsDCoords().x();
									  				int oldX = (int) coords.x();
									  				int newX = Math.min(width, Math.max(0, oldX + delta));
									  				coords.x(newX);
									  				
									  				foreground.attr("d", (c1,d1,i1) -> getPathString(d1));
									  				// sort the dimensions by position along the x-axis
									  				// workaround for d3gwt's incomplete ArrayList API
									  				List<String> buffer = new ArrayList<>();
									  				for (Value itr : dimensions.asIterable())
									  					buffer.add(itr.asString());
									  					
									  				Collections.sort(buffer, (a, b) -> {
									  					return getXPosition(a) - getXPosition(b);
									  				});
									  				
									  				dimensions = Array.fromIterable(buffer);
									  				x.domain(dimensions);
									  				
									  				group.attr("transform", (c1,d1,i1) -> {
									  					return "translate(" + getXPosition(d1.asString()) + ")";
									  				});
									  				
									  				return null;
									  			 })
									  			 .on(DragEventType.DRAGEND, (c,d,i) -> {
									  				String dim = d.asString();
									  				
									  				dragCoords.remove(dim);
									  				
									  				getTransition(D3.select(c)).attr("transform", "translate(" + x.apply(dim).asInt() + ")");
									  				getTransition(foreground).attr("d", (c1,d1,i1) -> getPathString(d1));
									  				background.attr("d", (c1,d1,i1) -> getPathString(d1))
									  						  .transition()
									  						  .delay(100)
									  						  .duration(100)
									  						  .attr("visibility", "visible");
									  				
									  				return null;
									  			 }));
			
			// add axis and titles
			group.append("g")
				 .attr("class", "axis")
				 .each((c,d,i) -> {
					 D3.select(c).call(axis.scale(y.get(d.asString())));
					 return null;
				 })
				 .append("text")
				 .style("text-anchor", "middle")
				 .style("font-size", "14px")
				 .style("font-weight", "bold")
				 .attr("y", -25)
				 .attr("transform", "rotate(-20)")
				 .text((c,d,i) -> {
					 GrammaticalConstruction gram = GrammaticalConstruction.lookup(d.asString());
					 if (gram != null)
					 {
						 // get the localized string and format it a bit
						 String name = GrammaticalConstructionLocale.get().getLocalizedName(gram, localeCore.getLanguage());
						 int delimiter = name.indexOf("(");
						 if (delimiter == -1)
							 return name;
						 else
							 return name.substring(0, delimiter);
					 }
					 else
						 return d.asString();	// for the default dimensions, use the same text as it's already localized
				 });
			
			// store brushes for each axis
			group.append("g")
				 .attr("class", "brush")
				 .each((c,d,i) -> {
					 String dim = d.asString();
					 Brush brush = D3.svg().brush()
							 			   .y(y.get(dim))
							 			   .on(BrushEvent.BRUSH_START, (cx,dx,ix) -> {
							 				  onBrushStart();
							 				  return null;
							 			   })
							 			   .on(BrushEvent.BRUSH, (cx,dx,ix) -> {
							 				  onBrush();
							 				  return null;
							 			   });
					 
					 brushes.put(dim, brush);
					 D3.select(c).call(brush);
					 return null;
				 })
				 .selectAll("rect")
				 .attr("x", -8)
				 .attr("width", 16);
		}
		
		List<RankableDocument> getFilteredDocs()
		{
			List<RankableDocument> out = new ArrayList<>();
			// go through "path" in "svg", select those withOUT style=display:none
			svg.selectAll("path")
			  .each((c,d,i) -> {
				  if (c.getStyle().getDisplay() == Display.NONE.getCssName())
				  {
					  String resultDim = getDefaultDimensionId(DefaultDimension.RESULT);
					  int rank = d.getProperty(resultDim).asInt();
					  out.add(getDoc(rank));
				  }
				  
				  return null;
			  });
			
			return out;
		}
		
		void resetSelectedAxes()
		{
			selectedAxes.clear();
			
			// add the default dimensions
			toggleDefaultAxis(DefaultDimension.RESULT, true, false);
			toggleDefaultAxis(DefaultDimension.NUM_WORDS, true, false);
		}
		
		void doInit(Input i)
		{
			input = i;
			
			chkAxisWordsUI.setValue(true, false);
			chkAxisSentencesUI.setValue(false, false);
			chkAxisComplexityUI.setValue(false, false);
			chkAxisKeywordsUI.setValue(false, false);
			
			bdlEnglishSlidersUI.setVisible(false);
			bdlGermanSlidersUI.setVisible(false);
			
			resetSelectedAxes();
			
			// setup sliders
			switch (input.getSliders().getLanguage())
			{
			case ENGLISH:
				toggles = bdlEnglishSlidersUI;
				break;
			case GERMAN:
				toggles = bdlGermanSlidersUI;
				break;
			}
			
			toggles.setVisible(true);
			toggles.forEachWeightSlider(n -> {
				GrammaticalConstructionWeightSlider o = input.getSliders().getWeightSlider(n.getGram());
				n.setEnabled(false, false);
				n.setSliderVisible(false);
				n.setResultCountVisible(false);
				n.refreshLocalization();
				n.setWeight(o.getWeight(), false);
				
				// toggle axis if the slider is weighted
				if (o.hasWeight())
				{
					String dim = getConstructionDimesionId(n.getGram());
					toggleAxis(dim, true, false);
					n.setEnabled(true, false);
				}
				
				n.setToggleHandler((w, e) -> {
					GrammaticalConstructionWeightSlider slider = (GrammaticalConstructionWeightSlider)w;
					String dim = getConstructionDimesionId(slider.getGram());
					toggleAxis(dim, e, true);
					
					// set/reset the weights of the original sliders
					GrammaticalConstructionWeightSlider old = input.getSliders().getWeightSlider(slider.getGram());
					if (e)
						old.setWeight(GenericWeightSlider.getSliderMax(), true);
					else
						old.setWeight(GenericWeightSlider.getSliderMin(), true);
				});
				
				n.setResetHandler((w,e) -> {
					// turn off axis
					w.setEnabled(false, e);
				});
			});
		}
		
		State()
		{
			input = null;
			
			width = WIDTH - MARGINS.left - MARGINS.right;
			height = HEIGHT - MARGINS.top - MARGINS.bottom;
			
			x = D3.scale.ordinal().rangePoints(0, width, 1);
			y = new HashMap<>();
			brushes = new HashMap<>();
			dragCoords = new HashMap<>();
			line = D3.svg().line();
			axis = D3.svg().axis().orient(Orientation.LEFT);
			background = foreground = null;
			dimensions = null;
			
			svg = null;
			group = null;
			cache = null;
			selectedAxes = new HashSet<>();
			toggles = null;
			filterHandler = null;
			resetHandler = null;
		}
		
		public void resetSvg()
		{
			if (svg != null)
				svg.selectAll("*").remove();
		}
		
		public void init(Input i)
		{
			doInit(i);
			reload(true);
		}
		
		public void resetFilter()
		{
			chkAxisWordsUI.setValue(true, false);
			chkAxisSentencesUI.setValue(false, false);
			chkAxisComplexityUI.setValue(false, false);
			chkAxisKeywordsUI.setValue(false, false);
			
			toggles.resetState(false);
			resetSelectedAxes();
			reload(false);
			
			if (resetHandler != null)
				resetHandler.handle();
		}
		
		public void applyFilter()
		{
			List<RankableDocument> filtered = getFilteredDocs();
			
			if (filtered.isEmpty())
				MaterialToast.fireToast(getLocalizedString(DocumentCollectionVisualizerLocale.DESC_NoFilteredDocs));
			else
			{
				String msg = getLocalizedString(DocumentCollectionVisualizerLocale.DESC_HasFilteredDocs	) + ": " + filtered.size();
				MaterialToast.fireToast(msg);
			}
			
			if (filterHandler != null)
				filterHandler.handle(filtered);
		}
		
		public void toggleDefaultAxis(DefaultDimension dim, boolean state, boolean reload)
		{
			String name = getDefaultDimensionId(dim);
			toggleAxis(name, state, reload);
		}
		
		public void setFilterHandler(ApplyFilterHandler handler) {
			filterHandler = handler;
		}
		
		public void setResetHandler(ResetFilterHandler handler) {
			resetHandler = handler;
		}
	}
	
	
	private static DocumentCollectionVisualizerUiBinder uiBinder = GWT
			.create(DocumentCollectionVisualizerUiBinder.class);

	interface DocumentCollectionVisualizerUiBinder extends UiBinder<Widget, DocumentCollectionVisualizer>
	{
	}

	@UiField
	MaterialWindow				mdlVisualizerUI;
	@UiField
	MaterialSplitPanel			pnlSplitContainer;
	@UiField
	MaterialLabel				lblTitleUI;
	@UiField
	FlowPanel					pnlSVGContainerUI;
	@UiField
	MaterialButton				btnResetUI;
	@UiField
	MaterialCheckBox			chkAxisWordsUI;
	@UiField
	MaterialCheckBox			chkAxisSentencesUI;
	@UiField
	MaterialCheckBox			chkAxisComplexityUI;
	@UiField
	MaterialCheckBox			chkAxisKeywordsUI;
	@UiField
	MaterialRow					pnlToggleContainerUI;
	@UiField
	ConstructionSliderBundleEnglish		bdlEnglishSlidersUI;
	@UiField
	ConstructionSliderBundleGerman		bdlGermanSlidersUI;
	@UiField
	MaterialButton				btnApplyUI;
	
	SimpleLocalizedTextWidget<MaterialLabel>			lblTitleLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnResetLC;
	SimpleLocalizedTextWidget<MaterialCheckBox>			chkAxisWordsLC;
	SimpleLocalizedTextWidget<MaterialCheckBox>			chkAxisSentencesLC;
	SimpleLocalizedTextWidget<MaterialCheckBox>			chkAxisComplexityLC;
	SimpleLocalizedTextWidget<MaterialCheckBox>			chkAxisKeywordsLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnApplyLC;
	
	State	state;
	
	private void initLocale()
	{
		lblTitleLC = new SimpleLocalizedTextWidget<>(lblTitleUI, DocumentCollectionVisualizerLocale.DESC_lblTitleCaptionUI);
		btnResetLC = new SimpleLocalizedTextButtonWidget<>(btnResetUI, DocumentCollectionVisualizerLocale.DESC_btnResetUI);
		chkAxisWordsLC = new SimpleLocalizedTextWidget<>(chkAxisWordsUI, DocumentCollectionVisualizerLocale.DESC_chkAxisWordsUI);
		chkAxisSentencesLC = new SimpleLocalizedTextWidget<>(chkAxisSentencesUI, DocumentCollectionVisualizerLocale.DESC_chkAxisSentencesUI);
		chkAxisComplexityLC = new SimpleLocalizedTextWidget<>(chkAxisComplexityUI, DocumentCollectionVisualizerLocale.DESC_chkAxisComplexityUI);
		chkAxisKeywordsLC = new SimpleLocalizedTextWidget<>(chkAxisKeywordsUI, DocumentCollectionVisualizerLocale.DESC_chkAxisKeywordsUI);
		btnApplyLC = new SimpleLocalizedTextButtonWidget<>(btnApplyUI, DocumentCollectionVisualizerLocale.DESC_btnApplyUI);

		registerLocale(DocumentCollectionVisualizerLocale.INSTANCE.en);
		registerLocale(DocumentCollectionVisualizerLocale.INSTANCE.de);
		
		registerLocalizedWidget(lblTitleLC);
		registerLocalizedWidget(btnResetLC);
		registerLocalizedWidget(chkAxisWordsLC);
		registerLocalizedWidget(chkAxisSentencesLC);
		registerLocalizedWidget(chkAxisComplexityLC);
		registerLocalizedWidget(chkAxisKeywordsLC);
		registerLocalizedWidget(btnApplyLC);
	}
	
	private void initHandlers()
	{
		btnResetUI.addClickHandler(e -> state.resetFilter());
		
		chkAxisWordsUI.addValueChangeHandler(e -> {
			state.toggleDefaultAxis(DefaultDimension.NUM_WORDS, e.getValue(), true);
		});
		chkAxisSentencesUI.addValueChangeHandler(e -> {
			state.toggleDefaultAxis(DefaultDimension.NUM_SENTENCES, e.getValue(), true);
		});
		chkAxisComplexityUI.addValueChangeHandler(e -> {
			state.toggleDefaultAxis(DefaultDimension.COMPLEXITY, e.getValue(), true);
		});
		chkAxisKeywordsUI.addValueChangeHandler(e -> {
			state.toggleDefaultAxis(DefaultDimension.KEYWORDS, e.getValue(), true);
		});
		
		btnApplyUI.addClickHandler(e -> {
			state.applyFilter();
			hide();
		});
	}
	
	private void initUI()
	{
		MaterialAnimation open = new MaterialAnimation();
		open.setTransition(gwt.material.design.client.ui.animate.Transition.FADEINDOWN);
		open.setDelay(0);
		open.setDuration(500);
		mdlVisualizerUI.setOpenAnimation(open);
		
		MaterialAnimation close = new MaterialAnimation();
		close.setTransition(gwt.material.design.client.ui.animate.Transition.FADEOUTUP);
		close.setDelay(10);
		close.setDuration(500);
		mdlVisualizerUI.setCloseAnimation(close);
		
		// hide the maximize button
		mdlVisualizerUI.getIconMaximize().setVisible(false);
	}
	
	public DocumentCollectionVisualizer()
	{
		super(LocalizationEngine.get());
		initWidget(uiBinder.createAndBindUi(this));
		
		state = new State();
		
		initLocale();
		initHandlers();
		initUI();
	}
	
	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		
		mdlVisualizerUI.setTitle(getLocalizedString(DocumentCollectionVisualizerLocale.DESC_lblTitleUI));
	}

	@Override
	public void show()
	{
		MaterialWindow.setOverlay(true);
		mdlVisualizerUI.open();
		// ### TODO invalidate the split panel to force re-layout
		// workaround for the bug where the layout is all buggered up when the client area is resized when th window was closed
	}

	@Override
	public void hide()
	{
		mdlVisualizerUI.close();
		state.resetSvg();
	}

	@Override
	public void visualize(Input input) {
		state.init(input);
	}

	@Override
	public void setApplyFilterHandler(ApplyFilterHandler handler) {
		state.setFilterHandler(handler);
	}

	@Override
	public void setResetFilterHandler(ResetFilterHandler handler) {
		state.setResetHandler(handler);
	}

}

final class Margin
{
	int		top;
	int		bottom;
	int		left;
	int		right;
	
	Margin(int t, int b, int l, int r)
	{
		top = t;
		bottom = b;
		left = l;
		right = r;
	}
}