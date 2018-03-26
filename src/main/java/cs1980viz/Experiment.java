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


/**
 * An experiment consists of a number of observations.
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public class Experiment {
	int observations;
	public Experiment(int size){
		observations = size;
	}
	
    public int getNumberOfObservations(){
		return observations;
	}

}