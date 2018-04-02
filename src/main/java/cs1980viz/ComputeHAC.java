
package cs1980viz;
import java.io.*;
import java.util.Comparator;
import java.util.ArrayList;

public class ComputeHAC{
	private static int total = 0;

	public static void main(String[] args) throws IOException, FileNotFoundException{
		String filename = "output/output.csv";
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
			
		DisplayClusters dc = new DisplayClusters();
		ArrayList<DendrogramNode> clusters = dc.getClusters(dendrogram);
		ArrayList<DendrogramNode> topClusters = new ArrayList<DendrogramNode>();
		for (int j = 0; j < 4; j++) {
			int max = 0;
			int maxPos = 0;
			for (int i = 0; i < clusters.size(); i++) {
				if (clusters.get(i).getObservationCount() > max) {
					max = clusters.get(i).getObservationCount();
					maxPos = i;
				}	
			}
			DendrogramNode node = clusters.get(maxPos);
			topClusters.add(node);
			clusters.remove(maxPos);
		}
		
		String[] names = dc.getNames();
		ArrayList<ArrayList<Integer>> clusterVals = new ArrayList<ArrayList<String>>();
		for(int j = 0; j<4; j++){

			clusterVals.set(j, leafNodes(topClusters.get(j)));
		}
		
		for (int i = 0; i < clusterVals.get(0).size(); i++) {
			System.out.println(names[clusterVals.get(0).get(i)]);
		}
	}
	
	public static ArrayList<Integer> leafNodes(DendrogramNode n){
		ArrayList<Integer> list = new ArrayList<>();
		if(n.getLeft() != null){
			list.addAll(leafNodes(n.getLeft()));
		}
		if(n.getRight() != null){
			list.addAll(leafNodes(n.getRight()));
		}
		if(n.getLeft() == null && n.getRight() == null){
			list.add(n.getObservation());
		}
		
		return list;
	}

}