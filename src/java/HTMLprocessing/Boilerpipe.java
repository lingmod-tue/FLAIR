/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HTMLprocessing;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import parsing.Constants;

/**
 *
 * @author Maria
 */
public class Boilerpipe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jennifer_Lawrence");

        for (String name : names) {

            try {
                // do it for every .txt file in directory top50peopleURLs
                String filename = name.replaceAll(" ", "_") + ".txt";
                String filenameFull = System.getProperty("user.dir") + "/" + filename;
                List<String> links = new ArrayList<>();
                int count = 1;

                FileReader fr = null;
                BufferedReader br = null;

                fr = new FileReader(filenameFull);
                br = new BufferedReader(fr);
                String line = "";

                // copy into a list
                while ((line = br.readLine()) != null) {
                    links.add(line.trim());
                }

                String pathname = Constants.PATH_TO_RESULTS + filename.substring(0, filename.indexOf(".txt"));

                // create directory
                File dir = new File(pathname);
                dir.mkdir();

                // for each url
                for (String link : links) {

                    String outfile = pathname + "/" + String.format("%03d", count) + ".txt";
                    FileWriter fw = null;
                    BufferedWriter bw = null;

                    try {
                        // TODO code application logic here

                        URL url = new URL(link);

//                        // force utf8
//                        InputSource is = new InputSource(); // changed!
//                        is.setEncoding("ISO-8859-1"); // changed!
//                        is.setByteStream(url.openStream()); // changed!

                        // NOTE: Use ArticleExtractor unless DefaultExtractor gives better results for you
                        //String text = ArticleExtractor.INSTANCE.getText(url);
                        String text = DefaultExtractor.INSTANCE.getText(url); // changed!

                        // Facilitate parsing by putting a full stop at the end of the line
                        // (not a good idea for song lyrics!)
                        StringBuilder newText = new StringBuilder();

                        String[] sents = text.split("\n");
                        for (String s : sents) {
                            newText.append(s);
                            if (!(s.endsWith(".") || s.endsWith("!") || s.endsWith("?") || s.endsWith("\""))) {
                                newText.append(".\n");
                            } else {
                                newText.append("\n");
                            }
                        }

                        System.out.println(link);

                        fw = new FileWriter(outfile);
                        bw = new BufferedWriter(fw);

                        bw.write(newText.toString());
                        bw.close();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BoilerpipeProcessingException ex) {
                        continue;
                        //Logger.getLogger(Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    count++;

                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
