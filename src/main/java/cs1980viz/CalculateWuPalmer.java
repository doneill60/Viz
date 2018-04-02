package cs1980viz;

import org.apache.commons.text.similarity.CosineSimilarity;

import java.io.*;
import java.util.*;

public class CalculateWuPalmer {
    static final Map<String, Integer> lv = new HashMap<String, Integer>();
    static final Map<String, Integer> rv = new HashMap<String, Integer>();
    static final Map<String, Integer> df = new HashMap<String, Integer>();
    static String location;
    static PriorityQueue<FileData> pq;
    static WuPalmerSimilarity wup = null;
    //Load csv directory for repeated use later
    static File csvDirectory;
    static int docCount;

    public static void main (String matchType, int data){
        //Calculate document frequency for use by the Wu and Palmer Algorithm
        calculateDF();
        wup = new WuPalmerSimilarity(df, docCount);
        String matchType = "";
        String similarityType = "";
        int simFiles = 0;
        int percentage = 0;
        int data = 0;


        try{
            File configFile = new File("src/main/resources/config.properties");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            reader.close();

            matchType = props.getProperty("MATCH_TYPE");
            simFiles = Integer.parseInt(props.getProperty("NUM_TOP_FILES"));
            percentage = Integer.parseInt(props.getProperty("PERCENT_MATCH"));
            similarityType = props.getProperty("SIMILARITY_TYPE");
            location = props.getProperty("OUTPUT_CSV_LOC");
            csvDirectory = new File(location);
        }
        catch(Exception e){
            System.out.println(e);
            System.exit(1);
        }

        if (similarityType.equals("wupalmer")){
            if (matchType.equals("t")){
                data = simFiles;
            }
            else{
                data = percentage;
            }

        }

        String fileLocation = "output";
        //Removing previously generated data
        File rm = new File(fileLocation);
        for(File file:rm.listFiles()){
            file.delete();
        }

        File txt = new File(location);
        FileWriter fwoutput = null;
        PrintWriter pwoutput = null;
        try {
            fwoutput = new FileWriter(new File("output","output.csv"));
            pwoutput = new PrintWriter(fwoutput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(File fileName:csvDirectory.listFiles()) {

            String leftFile = location + "/" + fileName.getName();
            Comparator<FileData> comparator = new FileDataComparator();

            try {

                BufferedReader reader = new BufferedReader(new FileReader(leftFile));
                String lineToParse;

                while ((lineToParse = reader.readLine()) != null) {
                    String[] parsed = lineToParse.split(",");
                    lv.put(parsed[0], Integer.parseInt(parsed[1]));
                }
                reader.close();

                File folder = new File(location);
                File[] list = folder.listFiles();

                //This is actually a min pq, we reversed the order of the comparator to compensate
                pq = new PriorityQueue<FileData>((list.length - 1), comparator);
                pwoutput.print(leftFile+",");

                //Comparing file with all other files
                for (int i = 0; i < list.length; i++) {

                    //Clearing hashmap
                    rv.clear();

                    String rightFile = location + "/" + list[i].getName();
                    pwoutput.print(rightFile+",");

                    //Making sure we arent comparing the same file
                    if (!leftFile.equals(rightFile)) {
                        reader = new BufferedReader(new FileReader(rightFile));

                        while ((lineToParse = reader.readLine()) != null) {
                            String[] parsed = lineToParse.split(",");
                            rv.put(parsed[0], Integer.parseInt(parsed[1]));
                        }
                        reader.close();

                        double sim = wup.calculateSim(lv, rv);
                        pwoutput.print(sim+",");
                        pq.add(new FileData(list[i].getName(), sim));
                    }
                }
                lv.clear();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /*
    *   Since our formula requires the idf of each term, the method first calculates
    *   the document frequency, or the number of documents that each term appears in.
    *   In the process, it counts the number of documents.
    *
    * */
    public static void calculateDF(){
        //Calculate the df map, mapping each term to the number of documents it appears in
        File csvDirectory = new File("csv_data");
        File[] files = csvDirectory.listFiles();
        docCount = files.length;
        for(File current:files){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(current));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String lineToParse;

            try {
                while ((lineToParse = reader.readLine()) != null) {
                    String[] parsed = lineToParse.split(",");
                    Integer docCount = df.get(parsed[0]);
                    //Case 1: This term has never been seen in the doc set
                    if(docCount==null){
                        df.put(parsed[0], 1);
                    }
                    //Case 2: This term has appeared again, update the count
                    else{
                        df.put(parsed[0], docCount+1);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
