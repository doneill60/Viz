/**
 * 
 */

package cs1980viz;

import org.apache.commons.csv.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.ArrayList;

public class test {
	
	public static void main (String args[]) {
		
		try{
						
			Reader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/csv_files/c2s_links.csv"), "utf-8"));
			CSVParser csvp = new CSVParser(br,CSVFormat.DEFAULT .withFirstRecordAsHeader());
			
			Iterable<CSVRecord> csvRec = csvp.getRecords();
			
			ArrayList<String> fids= new ArrayList<String>();
			ArrayList<String> ftypes= new ArrayList<String>();
			ArrayList<String> tids= new ArrayList<String>();
			ArrayList<String> ttypes= new ArrayList<String>();
			ArrayList<Integer> weights= new ArrayList<Integer>();
			
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
				
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
}

