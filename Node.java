package RBT;

import java.awt.Color;

public class Node {

	/* Denoting colors with boolean value false - red, true - black */
	public static boolean RED = false;
	public static boolean BLACK = true;

	private boolean color = RED;
	private Node left;
	private Node right;
	private Node parent;
	private int data;

	/* Simple Node Methods */

	public Node() {
	}

	public Node(int data) {
		this.data = data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public void removeFromParent() {
		if (getParent() == null)
			return;

		// Remove current node's links from the parent
		if (parent.getLeft() == this)
			parent.setLeft(null);
		else if (parent.getRight() == this)
			parent.setRight(null);

		this.parent = null;
	}

	public void setLeft(Node child) {

		// Re-arranging the parents is required
		if (getLeft() != null)
			getLeft().setParent(null);

		if (child != null) {
			child.removeFromParent();
			child.setParent(this);
		}

		this.left = child;
	}

	public void setRight(Node child) {

		// Re-arranging the parents is required
		if (getRight() != null) {
			getRight().setParent(null);
		}

		if (child != null) {
			child.removeFromParent();
			child.setParent(this);
		}

		this.right = child;
	}

	public int getData() {
		return data;
	}

	public Node getLeft() {
		return left;
	}

	public static Node getLeft(Node node) {
		return node == null ? null : node.getLeft();
	}

	public Node getRight() {
		return right;
	}

	public static Node getRight(Node node) {
		return node == null ? null : node.getRight();
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	/* Red Black Node Functions */

	public boolean isRed() {
		return getColor() == RED;
	}

	public boolean isBlack() {
		return !isRed();
	}

	public static boolean isRed(Node node) {
		return getColor(node) == RED;
	}

	public static boolean isBlack(Node node) {
		return !isRed(node);
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public static void setColor(Node node, boolean color) {
		if (node == null)
			return;
		node.setColor(color);
	}

	public boolean getColor() {
		return color;
	}

	public static boolean getColor(Node node) {
		// As null node is considered to be black
		return node == null ? BLACK : node.getColor();
	}
	
	public void toggleColor() {
		color = !color;
	}

	public static void toggleColor(Node node) {
		if (node == null)
			return;

		node.setColor(!node.getColor());
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public static Node getParent(Node node) {
		return (node == null) ? null : node.getParent();
	}

	public Node getGrandparent() {
		Node parent = getParent();
		return (parent == null) ? null : parent.getParent();
	}
	public static Node getGrandparent(Node node) {
		return (node == null) ? null : node.getGrandparent();
	}

	public Node getSibling() {
		Node parent = getParent();
		if (parent != null) { // No sibling of root node
			if (this == parent.getRight())
				return (Node) parent.getLeft();
			else
				return (Node) parent.getRight();
		}
		return null;
	}

	public static Node getSibling(Node node) {
		return (node == null) ? null : node.getSibling();
	}

	public Node getUncle() {
		Node parent = getParent();
		if (parent != null) { // No uncle of root
			return parent.getSibling();
		}
		return null;
	}

	public Color getActualColor() {
		if (isRed())
			return  Color.RED;
		else
			return Color.BLACK;

	}

	public static Node getUncle(Node node) {
		return (node == null) ? null : node.getUncle();
	}
}