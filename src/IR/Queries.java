package IR;

import IR.Preprocessor;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import java.io.*;
import java.lang.StringBuilder;

public class Queries {
	// process query list
	public static List<String> processQuery(String query) {
		List<String> queryList= new ArrayList<String>();
        
        Preprocessor preprocessor = new Preprocessor();

        // query pre-processing
		for( String q : query.split(" ") ) {
			q = q.toLowerCase();

			if ( preprocessor.isStopWord(q) ) {
				continue;
			}
			else {                                        
				q = preprocessor.stemWord(q);
				queryList.add(q);
			}
		}

		return queryList;
	}

	public static Map<String, String> parseQueries(String folderPath) {
        // get a list of all the docs
        File[] files = new File(folderPath).listFiles();

        Preprocessor preprocessor = new Preprocessor();
        Map<String, String> map = new LinkedHashMap<String, String>();

        // sort queries by their filename
        Arrays.sort(files, new Comparator() {
            public int compare(Object str1, Object str2) {
                String s1 = ((File)str1).getName();
                String s2 = ((File)str2).getName();

                int file1 = Integer.parseInt( s1.split("\\.")[0] );
                int file2 = Integer.parseInt( s2.split("\\.")[0] );

                return file1 - file2;
            }
        });
        
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
                    }
                }
            }// end while
                             
            } catch (IOException e) {
                System.out.println("Skipping file: " +f.getName());
            }

            // add to map
            map.put(f.getName(), str.toString());
        } // end for
        
        return map;
    }
}