
/* BufferedImageStack.java
 * by Baihan Lin for CSE 373 Assignment 1, Autumn, 2016.
 * Section BC.
 */

import java.awt.image.BufferedImage;

public class BufferedImageStack {

	private BufferedImage[] biArray;
	private int stackSize;
	private int end; // end of the array, or the last item into the stack
	private int arraySize;
	private static final int INITIAL_ARRAY_SIZE = 2;

	public BufferedImageStack() {
		// TODO Auto-generated constructor stub
		stackSize = 0;
		arraySize = 2;
		biArray = new BufferedImage[INITIAL_ARRAY_SIZE];
		end = -1;
	}

	// enters the buffered image onto the stack and returns nothing. 
	// If this would exceed the capacity of the array, then a new array 
	// should be allocated having double the size of the old array, and 
	// the old array's elements copied to the new array.
	public void push(BufferedImage bi) {
		// TODO
		end++;
		stackSize++;
		if (end >= arraySize) {

			BufferedImage[] temp = new BufferedImage[arraySize*2];
			for(int i = 0; i < arraySize; i++) {
				temp[i] = biArray[i];
			}
			arraySize = arraySize * 2; 
			biArray = temp;
		}
		biArray[end] = bi;

	}

	//	get(int index): returns the buffered image at the position given by the index. 
	// (This is not commonly available in a stack, but it facilitates the testing by 
	// our autograder.) If the index is out of range, the method should throw an 
	// TODO
	// IndexOutOfBounds exception. (Note: get(0) gets the bottom element of the stack 
	// -- the one that was pushed in first, but not yet popped out.)
	public BufferedImage get(int index) {
		// TODO
		if (index >= arraySize || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return biArray[index];
	}


	// throws an exception if the stack is empty; otherwise returns the top buffered image, 
	// removing it from the stack. 
	// TODO	
	// The exception should be an instance of java.util.EmptyStackException. 
	// In this assignment, you are not required to ever replace a large array by a smaller array 
	// when the number of stack elements decreases because of pop operations.
	public BufferedImage pop()  {
		// TODO 
		if (this.isEmpty()) {
			throw new java.util.EmptyStackException();
		}
		BufferedImage biOut = biArray[end];
		biArray[end] = null;
		end--;
		stackSize--;
		return biOut;
	}

	//returns the current size of the array being used to hold the stack elements. Like the get operation, this is not a normal stack operation, but may be used by the grading software to assure compliance with the specifications.
	public int getArraySize()  {
		return arraySize;
	}

	// returns the number elements currently in the stack.
	public int getSize() {
		return stackSize;
	}

	// returns true if there are no items in the stack; false otherwise.
	public boolean isEmpty() {
		if (stackSize == 0) {
			return true;
		}
		return false;
	}


}