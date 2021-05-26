package RBT;

public class RedBlackTree {
	   protected Node root;// head of tree
	    protected int size = 0; // size of array

	    public Node getRoot() {
	        return this.root; //return root
	    }

	    public void insert(int item) {
	        if (this.isEmpty()) {
	            this.root = new Node(item);// tree is empty
	        } else {
	            this.insert(this.root, item); 
	        }
	        this.root.setColor(Node.BLACK);
	        ++this.size;
	    }

	    private void insert(Node node, int item) {
	        if (this.contains(item)) { //this element is already inserted
	            return;
	        }
	        // if key less than data in the parent 
	        if (node.getData() >item) {
	            if (node.hasLeft()) {
	                this.insert(node.getLeft(), item);
	            } else {
	                Node inserted = new Node(item);
	                node.setLeft(inserted);
	                this.balanceAfterInsert(inserted);
	                // edit tree after insert new node
	            }
	        }// if key less than data in the parent  
	        else if (node.hasRight()) {
	            this.insert(node.getRight(), item);
	        } else {
	            Node inserted = new Node(item);
	            node.setRight(inserted);
	            this.balanceAfterInsert(inserted);
	        }
	    }

	    public void remove(int data) {
	        if (!this.contains(data)) { // if tree doesn't contain key no thing to do
	            return;
	        }
	        
	        Node node = this.find(data); // find the node 
	        if (node.getLeft() != null && node.getRight() != null) {
	            Node successor = this.getSuccessor(node); // get node that will replace
	            //the deleted node 
	            node.setData(successor.getData()); // get smallest  lift child of right child
	            node = successor; // to delete the successor  
	        }
	        
	        Node pullUp = node.getLeft() == null ? node.getRight() : node.getLeft();
	        if (pullUp != null) {
	            if (node == this.root) {
	                node.removeFromParent();
	                this.root = node;
	            } else if (Node.getLeft(node.getParent()) == node) {
	                node.getParent().setLeft(pullUp);
	            } else {
	                node.getParent().setRight(pullUp);
	            }
	            if (Node.isBlack(node)) {
	                this.balanceAfterDelete(pullUp);
	            }
	        } else if (node == this.root) {
	            this.root = null;
	        } else {
	            if (Node.isBlack(node)) {
	                this.balanceAfterDelete(node);
	            }
	            node.removeFromParent();
	        }
	    }

	    public boolean isEmpty() {
	            return (this.root == null);
	    }

	    public void clear() {
	        this.root = null;
	    }

	    public int getSize() {
	        return this.size;
	    }

	    public void inOrder() {
	        this.inOrder(this.root);
	    }

	    private void inOrder(Node node) {
	        if (node != null) {
	            this.inOrder(node.getLeft());
	            System.out.print(node.getData() + " ");
	            this.inOrder(node.getRight());
	        }
	    }

	    public boolean contains(int data) {
	        return this.contains(this.root, data);
	    }

	    private boolean contains(Node root, int data) {
	        if (root == null) {
	            return false;
	        }
	        if (root.getData() > data) {
	            return this.contains(root.getLeft(), data);
	        }
	        if (root.getData() <data) {
	            return this.contains(root.getRight(), data);
	        }
	        return true;
	    }

	    public Node find(int data) {
	        return this.find(this.root, data);
	    }

	    private Node find(Node root, int data) {
	        if (root == null) {
	            return null;
	        }
	        if (root.getData()> data) {
	            return this.find(root.getLeft(), data);
	        }
	        if (root.getData() < data) {
	            return this.find(root.getRight(), data);
	        }
	        return root;
	    }

	    public int getDepth() {
	        return this.getDepth(this.root);
	    }

	    private int getDepth(Node node) {
	        if (node != null) {
	            int right_depth;
	            int left_depth = this.getDepth(node.getLeft());
	            return left_depth > (right_depth = this.getDepth(node.getRight())) ? left_depth + 1 : right_depth + 1;
	        }
	        return 0;
	    }

	    private Node getSuccessor(Node root) {
	        Node leftTree = root.getLeft();
	        if (leftTree != null) {
	            while (leftTree.getLeft() != null) {
	                leftTree = leftTree.getLeft();
	            }
	        }
	        return leftTree;
	    }
	    // edit tree after insert new key using cases
	    private void balanceAfterInsert(Node node) {
	        if (node == null || node == this.root || Node.isBlack(node.getParent())) {
	            return;
	        }
	        /*
	         * case A
	         * uncle is red 
	         * to be done
	         * toggleColor of parent black and uncle black and grandparent red
	         * repeat with grandparent because it will be red   */
	        if (Node.isRed(node.getUncle())) {
	            Node.toggleColor(node.getParent());
	            Node.toggleColor(node.getUncle());
	            Node.toggleColor(node.getGrandparent());
	            this.balanceAfterInsert(node.getGrandparent());
	        }
	        /*
	         * case B 
	         * uncle is black 
	         *  similar to AVL Tree */
	        else if (this.hasLeftParent(node)) {
	            if (this.isRightChild(node)) {
	                node = node.getParent();
	                this.rotateLeft(node);
	            }
	            Node.setColor(node.getParent(), Node.BLACK);
	            Node.setColor(node.getGrandparent(), Node.RED);
	            this.rotateRight(node.getGrandparent());
	        } else if (this.hasRightParent(node)) {
	            if (this.isLeftChild(node)) {
	                node = node.getParent();
	                this.rotateRight(node);
	            }
	   
	            Node.setColor(node.getParent(), Node.BLACK);
	            Node.setColor(node.getGrandparent(), Node.RED);
	            this.rotateLeft(node.getGrandparent());
	        }
	    }
	    /*
	     * edit tree after delete node  */
		private void balanceAfterDelete(Node node) {
	        while (node != this.root && node.isBlack()) {
	            Node sibling = node.getSibling();
	            if (node == Node.getLeft(node.getParent())) {
	                if (Node.isRed(sibling)) {
	                    Node.setColor(sibling, Node.BLACK);
	                    Node.setColor(node.getParent(), Node.RED);
	                    this.rotateLeft(node.getParent());
	                    sibling = (Node) Node.getRight(node.getParent());
	                }
	                if (Node.isBlack(Node.getLeft(sibling)) && Node.isBlack(Node.getRight(sibling))) {
	                    Node.setColor(sibling, Node.RED);
	                    node = node.getParent();
	                    continue;
	                }
	                if (Node.isBlack(Node.getRight(sibling))) {
	                    Node.setColor(Node.getLeft(sibling), Node.BLACK);
	                    Node.setColor(sibling, Node.RED);
	                    this.rotateRight(sibling);
	                    sibling = (Node) Node.getRight(node.getParent());
	                }
	                Node.setColor(sibling, Node.getColor(node.getParent()));
	                Node.setColor(node.getParent(), Node.BLACK);
	                Node.setColor(Node.getRight(sibling), Node.BLACK);
	                this.rotateLeft(node.getParent());
	                node = this.root;
	                continue;
	            }
	            if (Node.isRed(sibling)) {
	                Node.setColor(sibling, Node.BLACK);
	                Node.setColor(node.getParent(), Node.RED);
	                this.rotateRight(node.getParent());
	                sibling = (Node) Node.getLeft(node.getParent());
	            }
	            if (Node.isBlack(Node.getLeft(sibling)) && Node.isBlack(Node.getRight(sibling))) {
	                Node.setColor(sibling, Node.RED);
	                node = node.getParent();
	                continue;
	            }
	            if (Node.isBlack(Node.getLeft(sibling))) {
	                Node.setColor(Node.getRight(sibling), Node.BLACK);
	                Node.setColor(sibling, Node.RED);
	                this.rotateLeft(sibling);
	                sibling = (Node) Node.getLeft(node.getParent());
	            }
	            Node.setColor(sibling, Node.getColor(node.getParent()));
	            Node.setColor(node.getParent(), Node.BLACK);
	            Node.setColor(Node.getLeft(sibling), Node.BLACK);
	            this.rotateRight(node.getParent());
	            node = this.root;
	        }
	        Node.setColor(node, Node.BLACK);
	    }

	    private void rotateRight(Node node) {
	        if (node.getLeft() == null) {
	            return;
	        }
	        Node leftTree = node.getLeft();
	        node.setLeft(leftTree.getRight());
	        if (node.getParent() == null) {
	            this.root = leftTree;
	        } else if (node.getParent().getLeft() == node) {
	            node.getParent().setLeft(leftTree);
	        } else {
	            node.getParent().setRight(leftTree);
	        }
	        leftTree.setRight(node);
	    }

	    private void rotateLeft(Node node) {
	        if (node.getRight() == null) {
	            return;
	        }
	        Node rightTree = node.getRight();
	        node.setRight(rightTree.getLeft());
	        if (node.getParent() == null) {
	            this.root = rightTree;
	        } else if (node.getParent().getLeft() == node) {
	            node.getParent().setLeft(rightTree);
	        } else {
	            node.getParent().setRight(rightTree);
	        }
	        rightTree.setLeft(node);
	    }

	    private boolean hasRightParent(Node node) {
	        if (Node.getRight(node.getGrandparent()) == node.getParent()) {
	            return true;
	        }
	        return false;
	    }

	    private boolean hasLeftParent(Node node) {
	        if (Node.getLeft(node.getGrandparent()) == node.getParent()) {
	            return true;
	        }
	        return false;
	    }

	    private boolean isRightChild(Node node) {
	        if (Node.getRight(node.getParent()) == node) {
	            return true;
	        }
	        return false;
	    }

	    private boolean isLeftChild(Node node) {
	        if (Node.getLeft(node.getParent()) == node) {
	            return true;
	        }
	        return false;
	    }
	    public static void main(String [] args) {
	    	RedBlackTree tree=new RedBlackTree();
	    	tree.insert(1);
	    	tree.insert(3);
	    	tree.insert(12);
	    	tree.insert(31);
	    	tree.remove(12);
	    	tree.remove(1);
	    	tree.inOrder();
	    }
}
