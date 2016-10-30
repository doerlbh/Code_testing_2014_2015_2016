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
	private int crm; 				// defined binary number for collision Resolution Method (below)

	// private constants to assist collision resolution method
	private static final int LINEAR_PROBING = 0; 	// defined number for Linear Probing
	private static final int QUADRATIC_PROBING = 1; // defined number for Quadratic Probing

	// private inner class to represent the key-value pairs to assist hashTable
	private class hashPair {	

		// private fields
		private int key;
		private long value;

		// Constructor to generate a hash pair with input key and value
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
	
	/**
	 * User-defined Exception
	 * Thrown under attempts to use invalid Load Factor for certain 
	 * collision resolution probing method.
	 */
	private class InvalidLoadFactorException extends Exception
	{
		public InvalidLoadFactorException() {}
		public InvalidLoadFactorException(String message)
		{
			super(message);
		}
	}

	/* Constructor to generate a hashTable with initial parameters implemented 
	 * as one array of hashPair to represent a (key, value) pair, and supporting
	 * two collision resolution methods: "Linear Probing" and "Quadratic Probing".
	 */
	public ColorHash(int tableSize, int bitsPerPixel, String collisionResolutionMethod, double rehashLoadFactor) throws Exception{

		// create hashTable as an array of hashPair
		hashTable = new hashPair[tableSize]; 

		// initializing hashTable parameters
		this.tbs = tableSize;
		this.bpp = bitsPerPixel;
		this.rlf = rehashLoadFactor;
		this.npair = 0;
		this.ncol = 0;

		// transform collision resolution method into binary numbers
		if (collisionResolutionMethod == "Linear Probing")  {
			if (rlf >= 1 || rlf <= 0){
				throw new InvalidLoadFactorException("Invalid Load Factor to perform Linear Probing");
			}
			this.crm = LINEAR_PROBING;
		} else if (collisionResolutionMethod == "Quadratic Probing"){
			if (rlf >= .5 || rlf <= 0){
				throw new InvalidLoadFactorException("Invalid Load Factor to perform Quadratic Probing");
			}
			this.crm = QUADRATIC_PROBING;
		} else {
			throw new IllegalArgumentException("specified collision resolution method unsupported");
		}
	}

	public ResponseItem colorHashPut(ColorKey key, long value){

	}

	public ResponseItem increment(ColorKey key){return null;}
	public ResponseItem colorHashGet(ColorKey key)  throws Exception{return null;}
	public long getCount(ColorKey key){return -1L;}
	public ColorKey getKeyAt(long tableIndex){return null;}
	public long getValueAt(long tableIndex){return -1L;}
	public double getLoadFactor(){return -1.0;}
	public int getTableSize(){return -1;}
	public void resize(){}


}
