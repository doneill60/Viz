package cs1980viz;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import edu.mit.jwi.*;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.item.*;

public class WuPalmerSimilarity {
    //A reference to our wordnet dictionary
    IDictionary dict = null;
    //Number of documents in our collection
    int docCount;
    POS[] pos = new POS[4];
    Map<String, Integer> df = null;

    /*
    *   Constructor
    *
    *       @param df the document frequency map containing the number of documents each term appears in
    *       @param docCount the total number of documents needed for the formula
    */
    public WuPalmerSimilarity(Map<String, Integer> df, int docCount) {
        //Set the document frequency map
        this.df = df;
        this.docCount = docCount;

        /*  In case we decide to load the wordnet dictionary into ram. This is the fastest way
        IRAMDictionary dict = new RAMDictionary (wnDir , ILoadPolicy . NO_LOAD );
        dict . load ( true );
        */

        // construct the URL to the Wordnet dictionary directory
        String wnhome = System.getenv("WNHOME");
        String path = wnhome + File.separator + "dict";
        //String path = "C:\\Users\\user\\Desktop\\WordNet\\dict";
        URL url = null;
        try {
            url = new URL("file", null, path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url == null) return;
        // construct the dictionary object and open it
        dict = new Dictionary(url);
        try {
            dict.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Add the various parts of speech
        pos[0] = POS.NOUN;
        pos[1] = POS.VERB;
        pos[2] = POS.ADJECTIVE;
        pos[3] = POS.ADVERB;
    }

    /*
        Method calculates the similarity between any two documents represented by the vectors.

            @param leftVector  Document 1 as a vector
            @param rightVector   Document 2 as a vector
            @return the similarity between them
     */
    public double calculateSim(Map<String, Integer> leftVector, Map<String, Integer> rightVector) {

        Set<String> leftTermSet = leftVector.keySet();
        Set<String> rightTermSet = rightVector.keySet();

        //Left numerator from equation: See Similarities.md in GitHub directory
        double leftSimToRight = 0;
        //Right numerator
        double rightSimToLeft = 0;

        //left sum from equation
        int leftIDFsum = 0;
        //right sum from equation
        int rightIDFsum = 0;

        for (String term : leftTermSet) {
            //Calculate numerator
            double maxSim = calculateMaxSim(term, rightVector);
            //Make sure the term is in the wordnet database. If not, skip it.
            if(maxSim < 0){
                continue;
            }
            leftSimToRight += maxSim;

            //Denominator
            int termDF = df.get(term);
            //Use the document frequency to calculate the IDF needed for the formula
            double termIDF = Math.log((double)docCount/termDF);
            leftIDFsum += termIDF;
        }

        for (String term : rightTermSet) {
            //Calculate numerator
            double maxSim = calculateMaxSim(term, leftVector);
            //Make sure the term is in the wordnet database. If not, skip it.
            if(maxSim < 0){
                continue;
            }
            rightSimToLeft += maxSim;

            //Denominator
            int termDF = df.get(term);
            //Use the document frequency to calculate the IDF needed for the formula
            double termIDF = Math.log((double)docCount/termDF);
            rightIDFsum += termIDF;
        }

        return .5 * ((leftSimToRight/leftIDFsum) + (rightSimToLeft/rightIDFsum));
    }

    /*
    *   Helper method that takes a term from the first document and calculates the max similarity
    *   between that term and every term in the other document
    *
    *       @param term the term to compare
    *       @vector the other document represented as a vector
    *       @return the max similarity between the term and every term in the other document or -1 if
    *       the word is not found in WordNet
    * */
    public double calculateMaxSim(String term, Map<String, Integer> vector) {
        //idf for the current term
        //double curIDF = Math.log(df.get(term));
        double curIDF = 1;

        //The max similarity score between this term and every term in the vector
        double maxSim = 0.0;

        IIndexWord idxWordLeft = null;
        //Part of Speech Type Array Index
        // Assume its a noun first
        int index = 0;
        while (idxWordLeft == null && index < 4) {
            idxWordLeft = dict.getIndexWord(term, pos[index]);
            index++;
        }
        //Get the first such instance
        IWordID wordIDLeft = null;
        try {
            wordIDLeft = idxWordLeft.getWordIDs().get(0);
        } catch (NullPointerException e) {
            //No word and part of speech matches, skip this comparison
            return -1.0;
        }
        IWord wordLeft = dict.getWord(wordIDLeft);
        ISynset synsetLeft = wordLeft.getSynset();

        //Get the synset hierarchy, selecting the first synset of the hypernym at each level
        List<ISynsetID> hypernymsLeft = new LinkedList<>();
        List<ISynsetID> temp = synsetLeft.getRelatedSynsets(Pointer.HYPERNYM);
        while (!temp.isEmpty()) {
            ISynsetID first = temp.get(0);
            hypernymsLeft.add(first);
            temp = dict.getSynset(first).getRelatedSynsets(Pointer.HYPERNYM);
        }
        Collections.reverse(hypernymsLeft);


        //Find the max similarity between the term provided to every term in the opposite doc set
        for (String curTerm : vector.keySet()) {
            IIndexWord idxWordRight = null;
            //Part of Speech Type Array Index
            //Assume its a noun first
            index = 0;
            while (idxWordRight == null && index < 4) {
                idxWordRight = dict.getIndexWord(curTerm, pos[index]);
                index++;
            }
            //Get the first such instance
            IWordID wordIDRight = null;
            try {
                wordIDRight = idxWordRight.getWordIDs().get(0);
            } catch (NullPointerException e) {
                //No word and part of speech matches, skip this comparison
                continue;
            }
            IWord wordRight = dict.getWord(wordIDRight);
            ISynset synsetRight = wordRight.getSynset();

            // get the hypernyms
            //Get the synset hierarchy, selecting the first synset of the hypernym at each level
            List<ISynsetID> hypernymsRight = new LinkedList<>();
            temp = synsetRight.getRelatedSynsets(Pointer.HYPERNYM);
            while (!temp.isEmpty()) {
                ISynsetID first = temp.get(0);
                hypernymsRight.add(first);
                temp = dict.getSynset(first).getRelatedSynsets(Pointer.HYPERNYM);
            }

            Collections.reverse(hypernymsRight);
            Iterator<ISynsetID> iteratorRight = hypernymsRight.iterator();

            //Compare the trees to find the Least Common Subsumer
            int lcs = 0;
            Iterator<ISynsetID> iteratorLeft = hypernymsLeft.iterator();
            while(iteratorLeft.hasNext() && iteratorRight.hasNext()){
                ISynsetID curLeft = iteratorLeft.next();
                ISynsetID curRight = iteratorRight.next();
                if(curLeft.equals(curRight)){
                    lcs++;
                }
                else{
                    break;
                }
            }

            double curSimilarity = 2*lcs/(double)(hypernymsLeft.size() + hypernymsRight.size());
            if(curSimilarity > maxSim){
                maxSim = curSimilarity;
            }
        }
        return maxSim * curIDF;
    }
}
