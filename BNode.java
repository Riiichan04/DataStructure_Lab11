package lab11;

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

    @Override
    public String toString() {
        return data + "";
    }
}
