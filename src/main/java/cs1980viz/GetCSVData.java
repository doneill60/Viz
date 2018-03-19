/**
 * Zachary Mell
 * Kevin Moore
 * Daniel O'Neill
 * Tegpartap Singh
 */

package cs1980viz;

import org.apache.commons.csv.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JFrame;
//import edu.uci.ics.jung.algorithms.layout.CircleLayout;
//import edu.uci.ics.jung.graph.DirectedSparseGraph;
//import edu.uci.ics.jung.visualization.VisualizationImageServer;
import java.util.Properties;

public class GetCSVData {
	
	public static void main (String args[]) {
		String fileLocation = "csv_data";
		String csv = "null";
		String isParsed = "false";
		
		try{
			File configFile = new File("src/main/resources/config.properties");
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			reader.close();
			
			csv = props.getProperty("FILE_NAME");
			isParsed = props.getProperty("CSV_PARSED");
			
		}
		catch(Exception e){
			System.out.println("file does not exist in proper folder!");
			System.exit(1);
		}
		try{
			if(isParsed.equals("false")){
				//Removing previously generated csv data
				File rm = new File(fileLocation);
				for(File file:rm.listFiles()){
					file.delete();
				}
				
				String csvLoc = "src/main/resources/csv_files/";
				csvLoc = csvLoc + csv;
				Reader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvLoc), "utf-8"));
				CSVParser csvp = new CSVParser(br,CSVFormat.DEFAULT .withFirstRecordAsHeader());
				
				Iterable<CSVRecord> csvRec = csvp.getRecords();
				
				for(CSVRecord rec : csvRec){
					String concept = rec.get(0);
					String fileName = rec.get(2);
					int weight = Integer.parseInt(rec.get(4));
					
					File testFile = new File(fileLocation, fileName);
					boolean fileExists = testFile.exists();
					
					if(fileExists == true){
						FileWriter fw = new FileWriter(new File("csv_data",fileName), true);
						PrintWriter pw = new PrintWriter(fw);
						
						pw.println(concept+","+weight);
						
						pw.close();
					}
					else{
						FileWriter fw = new FileWriter(new File("csv_data",fileName));
						PrintWriter pw = new PrintWriter(fw);
						
						pw.println(concept+","+weight);
						
						pw.close();
					}
					
				}
			}
			//Adding more csv data
			else{
				String csvLoc = "src/main/resources/csv_files/";
				csvLoc = csvLoc + csv;
				Reader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvLoc), "utf-8"));
				CSVParser csvp = new CSVParser(br,CSVFormat.DEFAULT .withFirstRecordAsHeader());
				
				Iterable<CSVRecord> csvRec = csvp.getRecords();
				
				for(CSVRecord rec : csvRec){
					String concept = rec.get(0);
					String fileName = rec.get(2);
					int weight = Integer.parseInt(rec.get(4));
					
					File testFile = new File(fileLocation, fileName);
					boolean fileExists = testFile.exists();
					
					if(fileExists == true){
						FileWriter fw = new FileWriter(new File("csv_data",fileName), true);
						PrintWriter pw = new PrintWriter(fw);
						
						pw.println(concept+","+weight);
						
						pw.close();
					}
					else{
						FileWriter fw = new FileWriter(new File("csv_data",fileName));
						PrintWriter pw = new PrintWriter(fw);
						
						pw.println(concept+","+weight);
						
						pw.close();
					}
					
				}
			}
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}

