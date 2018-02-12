/**
 * Zachary Mell
 * Kevin Moore
 * Daniel O'Neill
 * Tegpartap Singh
 */

package cs1980viz;

import java.util.Scanner;

public class Main {
	
	public static void main (String args[]) {
		GetCSVData.main(args);
		String fileLocation = "csv_data";
		
		System.out.println("Enter 1 for Cosine Similarity or 2 for Overlap Similarity: ");
		Scanner kb = new Scanner(System.in);
		int choice = Integer.parseInt(kb.nextLine());
		
		System.out.println("Name of first file to compare: ");
		String tf1 = kb.nextLine();
		System.out.println("Name of second file to compare: ");
		String tf2 = kb.nextLine();
		tf1 = fileLocation + "/" + tf1;
		tf2 = fileLocation + "/" + tf2;
			
		if(choice == 1){
			CalculateCosine.main(tf1, tf2);
		}
		else{
			CalculateOverlap.main();
		}
	}
}

