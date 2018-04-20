
package cs1980viz;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class ComputeHAC{
	private static int total = 0;
	private static int[] roots;
	public static void main(String[] args) throws IOException {
		String filename = "output/output.csv";;
		FileReader fr = new FileReader(filename);
		BufferedReader br  = new BufferedReader(fr);
		String curLine;
		int count = 0;
		while((curLine = br.readLine()) != null){
			count = count + 1;
			
		}
		Experiment experiment = new Experiment(count);
		DissimilarityMeasure dissimilarityMeasure = new DissimilarityMeasure(filename, count);
		AgglomerationMethod agglomerationMethod = new AverageLinkage();
		DendrogramBuilder dendrogramBuilder = new DendrogramBuilder(experiment.getNumberOfObservations());
		HierarchicalAgglomerativeClusterer hac = new HierarchicalAgglomerativeClusterer(experiment, dissimilarityMeasure, agglomerationMethod);
		hac.cluster(dendrogramBuilder);
		Dendrogram dendrogram = dendrogramBuilder.getDendrogram();

		ClusterMappings cm = new ClusterMappings(dendrogram,experiment);
		cm.createMap();
		Map<Long, DendrogramNode> mappings = cm.getMappings();
		long highestCommonNode[][] = cm.getHighestCommonNode();

		cm.writeMappings();
		cm.writeArray();
		
	}


}
