package arkref.analysis;

import arkref.ext.fig.basic.Option;
import com.flair.server.utilities.ServerLogger;

import java.io.IOException;
import java.util.Properties;

public class ARKref {

	public static class Opts {
		@Option(gloss = "Take input from STDIN and produce output on STDOUT")
		public static boolean stdin = false;
		@Option(gloss = "Input document paths, with or without extentions. e.g. data/*.sent")
		public static String[] input;
		//@Option(gloss="Write mention-tagged XML sentence output to .tagged")
		//public static boolean writeTagged = false;
		@Option(gloss = "Debug output")
		public static boolean debug = false;
		@Option(gloss = "Use ACE eval pipeline")
		public static boolean ace = false;
		@Option(gloss = "Force preprocessing")
		public static boolean forcePre = false;
		@Option(gloss = "Oracle semantics ... for analysis only")
		public static boolean oracleSemantics = false;
		@Option(gloss = "Number of sentences in possible antecedent window")
		public static int sentenceWindow = 999;
		@Option(gloss = "Properties file path")
		public static String propertiesFile = "ARKref.properties";
	}


	public static boolean showDebug() {
		return Opts.debug;
	}

	public static Properties getProperties() {
		if (properties == null) {
			synchronized (ARKref.class) {
				if (properties == null) {
					properties = new Properties();
					try {
						properties.load(ARKref.class.getResourceAsStream(ARKref.Opts.propertiesFile));
					} catch (IOException e) {
						ServerLogger.get().error(e, "Couldn't load ARKref properties");
					}
				}
			}

		}

		return properties;
	}

	private static Properties properties = null;

}
