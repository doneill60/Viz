package cs1980viz;

import java.io.*;
import java.util.Properties;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class DisplayClusters {
	//public static void main(String args[]) throws IOException, FileNotFoundException{
		public DisplayClusters (){}
		
		public ArrayList<DendrogramNode> getClusters(Dendrogram dendosaur) {
			ArrayList<DendrogramNode> clusters = new ArrayList<DendrogramNode>();
			
			Queue<DendrogramNode> queue = new LinkedList<DendrogramNode>();
			queue.clear();
			queue.add(dendosaur.getRoot());
			while (!queue.isEmpty() && clusters.size() < 300) {
				DendrogramNode node = queue.remove();
				if (node.getLeft() == null && node.getRight() == null) clusters.add(node);
				if (node.getLeft() != null) {
					queue.add(node.getLeft());
				}
				if (node.getRight() != null) {
					queue.add(node.getRight());
				}
			}
			
			while(!queue.isEmpty()) {
				DendrogramNode node = queue.remove();
				clusters.add(node);
			}
			
			return clusters;
		}
		
		public String[] getNames() throws IOException{
			String matrixLoc = "output/output.csv";
			FileReader fr = null;
			try {
				fr = new FileReader(matrixLoc);
			}
			catch (FileNotFoundException e) {
				System.exit(0);
			}
			BufferedReader br  = new BufferedReader(fr);
			String line = br.readLine();
			String[] split = line.split(",");
			int count = (split.length-1)/2;
			
			String[] names = new String[count];
			int num = 0;
			while (num < count) {
				for (int i = 1; i < split.length; i+=2) {
					names[num] = split[i];
				}
				num++;
			}
			
			return names;
		}
	//}
}