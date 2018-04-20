package cs1980viz;//package ch.usi.inf.sape.hac;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClusterMappings{
    private Map<Long, DendrogramNode> mappings;
    private long highestCommonNode[][];
    private Dendrogram dendrogram;
    private int nObservations;

    ClusterMappings(Dendrogram dendrogram, Experiment experiment){
        this.mappings = new HashMap<>();
        this.dendrogram = dendrogram;
        this.nObservations = experiment.getNumberOfObservations();
        this.highestCommonNode = new long[nObservations+1][nObservations+1];
    }

    public void createMap(){
        mappings.put((long)1, dendrogram.getRoot());
        map(dendrogram.getRoot(), dendrogram.getRoot().getLeft(), dendrogram.getRoot().getRight(), true, 1, 0);
        map(dendrogram.getRoot(), dendrogram.getRoot().getRight(), dendrogram.getRoot().getLeft(), false, 1, 0);
    }

    private void map(DendrogramNode parent, DendrogramNode current, DendrogramNode other, boolean wentLeft, long path, int level){
        long newPath = 0;

        if(!wentLeft){
            newPath = path << 1;
            newPath = newPath | 1;
        }else {
            newPath = path << 1;
            newPath = newPath | 0;
        }

        //Make the mapping to be referenced later
        mappings.put(newPath, current);

        //Update the highest common node

        //For every combination update the highest common node to be used as a key to index into the mapping
        if(wentLeft) {
            for (Integer cur : current.getDocs()) {
                for (Integer cur2 : other.getDocs()) {
                    int min = Math.min(cur, cur2);
                    int max = Math.max(cur, cur2);
                    highestCommonNode[min][max] = path;
                }
            }
        }
        //Base case: Leaf Node
        if(current instanceof ObservationNode){
            return;
        }
        map(current, current.getLeft(), current.getRight(),true, newPath, level+1);
        map(current, current.getRight(), current.getLeft(), false, newPath, level+1);
    }

    public void writeArray(){
        try {
            File clusterMain = new File("output\\array");
            FileOutputStream fos = new FileOutputStream(clusterMain);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(highestCommonNode);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void writeMappings(){
        File clusterMain = new File("html/clusters");
        clusterMain.mkdir();
        File csvData = new File("csv_data");
        Map<Integer, String> csvFileNames = new HashMap<>();
        try {
            int i = 1;
            for(File file:csvData.listFiles()){
                csvFileNames.put(i,file.getName());
                i++;
            }
            for (File file : clusterMain.listFiles()) {
                file.delete();
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }
        try {
            for (Long cur : mappings.keySet()) {
                FileWriter fw = new FileWriter(new File("html/clusters", "cluster"+cur.toString() + ".html"));
                PrintWriter pw = new PrintWriter(fw);
                pw.println("<!DOCTYPE html>");
                pw.println("<html>");
                pw.println("<body>");
                pw.println("<h1>Cluster "+ cur.toString() + "</h1>");
                pw.println("<h2> Member Elements </h2>");
                DendrogramNode dn = mappings.get(cur);
                ArrayList<Integer> docSet = dn.getDocs();
                if(docSet.isEmpty()){
                    System.out.println(cur.toString());
                }
                int j = 1;
                for(Integer neighbor: docSet){
                    String filename = csvFileNames.get(j);
                    pw.println("<p>"+ filename + "</p>");
                    j++;
                }
                pw.println("</body>");
                pw.println("</html>");
                pw.close();
                fw.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public long[][] getHighestCommonNode(){
        return highestCommonNode;
    }

    public Map<Long, DendrogramNode> getMappings(){
        return mappings;
    }
}
