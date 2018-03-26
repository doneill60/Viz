
package cs1980viz;
import java.io.*;
import java.util.Comparator;

public class ComputeHAC{
	private static int total = 0;
	private static int[] roots;
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
		
		roots = new int[count];
		
		DendrogramNode root = dendrogram.getRoot();
		DendrogramNode cur = root;
		/*count = 0;
		/while(cur.getLeft() != null){
			System.out.println("Main Observation: " + cur.getObservationCount() + " Left Observation: " + cur.getLeft().getObservationCount() + " Right Observation: " + cur.getRight().getObservationCount());
			count += 1;
			cur = cur.getLeft();
			if(cur.getRight() == null){
				System.out.println("Observe = " + cur.getObservationCount());
			}
		}*/
		leafNodes(root);
		System.out.println("Count = " + count);
		
	}
	
	public void leafNodes(Dendrogram n){
		if(n.getLeft() != null){
			leafNodes(n.getLeft());
		}
		if(n.getRight() != null){
			leftNodes(n.getRight());
		}
		if(n.getLeft() == null && n.getRight() == null){
			roots[total] = 1;
			total += 1;
		}
	}

}