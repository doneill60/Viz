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


import java.util.ArrayList;

/**
 * A DendrogramNode is a node in a Dendrogram.
 * It represents a subtree of the dendrogram tree.
 * It has two children (left and right), 
 * and it can provide the number of leaf nodes (ObservationNodes) in this subtree.
 * 
 * @author Matthias.Hauswirth@unisi.ch
 */
public interface DendrogramNode {
	
	DendrogramNode getLeft();
	DendrogramNode getRight();
	int getObservationCount();
	ArrayList<Integer> getDocs();

}