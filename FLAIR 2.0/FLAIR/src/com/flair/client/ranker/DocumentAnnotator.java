package com.flair.client.ranker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.flair.shared.RankableDocument;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/*
 * Annotates a RankableDocument's text with its constructions
 */
public class DocumentAnnotator
{
	private static class Output implements DocumentAnnotatorOutput.HighlightText
	{
		private final String		html;
		
		Output(String html) {
			this.html = html;
		}
		
		@Override
		public SafeHtml getHighlightedText() {
			return new SafeHtmlBuilder().appendEscapedLines(html).toSafeHtml();
		}
		
	}
	
	private static class Span
	{
		public final int		start;
		public final int		end;
		public final String		color;
		public String			title;
		
		Span(int s, int e, String c)
		{
			start = s;
			end = e;
			color = c;
			title = "";
		}
		
		public String getStartTag()
		{
			StringBuilder sb = new StringBuilder();
			sb.append("<span style='background-color:")
			  .append(color)
			  .append(";' title='")
			  .append(title)
			  .append("'>");
			return sb.toString();
		}
		
		public String getEndTag() 
		{
			StringBuilder sb = new StringBuilder();
			sb.append("</span>");
			return sb.toString();
		}
	}
	
	private static class OffsetData implements Comparable<OffsetData>
	{	
		public final int			offset;
		public final List<Span>		startSpans;
		public final List<Span>		endSpans;
		
		public OffsetData(int o)
		{
			offset = o;
			startSpans = new ArrayList<>();
			endSpans = new ArrayList<>();
		}
		
		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			
			// append end tags before opening start ones
			for (Span itr : endSpans)
				sb.append(itr.getEndTag());
			
			for (Span itr : startSpans)
				sb.append(itr.getStartTag());
			
			return sb.toString();
		}

		@Override
		public int compareTo(OffsetData o) {
			return Integer.compare(offset, o.offset);
		}

	}
	
	private static class Annotations
	{
		private final Map<String, Span>			spanMap = new HashMap<>();		// (unique) start-end indices > spans
		private final Map<Integer, OffsetData>	offsetMap = new HashMap<>();	// offset > offset data
		
		private String genKey(int start, int end) {
			return "" + start + "-" + end;
		}
		
		public void accumulate(int start, int end, String color, String title)
		{
			// merge spans that have the same start and end indices
			String key = genKey(start, end);
			Span existing = spanMap.get(key);
			boolean newSpan = false;
			if (existing == null)
			{
				existing = new Span(start, end, color);
				spanMap.put(key, existing);
				newSpan = true;
			}
			
			existing.title += " " + title;
			
			// map to offsets
			OffsetData startOd = offsetMap.get(start), endOd = offsetMap.get(end);
			if (startOd == null)
			{
				startOd = new OffsetData(start);
				offsetMap.put(start, startOd);
			}
			
			if (endOd == null)
			{
				endOd = new OffsetData(end);
				offsetMap.put(end, endOd);
			}
			
			if (newSpan)
			{
				startOd.startSpans.add(existing);
				endOd.endSpans.add(existing);
			}
		}
		
		public List<OffsetData> calcOffsets()
		{
			List<OffsetData> out = new ArrayList<>();
			
			for (OffsetData itr : offsetMap.values())
			{
				// sort spans descendingly by the end/start offsets respectively to preserve order
				Collections.sort(itr.startSpans, (a, b) -> {
					return -Integer.compare(a.end, b.end);
				});
				
				Collections.sort(itr.endSpans, (a, b) -> {
					return -Integer.compare(a.start, b.start);
				});
				
				out.add(itr);
			}

			// sort the offsets ascendingly
			Collections.sort(out);
			return out;
		}
	}
	
	private Annotations collateSpans(DocumentAnnotatorInput.HighlightText input)
	{
		Annotations out = new Annotations();
		
		for (GrammaticalConstruction itr : input.getAnnotationConstructions())
		{
			String color = input.getAnnotationColor(itr);
			String title = input.getConstructionTitle(itr);
			for (RankableDocument.ConstructionRange range : input.getDocument().getConstructionOccurrences(itr))
				out.accumulate(range.getStart(), range.getEnd(), color, title);
		}
			
		if (input.shouldAnnotateKeywords())
		{
			String color = input.getKeywordAnnotationColor();
			String title = input.getKeywordTitle();
			for (RankableDocument.KeywordRange range : input.getDocument().getKeywordOccurrences())
				out.accumulate(range.getStart(), range.getEnd(), color, title);
		}
		
		return out;
	}
	
	public DocumentAnnotatorOutput.HighlightText hightlightText(DocumentAnnotatorInput.HighlightText input)
	{
		// collate and sort spans
		Annotations annotations = collateSpans(input);
		String docText = input.getDocument().getText();
		List<OffsetData> offsets = annotations.calcOffsets();
		
		if (offsets.isEmpty())
			return new Output(docText);
		
		// ### we're assuming that spans don't overlap
		// ### meaning this shouldn't happen: <span1>...<span2>...</span1>...</span2>
		StringBuilder sb = new StringBuilder();
		int lastOffset = 0, offsetCount = offsets.size();
		OffsetData currentOffset;
		
		for (int i = 0; i < offsetCount; i++)
		{
			currentOffset = offsets.get(i);
			// extract text from the previous offset to current and append the current's spans
			sb.append(docText.substring(lastOffset, currentOffset.offset))
			  .append(currentOffset.toString());
			
			lastOffset = currentOffset.offset;
		}
		
		// copy the last bits to the buffer, if any
		if (lastOffset < docText.length())
			sb.append(docText.substring(lastOffset, docText.length()));
		
		return new Output(sb.toString());
	}
}
