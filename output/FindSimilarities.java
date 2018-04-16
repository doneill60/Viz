
import java.io.*;
//Searches through a given CSV collection to find the similarity of 2 files

public class FindSimilarities {

	public static void main(String args[]) throws IOException, FileNotFoundException { 
		String fileName = args[0];
		String file1 = args[1];
		String file2 = args[2];
		double sim = 0;
		
		FileReader fr = new FileReader(fileName);
		BufferedReader br  = new BufferedReader(fr);
		String curLine;
		while((curLine = br.readLine()) != null){
				String[] splitLine = curLine.split(",");
				if(splitLine[0].equals(file1)){
					System.out.println("Found " + file1);
					for(int i = 1; i < splitLine.length; i++){
						if(splitLine[i].equals(file2)){
							System.out.println("Found " + file2);
							sim = Double.parseDouble(splitLine[i+1]);
							break;
						}
					}
					break;
				}
		}
		System.out.println("The similarity of " + file1 + " and " + file2 + " is " + sim);
	}
}