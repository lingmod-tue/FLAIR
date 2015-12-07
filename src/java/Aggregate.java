
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static parsing.Constants.NUM_OF_RESULTS;
import parsing.Construction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Maria
 */
public class Aggregate {
    
    String dirCsvPath; // the directory with csv files
    HashMap<String,Construction> constructions;
    
    public Aggregate() {
        dirCsvPath = "";
    }
    
    public Aggregate(String filepath) {
        this();
        dirCsvPath = filepath;
        writeFromCsv();
    }
    

    public Aggregate(HashMap<String,Construction> constructions) {
        this();
        this.constructions = constructions;
    }
    
    
    private void writeFromObjects() {
        
    }
    
    
    
    private void writeFromCsv() {
        ArrayList<Construction> constructions = new ArrayList<>();

        String path = this.dirCsvPath;
        File f = new File(path);
        File[] allFiles = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return !name.toLowerCase().startsWith(".");
            }
        });

        for (File aFile : allFiles) {

            FileReader fr = null;
            BufferedReader br = null;
            try {

                fr = new FileReader(aFile);
                br = new BufferedReader(fr);
                String line = "";
                while ((line = br.readLine()) != null) {
//                        System.out.println(line);
                    String[] tokens = line.split(",");
                    String name = tokens[0];

                    double[] values = new double[NUM_OF_RESULTS];

//                        System.out.println(tokens.length + " // " + values.length);
                    for (int i = 1; i < tokens.length; i++) {
                        values[i - 1] = (double) Double.valueOf(tokens[i]);
                    }

                    boolean found = false;
                    int ind = -1;
                    for (int k = 0; k < constructions.size(); k++) {
                        if (name.equalsIgnoreCase(constructions.get(k).getName())) {
                            found = true;
                            ind = k;
                        }
                    }
                    // if this construction is not on the list yet
                    if (!found) {
                        Construction c = new Construction(name);
                        c.setDocRelFrequencies(values);
                        constructions.add(c);
                    } else { // if it is already on the list "constructions"
                        Construction c = constructions.remove(ind);
                        double[] newValues = new double[NUM_OF_RESULTS];
                        for (int l = 0; l < NUM_OF_RESULTS; l++) {
                            double value = c.getDocRelFrequencies()[l];
                            newValues[l] = value + values[l];
                        }
                        c.setDocRelFrequencies(newValues);
                        constructions.add(c);
                    }

                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Aggregate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Aggregate.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(Aggregate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        for (Construction construction : constructions) {
            int size = constructions.size();
            double[] freqs = construction.getDocRelFrequencies();
            for (int i = 0; i < freqs.length; i++) {
                freqs[i] = freqs[i] / (double) size;
            }
            construction.setDocRelFrequencies(freqs);
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            
            String finalOut = "[";

            for (Construction c : constructions) {
                if (c.getName().equalsIgnoreCase("construction")) {
                    continue;
                }
                double[] vals = c.getDocRelFrequencies();
                String output = "{\"construction\":\"" + c.getName() + "\",\"docs\":[";
                for (int i = 0; i < NUM_OF_RESULTS; i++) {
                    output += "[" + (i + 1) + "," + vals[i] + "],";
                }
                output = output.substring(0, output.length() - 1) + "]},\n";
                finalOut += output;
            }
            
            finalOut = finalOut.trim().substring(0,finalOut.lastIndexOf(",")) + "]";
            
            
            fw = new FileWriter("/Users/Maria/Sites/FLAIR_Visual/public_html/json/Jennifer_Lawrence.json");
            bw = new BufferedWriter(fw);
            
            
            bw.write(finalOut);
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(Aggregate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public static void main(String[] args) {

        String path = "/Users/Maria/Sites/FLAIR_Visual/public_html/csv";
        Aggregate agg = new Aggregate(path);

    }
}