/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modern.information.management;

/**
 *
 * @author User
 */
import java.util.Hashtable;
public class hashtest {
    Hashtable<String, hashtablevalue> words = new Hashtable<String, hashtablevalue>();
    public void addword(String word, int value, String Doc){
	hashtablevalue hashtablevalue;
        words.put(word, hashtablevalue =new hashtablevalue(Doc, value, word));
    }

    public void setWords(Hashtable<String, hashtablevalue> words) {
        this.words = words;
    }

    public Hashtable<String, hashtablevalue> getWords() {
        return words;
    }
    public void addtoword(String word, int value, String Doc){
        hashtablevalue hashtablevalue = words.get(word);
        hashtablevalue.add(Doc, value);
        words.put(word, hashtablevalue);
    }
}

class hashtablevalue{
    Integer value;
    String endwordThingy;
    Hashtable<String, Integer> hashVal;
    public hashtablevalue(String documentName,Integer docValue,String endwordThingy){
        hashVal = new Hashtable<String, Integer>();
        hashVal.put(documentName, docValue);
        value=docValue;
        this.endwordThingy=endwordThingy;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getEndwordThingy() {
        return endwordThingy;
    }

    public void setEndwordThingy(String endwordThingy) {
        this.endwordThingy = endwordThingy;
    }

    public Hashtable<String, Integer> getHashVal() {
        return hashVal;
    }

    public void setHashVal(Hashtable<String, Integer> hashVal) {
        this.hashVal = hashVal;
    }
    public void add(String documentName,Integer docValue){
            value+=docValue;
            hashVal.put(documentName,docValue);
    }
}
