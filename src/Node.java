/*
        ∗ @file: Node.java
∗ @description: This program implements node class with comparable interface, nodes for value/left/right, isLeaf and utility methods
        ∗ @author: Alexander Chung
∗ @date: September 25, 2025
                                              */

// Implement the constructor
// Implement the setElement method
// Implement the setLeft method
// Implement the setRight method
// Implement the getLeft method
// Implement the getRight method
// Implement the getElement method
// Implement the isLeaf method
public class Node < T extends Comparable<T>>{

    private T value;
    private Node<T> left;
    private Node<T> right;

    public Node (T value){
        this.value = value;
        if (value == null) throw new IllegalArgumentException("Node value cannot be null"); //doesn't allow value to be null
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public boolean isLeaf(){ //added to improve removeVal
        if(left == null && right == null){
            return true;
        }
        else {return false;}
    }
}