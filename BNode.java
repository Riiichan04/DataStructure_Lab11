package lab11;

import java.util.List;

public class BNode<E extends Comparable<E>> {
    private E data;
    private BNode<E> left;
    private BNode<E> right;

    public BNode(E data, BNode<E> left, BNode<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public BNode(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    public E getData() {
        return data;
    }

    public BNode<E> getLeft() {
        return left;
    }

    public BNode<E> getRight() {
        return right;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setLeft(BNode<E> left) {
        this.left = left;
    }

    public void setRight(BNode<E> right) {
        this.right = right;
    }


    public int countSize(BNode<E> root) {
        return (root == null) ? 0 : 1 + countSize(root.getRight()) + countSize(root.getLeft());
    }

    public E findMinWithBranch(BNode<E> e) {
        return (e.getLeft() == null) ? e.getData() : findMinWithBranch(e.getLeft());
    }

    public E findMaxWithBranch(BNode<E> e) {
        return (e.getRight() == null) ? e.getData() : findMaxWithBranch(e.getRight());
    }

    public List<E> addDescendants(BNode<E> node, List<E> list) {
        if (node != null) {
            list.add(node.getData());
            addDescendants(node.getLeft(), list);
            addDescendants(node.getRight(), list);
            return list;
        } else return null;
    }

    public List<E> addAncestors(BNode<E> root, E data, List<E> list) {
        if (root == null) return null;
        else {
            list.add(root.getData());
            if (root.getData().compareTo(data) == 0) return null;
            else if (root.getData().compareTo(data) < 0) addAncestors(root.getRight(), data, list);
            else addAncestors(root.getLeft(), data, list);
            return list;
        }
    }

    public void inorderPrint(BNode<E> node) {
        if (node.getLeft() != null) inorderPrint(node.getLeft());
        System.out.print(node + " ");
        if (node.getRight() != null) inorderPrint(node.getRight());
    }

    public void preorderPrint(BNode<E> node) {
        System.out.print(node + " ");
        if (node.getLeft() != null) preorderPrint(node.getLeft());
        if (node.getRight() != null) preorderPrint(node.getRight());
    }

    public void postorderPrint(BNode<E> node) {
        if (node.getLeft() != null) postorderPrint(node.getLeft());
        if (node.getRight() != null) postorderPrint(node.getRight());
        System.out.print(node + " ");
    }

    @Override
    public String toString() {
        return data + "";
    }
}
