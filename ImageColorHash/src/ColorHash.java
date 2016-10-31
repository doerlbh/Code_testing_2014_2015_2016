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
	private HashPair[] hashTable; 	// array to hold hash table of key value pairs
	private int tbs;				// size of hash table
	private int bpp; 				// bits Per Pixel
	private double rlf; 			// rehash Load Factor
	private int npair;				// number of hashPair in the hashTable
	private int nColRehash;		// number of collisions to add during rehashing
	private int crm; 				// defined binary number for collision Resolution Method (below)

	// private constants to assist collision resolution method
	private static final int LINEAR_PROBING = 0; 	// defined number for Linear Probing
	private static final int QUADRATIC_PROBING = 1; // defined number for Quadratic Probing

	// private inner class to represent the key-value pairs to assist hashTable
	private class HashPair {	

		// private fields
		private ColorKey key;
		private long value;

		// Constructor to generate a hash pair with input key and value
		public HashPair(ColorKey key, long value) {
			this.key = key;
			this.value = value;
		}

		// public method to set the key of the hash pair to input key
		public void setKey(ColorKey keyNew) {
			key = keyNew;
		}

		// public method to set the value of the hash pair to input value
		public void setVal(long valueNew) {
			value = valueNew;
		}		

		// public method to retrieve the key of the hash pair
		public ColorKey getKey() {
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
		hashTable = new HashPair[tableSize]; 

		// initializing hashTable parameters
		this.tbs = tableSize;
		this.bpp = bitsPerPixel;
		this.rlf = rehashLoadFactor;
		this.npair = 0;
		this.nColRehash = 0;

		// transform collision resolution method into binary numbers
		if (collisionResolutionMethod.equals("Linear Probing"))  {
			if (rlf >= 1 || rlf <= 0){
				throw new InvalidLoadFactorException("Invalid Load Factor to perform Linear Probing");
			}
			this.crm = LINEAR_PROBING;
		} else if (collisionResolutionMethod.equals("Quadratic Probing")){
			if (rlf >= .5 || rlf <= 0){
				throw new InvalidLoadFactorException("Invalid Load Factor to perform Quadratic Probing");
			}
			this.crm = QUADRATIC_PROBING;
		} else {
			throw new IllegalArgumentException("specified collision resolution method unsupported");
		}
	}

	public ResponseItem colorHashPut(ColorKey key, long value){

		int nCol   = 0;				// number of collisions involved
		boolean dRehash = false;	// didRehash: true if operations caused a rehash
		boolean dUpdate = false;	// didUpdate: true if operation caused value overwritten

		int[] probeState = doProbing(key); 	// Get probe state information
		int indHash = probeState[0];		// Index of current hash pair
		nCol = probeState[1];				// number of collision for current state

		HashPair curHash = hashTable[indHash];

		if (curHash == null) {  // Empty spot found
			dRehash = ifRehash();
			if (dRehash){
				probeState = doProbing(key);  // Probe in newly created hash table for a spot
				nCol = nCol + nColRehash;
				nCol = nCol + probeState[1];  // Add collisions from probing new table
				indHash = probeState[0];  // Save the newly found spot in the rehashed table
				
				
			}
			hashTable[indHash] = new HashPair(key, value); // Insert the key
			npair++;

		} else if (curHash.getKey().equals(key)) { // Duplicate key found, update value
			hashTable[indHash].setVal(value);
			dUpdate = true;
		}
		return new ResponseItem(value, nCol, dRehash, dUpdate);

	}


	private boolean ifRehash() {
		if (getLoadFactor() >= rlf) {
			resize();
			return true;
		} else {
			return false;
		}
	}

	private int[] doProbing(ColorKey key) {
		// TODO Auto-generated method stub
		return null;
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
