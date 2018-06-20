package IR;

import TF_IDF.TfIdf;
import MapReduce.*;
import IR.Preprocessor;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.lang.StringBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// This class is a container for the document set + any functions
// needed to create the set.

public class DocumentSet {

    private int fileCount =0;
    private Map<String, Double> fileLengths = new HashMap<String, Double>();
    private Map<String, Map<String, Integer>> termIndex = new HashMap<String, Map<String, Integer>>();


    public DocumentSet(String folder) {
    	// initialise document set
        termIndex = mapReduce( parseDocuments(folder) );
    }

    private Map<String, String> parseDocuments(String folderPath) {
        File[] files;
        double fileLength = 0;
        // get a list of all the docs
        files = new File(folderPath).listFiles();
        fileCount = files.length;

        Preprocessor preprocessor = new Preprocessor();
        Map<String, String> map = new HashMap<String, String>();        
        
        // process the files
        for (File f : files) {
            StringBuilder str = new StringBuilder(""); // creates a string of document for HashMap

            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String line;

                // reads a line at a time
                while ((line = reader.readLine()) != null) {
                    // split line into words (removes all punctuation except apostrophes)
                    String[] wordsArray = line.split("[^a-zA-Z0-9']+");
                     
                    for (String word : wordsArray) {
                    // for consistency etc.
                    word = word.toLowerCase();

                    if ( preprocessor.isStopWord(word) ) {
                            // if the word is a stop word, skip it
                            continue;
                    }
                    else {                                        
                        word = preprocessor.stemWord(word);

                        str.append(word).append(" ");
                        
                        //counting file length [will be added to the map key]
                        fileLength ++;                   
                    }
                }
            }// end while
                             
            } catch (IOException e) {
                System.out.println("Skipping file: " +f.getName());
            }

            // add to map
            map.put(f.getName(), str.toString());
            fileLengths.put(f.getName(), fileLength);
            fileLength = 0;
        } // end for
        
        return map;
    }


    // cpoied from MapReduce example class
    private Map<String, Map<String, Integer>> mapReduce(Map<String, String> input) {
        Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();

        // MAP:               
        List<MappedItem> mappedItems = new LinkedList<MappedItem>();
                
        Iterator<Map.Entry<String, String>> inputIter = input.entrySet().iterator();
        while(inputIter.hasNext()) {
            Map.Entry<String, String> entry = inputIter.next();
            String file = entry.getKey();
            String contents = entry.getValue();
                               
            MapReduce.map(file, contents, mappedItems);
        }
        

        // GROUP:              
        Map<String, List<String>> groupedItems = new HashMap<String, List<String>>();
                       
        Iterator<MappedItem> mappedIter = mappedItems.iterator();
        while(mappedIter.hasNext()) {
            MappedItem item = mappedIter.next();
            String word = item.getWord();
            String file = item.getFile();
            List<String> list = groupedItems.get(word);
            if (list == null) {
                    list = new LinkedList<String>();
                    groupedItems.put(word, list);
            }
            list.add(file);
        }               


        // REDUCE:
        Iterator<Map.Entry<String, List<String>>> groupedIter = groupedItems.entrySet().iterator();
        while(groupedIter.hasNext()) {
            Map.Entry<String, List<String>> entry = groupedIter.next();
            String word = entry.getKey();
            List<String> list = entry.getValue();
                               
            MapReduce.reduce(word, list, output);
        }

        return output;
    }

     // getters
    public int fileCount() {
    	return fileCount;
    }
    public Map<String, Double> fileLengths() {
    	return fileLengths;
    }
    public Map<String, Map<String, Integer>> termIndex() {
    	return termIndex;
    }
}
