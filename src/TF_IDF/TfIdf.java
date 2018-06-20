package TF_IDF;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TfIdf {

    private Map<String, Map<String, Integer>> termIndex = new HashMap<String, Map<String, Integer>>();
    private Map<String, Double> fileLengths;
    private Map<String, Double>queryTFIDF = new HashMap<String, Double>();
    private int fileCount;
    private List<String> query;

    public TfIdf(Map<String, Map<String, Integer>> index, Map<String, Double> lengths, int count, List<String> queryList){
        // Only passing this huge hashmap in once for efficiency
        termIndex = index;
        fileCount = count;
        fileLengths = lengths;
        query = queryList;

    }
    
//    TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document)
    public Map<String, Map<String, Double>> tf_idf(String term) {
        Map<String, Map<String, Double>>resultMap = new HashMap<String, Map<String, Double>>();
        Map<String, Double>termFreqFileName = new HashMap<String, Double>();
        double docLength;
        String filename;
        double TF;
        
        Set<Map.Entry<String, Integer>> entries;

        if(termIndex.containsKey(term)){
            entries = termIndex.get(term).entrySet();//returns only the term set
      
//        e.g.
//        entry1: [368.txt, 1]
//        entry2: [374.txt, 1]
//        entry3: [25.txt, 1]

            for (Map.Entry entry : entries) {          
                String[] amountOfATermInADocument = entry.toString().split("=");
                filename=amountOfATermInADocument[0];

                docLength=fileLengths.get(filename);
                TF = Double.parseDouble(amountOfATermInADocument[1]) / docLength;
                termFreqFileName.put(filename, TF * idf(term));
            }
        }

        resultMap.put(term, termFreqFileName);

        return resultMap;
    } 

//    IDF(t) = log(Total number of documents / Number of documents with term t in it). 
    private double idf(String term) { 
        if(termIndex.containsKey(term)) {
            double relevantDocCount = termIndex.get(term).entrySet().size();
        
            return Math.log10(fileCount / relevantDocCount); 
        }
        return 0;
    }
    
//    public Map<String, Double> get_query_TFIDF_Map(String term, List<String> query) {
//        //get tf for all query terms, multiply by IDF
//        return queryTFIDF;
//    }
    
    public Map<String, Double> get_query_TFIDF_Map(String term) {       
//        Set<Map.Entry<String, Integer>> entries;

        if(termIndex.containsKey(term)){
//            for(int i=0;i<query.size();i++){
                double tf = Collections.frequency(query, term);
                tf = tf/query.size();
                
//            }
//            entries = termIndex.get(term).entrySet();//returns only the term set
            queryTFIDF.put(term, tf * idf(term));
        }
        return queryTFIDF;
    }
    
}