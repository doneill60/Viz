package cs1980viz;
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
//package ch.usi.inf.sape.hac.dendrogram;

import java.util.ArrayList;


/**
 * A MergeNode represents an interior node in a Dendrogram.
 * It corresponds to a (non-singleton) cluster of observations.
 *
 * @author Matthias.Hauswirth@usi.ch
 */
public final class MergeNode implements DendrogramNode {

	private final DendrogramNode left;
	private final DendrogramNode right;
	private final double dissimilarity;
	private final int observationCount;
	private ArrayList<Integer> docs;


	public MergeNode(final DendrogramNode left, final DendrogramNode right, final double dissimilarity) {
		this.left = left;
		this.right = right;
		this.dissimilarity = dissimilarity;
		observationCount = left.getObservationCount()+right.getObservationCount();
		docs = new ArrayList<Integer>();
		mergeDocuments(left,right);
	}

	private void mergeDocuments(final DendrogramNode left, final DendrogramNode right){
		ArrayList<Integer> leftList = left.getDocs();
		ArrayList<Integer> rightList = right.getDocs();
		int leftIndex = 0;
		int rightIndex = 0;

		outer: while(leftIndex < leftList.size()){
			if(leftIndex >= leftList.size() || rightIndex >= rightList.size()){
				break outer;
			}
			while(rightIndex < rightList.size()){
				if(leftIndex >= leftList.size() || rightIndex >= rightList.size()){
					break outer;
				}

				Integer curLeftDoc = leftList.get(leftIndex);
				Integer curRightDoc = rightList.get(rightIndex);
				if(curLeftDoc < curRightDoc){
					docs.add(curLeftDoc);
					leftIndex++;
				}else{
					docs.add(curRightDoc);
					rightIndex++;
				}
			}
		}
		if((leftList.size() - leftIndex) != 0){
			while(leftIndex < leftList.size()){
				docs.add(leftList.get(leftIndex));
				leftIndex++;
			}
		} else if((rightList.size() - rightIndex) != 0){
			while(rightIndex < rightList.size()){
				docs.add(rightList.get(rightIndex));
				rightIndex++;
			}
		}
	}

	public final ArrayList<Integer> getDocs(){
		return docs;
	}

	public int getObservationCount() {
		return observationCount;
	}

	public final DendrogramNode getLeft() {
		return left;
	}

	public final DendrogramNode getRight() {
		return right;
	}

	public final double getDissimilarity() {
		return dissimilarity;
	}

}