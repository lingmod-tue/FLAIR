package com.flair.client.model;

import com.flair.client.model.interfaces.ConstructionSettingsProfile;
import com.flair.client.model.interfaces.SettingsExportService;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.parser.DocumentReadabilityLevel;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

final class SettingsUrlExporter implements SettingsExportService {
	private final String PARAM_SIGIL = "encodedSettings";
	private final String PARAM_LANGUAGE = "lang";
	private final String PARAM_DOCLEVEL_A = "docLevelA";
	private final String PARAM_DOCLEVEL_B = "docLevelB";
	private final String PARAM_DOCLEVEL_C = "docLevelC";
	private final String PARAM_KEYWORDS = "keywords";
	private final String PARAM_DOCLENGTH = "docLength";

	private final String PARAM_VAL_SEPARATOR = "_";

	private String generateParamVal(boolean enabled, int weight) {
		return enabled + PARAM_VAL_SEPARATOR + weight;
	}

	private boolean isWeightEnabled(String paramVal) {
		int idx = paramVal.indexOf(PARAM_VAL_SEPARATOR);
		if (idx == -1)
			return false;
		else
			return paramVal.substring(0, idx).equalsIgnoreCase("true") ? true : false;
	}

	private int getWeight(String paramVal) {
		int idx = paramVal.indexOf(PARAM_VAL_SEPARATOR);
		if (idx == -1)
			return 0;
		else
			return Integer.parseInt(paramVal.substring(idx + 1, paramVal.length()));
	}

	private String getConstructionTag(GrammaticalConstruction gram) {
		return gram.getID();
	}

	private void buildUrl(StringBuilder sb, String param, String value) {
		sb.append(param).append("=").append(value).append("&");
	}

	@Override
	public ConstructionSettingsProfile importSettings() {
		ConstructionSettingsProfileImpl out = null;

		// check for the export sigil
		if (Window.Location.getParameter(PARAM_SIGIL) != null) {
			String ls = Window.Location.getParameter(PARAM_LANGUAGE);
			if (ls != null) {
				Language l = Language.fromString(ls);
				out = new ConstructionSettingsProfileImpl();

				out.setLanguage(l);

				String lvla = Window.Location.getParameter(PARAM_DOCLEVEL_A),
						lvlb = Window.Location.getParameter(PARAM_DOCLEVEL_B),
						lvlc = Window.Location.getParameter(PARAM_DOCLEVEL_C),
						kw = Window.Location.getParameter(PARAM_KEYWORDS),
						docl = Window.Location.getParameter(PARAM_DOCLENGTH);

				if (lvla != null)
					out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_A, lvla.equalsIgnoreCase("true") ? true : false);

				if (lvlb != null)
					out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_B, lvlb.equalsIgnoreCase("true") ? true : false);

				if (lvlc != null)
					out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_C, lvlc.equalsIgnoreCase("true") ? true : false);

				if (kw != null) {
					boolean enabled = isWeightEnabled(kw);
					int weight = getWeight(kw);

					out.getKeywords().setEnabled(enabled);
					out.getKeywords().setWeight(weight);
				}

				if (docl != null)
					out.setDocLengthWeight(Integer.parseInt(docl));

				// run through all of the lang's grams
				for (GrammaticalConstruction itr : GrammaticalConstruction.getForLanguage(l)) {
					String g = Window.Location.getParameter(getConstructionTag(itr));
					if (g != null)
						out.setGramData(itr, isWeightEnabled(g), getWeight(g));
				}
			}
		}

		return out;
	}

	@Override
	public String exportSettings(ConstructionSettingsProfile settings) {
		StringBuilder sb = new StringBuilder();

		// append the base URL
		sb.append(Window.Location.getHost())
				.append(Window.Location.getPath())
				.append("?");

		// append sigil, language, doc levels and keyword settings
		buildUrl(sb, PARAM_SIGIL, "1");

		buildUrl(sb, PARAM_LANGUAGE, settings.getLanguage().toString());
		buildUrl(sb, PARAM_DOCLENGTH, "" + settings.getDocLengthWeight());
		buildUrl(sb, PARAM_DOCLEVEL_A, "" + settings.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_A));
		buildUrl(sb, PARAM_DOCLEVEL_B, "" + settings.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_B));
		buildUrl(sb, PARAM_DOCLEVEL_C, "" + settings.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_C));
		buildUrl(sb, PARAM_KEYWORDS, generateParamVal(settings.isKeywordsEnabled(), settings.getKeywordsWeight()));

		// append all of the language's gram constructions that have non-default values (either disabled or have non-zero weights)
		for (GrammaticalConstruction itr : GrammaticalConstruction.getForLanguage(settings.getLanguage())) {
			if (settings.hasConstruction(itr)) {
				boolean enabled = settings.isConstructionEnabled(itr);
				int weight = settings.getConstructionWeight(itr);

				if (enabled == false || weight != 0)
					buildUrl(sb, getConstructionTag(itr), generateParamVal(enabled, weight));
			}
		}

		// remove the trailing ampersand and encode the URL
		String out = sb.toString();
		return URL.encode(out.substring(0, out.length() - 1));
	}

}
