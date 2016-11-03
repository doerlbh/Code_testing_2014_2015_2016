/** ComparePaintings.java
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

public class ComparePaintings {

	// Fields
	private ColorHash ch;		// ColorHash
	private FeatureVector fv;	// FeatureVector
	private long sumCol;		// sum of collisions
	private String pm;			// probing method

	// Constants
	private static final int DEFAULT_BPP = 6;		// default bits Per Pixel
	private static final int DEFAULT_TBS = 11;		// default table size
	private static final double DEFAULT_RLF = 0.4;	// default rehash load factor

	// constructor.
	public ComparePaintings(){
		pm = ColorHash.LINEAR_PROBING;
		sumCol = 0;
	}; 

	// Load the image, construct the hash table, count the colors.
	ColorHash countColors(String filename, int bitsPerPixel) throws Exception {

		sumCol = 0;
		ImageLoader il = new ImageLoader(filename);
		ResponseItem ri = null;
		ch = new ColorHash(DEFAULT_TBS, bitsPerPixel, pm, DEFAULT_RLF);
		for (int x = 0; x < il.getWidth(); x++) {
			for (int y = 0; y < il.getHeight(); y++) {
				sumCol += ri.nCollisions;
				ri = ch.increment(il.getColorKey(x, y, bitsPerPixel));
			}
		}
		fv = new FeatureVector(bitsPerPixel);
		fv.getTheCounts(ch);
		return ch;
	}

	//	Starting with two hash tables of color counts, compute a measure of similarity based on the cosine distance of two vectors.
	double compare(ColorHash painting1, ColorHash painting2) throws Exception {
		FeatureVector fv1 = new FeatureVector(DEFAULT_BPP);
		FeatureVector fv2 = new FeatureVector(DEFAULT_BPP);
		fv1.getTheCounts(painting1);
		fv2.getTheCounts(painting2);
		double cosSim = fv1.cosineSimilarity(fv2);
		return cosSim;
	}

	//	A basic test for the compare method: S(x,x) should be 1.0, so you should compute the similarity of an image with itself and print out the answer. If it comes out to be 1.0, that is a good sign for your implementation so far.
	void basicTest(String filename) throws Exception {
		ComparePaintings cp2 = new ComparePaintings();
		cp2.countColors(filename, DEFAULT_BPP);
		System.out.print("Cosine Similarity of the two images is ");
		System.out.println(fv.cosineSimilarity(cp2.fv));		
	}

	//		Using the three given painting images and a variety of bits-per-pixel values, compute and print out a table of collision counts in the following format:
	void CollisionTests() {
		// Implement this.				
	}

	// This simply checks that the images can be loaded, so you don't have 
	// an issue with missing files or bad paths.
	void imageLoadingTest() {
		ImageLoader mona = new ImageLoader("MonaLisa.jpg");
		ImageLoader starry = new ImageLoader("StarryNight.jpg");
		ImageLoader christina = new ImageLoader("ChristinasWorld.jpg");
		System.out.println("It looks like we have successfully loaded all three test images.");

	}

	/**
	 * This is a basic testing function, and can be changed.
	 */
	public static void main(String[] args) {
		ComparePaintings cp = new ComparePaintings();
		cp.imageLoadingTest();

		ComparePaintings cp1 = new ComparePaintings();

	}

}
