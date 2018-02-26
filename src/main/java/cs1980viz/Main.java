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
		
		boolean exit = false;
		boolean valid = false;
		String fileLocation = "csv_data";
		String csvFile = "c2s_links.csv";
		String similarityType = "cosine";
		String matchType = "t";
		int simFiles = 20;
		int percentage = 50;
		
		
		while(!false){
			System.out.println("1: Change CSV File");
			System.out.println("2: Compute CSV Data");
			System.out.println("3: Edit Similarity Settings");
			System.out.println("4: Calculate Data Similarity (Output to HTMLs)");
			System.out.println("5: Exit");
			
			//System.out.println("Enter 1 for Cosine Similarity or 2 for Overlap Similarity: ");
			Scanner kb = new Scanner(System.in);
			int choice = Integer.parseInt(kb.nextLine());
			
			//System.out.println("Name of file to compare: ");
			//String tf1 = kb.nextLine();
			//tf1 = fileLocation + "/" + tf1;
			
			if(choice == 1){
				System.out.println("Current CSV file: \""+csvFile+"\"\n");
				System.out.println("Enter name of new CSV file:");
				csvFile = kb.nextLine();
			}
			else if(choice == 2){
				GetCSVData.main(csvFile);
			}
			else if(choice == 3){
				valid = false;
				while(!valid){
					System.out.println("1: Cosine Similarity");
					System.out.println("2: Overlap Similarity");
					choice = Integer.parseInt(kb.nextLine());
					
					if(choice == 1){
						similarityType = "cosine";
						valid = true;
					}
					else if(choice == 2){
						similarityType = "overlap";
						valid = true;
					}
					else{
						System.out.println("Invalid Choice!\n");
					}
				}
				
				valid = false;
				
				while(!valid){
					System.out.println("1: Top Matched Results");
					System.out.println("2: % Match");
					choice = Integer.parseInt(kb.nextLine());
					
					if(choice == 1){
						System.out.println("How many of the top results would you like to match?");
						simFiles = Integer.parseInt(kb.nextLine());
						matchType = "t";
						valid = true;
					}
					else if(choice == 2){
						System.out.println("What is the percentage of similarity you would like to match?");
						percentage = Integer.parseInt(kb.nextLine());
						matchType = "s";
						valid = true;
					}
					else{
						System.out.println("Invalid Choice!\n");
					}
				}
				
				
			}
			else if(choice == 4){
				if(similarityType.equals("cosine")){
					if(matchType.equals("t")){
						CalculateCosine.main(matchType, simFiles);
					}
					else{
						CalculateCosine.main(matchType, percentage);
					}
				}
				else{
					if(matchType.equals("t")){
						CalculateOverlap.main(matchType, simFiles);
					}
					else{
						CalculateOverlap.main(matchType, percentage);
					}
				}
			}
			else if(choice == 5){
				System.exit(0);
			}
			else{		
				System.out.println("Invalid Choice!\n");
			}
		}
	}
}

