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
        if (this.root == null) {
            this.setRoot(new BNode<>(e));
        } else {
            if (root.getData().compareTo(e) > 0) {
                if (root.getLeft() == null) {
                    root.setLeft(new BNode<>(e));
                }
                else add(root.getLeft(), e);
            }
            else {
                if (root.getRight() == null) {
                    root.setRight(new BNode<>(e));
                }
                else add(root.getRight(), e);
            }
        }
    }

    // Add a collection of elements col into BST
    public void add(Collection<E> col) {
        for (E e : col) {
            add(e);
        }
    }

    // compute the depth of a node in BST
    public int depth(E node) {
        if (this.root.getData().compareTo(node) == 0) return 0;
        else {
            BNode<E> currentNode = search(root, node);
            if (currentNode != null) {
                return findDepth(currentNode);
            }
            else return -1;
        }
    }

    public BNode<E> search(BNode<E> root, E node) {
        if (root != null) {
            // like binary search
            if (root.getData().compareTo(node) > 0) {
                return search(root.getLeft(), node);
            }
            else if (root.getData().compareTo(node) < 0) {
                return search(root.getRight(), node);
            }
            else return root;
        }
        else return null;
    }

    public int findDepth(BNode<E> node) {
        // if node is root
        if (node == null) return 0;
            // if node have 2 child
        else if (node.getLeft() != null && node.getRight() != null) {
            int leftDepth = 1 + findDepth(node.getLeft());
            int rightDepth = 1 + findDepth(node.getRight());
            return Math.max(leftDepth, rightDepth);
        }
        // if node is leaf
        else if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        }
        // if node have 1 child
        else if (node.getLeft() == null) {
            return 1 + findDepth(node.getRight());
        }
        else return 1 + findDepth(node.getLeft());
    }

    // compute the height of BST
    public int height() {
        return findDepth(this.root);
    }

    // Compute total nodes in BST
    public int size() {
        return countSize(root);
    }

    public int countSize(BNode<E> root) {
        if (root == null) return 0;
        else return 1 + countSize(root.getRight()) + countSize(root.getLeft());
    }

    // Check whether element e is in BST
    public boolean contains(E e) {
        return search(root, e) != null;
    }

    // Find the minimum element in BST
    public E findMin() {
        return findMinWithBranch(this.root);
    }

    public E findMinWithBranch(BNode<E> e) {
        if (e.getLeft() == null) return e.getData();
        else return findMinWithBranch(e.getLeft());
    }

    // Find the maximum element in BST
    public E findMax() {
        return findMaxWithBranch(this.root);
    }

    public E findMaxWithBranch(BNode<E> e) {
        if (e.getRight() == null) return e.getData();
        else return findMaxWithBranch(e.getRight());
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
            else if (removeNode.getLeft() == null) {
                parent.setRight(removeNode.getRight());
            }
            else if (removeNode.getRight() == null) {
                parent.setLeft(removeNode.getLeft());
            }
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
        if (node.getLeft() != null && node.getRight() != null) {
            if (node.getLeft().getData().compareTo(data) == 0 || node.getRight().getData().compareTo(data) == 0) {
                return node;
            }
            else {
                BNode<E> leftVal = findNodeParent(node.getLeft(), data);
                BNode<E> rightVal = findNodeParent(node.getRight(), data);
                return (leftVal != null) ? leftVal : rightVal;
            }
        }
        else if (node.getLeft() == null && node.getRight() == null) {
            return null;
        }
        else if (node.getLeft() == null) {
            if (node.getRight().getData().compareTo(data) == 0) {
                return node;
            }
            else return findNodeParent(node.getRight(), data);
        }
        else {
            if (node.getLeft().getData().compareTo(data) == 0) {
                return node;
            }
            else return findNodeParent(node.getLeft(), data);
        }
    }

    // get the descendants of a node
    public List<E> descendants(E data) {
        BNode<E> currentNode = search(root, data);
        List<E> result = new LinkedList<>();
        if (currentNode == null) return null;
        else {
            return addDescendants(currentNode, result);
        }
    }

    public List<E> addDescendants(BNode<E> node, List<E> list) {
        if (node != null) {
            list.add(node.getData());
            addDescendants(node.getLeft(), list);
            addDescendants(node.getRight(), list);
            return list;
        }
        else return null;
    }

    // get the ancestors of a node
    public List<E> ancestors(E data) {
        BNode<E> currentNode = search(root, data);
        List<E> result = new LinkedList<>();
        if (currentNode == null) return null;
        else {
            return addAncestors(this.root, data, result);
        }
    }

    public List<E> addAncestors(BNode<E> root, E data, List<E> list) {
        if (root == null) {
            return null;
        }
        else {
            list.add(root.getData());
            if (root.getData().compareTo(data) == 0) return null;
            else if (root.getData().compareTo(data) < 0) addAncestors(root.getRight(), data, list);
            else addAncestors(root.getLeft(), data, list);
            return list;
        }
    }

    //Task 2
    // display BST using inorder approach
    public void inorder() {
        inorderPrint(this.root);
    }

    public void inorderPrint(BNode<E> node) {
        if (node.getLeft() != null) {
            inorderPrint(node.getLeft());
        }
        System.out.print(node + " ");
        if (node.getRight() != null) {
            inorderPrint(node.getRight());
        }
    }

    // display BST using preorder approach
    public void preorder() {
        preorderPrint(this.root);
    }

    public void preorderPrint(BNode<E> node) {
        System.out.print(node + " ");
        if (node.getLeft() != null) {
            preorderPrint(node.getLeft());
        }
        if (node.getRight() != null) {
            preorderPrint(node.getRight());
        }
    }

    // display BST using postorder approach
    public void postorder() {
        postorderPrint(this.root);
    }

    public void postorderPrint(BNode<E> node) {
        if (node.getLeft() != null) {
            postorderPrint(node.getLeft());
        }
        if (node.getRight() != null) {
            postorderPrint(node.getRight());
        }
        System.out.print(node + " ");
    }
}
