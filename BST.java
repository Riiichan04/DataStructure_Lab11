package lab11;

import java.util.*;

public class BST<E extends Comparable<E>> {
    private BNode<E> root;

    public BST() {
        this.root = null;
    }

    public void setRoot(BNode<E> root) {
        this.root = root;
    }

    // Task 1
    // Add element e into BST
    public void add(E e) {
        add(this.root, e);
    }

    public void add(BNode<E> root, E e) {
        if (this.root == null) this.setRoot(new BNode<>(e));
        else {
            if (root.getData().compareTo(e) > 0) {
                if (root.getLeft() == null) root.setLeft(new BNode<>(e));
                else add(root.getLeft(), e);
            } else {
                if (root.getRight() == null) root.setRight(new BNode<>(e));
                else add(root.getRight(), e);
            }
        }
    }

    // Add a collection of elements col into BST
    public void add(Collection<E> col) {
        for (E e : col) add(e);
    }

    // compute the depth of a node in BST
    public int depth(E node) {
        BNode<E> currentNode = search(this.root, node);
        return (currentNode != null) ? findDepth(currentNode) : -1;
    }

    public BNode<E> search(BNode<E> root, E node) {
        if (root != null) {
            // like binary search
            if (root.getData().compareTo(node) > 0) return search(root.getLeft(), node);
            else if (root.getData().compareTo(node) < 0) return search(root.getRight(), node);
            else return root;
        } else return null;
    }

    public int findDepth(BNode<E> node) {
        // if node have 2 child
        if (node.getLeft() != null && node.getRight() != null) {
            int leftDepth = 1 + findDepth(node.getLeft());
            int rightDepth = 1 + findDepth(node.getRight());
            return Math.max(leftDepth, rightDepth);
        }
        // if node is leaf
        else if (node.getLeft() == null && node.getRight() == null) return 0;
            // if node have 1 child
        else return (node.getLeft() == null) ? 1 + findDepth(node.getRight()) : 1 + findDepth(node.getLeft());
    }

    // compute the height of BST
    public int height() {
        return findDepth(this.root);
    }

    // Compute total nodes in BST
    public int size() {
        return this.root.countSize(this.root);
    }

    // Check whether element e is in BST
    public boolean contains(E e) {
        return search(this.root, e) != null;
    }

    // Find the minimum element in BST
    public E findMin() {
        return this.root.findMinWithBranch(this.root);
    }

    // Find the maximum element in BST
    public E findMax() {
        return this.root.findMaxWithBranch(this.root);
    }

    // Remove element e from BST
    public boolean remove(E e) {
        BNode<E> removeNode = search(this.root, e);
        if (removeNode == null) return false;
        else {
            BNode<E> parent = findNodeParent(this.root, e);
            //if Node don't have child
            if (removeNode.getLeft() == null && removeNode.getRight() == null) {
                if (parent.getData().compareTo(removeNode.getData()) < 0) parent.setRight(null);
                else parent.setLeft(null);
            }
            //if Node have 1 child
            else if (removeNode.getLeft() == null) parent.setRight(removeNode.getRight());
            else if (removeNode.getRight() == null) parent.setLeft(removeNode.getLeft());
                // if Node have 2 child
            else {
                BNode<E> newNode = findSuccessor(removeNode.getRight());
                // recreate Successor node
                remove(newNode.getData());
                newNode.setLeft(removeNode.getLeft());
                newNode.setRight(removeNode.getRight());
                // add Successor node
                if (parent.getData().compareTo(newNode.getData()) < 0) parent.setRight(newNode);
                else parent.setLeft(newNode);
            }
        }
        return true;
    }

    public BNode<E> findSuccessor(BNode<E> node) {
        if (node.getLeft() == null) return node;
        else return findSuccessor(node.getLeft());
    }

    public BNode<E> findNodeParent(BNode<E> node, E data) {
        //if Node have 2 child
        if (node.getLeft() != null && node.getRight() != null) {
            if (node.getLeft().getData().compareTo(data) == 0 || node.getRight().getData().compareTo(data) == 0)
                return node;
            else {
                BNode<E> leftVal = findNodeParent(node.getLeft(), data);
                BNode<E> rightVal = findNodeParent(node.getRight(), data);
                return (leftVal != null) ? leftVal : rightVal;
            }
        }
        //if Node have 0 child
        else if (node.getLeft() == null && node.getRight() == null) return null;
            //if Node have 1 child
        else {
            if (node.getLeft() == null) {
                return (node.getRight().getData().compareTo(data) == 0) ? node : findNodeParent(node.getRight(), data);
            } else return (node.getLeft().getData().compareTo(data) == 0) ? node : findNodeParent(node.getLeft(), data);
        }
    }

    // get the descendants of a node
    public List<E> descendants(E data) {
        BNode<E> currentNode = search(this.root, data);
        return (currentNode == null) ? null : currentNode.addDescendants(currentNode, new LinkedList<>());
    }

    // get the ancestors of a node
    public List<E> ancestors(E data) {
        return (search(this.root, data) == null) ? null : this.root.addAncestors(this.root, data, new LinkedList<>());
    }

    //Task 2
    // display BST using inorder approach
    public void inorder() {
        this.root.inorderPrint(this.root);
    }

    // display BST using preorder approach
    public void preorder() {
        this.root.preorderPrint(this.root);
    }

    // display BST using postorder approach
    public void postorder() {
        this.root.postorderPrint(this.root);
    }
}
