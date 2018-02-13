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

public class CalculateOverlap {
	static final Map<Integer, String> a = new HashMap<Integer, String>();
	static final Map<Integer, String> b = new HashMap<Integer, String>();
	static final Map<Integer, String> aIntB = new HashMap<Integer, String>();
	static final String location = "csv_data";
	static PriorityQueue<FileData> queue;
	
	public static void main (String f1) {
		
		
		Comparator<FileData> comparator = new FileDataComparator();
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(f1));
			String lineToParse;
			
			int key = 0;
			while((lineToParse = reader.readLine()) != null){
				String[] parsed = lineToParse.split(",");
				a.put(key, parsed[0]);
				key++;
			}
			reader.close();
			
			File folder = new File(location);
			File[] list = folder.listFiles();
			
			queue = new PriorityQueue<FileData>((list.length-1), comparator);
			
			//Comparing file with all other files
			for (int i = 0; i < list.length; i++){
				
				//Clearing hashmap
				b.clear();
				
				String tempF = location+"/"+list[i].getName();
				
				//Making sure we arent comparing the same file
				if(!f1.equals(tempF)){
					reader = new BufferedReader(new FileReader(tempF));
					
					key = 0;
					while((lineToParse = reader.readLine()) != null){
						String[] parsed = lineToParse.split(",");
						b.put(key, parsed[0]);
						key++;
					}
					reader.close();
					
					aIntB.clear();
					for(int z = 0; z < a.size(); z++){
						String testString = a.get(z);
						if(b.containsValue(testString)){
							aIntB.put(z,testString);
						}
					}
					
					//Calculating overlap similarity
					double sim = ((double)(aIntB.size())/(double)(a.size() + b.size() - aIntB.size()));
					
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

