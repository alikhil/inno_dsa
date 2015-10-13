package MyUtils;

/**
 * Created by Alik Khilazhev on 08.09.2015.
 */
public class LinkedList<E> implements List<E> {

    private class Node<E>{
        private Node<E> next;
        private Node<E> prev;
        private E element;
        public Node(E elem, Node<E> p, Node<E> n){
            element = elem;
            prev = p;
            next = n;
        }

        public void setElement(E item){
            element = item;
        }

        public Node<E> getNext(){
            return next;
        }

        public Node<E> getPrev(){
            return prev;
        }

        public void setPrev(Node<E> p){
            prev = p;
        }

        public void setNext(Node<E> n){
            next = n;
        }

        public E getElement(){
            return element;
        }
    }

    private int sz = 0;
    private Node header;
    private Node trailer;

    public LinkedList(){
        header = new Node(null, null, null);
        trailer = new Node(null, header, null);
        header.setNext(trailer);
    }

    public void addFirst(E elem){
        Node<E> cur = new Node<>(elem, header, header.getNext());
        header.getNext().setPrev(cur);
        header.setNext(cur);
    }

    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void add(int i, E item) {
        Node<E> current = getNode(i);
        Node<E> prev = current.getPrev();
        Node<E> newNode = new Node<>(item, prev, current);
        prev.setNext(newNode);
        current.setPrev(newNode);
        sz++;
    }

    private Node<E> getNode(int i) {
        Node<E> current = header;
        for(int j = 0;j < i; j++){
            current = current.getNext();
        }
        return current;
    }

    @Override
    public E get(int i) {
        Node<E> current = getNode(i);
        return current.getElement();
    }

    @Override
    public E set(int i, E item) {
        Node<E> current = getNode(i);
        E oldVal = current.getElement();
        current.setElement(item);
        return oldVal;
    }

    @Override
    public E remove(int i) {
        Node<E> current = getNode(i);
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        sz--;
        return current.getElement();
    }
}
