package IR;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Maps {
	public static void printMap(Map<String, Map<String, Double>> map) {
		Iterator it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			System.out.println(entry);
		}
	}

    // returns the map entries in sorted order
	public static List<Map.Entry> sortMapByValue(Map map) {
		List<Map.Entry> sorted = new ArrayList<Map.Entry>(map.entrySet());

		Collections.sort(sorted, new Comparator() {
			public int compare(Object entry1, Object entry2) {
				double d1 = (double) ((Map.Entry) entry1).getValue();
				double d2 = (double) ((Map.Entry) entry2).getValue();

				return Double.compare(d2, d1);
			}
		});

		return sorted;
	}

	// returns the map entries in sorted order
	public static List<String> sortMapByKey(Map map) {
		List<String> sorted = new ArrayList<String>(map.keySet());

		// sort filenames by numeric value
		Collections.sort(sorted, new Comparator() {
			public int compare(Object str1, Object str2) {
				String s1 = (String) str1;
				String s2 = (String) str2;

				int file1 = Integer.parseInt( s1.split("\\.")[0] );
				int file2 = Integer.parseInt( s2.split("\\.")[0] );

				return file1 - file2;
			}
		});

		return sorted;
	}
}