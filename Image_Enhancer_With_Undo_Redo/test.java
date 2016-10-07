/**
 * test.java
 * 
 * by Baihan Lin 
 * for CSE 373 Assignment 1, Autumn, 2016.
 * Section BC.
 * 
 * This class is just for testing and debugging purpose.
 * 
 * @author		Baihan Lin < sunnylin@uw.edu >
 * @version 	1.2
 * @since 		1.0 2016-10-01
 * 
 */

import java.awt.image.BufferedImage;

public class test {

	public test() {

	}

	public static void main(String[] args) {

		BufferedImageStack biA = new BufferedImageStack();
		
		System.out.println(biA);

		System.out.println(biA.isEmpty());

		BufferedImage x = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		
		BufferedImage y = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		
		BufferedImage z = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
		
		biA.push(x);
		biA.push(y);
		biA.push(z);
		
		System.out.println(biA.getSize());
		
		biA.pop();
		
		System.out.println(biA.getSize());
		
		System.out.println(biA.isEmpty());
		
		System.out.println(biA.getArraySize());
		
		System.out.println(biA.get(0));

	}
}
