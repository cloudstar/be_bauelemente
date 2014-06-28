package datamodel;

import java.util.*;

public class BinTree<T> implements Iterable<T> {

	// testing
	public static void main(String[] args) {
		Coord[] a = { new Coord(6, 9), new Coord(7, 9), new Coord(8, 9),
				new Coord(8, 9), new Coord(8, 9), new Coord(9, 9),
				new Coord(4, 9), new Coord(3, 9), };
		BinTree<Coord> bst = null;
		// testing comparator
		// build a BST with a rule: Left > Parent > Right
		bst = new BinTree<Coord>(new Comparator<Coord>() {

			@Override
			public int compare(Coord o1, Coord o2) {
				return o1.x - o2.x;
			}
		});

		for (Coord n : a)
			bst.insert(n);

		bst.preOrderTraversal();
		System.out.println();
		bst.inOrderTraversal();
		System.out.println();

		for(Coord c: bst.tail(new Coord(4,5))){
			System.out.println(""+c);
		}

		// testing diameter
		System.out.println("diameter = " + bst.diameter());
		// testing width
		System.out.println("width = " + bst.width());
	}

	private Node<T> root;
	private Comparator<T> comparator;

	public BinTree() {
		root = null;
		comparator = null;
	}

	public BinTree(Comparator<T> comp) {
		root = null;
		comparator = comp;
	}

	private int compare(T x, T y) {
		// if(comparator == null) return x.compareTo(y);
		// else
		return comparator.compare(x, y);
	}

	/*****************************************************
	 * 
	 * INSERT
	 * 
	 ******************************************************/
	public void insert(T data) {
		root = insert(root, data);
	}

	private Node<T> insert(Node<T> p, T toInsert) {
		if (p == null)
			return new Node<T>(toInsert);

		// if (compare(toInsert, p.data) == 0)
		// return p;

		if (compare(toInsert, p.data) < 0)
			p.left = insert(p.left, toInsert);
		else
			// greater and equal
			p.right = insert(p.right, toInsert);

		return p;
	}

	/*****************************************************
	 * 
	 * SEARCH
	 * 
	 ******************************************************/

	public boolean contains(T toSearch) {
		return searchNode(root, toSearch) != null;
	}

	public boolean search(Node<T> p, T toSearch) {
		if (p == null)
			return false;
		else if (compare(toSearch, p.data) == 0 && p.data.equals(toSearch))
			return true;
		else if (compare(toSearch, p.data) < 0)
			return search(p.left, toSearch);
		else
			return search(p.right, toSearch);
	}

	public Node<T> searchNode(T toSearch) {
		return searchNode(root, toSearch);
	}

	private Node<T> searchNode(Node<T> p, T toSearch) {
		if (p == null)
			return null;
		else if (compare(toSearch, p.data) == 0 && p.data.equals(toSearch))
			return p;
		else if (compare(toSearch, p.data) < 0)
			return searchNode(p.left, toSearch);
		else
			return searchNode(p.right, toSearch);
	}

	/*****************************************************
	 * 
	 * DELETE
	 * 
	 ******************************************************/
	boolean deleted = false;

	public boolean remove(T toDelete) {
		deleted = false;
		delete(toDelete);
		return deleted;
	}

	public void delete(T toDelete) {
		root = delete(root, toDelete);
	}

	private Node<T> delete(Node<T> p, T toDelete) {
		if (p == null)
			throw new RuntimeException("cannot delete.");
		else if (compare(toDelete, p.data) < 0)
			p.left = delete(p.left, toDelete);
		else if (compare(toDelete, p.data) >= 0 && !p.data.equals(toDelete))
			p.right = delete(p.right, toDelete);
		else {
			deleted = true;
			if (p.left == null)
				return p.right;
			else if (p.right == null)
				return p.left;
			else {
				// get data from the rightmost node in the left subtree
				p.data = retrieveData(p.left);
				// delete the rightmost node in the left subtree
				p.left = delete(p.left, p.data);
			}
		}
		return p;
	}

	private T retrieveData(Node<T> p) {
		while (p.right != null)
			p = p.right;

		return p.data;
	}

	/*************************************************
	 * 
	 * toString
	 * 
	 **************************************************/

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (T data : this)
			sb.append(data.toString());

		return sb.toString();
	}

	/*************************************************
	 * 
	 * TRAVERSAL
	 * 
	 **************************************************/

	public LinkedList<T> tail(T bound) {
		LinkedList<T> list = new LinkedList<>();
		Node<T> ro = searchNode(bound);

		if (ro != null)
			ro.preOrder(list);

		return list;
	}

	public void preOrderTraversal() {
		System.out.println("PreOrderTraversal");
		preOrderHelper(root);
	}

	private void preOrderHelper(Node<T> r) {
		if (r != null) {
			System.out.println(r + " ");
			preOrderHelper(r.left);
			preOrderHelper(r.right);
		}
	}

	public void inOrderTraversal() {
		System.out.println("InOrderTraversal");
		inOrderHelper(root);
	}

	private void inOrderHelper(Node<T> r) {
		if (r != null) {
			inOrderHelper(r.left);
			System.out.println(r + " ");
			inOrderHelper(r.right);
		}
	}

	/*************************************************
	 * 
	 * CLONE
	 * 
	 **************************************************/

	public BinTree<T> clone() {
		BinTree<T> twin = null;

		if (comparator == null)
			twin = new BinTree<T>();
		else
			twin = new BinTree<T>(comparator);

		twin.root = cloneHelper(root);
		return twin;
	}

	private Node<T> cloneHelper(Node<T> p) {
		if (p == null)
			return null;
		else
			return new Node<T>(p.data, cloneHelper(p.left),
					cloneHelper(p.right));
	}

	/*************************************************
	 * 
	 * MISC
	 * 
	 **************************************************/

	public int height() {
		return height(root);
	}

	private int height(Node<T> p) {
		if (p == null)
			return -1;
		else
			return 1 + Math.max(height(p.left), height(p.right));
	}

	public int countLeaves() {
		return countLeaves(root);
	}

	private int countLeaves(Node<T> p) {
		if (p == null)
			return 0;
		else if (p.left == null && p.right == null)
			return 1;
		else
			return countLeaves(p.left) + countLeaves(p.right);
	}

	// The width of a binary tree is the maximum number of elements on one level
	// of the tree.
	public int width() {
		int max = 0;
		for (int k = 0; k <= height(); k++) {
			int tmp = width(root, k);
			if (tmp > max)
				max = tmp;
		}
		return max;
	}

	// rerturns the number of node on a given level
	public int width(Node<T> p, int depth) {
		if (p == null)
			return 0;
		else if (depth == 0)
			return 1;
		else
			return width(p.left, depth - 1) + width(p.right, depth - 1);
	}

	// The diameter of a tree is the number of nodes
	// on the longest path between two leaves in the tree.
	public int diameter() {
		return diameter(root);
	}

	private int diameter(Node<T> p) {
		if (p == null)
			return 0;

		// the path goes through the root
		int len1 = height(p.left) + height(p.right) + 3;

		// the path does not pass the root
		int len2 = Math.max(diameter(p.left), diameter(p.right));

		return Math.max(len1, len2);
	}

	/*****************************************************
	 * 
	 * TREE ITERATOR
	 * 
	 ******************************************************/

	public Iterator<T> iterator() {
		return new MyIterator();
	}

	// pre-order
	private class MyIterator implements Iterator<T> {
		Stack<Node<T>> stk = new Stack<Node<T>>();

		public MyIterator() {
			if (root != null)
				stk.push(root);
		}

		public boolean hasNext() {
			return !stk.isEmpty();
		}

		public T next() {
			Node<T> cur = stk.peek();
			if (cur.left != null) {
				stk.push(cur.left);
			} else {
				Node<T> tmp = stk.pop();
				while (tmp.right == null) {
					if (stk.isEmpty())
						return cur.data;
					tmp = stk.pop();
				}
				stk.push(tmp.right);
			}

			return cur.data;
		}// end of next()

		public void remove() {

		}
	}// end of MyIterator

	/*****************************************************
	 * 
	 * the Node class
	 * 
	 ******************************************************/

	private class Node<T> {
		private T data;
		private Node<T> left, right;

		public Node(T data, Node<T> l, Node<T> r) {
			left = l;
			right = r;
			this.data = data;
		}

		public Node(T data) {
			this(data, null, null);
		}

		public void preOrder(LinkedList<T> list) {
			if (right != null)
				right.preOrder(list);
			list.add(data);
			if (left != null)
				left.preOrder(list);
		}

		public String toString() {
			return data.toString();
		}
	}
}
