/**
 * Zachary Mell
 * Kevin Moore
 * Daniel O'Neill
 * Tegpartap Singh
 */

package cs1980viz;

import java.util.Scanner;
import org.apache.commons.text.similarity.*;
import java.io.*;
import java.util.Properties;

public class Main {
	
	public static void main (String args[]) {
		String csvFile = null;
		String similarityType = null;
		String matchType = null;
		int simFiles = 0;
		int percentage = 0;
				
		try {
			File configFile = new File("src/main/resources/config.properties");
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			reader.close();
			
			csvFile = props.getProperty("FILE_NAME");
			similarityType = props.getProperty("SIMILARITY_TYPE");
			matchType = props.getProperty("MATCH_TYPE");
			simFiles = Integer.parseInt(props.getProperty("NUM_TOP_FILES"));
			percentage = Integer.parseInt(props.getProperty("PERCENT_MATCH"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GetCSVData.main(csvFile);
		
		if (similarityType.equals("cosine")){
			CalculateCosine.main(matchType, simFiles);
		}
		else if (similarityType.equals("overlap")) {
			CalculateOverlap.main(matchType, simFiles);
		}
	}
}

