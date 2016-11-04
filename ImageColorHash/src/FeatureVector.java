/** FeatureVector.java
 *  a class for for storing the results of counting the occurrences of colors.
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

	/** A method to go through all possible key values in order
	 * and get the count from the hash table and put it into 
	 * this feature vector.
	 * @param ch: the ColorHash being called
	 * @throws Exception if ColorHash not met
	 */
	public void getTheCounts(ColorHash ch) throws Exception {
		for (int i = 0; i < keySpaceSize; i++) {
			ColorKey key = new ColorKey(i, bitsPerPixel);;
			if (key != null) {
				colorCounts[i] = ch.getCount(key);
			}
		}

	}

	/** a method to calculate the cosine similarity value of the 
	 * other FeatureVector versus current FeatureVector
	 * 
	 * @param other: the other FeatureVector to Compare
	 * @return cosSim: the cosine similarity value of the two vectors
	 * @throws Exception if condition not met
	 */
	public double cosineSimilarity(FeatureVector other) throws Exception {
		double dotProd = dot(this.colorCounts, other.colorCounts);
		double vecProd = vec(this.colorCounts, other.colorCounts);
		double cosSim = dotProd/vecProd;
		return cosSim; 
	}

	/** a private method to support cosineSimilarity.
	 * To calculate the dot product of two vectors
	 * 
	 * @param A: a vector in format of long[]
	 * @param B: a vector in format of long[]
	 * @return prod: the dot product of two vectors
	 * @throws Exception if length of two vectors are different
	 */
	private static double dot(long[] A, long[] B) throws Exception {
		if (A.length != B.length){
			throw new ArithmeticException("Vector must have the same length.");
		}
		double prod = 0;
		for (int i = 0; i < A.length; i++) {
			prod += (double) A[i] * (double) B[i];
		}
		return prod;
	}

	/** a private method to support cosineSimilarity.
	 * To calculate the vector product of two vectors
	 * 
	 * @param A: a vector in format of long[]
	 * @param B: a vector in format of long[]
	 * @return prod: the vector product of two vectors
	 * @throws Exception if length of two vectors are different
	 */
	private static double vec(long[] A, long[] B) throws Exception {
		if (A.length != B.length){
			throw new ArithmeticException("Vector must have the same length.");
		}
		double prodA = 0;
		double prodB = 0;
		for (int i = 0; i < A.length; i++) {
			prodA +=  (double) A[i] * (double) A[i];
			prodB +=  (double) B[i] * (double) B[i];
		}
		double prod = Math.sqrt(prodA*prodB);
		return prod;
	}

	/**
	 * Optional main method for your own tests of these methods.
	 */
	public static void main(String[] args) {

		try {

			// ColorKey testing
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

			// ColorHash testing
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

			// FeatureVector testing
			FeatureVector fv = new FeatureVector(6);
			System.out.println("the keyspace for fv is " + fv.keySpaceSize);

			// FeatureVector getTheCounts Testing
			fv.getTheCounts(ch);
			long[] fvcc = fv.colorCounts;
			System.out.print("fv's colorCounts: "); 
			for (int i = 0; i < fvcc.length; i++) {
				System.out.print(fvcc[i]+" ");
			}

			// FeatureVector cosineSimilarity Testing
			ColorHash ch2 = new ColorHash(3, 6, "Linear Probing", 0.9);
			System.out.println("Linear Probing Testing");
			ch2.increment(random2);
			ch2.increment(black);
			ch2.increment(random3);
			ch2.increment(green);
			ch2.increment(random3);
			ch2.increment(blackKey8);
			ch2.increment(black);
			ch2.increment(white);
			ch2.increment(random1);
			FeatureVector fv2 = new FeatureVector(6);
			fv2.getTheCounts(ch2);

			double cosSim = fv.cosineSimilarity(fv2);
			double cosdot = dot(fv.colorCounts, fv2.colorCounts);
			double cosvec = vec(fv.colorCounts, fv2.colorCounts);
			System.out.println("fv/fv2's cosdot = "+cosdot); 
			System.out.println("fv/fv2's cosvec = "+cosvec); 
			System.out.println("fv/fv2's cosSim = "+cosSim); 

			System.out.println("fv/fv's cosSim = "+fv.cosineSimilarity(fv)); 

		}
		catch(Exception e) {
			System.out.println(e);
		}

	}

}
