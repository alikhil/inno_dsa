import com.sun.org.apache.xpath.internal.ExpressionNode;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Alik Khilazhev
 * @email a.khilazhev@innopolis.ru
 * @date 03.11.2015
 * In accordance with the academic honor, I Alik Khilazhev certify that
 * the answers here are my own work without copying of others and
 * solutions from Internet or other sources."
 */
public class Main {
    public static void main(String[] args) throws Exception {

        // Initializing IO
        BufferedReader reader = new BufferedReader(new FileReader("test.in"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("test.out"));

        String line = reader.readLine();

        while (line != null && !line.isEmpty()) {
            // little trick allows to do not care about Negate operation
            char[] input = line.replace("N","N 0 ").toCharArray();

            LinkedStack<Node> nodeStack = new LinkedStack<>();
            BinaryTree tree = new BinaryTree();
            for (int i = 0; i < input.length; i++) {
                if (input[i] != ' ' && input[i] != '(') {
                    if (input[i] == ')') {
                        Node right = nodeStack.pop();
                        Node left =  nodeStack.pop();
                        Node operatorNode = nodeStack.pop();

                        operatorNode.setRight(right);
                        operatorNode.setLeft(left);

                        nodeStack.push(operatorNode);
                    } else {
                        if(Character.isDigit(input[i]))
                            nodeStack.push(new Node(input[i],true));
                        else
                            nodeStack.push(new Node(input[i],false));
                    }
                }
            }
            tree.setRoot(nodeStack.pop());
            ArrayList<Integer> result = tree.calculate();
            // printing results
            int count = result.size();
            if(count == 1)
                writer.write(result.get(0).toString() + "\n");
            else {
                for (int i = 0; i < count - 1; i++) {
                    writer.write(result.get(i).toString() + " ");
                }
                writer.write("\n" + result.get(count - 1) + "\n");
            }
            //getting new input
            line = reader.readLine();
        }
        //closing files
        writer.close();
        reader.close();

    }
}

/**
 * Binary tree
 */
class BinaryTree {

    private Node root;

    /**
     * Setting roor of tree
     * @param newRoot new root of tree
     */
    public void setRoot(Node newRoot) {
        root = newRoot;
    }

    /**
     * Calculating result of whole expression
     * @return list of intermediate results
     */
    public ArrayList<Integer> calculate() {

        ArrayList<Integer> result = new ArrayList<>();
        root.getExpressionResult(result);
        return result;
    }

}

/**
 * Node of binary tree
 */
class Node  {

    private Object value;
    private Node left;
    private Node right;
    public boolean isDigit;

    /**
     * Node initialization
     * @param val value
     * @param isDigit is value digit?(0-9)
     */
    public Node(Object val, boolean isDigit){
        left = null;
        right = null;
        value = val;
        this.isDigit = isDigit;
    }

    /**
     * Setting left child of node
     * @param l left child
     */
    public void setLeft(Node l){
        left = l;
    }

    /**
     * Setting right child of node
     * @param r right child
     */
    public void setRight(Node r){
        right = r;
    }

    /**
     * Getting result of expression and saving it on resultList
     * @param resultList list of results
     * @return result of expression
     */
    public int getExpressionResult(ArrayList<Integer> resultList)  {
        if(isDigit)
            return Integer.parseInt(value.toString());

        char command = (Character)value;
        Integer result = 0;
        int leftValue,rightValue;

        switch (command){
            case 'A':
                leftValue = left.getExpressionResult(resultList);
                rightValue = right.getExpressionResult(resultList);
                result = rightValue + leftValue;
                break;
            case 'M':
                leftValue = left.getExpressionResult(resultList);
                rightValue = right.getExpressionResult(resultList);
                result = rightValue * leftValue;
                break;
            case 'S':
                leftValue = left.getExpressionResult(resultList);
                rightValue = right.getExpressionResult(resultList);
                result = leftValue - rightValue;
                break;
            case 'D':
                leftValue = left.getExpressionResult(resultList);
                rightValue = right.getExpressionResult(resultList);
                result = rightValue == 0 ? 0 : leftValue / rightValue;
                break;
            case 'N':
                result = -right.getExpressionResult(resultList);
                break;
        }

        resultList.add(result);
        return result;

    }
}

class LinkedStack<E> extends LinkedList<E>{


    public LinkedStack(){

    }

    /**
     * Pushing element to stack
     * @param element element
     */
    public void push(E element){
        addFirst(element);
    }

    /**
     * Deleting element from top of stack
     * @return value on top of stack
     * @throws Exception if stack is empty
     */
    public E pop() throws Exception {
        return removeFirst();
    }

    /**
     * Getting element from top of stack
     * @return element
     */
    public E peek(){
        return getFirst();
    }
}

/**
 * Simplified Linked List
 * @param <E> type of storing elements
 */
class LinkedList<E> {

    private class Node<E> {
        private Node<E> next;
        private Node<E> prev;
        private E element;

        public Node(E elem, Node<E> p, Node<E> n) {
            element = elem;
            prev = p;
            next = n;
        }

        public void setElement(E item) {
            element = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> p) {
            prev = p;
        }

        public void setNext(Node<E> n) {
            next = n;
        }

        public E getElement() {
            return element;
        }
    }

    private int sz = 0;
    private Node header;
    private Node trailer;

    /**
     * Initialization
     */
    public LinkedList() {
        header = new Node(null, null, null);
        trailer = new Node(null, header, null);
        header.setNext(trailer);
    }

    /**
     * Adds first element to list
     * @param elem element to add
     */
    public void addFirst(E elem) {
        Node<E> cur = new Node<>(elem, header, header.getNext());
        header.getNext().setPrev(cur);
        header.setNext(cur);
        sz++;
    }

    /**
     * Get size of list
     * @return size of list
     */
    public int size() {
        return sz;
    }

    /**
     * Checks if list is empty
     * @return list is empty?
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Getting first element of list
     * @return first element
     */
    public E getFirst(){
        return (E)header.getNext().getElement();
    }

    /**
     * Removes first element from list
     * @return first element
     * @throws Exception if list is empty
     */
    public E removeFirst() throws Exception {
        if(isEmpty())
            throw new Exception("Linked list is empty. Nothing to delete");
        Node<E> first = header.getNext();
        E value = first.getElement();
        header.setNext(first.getNext());
        first.getNext().setPrev(header);
        sz--;
        return value;
    }

}