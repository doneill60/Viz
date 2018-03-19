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
package ch.usi.inf.sape.hac.experiment;

import java.io.BufferedReader;

/**
 * Computes the dissimilarity between two observations in an experiment.
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public class DissimilarityMeasure {
	double Sims[][];
	
	public DissimilarityMeasure(String csvName, int size){
		Sims[][] = new Double[size][size];
		FileReader fr = new FileReader(csvName);
		BufferedReader br  = new BufferedReader(fr);
		String curLine;
		int column = 0;
		int row = 0;
		while((curLine = br.readLine()) != null){
			String[] splitLine = curLine.split(',');
			
			for(int i = 2; i < splitLine.length(); i += 2){
				Sims[row][column] = Double.parseDouble(splitLine[i]);
				column = column + 1;
			}
			row = row + 1;
		}
		
	}
	
    public double computeDissimilarity(int observation1, int observation2){
		return 1 - Sims[observation1][observation2];
		
	}

}