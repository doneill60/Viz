/**
 * 
 */
 
package cs1980viz;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.text.similarity.*;

public class CalculateCosine {
	static final Map<CharSequence, Integer> lv = new HashMap<CharSequence, Integer>();
	static final Map<CharSequence, Integer> rv = new HashMap<CharSequence, Integer>();
	static final String location = "csv_data";
	static PriorityQueue<FileData> queue;
	
	public static void main (String f1) {
		
		
		Comparator<FileData> comparator = new FileDataComparator();
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(f1));
			String lineToParse;
			
			while((lineToParse = reader.readLine()) != null){
				String[] parsed = lineToParse.split(",");
				lv.put(parsed[0], Integer.parseInt(parsed[1]));
			}
			reader.close();
			
			File folder = new File(location);
			File[] list = folder.listFiles();
			
			queue = new PriorityQueue<FileData>((list.length-1), comparator);
			
			//Comparing file with all other files
			for (int i = 0; i < list.length; i++){
				
				//Clearing hashmap
				rv.clear();
				
				String tempF = location+"/"+list[i].getName();
				
				//Making sure we arent comparing the same file
				if(!f1.equals(tempF)){
					reader = new BufferedReader(new FileReader(tempF));
					
					while((lineToParse = reader.readLine()) != null){
						String[] parsed = lineToParse.split(",");
						rv.put(parsed[0], Integer.parseInt(parsed[1]));
					}
					reader.close();
					
					CosineSimilarity cs = new CosineSimilarity();
					double sim = cs.cosineSimilarity(lv,rv);
					queue.add(new FileData(list[i].getName(), sim));
				}
				
				
				
			}
			
		}
		catch(Exception e){
			System.out.println("Filename not valid");
		}
		
		System.out.println("\nTop 20 files with highest similarity to "+f1+":\n");
		for(int i = 0; i < 20; i++){
			FileData tempfd = queue.remove();
			System.out.println(tempfd.name+" "+tempfd.similarity);
		}
	}
}

