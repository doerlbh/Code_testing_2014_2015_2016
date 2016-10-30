import java.awt.image.BufferedImage;

/** ColorHash.java
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

public class ColorHash {
	// Implement this class, including whatever data members you need and all
	// of the public methods below.  You may create any number of private methods
	// if you find them to be helpful.
	
	private ColorKey[] colorKeyArray; // array to hold hash table of color keys
	private static final int DEFAULT_TABLE_SIZE = 61; // default hash table size	
	
	public ColorHash(int tableSize, int bitsPerPixel, String collisionResolutionMethod, double rehashLoadFactor){
		stackSize = 0;
		arraySize = 2;
		biArray = new BufferedImage[DEFAULT_ARRAY_SIZE];
		
		
		
		
	}
	
	public ResponseItem colorHashPut(ColorKey key, long value){return null;}
	public ResponseItem increment(ColorKey key){return null;}
	public ResponseItem colorHashGet(ColorKey key)  throws Exception{return null;}
	public long getCount(ColorKey key){return -1L;}
	public ColorKey getKeyAt(long tableIndex){return null;}
	public long getValueAt(long tableIndex){return -1L;}
	public double getLoadFactor(){return -1.0;}
	public int getTableSize(){return -1;}
	public void resize(){}


}
