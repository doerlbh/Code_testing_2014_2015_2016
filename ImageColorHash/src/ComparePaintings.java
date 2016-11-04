/** ComparePaintings.java
 *  a class to compare paints based on ColorHash and FeatureVector.
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
	private static final int DEFAULT_TBS = 13;		// default table size to kick start
	private static final double DEFAULT_RLF = 0.4;	// default rehash load factor to kick start
	private static final String IMG_01 = "MonaLisa.jpg"; 
	private static final String IMG_02 = "StarryNight.jpg"; 
	private static final String IMG_03 = "ChristinasWorld.jpg"; 
	private static final String IMG_04 = "WaterLilies.jpg"; 
	private static final String IMG_05 = "ParisAutumn.jpg"; 
	private static final String IMG_06 = "ButterflyShip.jpg"; 
	private static final String IMG_07 = "Kiss.jpg"; 
	private static final String IMG_08 = "StJeromeReading.jpg"; 
	private static final String IMG_09 = "CityRises.jpg"; 
	private static final String IMG_10 = "Untitled.jpg"; 

	// constructor for ComparePaintings
	public ComparePaintings(){
		pm = ColorHash.LINEAR_PROBING;
		sumCol = 0;
	}; 

	/**
	 *  Load the image, construct the hash table, count the colors.
	 * @param filename: filename of the image loaded
	 * @param bitsPerPixel: bits per pixel used to define ColorHash
	 * @return ch: a ColorHash from the image 
	 * @throws Exception if ColorHash requirement not met
	 */
	ColorHash countColors(String filename, int bitsPerPixel) throws Exception {

		sumCol = 0;
		ImageLoader il = new ImageLoader(filename);
		ResponseItem ri = null;
		ch = new ColorHash(DEFAULT_TBS, bitsPerPixel, pm, DEFAULT_RLF);
		for (int x = 0; x < il.getWidth(); x++) {
			for (int y = 0; y < il.getHeight(); y++) {
				ri = ch.increment(il.getColorKey(x, y, bitsPerPixel));
				sumCol += ri.nCollisions;
			}
		}
		fv = new FeatureVector(bitsPerPixel);
		fv.getTheCounts(ch);
		return ch;
	}

	/**	Starting with two hash tables of color counts, compute a measure
	 * of similarity based on the cosine distance of two vectors.
	 * @param painting1: ColorHash 1
	 * @param painting2: ColorHash 2
	 * @return cosSim: the calculated cosine similarity value
	 * @throws Exception if FeatureVector is not met
	 */
	double compare(ColorHash painting1, ColorHash painting2) throws Exception {
		FeatureVector fv1 = new FeatureVector(DEFAULT_BPP);
		FeatureVector fv2 = new FeatureVector(DEFAULT_BPP);
		fv1.getTheCounts(painting1);
		fv2.getTheCounts(painting2);
		double cosSim = fv1.cosineSimilarity(fv2);
		return cosSim;
	}

	/**	A basic test for the compare method: S(x,x) should be 1.0, 
	 * compute the similarity of an image with itself and print out 
	 * the answer. 
	 * @param filename: the image to load
	 * @throws Exception if ComparePaintings not met
	 */
	void basicTest(String filename) throws Exception {
		ComparePaintings cp2 = new ComparePaintings();
		cp2.countColors(filename, DEFAULT_BPP);
		System.out.print("Cosine Similarity Value between the two images is ");
		System.out.println(fv.cosineSimilarity(cp2.fv));		
	}

	/** Using the three given painting images and a variety 
	 * of bits-per-pixel values, compute and print out a table 
	 * of collision counts in the specified format
	 * @throws Exception if ColorHash not met
	 */
	void CollisionTests() throws Exception {

		System.out.println("Bits Per Pixel   C(Mona,linear)  C(Mona,quadratic)  C(Starry,linear) "
				+ "C(Starry,quadratic) C(Christina,linear) C(Christina,quadratic)");

		ComparePaintings cp01 = new ComparePaintings();
		ComparePaintings cp02 = new ComparePaintings();
		ComparePaintings cp03 = new ComparePaintings();

		for (int bpp = 24; bpp >= 3; bpp -= 3) {

			System.out.format("%-17d", bpp);

			cp01.pm = ColorHash.LINEAR_PROBING;
			cp01.countColors(IMG_01, bpp);
			System.out.format("%-16d", cp01.sumCol);
			cp01.pm = ColorHash.QUADRATIC_PROBING;
			cp01.countColors(IMG_01, bpp);
			System.out.format("%-19d", cp01.sumCol);

			cp02.pm = ColorHash.LINEAR_PROBING;
			cp02.countColors(IMG_02, bpp);
			System.out.format("%-17d", cp02.sumCol);
			cp02.pm = ColorHash.QUADRATIC_PROBING;
			cp02.countColors(IMG_02, bpp);
			System.out.format("%-20d", cp02.sumCol);

			cp03.pm = ColorHash.LINEAR_PROBING;
			cp03.countColors(IMG_03, bpp);
			System.out.format("%-20d", cp03.sumCol);
			cp03.pm = ColorHash.QUADRATIC_PROBING;
			cp03.countColors(IMG_03, bpp);
			System.out.format("%-22d%n", cp03.sumCol);
		}
	}

	/** Using the three given painting images and a variety 
	 * of bits-per-pixel values, compute and print out a 
	 * table of similarity values in the specified format
	 * @throws Exception if ComparePaintings not met
	 */
	void fullSimilarityTests() throws Exception {
		System.out.println("Bits Per Pixel       S(Mona,Starry)    S(Mona,Christina)     S(Starry,Christina)");

		ComparePaintings cp01 = new ComparePaintings();
		ComparePaintings cp02 = new ComparePaintings();
		ComparePaintings cp03 = new ComparePaintings();

		for (int bpp = 24; bpp >= 3; bpp-=3) {

			cp01.countColors(IMG_01, bpp);
			cp02.countColors(IMG_02, bpp);
			cp03.countColors(IMG_03, bpp);

			System.out.format("%-21d", bpp);
			System.out.format("%-18f", cp01.fv.cosineSimilarity(cp02.fv) );
			System.out.format("%-22f", cp01.fv.cosineSimilarity(cp03.fv) );
			System.out.format("%-22f%n", cp02.fv.cosineSimilarity(cp03.fv) );
		}

	}

	/** Choose 10 different images of paintings from the web. Create 
	 * a table with 10 rows and 10 columns that gives the results of 
	 * running the cosine similarity analysis on each pair. Present 
	 * the table neatly in your report.
	 * @throws Exception if ComparePaintings not met
	 */
	void extraCredit10ImagesTest() throws Exception {
		int bpp = 6;

		ComparePaintings cp01 = new ComparePaintings();
		ComparePaintings cp02 = new ComparePaintings();
		ComparePaintings cp03 = new ComparePaintings();
		ComparePaintings cp04 = new ComparePaintings();
		ComparePaintings cp05 = new ComparePaintings();
		ComparePaintings cp06 = new ComparePaintings();
		ComparePaintings cp07 = new ComparePaintings();
		ComparePaintings cp08 = new ComparePaintings();
		ComparePaintings cp09 = new ComparePaintings();
		ComparePaintings cp10 = new ComparePaintings();

		cp01.countColors(IMG_01, bpp);
		cp02.countColors(IMG_02, bpp);
		cp03.countColors(IMG_03, bpp);
		cp04.countColors(IMG_04, bpp);
		cp05.countColors(IMG_05, bpp);
		cp06.countColors(IMG_06, bpp);
		cp07.countColors(IMG_07, bpp);
		cp08.countColors(IMG_08, bpp);
		cp09.countColors(IMG_09, bpp);
		cp10.countColors(IMG_10, bpp);

		System.out.format("%-18s", "");
		System.out.format("%-11s", "MonaLisa");
		System.out.format("%-14s", "StarryNight");
		System.out.format("%-18s", "ChristinasWorld");
		System.out.format("%-14s", "WaterLilies");
		System.out.format("%-14s", "ParisAutumn");
		System.out.format("%-16s", "ButterflyShip");
		System.out.format("%-10s", "Kiss");
		System.out.format("%-18s", "StJeromeReading");
		System.out.format("%-12s", "CityRises");
		System.out.format("%-11s%n", "Untitled");

		System.out.format("%-18s", "MonaLisa");
		System.out.format("%-11f", cp01.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp01.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp01.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp01.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp01.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp01.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp01.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp01.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp01.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp01.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "StarryNight");
		System.out.format("%-11f", cp02.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp02.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp02.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp02.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp02.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp02.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp02.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp02.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp02.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp02.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "ChristinasWorld");
		System.out.format("%-11f", cp03.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp03.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp03.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp03.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp03.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp03.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp03.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp03.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp03.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp03.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "WaterLilies");
		System.out.format("%-11f", cp04.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp04.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp04.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp04.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp04.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp04.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp04.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp04.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp04.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp04.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "ParisAutumn");
		System.out.format("%-11f", cp05.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp05.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp05.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp05.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp05.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp05.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp05.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp05.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp05.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp05.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "ButterflyShip");
		System.out.format("%-11f", cp06.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp06.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp06.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp06.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp06.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp06.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp06.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp06.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp06.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp06.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "Kiss");
		System.out.format("%-11f", cp07.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp07.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp07.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp07.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp07.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp07.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp07.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp07.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp07.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp07.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "StJeromeReading");
		System.out.format("%-11f", cp08.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp08.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp08.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp08.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp08.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp08.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp08.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp08.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp08.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp08.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "CityRises");
		System.out.format("%-11f", cp09.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp09.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp09.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp09.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp09.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp09.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp09.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp09.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp09.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp09.fv.cosineSimilarity(cp10.fv));

		System.out.format("%-18s", "Untitled");
		System.out.format("%-11f", cp10.fv.cosineSimilarity(cp01.fv));
		System.out.format("%-14f", cp10.fv.cosineSimilarity(cp02.fv));
		System.out.format("%-18f", cp10.fv.cosineSimilarity(cp03.fv));
		System.out.format("%-14f", cp10.fv.cosineSimilarity(cp04.fv));
		System.out.format("%-14f", cp10.fv.cosineSimilarity(cp05.fv));
		System.out.format("%-16f", cp10.fv.cosineSimilarity(cp06.fv));
		System.out.format("%-10f", cp10.fv.cosineSimilarity(cp07.fv));
		System.out.format("%-18f", cp10.fv.cosineSimilarity(cp08.fv));
		System.out.format("%-12f", cp10.fv.cosineSimilarity(cp09.fv));
		System.out.format("%-11f%n", cp10.fv.cosineSimilarity(cp10.fv));

	}

	/** A method to check that the images can be loaded, 
	 * so we don't have an issue with missing files or bad paths.
	 */
	void imageLoadingTest() {
		ImageLoader mona = new ImageLoader("MonaLisa.jpg");
		ImageLoader starry = new ImageLoader("StarryNight.jpg");
		ImageLoader christina = new ImageLoader("ChristinasWorld.jpg");
		System.out.println("It looks like we have successfully loaded all three test images.");

	}

	/** a method to count the number of black pixels in Mona Lisa
	 * @param filename: Mona Lisa in this case
	 * @return black: the number of black pixels in this image
	 * @throws Exception if condition not met
	 */
	long countBlack(String filename) throws Exception {
		ComparePaintings cp01 = new ComparePaintings();
		ColorHash ch01 = cp01.countColors(filename, DEFAULT_BPP);
		FeatureVector fv01 = new FeatureVector(DEFAULT_BPP);
		fv01.getTheCounts(ch01);
		long[] color = fv01.colorCounts;
		long black = color[0];
		return black;
	}


	/**
	 * This is a basic testing function, and can be changed.
	 * @throws Exception if condition not met
	 */
	public static void main(String[] args) throws Exception {
		// loading test
		ComparePaintings cp = new ComparePaintings();
		cp.imageLoadingTest();

		// basicTest
		ComparePaintings cp1 = new ComparePaintings();
		cp1.countColors(IMG_01, 6);
		cp1.basicTest(IMG_02);

		// fullSimilarityTests
		cp.fullSimilarityTests();

		// CollisionTests
		cp.CollisionTests();


		// extra credits
		cp.extraCredit10ImagesTest();

		// countBlack
		long black = cp.countBlack(IMG_01);
		System.out.println("In Mona Lisa there is "+black+" black pixels ");

	}

}
