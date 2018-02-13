/**
 * Zachary Mell
 * Kevin Moore
 * Daniel O'Neill
 * Tegpartap Singh
 */

package cs1980viz;

import java.util.Scanner;
import org.apache.commons.text.similarity.*;

public class Main {
	
	public static void main (String args[]) {
		
		String fileLocation = "csv_data";
		GetCSVData.main();
		
		System.out.println("Enter 1 for Cosine Similarity or 2 for Overlap Similarity: ");
		Scanner kb = new Scanner(System.in);
		int choice = Integer.parseInt(kb.nextLine());
		
		System.out.println("Name of file to compare: ");
		String tf1 = kb.nextLine();
		tf1 = fileLocation + "/" + tf1;
			
		if(choice == 1){
			CalculateCosine.main(tf1);
		}
		else{		
			CalculateOverlap.main(tf1);
		}
	}
}

