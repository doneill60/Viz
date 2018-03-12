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
	
	public static void main (String matchType, int data) {
		String fileLocation = "html";
		//Removing previously generated html data
		File rm = new File(fileLocation);
		for(File file:rm.listFiles()){
			file.delete();
		}
		
		File txt = new File("csv_data");
		for(File fileName:txt.listFiles()){
			
			String f1 = location+"/"+fileName.getName();
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
			try{
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
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
}

