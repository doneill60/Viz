# Text Based Similarities

## Overlap/Jaccard Similarity

This simply evalutes the concepts in the document as a set. The core idea being to see which concepts overlap between any 2 documents. The formula is given below:

[[https://github.com/zacharymell/CS1980/img/overlap.png]]

The formula essentially asks are these two documents more similar than they are different?


## Cosine Similarity

The core idea of this algorithm is turning documents into vectors. If you'll recall, no matter the dimension, a vector is a straight line  with different dependent variables for each independent variable. Let us consider a simple example: Assume that document A only has 2 words, and similarly, document B only has two words. Lets assume for document A that "Wumpus" appears 3 times and "Chungus" appears 2. Turning this into a vector, we get the vector defined by the point in space (3,2). Lets assume that for document B "Wumpus" appears 1 time and "Chungus" appears 5. The resulting vector is given by the point in space (1,5). One way to determine the similarity of vectors in space is to line up the tails and measure the angle between them. That is all cosine similarity is doing. Turning documents into vectors, and calculating similarity based on the angle between those vectors. The formula is given below

[[https://github.com/zacharymell/CS1980/img/cosine.png]]

More info can be found below:
http://stefansavev.com/blog/cosine-similarity/

# Drawbacks

Some drawbacks of using this method is that it purely operates on text and doesn't have a sense of semantics or synonyms. For instance if the word "stalk" and "stem" are in two documents the algorithm will deem the concepts different when thats far from the case. This can be tackled by either having the algorithm extracting the concepts use consistent binning or by utlizing semantic similarity. Relevant documents can be found below:

http://infolab.stanford.edu/~ullman/mmds/ch3.pdf
http://aircconline.com/mlaij/V3N1/3116mlaij03.pdf
http://wordnetweb.princeton.edu/perl/webwn?s=stem&sub=Search+WordNet&o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&h=0000000
https://web.stanford.edu/class/cs124/lec/semlec.pdf
