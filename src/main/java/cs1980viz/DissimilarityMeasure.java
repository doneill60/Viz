/*
 * This file is licensed to You under the "Simplified BSD License".
 * You may not use this software except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/bsd-license.php
 * 
 * See the COPYRIGHT file distributed with this work for information
 * regarding copyright ownership.
 */
package cs1980viz;

import java.io.*;

/**
 * Computes the dissimilarity between two observations in an experiment.
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public class DissimilarityMeasure {
	double sims[][];
	
	public DissimilarityMeasure(String csvName, int size){
		sims = new double[size][size];
		FileReader fr = null;
		try {
			fr = new FileReader(csvName);
		}
		catch (FileNotFoundException e) {
			System.exit(0);
		}
		BufferedReader br  = new BufferedReader(fr);
		String curLine;
		int column = 0;
		int row = 0;
		try {
			while((curLine = br.readLine()) != null){
				String[] splitLine = curLine.split(",");

				column = 0;
				for(int i = 2; i < splitLine.length; i += 2){
					sims[row][column] = Double.parseDouble(splitLine[i]);
					column = column + 1;
				}
				row = row + 1;
			}
		}
		catch (IOException e) {
			System.exit(0);
		}
		
	}
	
    public double computeDissimilarity(Experiment experiment, int observation1, int observation2){
		return 1 - sims[observation1][observation2];
		
	}

}