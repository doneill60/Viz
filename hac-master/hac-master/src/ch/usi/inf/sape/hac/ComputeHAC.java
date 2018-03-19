//package ch.usi.inf.sape.hac;

import java.io.*;
import java.util.Comparator;
import ch.usi.inf.sape.hac.experiment.*;
import ch.usi.inf.sape.hac.HierarchicalAgglomerativeClusterer;
import ch.usi.inf.sape.hac.dendrogram.*;
import ch.usi.inf.sape.hac.agglomeration.*;

public class ComputeHAC{

	public static void main(String[] args) throws IOException, FileNotFoundException{
		FileReader fr = new FileReader(args[1]);
		BufferedReader br  = new BufferedReader(fr);
		String curLine;
		int count = 0;
		while((curLine = br.readLine()) != null){
			count = count + 1;
		}
		
		Experiment experiment = new Experiment(count);
		DissimilarityMeasure dissimilarityMeasure = new DissimilarityMeasure(args[1], count);
		AgglomerationMethod agglomerationMethod = new AverageLinkage();
		DendrogramBuilder dendrogramBuilder = new DendrogramBuilder(experiment.getNumberOfObservations());
		HierarchicalAgglomerativeClusterer hac = new HierarchicalAgglomerativeClusterer(experiment, dissimilarityMeasure, agglomerationMethod);
		hac.cluster(dendrogramBuilder);
		Dendrogram dendrogram = dendrogramBuilder.getDendrogram();
		
	}

}