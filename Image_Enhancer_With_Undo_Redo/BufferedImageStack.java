/**
 * BufferedImageStack.java
 * 
 * by Baihan Lin 
 * for CSE 373 Assignment 1, Autumn, 2016.
 * Section BC.
 * 
 * This class manages a stack (ADT) of BufferedImage objects using arrays to
 * contain the elements within a stack of BufferedImage.
 * 
 * @author		Baihan Lin < sunnylin@uw.edu >
 * @version 	1.2
 * @since 		1.0 2016-10-01
 * 
 */


import java.awt.image.BufferedImage;
import java.util.EmptyStackException;


public class BufferedImageStack {

	private BufferedImage[] biArray; // array to hold stack elements
	private int stackSize; // size of the stack elements
	private int end; // end of the array, or the index of the last item in stack
	private int arraySize; // size of the array holding elements
	private static final int DEFAULT_ARRAY_SIZE = 2; // default array size

	/**
	 * Class Constructor
	 */
	public BufferedImageStack() {
		stackSize = 0;
		arraySize = 2;
		biArray = new BufferedImage[DEFAULT_ARRAY_SIZE];
		end = -1;
	}

	/**
	 * This method is to push a BufferedImage into the stack.
	 * If exceed the array capacity, extend to double capacity.
	 * 
	 * @param bi: a BufferedImage object.
	 */
	public void push(BufferedImage bi) {
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

	/**
	 * This method is to pop the BufferedImage at the top of
	 * the stack (last one pushed in).
	 * 
	 * @return biOut: popped-out BufferedImage.
	 * @throws EmptyStackException When the stack is empty.
	 */
	public BufferedImage pop() throws EmptyStackException {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}
		BufferedImage biOut = biArray[end];
		biArray[end] = null;
		end--;
		stackSize--;
		return biOut;
	}

	/**
	 * This method is to get the BufferedImage at a specific 
	 * index of this stack.
	 * 
	 * @param index: index integer.
	 * @return biArray[index]: BufferedImage at some index.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	public BufferedImage get(int index) throws IndexOutOfBoundsException {
		if (index >= this.getSize() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return biArray[index];
	}

	/**
	 * This method is to get the current size of the array 
	 * used to hold stack elements.
	 * 
	 * @return arraySize: the current size of the array being 
	 *         used to hold the stack elements.
	 */
	public int getArraySize()  {
		return arraySize;
	}

	/**
	 * This method is to get the size (number elements) 
	 * currently in the stack.
	 * 
	 * @return stackSize: the number elements currently in the stack.
	 */
	public int getSize() {
		return stackSize;
	}

	/**
	 * This method is to show whether the stack is empty.
	 * 
	 * @return <code>true</code> is the stack is empty, 
	 *         <code>false</code> if otherwise.
	 */
	public boolean isEmpty() {
		if (stackSize == 0) {
			return true;
		}
		return false;
	}


}