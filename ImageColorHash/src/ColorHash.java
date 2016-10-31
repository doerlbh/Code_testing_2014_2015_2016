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
	private int nPair;				// number of hashPair in the hashTable
	private int nColRehash;			// number of collisions to add during rehashing
	private String crm; 			// defined collision resolution method (below)

	// private string constants to define collision resolution method
	private static final String LINEAR_PROBING = "Linear Probing"; 
	private static final String QUADRATIC_PROBING = "Quadratic Probing"; 

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
		this.nPair = 0;
		this.nColRehash = 0;

		// transform collision resolution method into binary numbers
		if (collisionResolutionMethod.equals(LINEAR_PROBING))  {
			if (rlf >= 1 || rlf <= 0){
				throw new InvalidLoadFactorException("Invalid Load Factor to perform Linear Probing");
			}
			this.crm = LINEAR_PROBING;
		} else if (collisionResolutionMethod.equals(QUADRATIC_PROBING)){
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
		boolean dRehash = false;	// dRehash: true if operations caused a rehash
		boolean dUpdate = false;	// dUpdate: true if operation caused value overwritten

		int[] probeState = doProbing(key); 	// Get probe state information
		int indHash = probeState[0];		// Index of current hash pair
		nCol = probeState[1];				// number of collision for current state

		HashPair curHash = hashTable[indHash];

		if (curHash == null) {  // Empty spot found
			dRehash = ifRehash();
			if (dRehash){
				probeState = doProbing(key);  // Probe in newly created hash table for a spot
				nCol = nCol + nColRehash;
				nCol = nCol + probeState[1];  // Add collisions from doProbing new table
				indHash = probeState[0];  // Save the newly found spot in the rehashed table
			}
			hashTable[indHash] = new HashPair(key, value); // Insert the key
			nPair++;

		} else if (curHash.isKey(key)) { // Duplicate key found, update value
			hashTable[indHash].setVal(value);
			dUpdate = true;
		}
		return new ResponseItem(value, nCol, dRehash, dUpdate);

	}


	public ResponseItem increment(ColorKey key){

		long value = 1;				// value in incrementation
		int nCol = 0;				// number of collisions involved
		boolean dRehash = false;	// dRehash: true if operations caused a rehash
		boolean dUpdate = false;	// dUpdate: true if operation caused value overwritten

		int[] probeState = doProbing(key); // Get insert/update position and numCollisions
		int indHash = probeState[0];
		nCol = probeState[1];

		HashPair curHash = hashTable[indHash];

		if (curHash == null) { // Empty spot found
			dRehash = ifRehash();
			if (dRehash){
				probeState = doProbing(key);
				nCol += nColRehash;
				nCol += probeState[1];
				indHash = probeState[0];
			}
			hashTable[indHash] = new HashPair(key, value);
			nPair++;
		} else if (curHash.isKey(key)) { // Duplicate key found, increment value
			value = hashTable[indHash].getVal() + 1;
			hashTable[indHash].setVal(value);
			dUpdate = true;
		}
		return new ResponseItem(value, nCol, dRehash, dUpdate);
	}

	public ResponseItem colorHashGet(ColorKey key) throws Exception{

		long value = -1L;			// default value in case key not found
		int nCol = 0;				// number of collisions involved
		boolean dRehash = false;	// dRehash: true if operations caused a rehash
		boolean dUpdate = false;	// dUpdate: true if operation caused value overwritten

		int[] probeState = doProbing(key); // Get insert/update position and numCollisions
		int indHash = probeState[0];
		nCol = probeState[1];

		HashPair curHash = hashTable[indHash];

		if (curHash == null) { 	// Empty spot
			throw new MissingColorKeyException("ColorKey not found in Hash Table");
		} else if (curHash.isKey(key)) { // Key found, return the value
			value = hashTable[indHash].getVal();
		}
		return new ResponseItem(value, nCol, dRehash, dUpdate);
	}


	public long getCount(ColorKey key){
		long value = -1L;			// default value in case key not found
		int nCol = 0;				// number of collisions involved

		int[] probeState = doProbing(key); // Get insert/update position and numCollisions
		int hashIndex = probeState[0];
		nCol = probeState[1];

		HashPair curHash = hashTable[hashIndex];

		if (curHash == null) { // Key not found, so we return 0
			value = 0L;
		} else if (curHash.isKey(key)) { // Key found, return associated value
			value = hashTable[hashIndex].getVal();
		}

		return value;
	}


	public ColorKey getKeyAt(int tableIndex){
		if (tableIndex >= getTableSize() || tableIndex < 0){
			throw new IndexOutOfBoundsException();
		}
		return hashTable[tableIndex].getKey();
	}


	public long getValueAt(int tableIndex){
		if (tableIndex >= getTableSize() || tableIndex < 0){
			throw new IndexOutOfBoundsException();
		}

		if (hashTable[tableIndex] == null){
			return -1L;
		} else {
			return hashTable[tableIndex].getVal();
		}
	}

	public double getLoadFactor(){
		return (nPair + 1.0) / hashTable.length; 
	}


	public int getTableSize(){
		return hashTable.length; 		
		// - 1 ???

	}

	public void resize(){
		nColRehash = 0;

		// new table size must be at least double the old size
		int newTableSize = getTableSize() * 2;
		// new table size must be a prime number
		while (!IsPrime.isPrime(newTableSize)){ newTableSize++; }

		// Create temps
		try{
			ColorHash tempCH = new ColorHash(newTableSize, bpp, crm, rlf);
			HashPair[] tempHT = tempCH.getHashTable();

			HashPair curHash;
			for (int i = 0; i < getTableSize(); i++) {
				curHash = hashTable[i];
				if (curHash != null){
					ResponseItem ri = tempCH.colorHashPut(curHash.getKey(), curHash.getVal());
					nColRehash += ri.nCollisions;
				}
			}
			hashTable = tempHT;
		} catch (Exception InvalidLoadFactor) {
			System.out.println(InvalidLoadFactor);
		}		
	}

	private HashPair[] getHashTable(){
		return hashTable;
	}

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
				if (crm.equals(LINEAR_PROBING)){
					indHash++;
					if (indHash == getTableSize()){
						indHash = 0;
					} // Wrap around array if needed
				} else if (crm.equals(QUADRATIC_PROBING)){
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
	 * collision resolution doProbing method.
	 */
	private class InvalidLoadFactorException extends Exception
	{
		public InvalidLoadFactorException() {}
		public InvalidLoadFactorException(String message)
		{
			super(message);
		}
	}

	/**
	 * User-defined Exception
	 * Thrown under attempts to find a ColorKey 
	 * which doesn't exist in Hash Table.
	 */
	private class MissingColorKeyException extends Exception
	{
		public MissingColorKeyException() {}
		public MissingColorKeyException(String message)
		{
			super(message);
		}
	}

}
