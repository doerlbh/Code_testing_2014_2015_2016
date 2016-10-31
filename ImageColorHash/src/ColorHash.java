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
		public long getVal() {
			return value;
		}

		public boolean isKey(ColorKey testKey) {
			return (testKey.equals(key));
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

		} else if (curHash.isKey(key)) { // Duplicate key found, update value
			hashTable[indHash].setVal(value);
			dUpdate = true;
		}
		return new ResponseItem(value, nCol, dRehash, dUpdate);

	}


	public ResponseItem increment(ColorKey key){

		long value = 1;				// value
		int nCol = 0;				// number of collisions involved
		boolean dRehash = false;	// didRehash: true if operations caused a rehash
		boolean dUpdate = false;	// didUpdate: true if operation caused value overwritten

		int[] probeState = doProbing(key); // Get insert/update position and numCollisions
		int indHash = probeState[0];
		nCol = probeState[1];

		HashPair currentHash = hashTable[indHash];

		if (currentHash == null) { // Empty spot found
			dRehash = ifRehash();
			if (dRehash){
				probeState = doProbing(key);
				nCol += nColRehash;
				nCol += probeState[1];
				indHash = probeState[0];
			}
			hashTable[indHash] = new HashPair(key, value);
			npair++;
		} else if (currentHash.isKey(key)) { // Duplicate key found, increment value
			value = hashTable[indHash].getVal() + 1;
			hashTable[indHash].setVal(value);
			dUpdate = true;
		}
		return new ResponseItem(value, nCol, dRehash, dUpdate);
	}

	public ResponseItem colorHashGet(ColorKey key) throws Exception{return null;}


	public long getCount(ColorKey key){return -1L;}


	public ColorKey getKeyAt(long tableIndex){return null;}


	public long getValueAt(long tableIndex){return -1L;}


	public double getLoadFactor(){return -1.0;}


	public int getTableSize(){return -1;}


	public void resize(){}

	/**
	 * Check if we are over the load factor and must rehash. If we are, then go ahead and rehash and set flag.
	 */
	private boolean ifRehash() {
		if (getLoadFactor() >= rlf) {
			resize();
			return true;
		} else {
			return false;
		}
	}

	private int[] doProbing(ColorKey key) {

		int nCol = 0;

		int[] probeState = new int[2];

		int indHash = key.hashCode() % hashTable.length; // Get the first target index

		boolean existKey = false; // True if we found a place to insert/update
		while (!existKey){

			HashPair curHash = hashTable[indHash];

			if (curHash == null || curHash.isKey(key)) { // Empty/duplicate spot found
				existKey = true;
			} else {  // Otherwise we have a collision, and will probe for a new spot using specified collision method
				nCol++;
				if (crm == 0){
					indHash++;
					if (indHash == getTableSize()){
						indHash = 0;
					} // Wrap around array if needed
				} else if (crm == 1){
					indHash = nCol * nCol + key.hashCode() % hashTable.length;
					while(indHash >= getTableSize()){
						indHash -= getTableSize();
					}
				}
			}
		}
		probeState[0] = indHash;
		probeState[1] = nCol;
		return probeState;
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

}
