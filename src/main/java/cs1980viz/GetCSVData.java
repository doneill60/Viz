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
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class GetCSVData {
	
	public static void main (String args[]) {
		String fileLocation = "csv_data";
		
		try{
			//Removing previously generated csv data
			File rm = new File("csv_data");
			for(File file:rm.listFiles()){
				file.delete();
			}
			
			Reader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/csv_files/c2s_links.csv"), "utf-8"));
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
					
					for(int i = 0; i < weight; i++){
						pw.println(concept);
					}
					pw.close();
				}
				else{
					FileWriter fw = new FileWriter(new File("csv_data",fileName));
					PrintWriter pw = new PrintWriter(fw);
					
					for(int i = 0; i < weight; i++){
						pw.println(concept);
					}
					pw.close();
				}
				
			}
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}

