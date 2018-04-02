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
import java.util.Properties;

public class CalculateCosine {
	static final Map<CharSequence, Integer> lv = new HashMap<CharSequence, Integer>();
	static final Map<CharSequence, Integer> rv = new HashMap<CharSequence, Integer>();
	static String location;
	//This is a min - pq. The comparator was modified to produce the opposite effect to make this a max pq
	static PriorityQueue<FileData> queue;
	
	public static void main (String args[]) throws IOException{
		String matchType = "";
		String similarityType = "";
		int simFiles = 0;
		int percentage = 0;
		int data = 0;
		
		
		try{
			File configFile = new File("src/main/resources/config.properties");
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			reader.close();
			
			matchType = props.getProperty("MATCH_TYPE");
			simFiles = Integer.parseInt(props.getProperty("NUM_TOP_FILES"));
			percentage = Integer.parseInt(props.getProperty("PERCENT_MATCH"));
			similarityType = props.getProperty("SIMILARITY_TYPE");
			location = props.getProperty("OUTPUT_CSV_LOCATION");
		}
		catch(Exception e){
			System.out.println(e);
			System.exit(1);
		}
		
		if (similarityType.equals("cosine")){
			if (matchType.equals("t")){
				data = simFiles;
			}
			else{
				data = percentage;
			}
			
		}
		
		
		
		String fileLocation = "output";
		//Removing previously generated data
		File rm = new File(fileLocation);
		for(File file:rm.listFiles()){
			file.delete();
		}
		
		File txt = new File(location);
		FileWriter fwoutput = new FileWriter(new File("output","output.csv"));
		PrintWriter pwoutput = new PrintWriter(fwoutput);
		
		for(File fileName:txt.listFiles()){
			
			String f1 = location+"/"+fileName.getName();
			Comparator<FileData> comparator = new FileDataComparator();
			
			try{
				
				BufferedReader reader = new BufferedReader(new FileReader(f1));
				String lineToParse;
				
				lv.clear();
				
				while((lineToParse = reader.readLine()) != null){
					String[] parsed = lineToParse.split(",");
					lv.put(parsed[0], Integer.parseInt(parsed[1]));
				}
				reader.close();
				
				File folder = new File(location);
				File[] list = folder.listFiles();
				
				queue = new PriorityQueue<FileData>((list.length-1), comparator);
				pwoutput.print(f1+",");
				
				//Comparing file with all other files
				for (int i = 0; i < list.length; i++){
					
					//Clearing hashmap
					rv.clear();
					
					String tempF = location+"/"+list[i].getName();
					
					pwoutput.print(tempF+",");
					reader = new BufferedReader(new FileReader(tempF));
					
					while((lineToParse = reader.readLine()) != null){
						String[] parsed = lineToParse.split(",");
						rv.put(parsed[0], Integer.parseInt(parsed[1]));
					}
					reader.close();
					
					CosineSimilarity cs = new CosineSimilarity();
					double sim = cs.cosineSimilarity(lv,rv);
					pwoutput.print(sim+",");
					queue.add(new FileData(list[i].getName(), sim));
					
				}
				pwoutput.print("\n");
				
			}
			catch(Exception e){
				System.out.println("Filename not valid");
			}
			try{
				/**
				if(matchType.equals("t")){
					
					FileWriter fw = new FileWriter(new File("html",fileName.getName()+".html"));
					PrintWriter pw = new PrintWriter(fw);
						
					pw.println("<!DOCTYPE html>");
					pw.println("<html>");
					pw.println("<body>");
					pw.println("<h1>"+fileName.getName()+"</h1>");
						
					
					for(int i = 0; i < data; i++){
						FileData tempfd = queue.remove();
						pw.println("<a href=\""+tempfd.name+".html\">"+tempfd.name+".html</a>");
						int sim = (int)(tempfd.similarity*100);
						pw.println("&#9; "+sim+"% similar");
						pw.println("<br/>");
					}
					
					pw.println("</body>");
					pw.println("</html>");
					pw.close();
				}
				else{
					FileWriter fw = new FileWriter(new File("html",fileName.getName()+".html"));
					PrintWriter pw = new PrintWriter(fw);
						
					pw.println("<!DOCTYPE html>");
					pw.println("<html>");
					pw.println("<body>");
					pw.println("<h1>"+fileName.getName()+"</h1>");
					
					for(int i = 0; i < queue.size(); i++){
						FileData tempfd = queue.remove();
						if(tempfd.similarity*100 > data){
							pw.println("<a href=\""+tempfd.name+".html\">"+tempfd.name+".html</a>");
							int sim = (int)(tempfd.similarity*100);
							pw.println("&#9; "+sim+"% similar");
							pw.println("<br/>");
						}
						else{
							break;
						}
					}
					pw.println("</body>");
					pw.println("</html>");
					pw.close();
				}*/
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		}
	}
}

