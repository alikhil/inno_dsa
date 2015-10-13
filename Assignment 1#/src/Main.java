import jdk.nashorn.internal.objects.NativeUint16Array;

import java.io.*;
import java.util.Iterator;

/**
 * Created by Alik Khilazhev on 15.09.2015.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test.in"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("test.out"));

            int N = Integer.parseInt(reader.readLine());
            int M = Integer.parseInt(reader.readLine());
            int I = Integer.parseInt(reader.readLine());
            String ciphertext = reader.readLine();
            String text = reader.readLine();

            SuperCryptor cryptor = new SuperCryptor(N, M, I, ciphertext, text);
            CircularLinkedList<Character> list = cryptor.decrypt();

            for (int i = 0; i < list.size(); i++) {
                writer.write(list.first());
                list.shift();
            }
            writer.write("\n");
            CircularLinkedList<Character> cryptedList = cryptor.encrypt(list);

            for (int i = 0; i < cryptedList.size(); i++) {
                writer.write(cryptedList.first());
                cryptedList.shift();
            }

            reader.close();
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file");
        }
    }

}

/**
 * Class for decrypring and encrypting
 */
class SuperCryptor {

    /**
     * Initalizating class
     *
     * @param n             character count in encrypted string
     * @param m             character count in decrypted part
     * @param i             step number for encrypting
     * @param encrypted     encrypted string
     * @param decryptedPart decrypted part of encrypted string
     */
    public SuperCryptor(int n, int m, int i, String encrypted, String decryptedPart) {
        N = n;
        M = m;
        I = i;
        this.encrypted = encrypted;
        this.decryptedPart = decryptedPart;
    }

    /**
     * Decrypting encrypted string
     *
     * @return encrypted list with characters
     */
    public CircularLinkedList<Character> decrypt() {
        foundPermutation = false;
        int[] array = new int[M];
        for (int i = 0; i < M; i++)
            array[i] = i;
        permutation(array, 0, M);
        CircularLinkedList<Character> decryptedList = new CircularLinkedList<>();
        for (int i = 0; i < N; i++) {
            decryptedList.addLast(encrypted.charAt(keyArray[i % M] + ((i / M) * M)));
        }
        return decryptedList;
    }

    /**
     * Encrypting list of character
     *
     * @param list list to encrypt
     * @return encrypted list
     */
    public CircularLinkedList<Character> encrypt(CircularLinkedList<Character> list) {
        if(I <= 1)
            return list;
        else {
            CircularLinkedList<Character> newEncrypted = new CircularLinkedList<>();
            int cnt = 0;
            while (!list.isEmpty()) {
                Character cur = list.first();
                if (++cnt % I == 0) {
                    newEncrypted.addLast(cur);
                    list.removeFirst();
                    cnt = 1;
                }
                list.shift();
            }
            return newEncrypted;
        }
    }

    private int N;
    private int M;
    private int I;

    private String encrypted;
    private String decryptedPart;
    private Boolean foundPermutation;
    private int[] keyArray;

    /**
     * Generating permutations of array
     *
     * @param a given array
     * @param i start
     * @param n length
     */
    private void permutation(int a[], int i, int n) {
        if (!foundPermutation) {
            int temp;
            if (i == n) {
                String current = "";
                for (int j = 0; j < n; j++) {
                    current = current + encrypted.charAt(a[j]);
                }
                if (current.compareTo(decryptedPart) == 0) {
                    foundPermutation = true;
                    keyArray = a.clone();
                }
            } else {
                for (int j = i; j < n; j++) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    permutation(a, i + 1, n);
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

}

/**
 * Circular single linked list storing E type objects
 *
 * @param <E> type of storing objects
 */
class CircularLinkedList<E> {

    class Node<E> {
        private Node<E> next;
        private E element;

        public Node(E elem, Node<E> n) {
            element = elem;
            next = n;
        }

        public void setElement(E item) {
            element = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }

        public E getElement() {
            return element;
        }
    }

    private int size;
    private Node tail;

    /**
     * Initalizating list
     */
    public CircularLinkedList() {
        tail = new Node(null, null);
    }

    /**
     * Getting size of list
     *
     * @return size of list
     */
    public int size() {
        return size;
    }

    /**
     * Checking if list is empty
     *
     * @return true if is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Adding first element to list
     *
     * @param item item to add
     */
    public void addFirst(E item) {
        if (isEmpty()){
            tail = new Node(item, null);
            tail.setNext(tail);
        }
        else {
            Node<E> newNode = new Node<>(item, tail.getNext());
            tail.setNext(newNode);
        }
        size++;
    }

    /**
     * Adding element to end of list
     * @param item item to add
     */
    public void addLast(E item) {
        addFirst(item);
        tail = tail.getNext();
    }

    /**
     * move first element to the tail
     */
    public void shift(){
        if(tail != null)
            tail = tail.getNext();
    }

    /**
     * Getting first element of list
     * @return first element
     */
    public E first(){
        E val = null;
        if(tail != null)
            val = (E)tail.getNext().getElement();
        return val;
    }

    /**
     * removing first element of list
      */
    public void removeFirst(){
        Node<E> first = tail.getNext();
        tail.setNext(first.getNext());
        first.setNext(null);
        size--;
    }
}


