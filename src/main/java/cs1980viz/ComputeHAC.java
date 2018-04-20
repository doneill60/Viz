
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
//		for (long[] arr : highestCommonNode) {
//			System.out.println(Arrays.toString(arr));
//		}
		System.out.println(highestCommonNode[3][4]);
		for(int i = 0; i < experiment.getNumberOfObservations(); i++){
			for(int j = i+1; j < experiment.getNumberOfObservations(); j++){
				ArrayList<Integer> list = mappings.get(highestCommonNode[i][j]).getDocs();
				ArrayList<Integer> other = mappings.get(highestCommonNode[i][j] << 1).getDocs();
				boolean first = (list.contains(i) && list.contains(j));
				boolean second = (!other.contains(i) || !other.contains(j));
				if(!(first && second)){
					System.out.println("FAIL\n\n\n\n\n\n");
					System.out.println("I " + i + " J " + j);
					System.out.println(list);
					System.out.println(other);
				}
			}
		}
		System.out.println("MAP1 " + mappings.get(highestCommonNode[1][101]).getDocs());
		System.out.println("MAP2 " + mappings.get(highestCommonNode[1][101] << 1).getDocs());
		cm.writeMappings();
		cm.writeArray();

		roots = new int[count];
		
		//DendrogramNode root = dendrogram.getRoot();
		//DendrogramNode cur = root;
		/*count = 0;
		/while(cur.getLeft() != null){
			System.out.println("Main Observation: " + cur.getObservationCount() + " Left Observation: " + cur.getLeft().getObservationCount() + " Right Observation: " + cur.getRight().getObservationCount());
			count += 1;
			cur = cur.getLeft();
			if(cur.getRight() == null){
				System.out.println("Observe = " + cur.getObservationCount());
			}
		}*/
		
		//leafNodes(root);
		//System.out.println("Count = " + count);
		
		for(int i = 0; i < count; i++){
			//System.out.println(i + " = " + roots[i]);
		}
		
	}
	
	public static void leafNodes(DendrogramNode n){
		if(n.getLeft() != null){
			leafNodes(n.getLeft());
		}
		if(n.getRight() != null){
			leafNodes(n.getRight());
		}
		if(n.getLeft() == null && n.getRight() == null){
			roots[total] = 1;
			total += 1;
		}
	}

}