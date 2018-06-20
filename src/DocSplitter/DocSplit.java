// used to split MED.ALL and MED.QRY into separate files

package DocSplitter;

import java.io.*;

public class DocSplit {

	public static void main(String[] args) {
		splitDoc("med/MED.ALL", "documents");
		splitDoc("med/MED.QRY", "queries");
	}

	public static void splitDoc(String name, String outputFolder) {
		try {
			BufferedReader reader = new BufferedReader( new FileReader(new File(name)) );
	    	String line;

	    	String docName = outputFolder + "/1.txt";
	    	BufferedWriter writer = new BufferedWriter( new FileWriter( new File(docName)) );
	    	reader.readLine(); // skip initial ".I 1"
	    	reader.readLine(); // skip initial ".W"

	    	while ((line = reader.readLine()) != null) {
	    		// check if line begins with .I [num]
	    		// then create the file
	    		if ( line.startsWith(".I ") ) {
	    			docName = outputFolder + "/" + line.split(" ")[1] +".txt";

	    			writer = new BufferedWriter( new FileWriter( new File(docName)) );

	    			reader.readLine(); // skip ".W"
	    		}
	    		else {
	    			writer.write(line + "\n");
	    			writer.flush();
	    		}
	    	}

	    	writer.close();

	    } catch (IOException e) {
    		e.printStackTrace();
    	}
	}

}