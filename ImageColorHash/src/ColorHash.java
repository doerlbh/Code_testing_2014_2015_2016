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
	
	// private fields
	private hashPair[] hashTable; 	// array to hold hash table of key value pairs
	private int tbs;				// size of hash table
	private int bpp; 				// bits Per Pixel
	private double rlf; 			// rehash Load Factor
	private int npair;				// number of hashPair in the hashTable
	private int ncol;				// number of collisions when resizing the hashTable
	private int crm; 				// defined binary number for collision Resolution Method

	// private constants to assist collision resolution method
	private static final int LINEAR_PROBING = 0; 	// defined number for Linear Probing
	private static final int QUADRATIC_PROBING = 1; // defined number for Quadratic Probing
	
	// private inner class to represent the key-value pairs to assist hashTable
	private class hashPair {	
		
		// private fields
		private int key;
		private long value;

		// A constructor to generate a hash pair with input key and value
		public hashPair(int key, long value) {
			this.key = key;
			this.value = value;
		}
		
		// public method to set the key of the hash pair to input key
		public void setKey(int keyNew) {
			key = keyNew;
		}
		
		// public method to set the value of the hash pair to input value
		public void setVal(long valueNew) {
			value = valueNew;
		}		

		// public method to retrieve the key of the hash pair
		public int getKey() {
			return key;
		}
		
		// public method to retrieve the value of the hash pair
		public long getValue() {
			return value;
		}		
	}
	
	
	public ColorHash(int tableSize, int bitsPerPixel, String collisionResolutionMethod, double rehashLoadFactor){
		hashTable = new hashPair[tableSize]; 
		tbs = tableSize;
		this.bpp 
		
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
