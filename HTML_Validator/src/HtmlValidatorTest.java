// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 2: HTML Validator

// This testing program stub creates a queue of HTML tags in a valid sequence
// and test whether addTag and removeAll work well.

import java.util.*;

public class HtmlValidatorTest {
	public static void main(String[] args) {

		Queue<HtmlTag> tags = new LinkedList<HtmlTag>();
		tags.add(new HtmlTag("b", true));
		tags.add(new HtmlTag("b", false));
		tags.add(new HtmlTag("br"));

		HtmlValidator validator = new HtmlValidator(tags);

		// Add some tags into the validator
		validator.addTag(new HtmlTag("happy", true));
		validator.addTag(new HtmlTag("happy", false));
		validator.addTag(new HtmlTag("happyOpen", true));
		validator.addTag(new HtmlTag("happyOpen", false));
		validator.addTag(new HtmlTag("frame"));
		validator.addTag(new HtmlTag("happyOpen", true));
		validator.addTag(new HtmlTag("html"));
		validator.addTag(new HtmlTag("happyOpen", false));

		System.out.println("This is my whole validator.");
		System.out.println(validator.getTags());

		// Make backup for the demonstration of removeAll
		HtmlValidator backUpValidator = new HtmlValidator(validator.getTags());
		HtmlValidator backUpValidator2 = new HtmlValidator(validator.getTags());
		HtmlValidator backUpValidator3 = new HtmlValidator(validator.getTags());

		// Test whether removeAll works
		System.out.println("This is my validator after removing happyOpen.");
		backUpValidator.removeAll("happyOpen");
		System.out.println(backUpValidator.getTags());

		// Test whether the string of element that contains target element mess with data
		System.out.println("This is my validator after removing happy but not happyOpen.");
		backUpValidator2.removeAll("happy");
		System.out.println(backUpValidator2.getTags());

		// Test whether the string of element not in queue will do nothing
		System.out.println("This is my validator after removing unhappy");
		backUpValidator3.removeAll("unhappy");
		System.out.println(backUpValidator3.getTags());
	}
}
