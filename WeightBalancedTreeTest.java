import static org.junit.Assert.*;
import org.junit.Test;

public class WeightBalancedTreeTest {
	private WeightBalancedTree tree;
	private final static double EPS = .00001;
	
	@Test
	public void test() {
		double[] weights = { .3, .4, .1, .2 };
		tree = new WeightBalancedTree(weights);
		WeightBalancedTree.Node root = tree.getRoot();
		assertEquals(1, root.val);
		assertEquals(0, root.left.val);
		assertEquals(3, root.right.val);
		assertEquals(2, root.right.left.val);
	}
	
	@Test
	public void test2() {
		/*
		 * For these tests, I printed the trees and checked visually
		 * if they were correctly produced. 
		 */
		double[] weights = { .3, .4, .1, .2, .1, .3 };
		tree = new WeightBalancedTree(weights);
		double[] weights2 = { .1, .1, .1, .1, .1, .1, .1, .1 };
		tree = new WeightBalancedTree(weights2);
		double[] weights3 = { .5, .4, .3, .2, .1, 0.0 };
		tree = new WeightBalancedTree(weights3);
		double[] weights4 = { 100, .4, .3, .2, .1, 0.0 };
		tree = new WeightBalancedTree(weights4);
	}
}
