// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 7: 20 Questions

// QuestionNode objects stores a single node of a binary tree of Strings.

public class QuestionNode {
	public String data; // data stored at this node
	public QuestionNode left; // reference to left subtree
	public QuestionNode right; // reference to right subtree

	// Constructs a leaf node (answer node) with the given data.
	public QuestionNode(String data) {
		this(data, null, null);
	}

	// Constructs a leaf (answer) or branch (question) node with the given data and links.
	public QuestionNode(String data, QuestionNode left, QuestionNode right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

    // This method serves two purposes: one is to show whether or not a node is leaf
	// node, true if so. another purpose is simply to show it's available for adding nodes
	// directly in the left and right
	public boolean isLeaf() {
		return left == null && right == null;  
	}
}
