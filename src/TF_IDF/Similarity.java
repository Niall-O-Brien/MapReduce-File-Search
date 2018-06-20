package TF_IDF;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;

public class Similarity {
	// converts the tfidf weight map from  Map<[word], Map<[filename], [weight]>  to
	// Map<[filename], Map<[word], [weight]>  for easier comparison
	public static Map<String, Map<String, Double>> convertMap(Map<String, Map<String, Double>> wordMap) {
		Map<String, Map<String, Double>> output = new HashMap<String, Map<String, Double>>();

		Iterator it = wordMap.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();

			String word = (String)entry.getKey();
			Map<String, Double> innerMap = (Map<String, Double>)entry.getValue();

			Iterator iter = innerMap.entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry ent = (Map.Entry)iter.next();
				Map<String, Double> newInnerMap = new HashMap<String, Double>();

				String file = (String) ent.getKey();
				double weight = (double) ent.getValue();

				// if output alreadt contains file, add to its map
				// else, create a new map
				if ( output.containsKey(file) ) {
					output.get(file).put(word, weight);
				}
				else {
					newInnerMap.put(word, weight);
					output.put( file, newInnerMap );
				}

				iter.remove();
			}

        	it.remove();
   		} // end outer interator

		return output;
	}


	// similarity function
	public static double similarity(Map<String, Double> query, Map<String, Double> document) {
		double x=0.0;
		double y=0.0;
		double z=0.0;
                
		for(Map.Entry<String, Double> documentEntry : document.entrySet()){
                    x += documentEntry.getValue()*documentEntry.getValue();
                }
                
		for(Map.Entry<String, Double> queryEntry : query.entrySet()){
                    y += queryEntry.getValue()*queryEntry.getValue();
                    
		    if (document.get(queryEntry.getKey()) != null)
                	z += queryEntry.getValue()*document.get(queryEntry.getKey());
                }
                
		z=z/Math.sqrt(y)*Math.sqrt(x);
		return z;
	}
}
