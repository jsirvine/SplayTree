import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SplayTreeTest {
	private SplayTree tree;

	@Before
	public void setUp() {
		double[] weights = {0.1, 0.2, 0.4, 0.3};
		tree = new SplayTree(weights);
	}
	
	@Test
	public void testConstructor() {
		SplayTree.Node root = tree.getRoot();
		int nodes = 0;
		SplayTree.Node maxNode = root;
		while (maxNode != null) {
			if (maxNode != root) {
				assertEquals(maxNode.val, maxNode.parent.val + 1);
			} else {
				assertEquals(0, maxNode.val);
			}
			nodes++;
			maxNode = maxNode.right;
		}
		assertEquals(4, nodes);
	}
	
	@Test
	public void testIsLeftChild() {
		assertFalse(SplayTree.isLeftChild(tree.getRoot()));
		assertFalse(SplayTree.isLeftChild(tree.getRoot().right));
		assertFalse(SplayTree.isLeftChild(tree.getRoot().left));
		SplayTree.Node root = tree.getRoot();
		SplayTree.Node node = tree.new Node();
		root.left = node;
		node.parent = root;
		assertTrue(SplayTree.isLeftChild(node));		
	}
}
