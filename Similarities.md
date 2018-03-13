# Text Based Similarities

## Overlap/Jaccard Similarity

This simply evalutes the concepts in the document as a set. The core idea being to see which concepts overlap between any 2 documents. The formula is given below:

![](/img/ovelap.PNG?raw=true) 

And for reference, set anatomy:

![](/img/set_vis.PNG?raw=true) 

The formula essentially asks are these two documents more similar than they are different?

An example:

![](/img/jaccard_example.PNG?raw=true) 

Pros: Engineering Effort - Its very simple to implement
Cons: Synonymy and Polysemy: Has only a binary sense of similarity. Either they are the same word or not. No regard for synonyms, taxonomy, etc. Also words might be the same but mean totally different things. Consider "Park". It can be a verb describing an action in driving or it can be used to describe a place.

## Cosine Similarity

The core idea of this algorithm is turning documents into vectors. If you'll recall, no matter the dimension, a vector is a straight line  with different dependent variables for each independent variable. Our independent variables here are the terms and the dependent variables here correspond to the term count. Each of these variable pairs gets plotted and each document gets represented as a vector, with each combination of vectors getting compared.  Let us consider a simple example: Assume that document A only has 2 words, and similarly, document B only has two words. Lets assume for document A that "Wumpus" appears 3 times and "Chungus" appears 2. Turning this into a vector, we get the vector defined by the point in space (3,2). Lets assume that for document B "Wumpus" appears 1 time and "Chungus" appears 5. The resulting vector is given by the point in space (1,5). One way to determine the similarity of vectors in space is to line up the tails and measure the angle between them. That is all cosine similarity is doing. Turning documents into vectors, and calculating similarity based on the angle between those vectors. The formula is given below

![](/img/cosine.PNG?raw=true) 

Pros: Research seems to indicate that it is both faster than Jaccard and more effective for similar applications*
Cons: Shares same setback due to binary nature of similarity.

More info can be found below:
http://stefansavev.com/blog/cosine-similarity/

*Reference:
http://www.ijaret.org/2.10/COMPARATIVE%20ANALYSIS%20OF%20JACCARD%20COEFFICIENT%20AND%20COSINE%20SIMILARITY%20FOR%20WEB%20DOCUMENT%20SIMILARITY%20MEASURE.pdf

## TF-IDF Cosine Similarity

This aims at providing a measure of importance to a word rather than simply its number of occurences. Consider a trivial example, every document in the collection is from the same place and contains the same header. Due to its repetitiveness, this wouldn't be useful information to use as the basis of comparison, so TF-IDF would render those words worthless. Consider another example. You have a cluster of files that all contains information about Pittsburgh touring sights. Two of those are historical sites and contain the word "history" in them. To someone searching for one historical site, the fact that the other is also a historical site would make that other document one of particular interest. Attatching more weight to this via TF-IDF is one way of distinguishing it's importance.

Pros: Weights allow more important words to carry more weight
Cons: Same as Cosine

# Wu and Palmer Knowledge-Based Similarity

We could use the following formula to compare two documents:

![](/img/knowledge_based.PNG?raw=true) 

and compute the similarities with the following similarity measure:

![](/img/wup.PNG?raw=true) 

This would use WordNet's free-to-use taxonomy system to determine each term's categorization.

![](/img/wordnet-hierarchy.png?raw=true)

Pros: More natural way of comparing words. Synonyms will be reflected as similar.
Cons: Expensive to calculate. Querying the dictionary to get taxonomy for every pair of terms and for every pair of documents.


# More Resources

http://infolab.stanford.edu/~ullman/mmds/ch3.pdf
http://aircconline.com/mlaij/V3N1/3116mlaij03.pdf
http://wordnetweb.princeton.edu/perl/webwn?s=stem&sub=Search+WordNet&o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&h=0000000
https://web.stanford.edu/class/cs124/lec/semlec.pdf
