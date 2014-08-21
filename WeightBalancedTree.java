/**
 * An implementation of a static BST backed by a weight-balanced tree.
 */
public class WeightBalancedTree implements BST {
	private Node root;
	
	public class Node {
		Node left;
		Node right;
		int val;
		double weight;
	};
	
	public class SplitResults {
		int index;
		double sumBefore;
		double sumAfter;
	};
	
	/**
	 * Constructs a new tree from the specified array of weights. The array entry
	 * at position 0 specifies the weight of 0, the entry at position 1 specifies
	 * the weight of 1, etc.
	 *
	 * @param The weights on the elements.
	 */
	public WeightBalancedTree(double[] elements) {
		double weightSum = 0;
		for (int i = 0; i < elements.length; ++i) {
			weightSum += elements[i];
		}
		// Construct tree recursively
		if (elements.length == 0) return;
		root = new Node();
		constructTree(root, 0, elements.length, weightSum, elements);
		//print();
	}
	
	/**
	 * Recursively construct a tree given a new uninitialize node to be the root, the
	 * range of nodes over which to construct the tree, and the sum of the weights in
	 * that range.
	 * @param node the node to be the root of this tree
	 * @param start the start index of the range
	 * @param end one past the last index of the range
	 * @param sum the sum of the weights of the elements in the range
	 * @param weights the weights of all elements in the whole global tree
	 */
	private void constructTree(Node node, int start, int end, double sum, double[] weights) {
		SplitResults results = findSplitPoint(start, end, sum, weights);
		int index = results.index;
		node.val = index;
		node.weight = weights[index];
		if (index != start) {
			Node left = new Node();
			constructTree(left, start, index, results.sumBefore, weights);
			node.left = left;
		}
		if (index != end -1) {			
			Node right = new Node();
			constructTree(right, index+1, end, results.sumAfter, weights);
			node.right = right;
		}	
	}
	
	/**
	 * Finds the optimal index in a range of weights such that the sum of the weights to
	 * the left of the index are as close to the sum of the weights to the right as possible.
	 * Returns the index, along with the sum of weights before and after this index 
	 * @param start the start index of range to examine
	 * @param end one past the last index of range to examine
	 * @param sum the sum of the range to examine
	 * @param weights the global array of all weights
	 * @return the best index, the sum of weights before and the sum of weights after, returned
	 * in a SplitResults struct.
	 */
	private SplitResults findSplitPoint(int start, int end, double sum, double[] weights) {
		SplitResults results = new SplitResults();
		if (end == start + 1) {
			results.index = start;
			results.sumBefore = 0;
			results.sumAfter = 0;
			return results;
		}
		int i = start;
		int j = end - 1;
		double iBefore = 0;
		double iAfter = sum - weights[i];
		double jBefore = sum - weights[j];
		double jAfter = 0;
		double iDiff = iAfter;
		double jDiff = jBefore;
		while (true) {
			// Iterate up from i
			double newDiff = Math.abs((iAfter - weights[i+1])- (iBefore + weights[i]));
			if (newDiff > iDiff) {
				results.sumBefore = iBefore;
				results.sumAfter = iAfter;
				results.index = i;
				return results;
			}
			iBefore += weights[i];
			iAfter -= weights[++i];
			iDiff = newDiff;
			// iterate down from j
			newDiff = Math.abs((jBefore - weights[j-1]) - (jAfter + weights[j]));
			if (newDiff > jDiff) {
				results.sumBefore = jBefore;
				results.sumAfter = jAfter;
				results.index = j;
				return results;
			}
			jAfter += weights[j];
			jBefore -= weights[--j];
			jDiff = newDiff;
		}
	}
	
	/**
	 * Non-recursively finds a key in a BST.
	 * @param node the root of the tree to search under
	 * @param key the desired key
	 * @return the node where the key exists, or null if it is not in the tree
	 */
	private Node findKey(Node node, int key) {
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
		return node != null;
	}
	
	
	/***********************************************************
	 * 
	 * The following code is for debugging and testing
	 * 
	 ***********************************************************/
	
	/**
	 * For testing
	 * @return
	 */
	public Node getRoot() { return root; }
	
	private Node leftOrNull(Node node) {
		return node == null ? null : node.left;
	}
	
	private Node rightOrNull(Node node) {
		return node == null ? null : node.right;
	}
	
	private static String toString(Node node) {
		if (node == null) return "#";
		else return "" + node.val;
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
