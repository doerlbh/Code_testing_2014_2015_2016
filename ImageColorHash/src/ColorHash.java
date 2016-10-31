import java.awt.image.BufferedImage;

/** ColorHash.java
 *  a class for hash table holding ColorKey with its value.
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

		// public method to test whether the value matches the 
		// the value of the ColorKey
		public boolean isKey(ColorKey testKey) {
			return (testKey.equals(key));
		}		
	}

	/**
	 * Constructor to generate a ColorHash object, which is a hashTable with 
	 * initial parameters implemented as one array of hashPair to represent 
	 * a (key, value) pair, and supporting two collision resolution methods: 
	 * "Linear Probing" and "Quadratic Probing".
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

		// check collision resolution method and initialize crm
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
			throw new IllegalArgumentException("Specified collision resolution method unsupported");
		}
	}

	/**
	 * a public method to insert a ColorKey with its value into hashTable if 
	 * not already existing, overwrite the value if already existing
	 * 
	 * @param key: The ColorKey to insert in the hashTable.
	 * @param value: The value of the ColorKey
	 * @return Returns a ResponseItem of the ColorKey.
	 */
	public ResponseItem colorHashPut(ColorKey key, long value){

		int nCol   = 0;				// number of collisions involved
		boolean dRehash = false;	// dRehash: true if operations caused a rehash
		boolean dUpdate = false;	// dUpdate: true if operation caused value overwritten

		int[] probeState = doProbing(key); 		// Get probe state information
		int indHash = probeState[0];			// Index of current hash pair
		nCol = probeState[1];					// number of collision for current state
		HashPair curHash = hashTable[indHash];	// obtain the current hashTable element

		if (curHash == null) {  // If empty in current hash table location
			dRehash = ifRehash();
			if (dRehash){
				probeState = doProbing(key);  	// Probing in the new hashTable for an empty location
				indHash = probeState[0];  		// find a new empty location
				nCol = nCol + nColRehash; 		// update nCol
				nCol = nCol + probeState[1];  	// update nCol
			}
			hashTable[indHash] = new HashPair(key, value); // Insert the ColorKey
			nPair++;
		} else if (curHash.isKey(key)) { // ColorKey already exists, overwrite
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

		int[] probeState = doProbing(key); 		// Get probe state information
		int indHash = probeState[0];			// Index of current hash pair
		nCol = probeState[1];					// number of collision for current state
		HashPair curHash = hashTable[indHash];	// obtain the current hashTable element

		if (curHash == null) {  // If empty in current hash table location
			dRehash = ifRehash();
			if (dRehash){
				probeState = doProbing(key);  	// Probing in the new hashTable for an empty location
				indHash = probeState[0];  		// find a new empty location
				nCol = nCol + nColRehash; 		// update nCol
				nCol = nCol + probeState[1];  	// update nCol
			}
			hashTable[indHash] = new HashPair(key, value); // Insert the ColorKey and value as 1
			nPair++;
		} else if (curHash.isKey(key)) { // ColorKey already exists, increment value
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

		int[] probeState = doProbing(key); 		// Get probe state information
		int indHash = probeState[0];			// Index of current hash pair
		nCol = probeState[1];					// number of collision for current state
		HashPair curHash = hashTable[indHash];	// obtain the current hashTable element

		if (curHash == null) { 	// If empty in current hash table location
			throw new MissingColorKeyException("ColorKey not found in hashTable");
		} else if (curHash.isKey(key)) { // ColorKey found, return  value
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

		int[] probeState = doProbing(key); 		// Get probe state information
		int indHash = probeState[0];			// Index of current hash pair
		nCol = probeState[1];					// number of collision for current state
		HashPair curHash = hashTable[indHash];	// obtain the current hashTable element

		if (curHash == null) { // ColorKey not found, return 0
			value = 0L;
		} else if (curHash.isKey(key)) { // ColorKey found, return value
			value = hashTable[indHash].getVal();
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
		if (tableIndex < 0 || tableIndex >= getTableSize()){
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
		if (tableIndex < 0 || tableIndex >= getTableSize()){
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

		// Get the new table size after resizing
		int tbsNew = twiceAndPrime(getTableSize());

		// Create new hashTable
		try{
			ColorHash newColorHash = new ColorHash(tbsNew, bpp, crm, rlf);			
			HashPair curHash; // start scanning through entire hashTable
			for (int i = 0; i < getTableSize(); i++) {
				curHash = hashTable[i];
				if (curHash != null){
					ResponseItem ri = newColorHash.colorHashPut(curHash.getKey(), curHash.getVal());
					nColRehash = nColRehash + ri.nCollisions;
				}
			}
			HashPair[] newHashTable = newColorHash.getHashTable();
			hashTable = newHashTable;
		} catch (Exception InvalidLoadFactor) {
			System.out.println(InvalidLoadFactor);
		}		
	}

	/**
	 * A private helper method to support resize
	 * 
	 * @param tableSize: old table size that needs to be resized
	 * @return newTableSize: the new table size which is at least twice as
	 * big the old table size and has to be a prime number
	 */
	private int twiceAndPrime(int tableSize) {
		// Condition 1: new hashTable size set at least twice the original size
		int newTableSize = getTableSize() * 2;

		// Condition 2: new table size must be a prime number
		while (!IsPrime.isPrime(newTableSize)){ 
			newTableSize++; 
		}
		return newTableSize;
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

		int[] probeState = new int[2]; 	// output
		boolean existKeyInd = false; 	// True if empty or the key found in the index
		int nCol = 0;					// number of collision

		int indHash = key.hashCode() % hashTable.length; // 1st index to test on
		while (!existKeyInd) {
			HashPair curHash = hashTable[indHash];
			if (curHash == null || curHash.isKey(key)) { // Empty or duplicate
				existKeyInd = true;
			} else {  // Collision case, need probing for another empty spot
				nCol++;
				if (crm.equals(LINEAR_PROBING)){
					indHash++;
					if (indHash == getTableSize()){
						indHash = 0; // circular setting 
					} 
				} else if (crm.equals(QUADRATIC_PROBING)){
					indHash = nCol * nCol + key.hashCode() % hashTable.length;
					while(indHash >= getTableSize()){
						indHash = indHash - getTableSize();
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
