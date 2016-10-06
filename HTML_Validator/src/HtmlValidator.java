// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 2: HTML Validator

// About: HtmlValidator is a class with functions to maintain and process
// the htmlTags in a queue for clients to access, copy, remove and validate.

import java.util.*;

public class HtmlValidator {

	private Queue<HtmlTag> htmlTags;

	// Constructor: To create a new HtmlValidator (Details see about)
	public HtmlValidator() {
		htmlTags = new LinkedList<HtmlTag>();
	}

	// Constructor: To create a new HtmlValidator with given tags input
	public HtmlValidator(Queue<HtmlTag> tags) {
		htmlTags = new LinkedList<HtmlTag>();
		checkNull(tags);
		for (int i = 0; i < tags.size(); i++) {
			htmlTags.add(tags.peek()); 
			tags.add(tags.remove());
		}
	}

	// Goal: To add the given HtmlTag to the queue of the HtmlTag
	// Pre: Input a HtmlTag. Throw an IllegalArgumentException if input is null
	// Post: Add a HtmlTag to the queue
	public void addTag(HtmlTag tag) {
		checkNull(tag);
		htmlTags.add(tag);
	}

	// Goal: To return a created copy of the given queue as a backup
	// Pre: Act on a queue of HtmlTag
	// Post: Return a copy of the given queue
	public Queue<HtmlTag> getTags() {
		Queue<HtmlTag> copyTags = new LinkedList<HtmlTag>();
		for (int i = 0; i < htmlTags.size(); i++) {
			copyTags.add(htmlTags.peek()); 
			htmlTags.add(htmlTags.remove());
		}		
		return copyTags;
	}

	// Goal: To remove all the tags of the input element
	// Pre: Input a element. Throw an IllegalArgumentException if input is null
	// Post: Remove all the tags of the element from the queue
	public void removeAll(String element) {
		checkNull(element);
		int s = htmlTags.size();
		for (int i = 0; i < s; i++) {
			if (htmlTags.peek().getElement().equalsIgnoreCase(element)) {
				htmlTags.remove();
			} else {
				htmlTags.add(htmlTags.remove());
			}
		}
	}

	// Goal: To present the HTML tags in an indented form and showing possible errors
	// Pre: Act on a queue of HtmlTag
	// Post: Print out a series of tags and show different types of errors
	public void validate() {
		Stack<HtmlTag> stackTags = new Stack<HtmlTag>();
		int indent = 0;
		for (int i = 0; i < htmlTags.size(); i++) {
			if (htmlTags.peek().isOpenTag()) {
				stackTags.push(htmlTags.peek());
				printIndentation(indent);
				System.out.println(stackTags.peek());
				indent += 4;
				if (stackTags.peek().isSelfClosing()) {
					stackTags.pop();
					indent -= 4;
				}
			} else {
				if (stackTags.size() != 0 && stackTags.peek().matches(htmlTags.peek())) {
					indent -= 4;
					printIndentation(indent);
					System.out.println(htmlTags.peek());
					stackTags.pop();
				} else {
					System.out.println("ERROR unexpected tag: " + htmlTags.peek());
				}
			}
			htmlTags.add(htmlTags.remove());
		}
		while (!stackTags.isEmpty()){
			System.out.println("ERROR unclosed tag: " + stackTags.pop());
		}
	}

	// Helper Program to avoid redundancy for condition check
	// Goal: To determine whether the input is null or not
	// Pre: given an object as an input
	// Post: if it is null, throw an IllegalArgumentException
	private void checkNull(Object o) {
		if (o == null) {
			throw new IllegalArgumentException();
		}
	}

	// Helper Program to avoid redundancy for printing indentation
	// Goal: To print out spaces to create indentation
	// Pre: given a number as an input
	// Post: print out spaces of that number
	private void printIndentation(int n) {
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				System.out.print(" ");
			}
		}
	}
}