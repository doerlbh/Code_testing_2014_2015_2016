// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 7: 20 Questions

// About: QuestionTree is a class with functions to generate a binary tree of
// questions and answers for the twenty questions game. It includes functions
// such as playing the game, loading the tree, and accessing the player status etc..

import java.io.*;
import java.util.*;

public class QuestionTree {

	private QuestionNode overallRoot;
	private UserInterface uInterface;         
	private int allGame;
	private int allWon;

	// construct a questiontree with the interface passed in for usage.
	// throws an IllegalArgumentException if the interface is null.
	public QuestionTree(UserInterface ui) {
		checkNull(ui);
		uInterface = ui;
		allGame = 0;
		allWon = 0;
		overallRoot = new QuestionNode("computer");
	}

	// public method to play the game. The interaction begins when play() called
	public void play() {
		overallRoot = play(overallRoot); 
	}

	// private method to play the game given the input of the level of the game
	// (i.e. where we are at building our tree of game)
	// Pre: the node passed in mustn't be null, throws IllegalArguemntException otherwise
	// Post: return a tree consisting of nodes to replace the original tree
	private QuestionNode play(QuestionNode root) {
		checkNull(root);
		allGame++;
		if(root.isLeaf()) {
			uInterface.print("Would your object happen to be " + root.data + "?");
			if(uInterface.nextBoolean()) {
				uInterface.println("I win!");
				allWon++;
			} else {
				uInterface.print("I lose. What is your object?");    
				String answer = uInterface.nextLine();
				uInterface.print("Type a yes/no question to distinguish"
						+ " your item from " + root.data + ":");
				String newQuestion = uInterface.nextLine();
				uInterface.print("And what is the answer for your object?");
				if (uInterface.nextBoolean()) {
					root = new QuestionNode(newQuestion, new QuestionNode(answer), root);
				} else {
					root = new QuestionNode(newQuestion, root, new QuestionNode(answer));					
				}
			}
		} else {
			uInterface.print(root.data);
			allGame--;
			if(uInterface.nextBoolean()) {
				root.left = play(root.left);
			} else {
				root.right = play(root.right);
			}
		}
		return root;
	}

	// Public method to save our game record (the constructed tree) into a file (pre-order).
	// Pre: the input PrintStream must not be null, throw IllegalArguementException otherwise 
	public void save(PrintStream out) {
		checkNull(out);
		save(out, overallRoot);
	}

	// helper program for save, input not only the printstream but also where on the tree the 
	// saving process have approached. 
	private void save(PrintStream out, QuestionNode root) {
		if (root != null) {
			if (root.isLeaf()) {
				out.println("A:" + root.data);
			} else {
				out.println("Q:" + root.data);
				save(out, root.left);
				save(out, root.right);
			}
		}
	}

	// public method for loading or updating current tree based on the input from the
	// document assigned by user, building up the tree in our data base.
	// Pre: scanner input must not be null, throw IllegalArguementException otherwise
	public void load(Scanner input) {
		checkNull(input);
		if (input.hasNext()) {
			overallRoot = buildTree(input, overallRoot, overallRoot); 
		}
	}

	// private/helper method for load(), take in scanner, current node and the entire root
	// as parameters. It specifies the process of attaching question/answer onto our game
	// tree.
	// Post: return a questionnode to replace the original node
	private QuestionNode buildTree(Scanner input, QuestionNode root, QuestionNode mirrorOverall) {
		if (input.hasNext()) {
			String line = input.nextLine();
			String nodeData = line.split(":")[1];
			QuestionNode newRoot = null;

			if (root == overallRoot) {
				root = new QuestionNode(nodeData);
				newRoot = root;                       // update newRoot for recursion
				mirrorOverall = root;
			} else {
				if (isQuestion(line)){
					if (root.isLeaf()) {
						root.left = new QuestionNode(nodeData);
						newRoot = root.left;     // update newRoot for recursion
					} else {
						root.right = new QuestionNode(nodeData);
						newRoot = root.right;
					} 
				}else {
					if (root.isLeaf()) {   //isLeaf() refer to left/right both ready for use
						root.left = new QuestionNode(nodeData);
						newRoot = root;
					} else {
						root.right = new QuestionNode(nodeData);
						newRoot = freeParent(root, mirrorOverall, mirrorOverall);
						                           // to go up tree looking for space to add
					} 
				}
			}
			buildTree(input, newRoot, mirrorOverall);
		}
		return root;
	}

	// helper method to give out the parent node which is available for adding other nodes
	// Pre: given the current position and the overall root. 
	// Post: return a questionnode as a reference for adding 
	private QuestionNode freeParent(QuestionNode node, QuestionNode root, QuestionNode head) {
		QuestionNode current = root;
		if (root == null) {
			return null;
		} else if (current == node || current.left == node) {
			return current;
		} else if (current.right == node) {     // the node left and right both full
			return freeParent(current, head, head);     // go up tree
		} else {
			QuestionNode leftResult = freeParent(node, root.left, head); // from top to bottom 
			if (leftResult != null) {
				return leftResult;
			} else {
				return freeParent(node, root.right, head);
			}
		}
	}

	// helper method to test whether the line passed in is a question or answer
	// if question return true, otherwise false
	private boolean isQuestion(String line) {
		return line.startsWith("Q");
	}

	// return the total games played in this round
	public int totalGames() {
		return allGame;
	}

	// return the total number of games won by computer
	public int gamesWon() {
		return allWon;
	}

	// Pre: given an object as an input
	// Post: if it is null, throw an IllegalArgumentException
	private void checkNull(Object o) {
		if (o == null) {
			throw new IllegalArgumentException();
		}
	}

}
