/*
        ∗ @file: BST.java
∗ @description: This program implements BST class implements comparable, implements iterator interface to traverse in ascending order, supports insert val/search val/remove val/print
        ∗ @author: Alexander Chung
∗ @date: September 25, 2025
                                              */

// Implement the constructor
// Implement the clear method
// Implement the size method
// Implement the insert method
// Implement the remove method
// Implement the search method
// Implement the iterator method
// Implement the BSTIterator class

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

//in-order traversal iterator for BST, elements returned in ascending order
public class BST <T extends Comparable<T>> implements Iterable<T> {
    private Node<T> root;

    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    //implements in-order BST iterator
    private class BSTIterator implements Iterator<T> {

        private Stack<Node<T>> nodeStack = new Stack<>();


        BSTIterator() {
            goLeftFrom(root);
        }

        //pushes all left descendants of node onto stack
        private void goLeftFrom(Node<T> node) {
            while (node != null) {
                nodeStack.push(node);
                node = node.getLeft();
            }
        }

        //checks if there are still elements and returns true if not reached the end
        @Override
        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }


        //next element to return, if no next element it throws an exception
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator exceeded");
            }
            Node<T> node = nodeStack.pop();
            T value = node.getValue();
            // visit node, go left from its right child
            goLeftFrom(node.getRight());
            return value;
        }
    }
    //inserts new value into BST, if tree is empty the value becomes the root, if not then inserted in correct position
    public void insertVal(T value) {

        if(value == null){
            return;
        }

        Node<T> newNode = new Node<>(value);

        if(root == null){
            root = newNode;
            return;
        }
        Node<T> cur = root;
        Node<T> prev = null;

        while(cur!=null){
            prev = cur;
            if(value.compareTo(cur.getValue()) < 0) {
                cur = cur.getLeft();
            }
            else if(value.compareTo(cur.getValue()) > 0){
                cur = cur.getRight();
            }
            else{
                return;
            }
        }
        if(value.compareTo(prev.getValue()) < 0){
            prev.setLeft(newNode);
        }
        else{
            prev.setRight(newNode);
        }
    }

    // searches for the value in BST, and returns the node containing the value or null if not there
    public Node<T> searchVal(T value) {
        Node<T> cur = root;
        while(cur!=null){

            if(value.equals(cur.getValue())){
                return cur;
            }
            else if(value.compareTo(cur.getValue()) > 0){
                cur = cur.getRight();
            }
            else{cur = cur.getLeft();
            }
        }
        return null;
    }

    //removes value from BST if it is there, checks if node is a leaf, checks if node has one child, checks if node has two children and replaces node with its successor
    public Node<T> removeVal(T value) {
        if(root == null) return null;

        Node<T> cur = root;
        Node<T> prevNode = null;
        Node<T> valRem = null;

        while(cur != null && !cur.getValue().equals(value)) {
            prevNode = cur;
            if(value.compareTo(cur.getValue()) < 0) {
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }
        }

        if(cur == null) return null;
        valRem = cur;

        // No children
        if(cur.isLeaf()) {
            if(cur == root){
                root = null;
            }
            else if(prevNode.getLeft() == cur){
                prevNode.setLeft(null);
            }
            else {
                prevNode.setRight(null);
            }
        }
        // One child
        else if(cur.getLeft() == null) { // Only right child
            if(cur == root){
                root = cur.getRight();
            }
            else if(prevNode.getLeft() == cur){
                prevNode.setLeft(cur.getRight());
            }
            else {
                prevNode.setRight(cur.getRight());
            }
        }
        else if(cur.getRight() == null) { // Only left child
            if(cur == root) {
                root = cur.getLeft();
            }
            else if(prevNode.getLeft() == cur) {
                prevNode.setLeft(cur.getLeft());
            }
            else {
                prevNode.setRight(cur.getLeft());
            }
        }
        //Two children
        else {

            Node<T> prevSuc = cur;
            Node<T> nextRt = cur.getRight();
            while(nextRt.getLeft() != null) {
                prevSuc = nextRt;
                nextRt = nextRt.getLeft();
            }

            cur.setValue(nextRt.getValue());

            if(prevSuc.getLeft() == nextRt) {
                prevSuc.setLeft(nextRt.getRight());
            } else {
                prevSuc.setRight(nextRt.getRight());
            }
        }
        return valRem;
    }

}