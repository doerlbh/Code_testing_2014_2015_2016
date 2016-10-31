import java.awt.image.BufferedImage;

/** ColorHash.java
 *  a class for hash table holding ColorKey with its hash code.
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
	private HashPair[] hashTable; 	// array to hold hash table of key-value pairs
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


	/**
	 * Constructor to generate a ColorHash object
	 * 
	 * A ColorHash object is a hashTable with initial parameters implemented 
	 * as one array of hashPair to represent a (key, value) pair, and supporting
	 * two collision resolution methods: "Linear Probing" and "Quadratic Probing".
	 *
	 * @param tbs stores the initial size of the hash table.
	 * @param bpp stores the number of bits per pixel: one of 3, 6, 9, 12, 15, 18, 21, or 24.
	 * @param crm stores the type of probing to use upon collisions (Linear Probing or Quadratic Probing)
	 * @param rlf stores the threshold to decide when to rehash hash table (defined as number of elements / table size)
	 * @throws Exception If the collision resolution method is invalid
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

	/**
	 * a public method to insert a ColorKey with its value into hashTable
	 * 
	 * If entry already exists for key, overwrite the value
	 * @param key: The ColorKey to insert/update in hash table.
	 * @param value: The value associated with the key
	 * @return Returns a ResponseItem that contains information about the task.
	 */
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

	/**
	 * A public method to search for a ColorKey and increment its value 
	 * by 1 if found, or store 1 as the value if not found
	 * 
	 * @param key: the ColorKey to increment
	 * @return the ResponseItem associated with the ColorKey
	 */
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

	/**
	 * A public method to search for a ColorKey and return value if found
	 * 
	 * @param key: the ColorKey to search for
	 * @return the value to the ColorKey if found
	 * @throws MissingColorKeyException if ColorKey not found
	 */
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

	/**
	 * A public method to search for a ColorKey and return value if found
	 * 
	 * @param key: the ColorKey to search for
	 * @return the value to the ColorKey if found, 0 if not found
	 */
	public long getCount(ColorKey key){
		long value = -1L;	// default value in case key not found
		int nCol = 0;		// number of collisions involved

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

	/**
	 * A public method to access the ColorKey at index tableIndex the hash table
	 * 
	 * @param tableIndex is the input which is the index at the hash table
	 * @return the ColorKey at index tableIndex in hash table if exists
	 * @throws IndexOutofBoundsException if index out of range
	 */
	public ColorKey getKeyAt(int tableIndex){
		if (tableIndex >= getTableSize() || tableIndex < 0){
			throw new IndexOutOfBoundsException();
		}
		return hashTable[tableIndex].getKey();
	}

	/**
	 * A public method to access the value at index tableIndex the hash table
	 * 
	 * @param tableIndex is the input which is the index at the hash table
	 * @return the value at index tableIndex in hash table if exists, -1L if 
	 * there is no entry at the the given tableIndex.
	 * @throws IndexOutofBoundsException if index out of range
	 */
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

	/**
	 * A public method to access the load factor of the hash table 
	 * 
	 * @return rlf of the hash table. 
	 */
	public double getLoadFactor(){
		return (nPair + 1.0) / hashTable.length; 
	}

	/**
	 * A public method to access the size of the hash table 
	 * 
	 * @return the size of the hash table. 
	 */
	public int getTableSize(){
		return hashTable.length; 		
	}

	/**
	 * A public method to allocate a new array to build a bigger hash table
	 * and then scan the old table from location 0 to location getTableSize()-1, 
	 * inserting the key-value pairs into the new table using the same approach 
	 * as with colorHashPut. The new size should be the smallest prime number 
	 * that is at least double the old table size. Before returning from resize, 
	 * the old array(s) should be replaced by the new one(s), so that they can 
	 * be garbage-collected.
	 */
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

	/**
	 * A private helper method to support colorHashPut, increment, 
	 * colorHashGet, getCount
	 * 
	 * Purpose: to retrieve the insert location of ColorKey while summing 
	 * number of collisions based on linear or quadratic probing based on
	 * crm from constructor input.
	 *
	 * @param key stores the ColorKey to insert in the hash table.
	 * @return An integer array storing the index of insert location and 
	 * number of collisions during the probing.
	 */
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
	 * A private helper method to support colorHashPut, increment
	 * 
	 * Purpose: to get whether rlf is exceeded and rehash necessary
	 * @return a boolean to indicate the necessity of rehashing.
	 * If necessary to rehash, do rehashing.
	 */
	private boolean ifRehash() {
		if (getLoadFactor() >= rlf) {
			resize();
			return true;
		} else {
			return false;
		}
	}

	private HashPair[] getHashTable(){
		return hashTable;
	}

	/**
	 * User-defined Exception
	 * 
	 * @throws InvalidLoadFactorException under attempts to use 
	 * invalid Load Factor for certain collision resolution Probing method.
	 */
	private class InvalidLoadFactorException extends Exception
	{
		private static final long serialVersionUID = 8849435559267965960L;
		public InvalidLoadFactorException() {}
		public InvalidLoadFactorException(String message)
		{
			super(message);
		}
	}

	/**
	 * User-defined Exception
	 * 
	 * @throws ColorKeyException under attempts to find a ColorKey 
	 * which doesn't exist in Hash Table.
	 */
	private class MissingColorKeyException extends Exception
	{
		private static final long serialVersionUID = 6361599218594738343L;
		public MissingColorKeyException() {}
		public MissingColorKeyException(String message)
		{
			super(message);
		}
	}

}
