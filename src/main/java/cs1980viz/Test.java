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

public class Test {
	
	public static void main (String args[]) {
		int recNum = 0;
		ArrayList<String> fids= new ArrayList<String>();
		ArrayList<String> ftypes= new ArrayList<String>();
		ArrayList<String> tids= new ArrayList<String>();
		ArrayList<String> ttypes= new ArrayList<String>();
		ArrayList<Integer> weights= new ArrayList<Integer>();
		
		try{
						
			Reader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/csv_files/c2s_links.csv"), "utf-8"));
			CSVParser csvp = new CSVParser(br,CSVFormat.DEFAULT .withFirstRecordAsHeader());
			
			Iterable<CSVRecord> csvRec = csvp.getRecords();
			
			
			for(CSVRecord rec : csvRec){
				//Splitting csv into individual features
				String fid = rec.get(0);
				String ftype = rec.get(1);
				String tid = rec.get(2);
				String ttype = rec.get(3);
				int weight = Integer.parseInt(rec.get(4));
				
				//Storing them in their respective ArrayList
				fids.add(fid);
				ftypes.add(ftype);
				tids.add(tid);
				ttypes.add(ttype);
				weights.add(weight);
				
				recNum++;
				
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		System.out.println(recNum+" records were read in and stored!");
		
		DirectedSparseGraph g = new DirectedSparseGraph();
		g.addVertex("Vertex1");
		g.addVertex("Vertex2");
		g.addVertex("Vertex3");
		g.addEdge("Edge1", "Vertex1", "Vertex2");
		g.addEdge("Edge2", "Vertex1", "Vertex3");
		g.addEdge("Edge3", "Vertex3", "Vertex1");
		VisualizationImageServer vs =
		  new VisualizationImageServer(
			new CircleLayout(g), new Dimension(200, 200));
	 
		JFrame frame = new JFrame();
		frame.getContentPane().add(vs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		
	}
}

