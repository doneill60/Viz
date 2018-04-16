
package cs1980viz;
import java.io.*;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ComputeHAC{
	private static int total = 0;
	static ArrayList<Integer> list;
	static String location;
	public static void main(String[] args) throws IOException, FileNotFoundException{
		String csv = "";
		int clusterNum = 0;
		int total = 0;
	
		//Reading in from a configuration file
		try{
			File configFile = new File("src/main/resources/cluster.properties");
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			reader.close();
			
			csv = props.getProperty("CSV_LOCATION");
			clusterNum = Integer.parseInt(props.getProperty("NUM_CLUSTERS"));
			total = Integer.parseInt(props.getProperty("NUM_IN_CLUSTERS"));
			location = props.getProperty("OUTPUT_HTML_LOC");
		}
		catch(Exception e){
			System.out.println(e);
			System.exit(1);
		}
		//Reading how many files are in total
		String filename = csv;
		FileReader fr = new FileReader(filename);
		BufferedReader br  = new BufferedReader(fr);
		String curLine;
		int count = 0;
		while((curLine = br.readLine()) != null){
			count = count + 1;
		}
		//Setting up the variables for HAC
		Experiment experiment = new Experiment(count);
		DissimilarityMeasure dissimilarityMeasure = new DissimilarityMeasure(filename, count);
		AgglomerationMethod agglomerationMethod = new AverageLinkage();
		DendrogramBuilder dendrogramBuilder = new DendrogramBuilder(experiment.getNumberOfObservations());
		HierarchicalAgglomerativeClusterer hac = new HierarchicalAgglomerativeClusterer(experiment, dissimilarityMeasure, agglomerationMethod);
		hac.cluster(dendrogramBuilder);
		Dendrogram dendrogram = dendrogramBuilder.getDendrogram();
			
		DisplayClusters dc = new DisplayClusters();
		
		//ArrayList<DendrogramNode> clusters = dc.getClusters(dendrogram);
		//ArrayList<DendrogramNode> topClusters = new ArrayList<DendrogramNode>();
		//	for (int j = 0; j < 2; j++) {
		//		int max = 0;
		//	int maxPos = 0;
		//	for (int i = 0; i < clusters.size(); i++) {
		//		if (clusters.get(i).getObservationCount() > max) {
		//			max = clusters.get(i).getObservationCount();
		//			maxPos = i;
		//		}	
		//	}
		//	DendrogramNode node = clusters.get(maxPos);
			//topClusters.add(node);
		//	clusters.remove(maxPos);
		//}
		//Getting all leaf nodes in an arraylist
		String[] names = dc.getNames();
		List<List<Integer>> clusterVals = new ArrayList<List<Integer>>();
		list = new ArrayList<>();
		leafNodes(dendrogram.getRoot());
		
		//Splitting the values up into the clusters
		int clusterSize = count / clusterNum;
		int count2 = 0;
		for(int i = 0; i < clusterNum; i++){
			clusterVals.add(list.subList(count2, count2 + clusterSize));
			count2 = count2 + clusterSize;
		}
		generateHTML(clusterVals, names);

	}
	
	//HTML files showing all the clusters and their contents
	public static void generateHTML(List<List<Integer>> clusterVals, String[] names) throws IOException{
		//Removing previously generated data
		File rm = new File(location);
		for(File file:rm.listFiles()){
			file.delete();
		}
		
		for (int i = 0; i < clusterVals.size(); i++) {
			FileWriter fw = new FileWriter(new File(location,"Cluster" + (i+1) +".html"));
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<body>");
			pw.println("<h1>Cluster"+(i + 1)+"</h1>");
			pw.println("<h2>Number of elements in cluster: " + clusterVals.get(i).size() + "<br/></h2>");
			for(int j = 0; j < clusterVals.get(i).size(); j++){
				String name = names[clusterVals.get(i).get(j)];
				String[] output = name.split("/");
				pw.println(output[1]);
				pw.println("<br/>");
			}
			pw.println("</body>");
			pw.println("</html>");
			pw.close();
		}
		
		FileWriter fw = new FileWriter(new File(location,"Clusters.html"));
		PrintWriter pw = new PrintWriter(fw);
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<body>");
		pw.println("<h1>Clusters</h1>");
		for(int i = 0; i < clusterVals.size(); i++){
			pw.println("<a href=\"Cluster"+(i + 1)+".html\">Cluster"+(i+1)+".html</a>");
			pw.println("<br/>");
		}
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
		
		

	}
	//Recursion to find bottom of tree
	public static void leafNodes(DendrogramNode n){
		if(n == null){
			return;
		}
		if(n.getLeft() == null && n.getRight() == null){
			list.add(n.getObservation());
		}
		
		leafNodes(n.getLeft());
		leafNodes(n.getRight());
	}

}