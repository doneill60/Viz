/**
 * Zachary Mell
 * Kevin Moore
 * Daniel O'Neill
 * Tegpartap Singh
 */

package cs1980viz;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Properties;

public class CalcHtmlDemo {
	
	public static void main (String args[]) {
		String matchType = "";
		String similarityType = "";
		int simFiles = 0;
		int percentage = 0;
		int data = 0;
		Comparator<FileData> comparator = new FileDataComparator();
		
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
		
		try{
			File csvMain = new File("output/output.csv");
			BufferedReader reader = new BufferedReader(new FileReader(csvMain));
			String line = "";
			
			//Removing previously generated data
			File rm = new File("html");
			for(File file:rm.listFiles()){
				file.delete();
			}
		
			while((line = reader.readLine()) != null){
				String[] lineData = line.split(",");
				PriorityQueue<FileData> queue = new PriorityQueue<FileData>((lineData.length/2), comparator);
				for (int j = 1; j < lineData.length; j=j+2){
					String[] fName = lineData[j].split("/");
					FileData fd = new FileData(fName[1],Double.parseDouble(lineData[j+1]));
					queue.add(fd);
				}
				
				if(matchType.equals("t")){
					
					FileWriter fw = new FileWriter(new File("html",lineData[0].split("/")[1]+".html"));
					PrintWriter pw = new PrintWriter(fw);
						
					pw.println("<!DOCTYPE html>");
					pw.println("<html>");
					pw.println("<body>");
					pw.println("<h1>"+lineData[0].split("/")[1]+": "+similarityType+" similarity</h1>");
					pw.println("<h2> Top "+data+" matches</h2>");
					
					for(int i = 0; i < data+1; i++){
						FileData tempfd = queue.remove();
						if(tempfd.name.equals(lineData[0].split("/")[1])){
							
						}
						else{
							pw.println("<a href=\""+tempfd.name+".html\">"+tempfd.name+"</a>");
							int sim = (int)(tempfd.similarity*100);
							pw.println("&#9; "+sim+"% similar");
							pw.println("<br/>");
						}
					}
					
					pw.println("</body>");
					pw.println("</html>");
					pw.close();
				}
				else{
					FileWriter fw = new FileWriter(new File("html",lineData[0].split("/")[1]+".html"));
					PrintWriter pw = new PrintWriter(fw);
						
					pw.println("<!DOCTYPE html>");
					pw.println("<html>");
					pw.println("<body>");
					pw.println("<h1>"+lineData[0].split("/")[1]+": "+similarityType+" similarity</h1>");
					pw.println("<h2> Matches over  "+data+"% similarity</h2>");
					
					for(int i = 0; i < queue.size(); i++){
						FileData tempfd = queue.remove();
						if(tempfd.name.equals(lineData[0].split("/")[1])){
							
						}
						else{
							if(tempfd.similarity*100 > data){
								pw.println("<a href=\""+tempfd.name+".html\">"+tempfd.name+"</a>");
								int sim = (int)(tempfd.similarity*100);
								pw.println("&#9; "+sim+"% similar");
								pw.println("<br/>");
							}
							else{
								break;
							}
						}
					}
					pw.println("</body>");
					pw.println("</html>");
					pw.close();
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
			System.exit(1);
		}
		
		
		
		
		
	}
}

