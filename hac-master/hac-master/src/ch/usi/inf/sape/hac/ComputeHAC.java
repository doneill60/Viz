package cs1980viz;

import java.io.*;
import java.util.Comparator;

import org.apache.commons.text.similarity.*;

public class ComputeHAC{

	public static void main(String csvFile){	
		FileReader fr = new FileReader(csvName);
		BufferedReader br  = new BufferedReader(fr);
		String curLine;
		int count = 0;
		while((curLine = br.readLine()) != null){
			count = count + 1;
		}
		
		Experiment experiment = new Experiment(count);
		DissimilarityMeasure dissimilarityMeasure = new DissimilarityMeasure(csvFile, count);
		AglomerationMethod aglomerationMethod = new AverageLinkage();
		DendrogramBuilder dendrogramBuilder = new DendrogramBuilder(experiment.getNumberOfObservations());
		HierarchicalAgglomerativeClusterer hac = new HierarchicalAgglomerativeClusterer(experiment, dissimilarityMeasure, aglomerationMethod);
		hac.cluster(dendrogramBuilder);
		Dendrogram dendrogram = dendrogramBuilder.getDendrogram();
		
	}

}