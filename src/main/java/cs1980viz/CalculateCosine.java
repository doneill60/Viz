/**
 * 
 */
 
package cs1980viz;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.text.similarity.*;

public class CalculateCosine {
	static final Map<CharSequence, Integer> lv = new HashMap<CharSequence, Integer>();
	static final Map<CharSequence, Integer> rv = new HashMap<CharSequence, Integer>();
		
	public static void main (String f1, String f2) {
		
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(f1));
			String lineToParse;
			
			while((lineToParse = reader.readLine()) != null){
				String[] parsed = lineToParse.split(",");
				lv.put(parsed[0], Integer.parseInt(parsed[1]));
			}
			reader.close();
			
			reader = new BufferedReader(new FileReader(f2));
			
			while((lineToParse = reader.readLine()) != null){
				String[] parsed = lineToParse.split(",");
				rv.put(parsed[0], Integer.parseInt(parsed[1]));
			}
			reader.close();
			
		}
		catch(Exception e){
			System.out.println("Filename not valid");
		}
		CosineSimilarity cs = new CosineSimilarity();
		double sim = cs.cosineSimilarity(lv,rv);
		System.out.println(sim);
	}
}

