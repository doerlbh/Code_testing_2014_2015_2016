//import ColorHash.InvalidLoadFactorException;

/** FeatureVector.java
 *  a class for  .
 * 
 * by Baihan Lin
 * for CSE 373 Assignment 3, Autumn, 2016.
 * Section BC. 
 * 
 * @author Baihan Lin  < sunnylin@uw.edu >
 * @version 	1.2
 * @since 		1.0 2016-10-25
 *
 */

public class FeatureVector {

	/**
	 * FeatureVector is a class for storing the results of
	 * counting the occurrences of colors.
	 * <p>
	 * Unlike the hash table, where the information can be
	 * almost anyplace with the array(s) (due to hashing), in the FeatureVector,
	 * the colors are in their numerical order and each count
	 * is in the array position for its color.
	 * <p>
	 * Besides storing the information, the class provides methods
	 * for getting the information (getTheCounts) and for computing
	 * the similarity between two vectors (cosineSimilarity).
	 */
	long[] colorCounts;
	int bitsPerPixel;
	int keySpaceSize;

	/**
	 * Constructor of a FeatureVector.
	 * 
	 * This creates a FeatureVector instance with an array of the
	 * proper size to hold a count for every possible element in the key space.
	 * 
	 * @param bpp	Bits per pixel. This controls the size of the vector.
	 * 				The keySpace Size is 2^k where k is bpp.
	 * 
	 */
	public FeatureVector(int bpp) {
		keySpaceSize = 1 << bpp;
		colorCounts = new long[keySpaceSize];
	}

	// You should implement this method.
	// It will go through all possible key values in order,
	// get the count from the hash table and put it into this feature vector.
	public void getTheCounts(ColorHash ch) {
		// TODO
		for (int i = 0; i < ch.getTableSize(); i++) {
			if (ch.getKeyAt(i) != null) {
				ColorKey key = ch.getKeyAt(i);
				colorCounts[i] = ch.getCount(key);
			}
		}
	}

	// Implement this method. Use the formula given in the A3 spec,
	// which is also explained at
	// https://en.wikipedia.org/wiki/Cosine_similarity
	// where A is this feature vector and B is the other feature vector.
	// When multiplying in the dot product, convert all the long values
	// to double before doing the multiplication.

	// Hint: you may wish to write some private methods here to help
	// computing the cosine similarity.  For example, it could be
	// nice to have a dot product method and a vector magnitude method.
	public double cosineSimilarity(FeatureVector other) throws Exception {
		// TODO
		double dotProd = dot(this.colorCounts, other.colorCounts);
		double vecProd = vec(this.colorCounts, other.colorCounts);
		double cosSim = dotProd/vecProd;
		return cosSim; 
	}

	private double vec(long[] A, long[] B) throws Exception {
		// TODO Auto-generated method stub
		if (A.length != B.length){
			throw new ArithmeticException("Vector must have the same length.");
		}
		double prod = 0;
		for (int i = 0; i < A.length; i++) {
			prod += (double) A[i] * (double) B[i];
		}
		return prod;
	}

	private double dot(long[] A, long[] B) throws Exception {
		// TODO Auto-generated method stub
		if (A.length != B.length){
			throw new ArithmeticException("Vector must have the same length.");
		}
		double prodA = 0;
		double prodB = 0;
		for (int i = 0; i < A.length; i++) {
			prodA += (double) A[i] * (double) A[i];
			prodB += (double) B[i] * (double) B[i];
		}
		double prod = Math.sqrt(prodA)*Math.sqrt(prodB);
		return prod;
	}

	/**
	 * Optional main method for your own tests of these methods.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ColorKey green = new ColorKey(0, 255, 0, 6);
			System.out.println("green ColorKey is: "+green);
			ColorKey black = new ColorKey(0, 0, 0, 6);
			System.out.println("black ColorKey is: "+black);
			ColorKey white = new ColorKey(255, 255, 255, 6);
			System.out.println("white ColorKey is: "+white);
			ColorKey random1 = new ColorKey(12, 156, 0, 6);
			System.out.println("random1 ColorKey is: "+random1);
			ColorKey random2 = new ColorKey(255, 145, 0, 6);
			System.out.println("random2 ColorKey is: "+random2);
			ColorKey random3 = new ColorKey(0, 255, 255, 6);
			System.out.println("random3 ColorKey is: "+random3);
			
			ColorKey blackKey8  = new ColorKey(91, 15);
			System.out.println("blackKey8 ColorKey is: "+blackKey8);

			ColorHash ch = new ColorHash(3, 6, "Linear Probing", 0.9);
			System.out.println("Linear Probing Testing");
			
			ch.increment(green);
			System.out.println("increment green Testing");
			ch.increment(green);
			System.out.println("increment green Testing");

			ch.increment(black);
			System.out.println("increment black Testing");
			ch.increment(black);
			System.out.println("increment black Testing");
			ch.increment(black);
			System.out.println("increment black Testing");
			ch.increment(black);
			System.out.println("increment black Testing");
			
			ch.increment(white);
			System.out.println("increment white Testing");
			ch.increment(white);
			System.out.println("increment white Testing");
			ch.increment(white);
			System.out.println("increment white Testing");
			
			ch.increment(random1);
			System.out.println("increment random1 Testing");
			ch.increment(random2);
			System.out.println("increment random2 Testing");
			ch.increment(random3);
			System.out.println("increment random3 Testing");
			ch.increment(blackKey8);
			System.out.println("increment blackKey8 Testing");
			
			FeatureVector fv = new FeatureVector(6);
			System.out.println("the keyspace for  FeatureVector is " + fv.keySpaceSize);
			
			fv.getTheCounts(ch);
			System.out.println("fv's colorCounts" + fv.colorCounts);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}

	}

}
