/**
 * An implementation of a BST backed by a splay tree.
 */
public class SplayTree implements BST {
	private Node root;
	
	public class Node {
		public int val;
		public Node parent;
		public Node left;
		public Node right;
		
	};
	
	/**
	 * Constructs a new tree from the specified array of weights. Since splay
	 * trees don't care about access probabilities, you should only need
	 * to read the length of the weights array and not the weights themselves.
	 * This tree should store the values 0, 1, 2, ..., n - 1, where n is the length
	 * of the input array.
	 *
	 * @param The weights on the elements.
	 */
	public SplayTree(double[] keys) {
		// Construct linear tree all on right spine to start
		if (keys.length == 0) return;
		root = new Node();
		Node maxNode = root;
		for (int i=1; i < keys.length; ++i) {
			Node newNode = new Node();
			newNode.val = i;
			newNode.parent = maxNode;
			maxNode.right = newNode;
			maxNode = newNode;
		}           
	}
	
	/**
	 * Connects a parent node to a child node correctly in a BST. Can take nulls.
	 * @param parent the parent node
	 * @param child the child node
	 */
	private void connect(Node parent, Node child) {
		if (child == null) return;
		child.parent = parent;
		if (parent == null) return;
		if (parent.val < child.val) {
			parent.right = child;
		} else {
			parent.left = child;
		}
	}
	
	/**
	 * Performs a tree rotation on a node.
	 * REQUIRED: Node must not the root.
	 * @param node the node to be rotated
	 */
	private void rotate(Node node) {
		Node parent = node.parent;
		Node grandparent = parent.parent;
		connect(grandparent, node);
		if (node.val < parent.val) {
			parent.left = null;
			connect(parent, node.right);
		} else {
			parent.right = null;
			connect(parent, node.left);
		}
		connect(node, parent);
	}
	
	/**
	 * Performs a 'zigzig' rotation on a node
	 * @param node the node to be zigzig'd
	 */
	private void zigzig(Node node) {
		rotate(node.parent);
		rotate(node);
	}
	
	/**
	 * Performs a 'zigzag' rotation on a node
	 * @param node the node to be zigzag'd
	 */
	private void zigzag(Node node) {
		rotate(node);
		rotate(node);
	}
	
	/**
	 * Performs a 'zig' rotation on a node
	 * @param node the node to be zig'd
	 */
	private void zig(Node node) {
		rotate(node);
	}
	
	/**
	 * Determines if a node is the left child of its parent.
	 * @param node the child node
	 * @return whether or not it is a left child
	 */
	public static boolean isLeftChild(Node node) {
		if (node == null || node.parent == null) return false;
		if (node.parent.left == node) return true;
		return false;
	}
	
	/**
	 * Performs the appropriate zig, zigzag, or zigzig operation on
	 * a node depending on where it lies relative to its ancestors.
	 * @param node the node to be zig'd, zigzag'd or zigzig'd
	 */
	public void zigOrzag(Node node) {
		Node parent = node.parent;
		if (parent.parent == null) {
			zig(node);
		} else if (isLeftChild(node) ^ isLeftChild(parent)) {
			zigzag(node);
		} else {
			zigzig(node);
		}
	}
	
	/**
	 * Splays a node up to the root
	 * @param node the node to be splayed
	 */
	public void splayNode(Node node) {
		while (node.parent != null) {
			zigOrzag(node);
		}
		root = node;
	}
	
	/**
	 * Non-recursively finds a key in a BST.
	 * @param node the root of the tree to search under
	 * @param key the desired key
	 * @return the node where the key exists, or null if it is not in the tree
	 */
	public Node findKey(Node node, int key) {
		while (node != null) {
			if (node.val == key) break;
			else if (key < node.val) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return node;
	}
	
	/**
	 * Returns whether the specified key is in the BST.
	 *
	 * @param key The key to test.
	 * @return Whether it's in the BST.
	 */
	public boolean contains(int key) {
		Node node = findKey(root, key);
		if (node == null) {
			return false;
		} else {
			splayNode(node);
			return true;
		}
	}
	

	/***********************************************************
	 * 
	 * The following code is for debugging and testing
	 * 
	 ***********************************************************/
	
	/**
	 * For testing
	 */
	public Node getRoot() {	return root; }
	
	private static String toString(Node node) {
		if (node == null) return "#";
		else return "" + node.val;
	}
	
	private Node leftOrNull(Node node) {
		return node == null ? null : node.left;
	}
	
	private Node rightOrNull(Node node) {
		return node == null ? null : node.right;
	}
	
	private void checkConnections(Node node) {
		if (node == null) return;
		if (node.left != null && node.left.parent != node) {
			System.out.println("==> Left child of " + node.val + " not connected both ways");
		}
		if (node.right != null && node.right.parent != node) {
			System.out.println("==> Right child of " + node.val + " not connected both ways");
		}
		checkConnections(node.left);
		checkConnections(node.right);
	}
	
	private void print() {
		Node left, right, left_left, left_right, right_left, right_right;
		Node left_left_left, left_left_right, left_right_left, left_right_right;
		Node right_left_left, right_left_right, right_right_left, right_right_right;
		left = root.left;
		right = root.right;
		left_left = leftOrNull(left);
		left_right = rightOrNull(left);
		right_left = leftOrNull(right);
		right_right = rightOrNull(right);
		left_left_left = leftOrNull(left_left);
		left_left_right = rightOrNull(left_left);
		left_right_left = leftOrNull(left_right);
		left_right_right = rightOrNull(left_right);
		right_left_left = leftOrNull(right_left);
		right_left_right = rightOrNull(right_left);
		right_right_left = leftOrNull(right_right);
		right_right_right = rightOrNull(right_right);
		System.out.println("\t\t" + toString(root));
		System.out.println("\t" + toString(left) + "\t\t" + toString(right));
		System.out.println("    " + toString(left_left) + "\t    " + toString(left_right) + 
				"\t    " + toString(right_left) + "\t    " + toString(right_right));
		System.out.println("  " + toString(left_left_left) + "  " + toString(left_left_right) + "    "+
				toString(left_right_left) + "   " + toString(left_right_right) + "   " +
				toString(right_left_left) + "   " + toString(right_left_right) + "    " + 
				toString(right_right_left) + "   " + toString(right_right_right));
	}
}
