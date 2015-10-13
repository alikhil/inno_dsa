import java.io.*;
import java.util.Iterator;

/**
 * Created by Alik Khilazhev on 15.09.2015.
 */
class Main {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test.in"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("test.out"));

            int N = Integer.parseInt(reader.readLine());
            String[] inputs = reader.readLine().split(" ");
            CircularLinkedList<Integer> list = new CircularLinkedList<>();
            for(int i = 0;i < inputs.length;i++){
                Integer cur = Integer.parseInt(inputs[i]);
                list.addLast(cur);
            }

            /*
            list.remove(N);
            list.remove(0);
            list.remove(3);
            */
            Iterator<Integer> iterator = list.iterator(true);
            int i = 1;
            while (!list.isEmpty()) {
                writer.write(iterator.next().toString());
                if(i++ % 2 == 0 )
                    iterator.remove();
            }
            /*
            int M = Integer.parseInt(reader.readLine());
            int I = Integer.parseInt(reader.readLine());
            String ciphertext = reader.readLine();
            String text = reader.readLine();
            SuperCryptor cryptor = new SuperCryptor(N, M, I, ciphertext, text);
            CircularLinkedList<Character> list = cryptor.decrypt();

            Iterator<Character> iterator = list.iterator(false);
            while (iterator.hasNext())
                writer.write(iterator.next().toString());
            writer.write("\n");
            iterator = cryptor.encrypt(list).iterator(false);
            while (iterator.hasNext())
                writer.write(iterator.next().toString());
            */
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

class SuperCryptor{


    public SuperCryptor(int n, int m, int i, String encrypted, String decryptedPart){
        N = n;
        M = m;
        I = i;
        this.encrypted = encrypted;
        this.decryptedPart = decryptedPart;
    }

    public CircularLinkedList<Character> decrypt(){
        foundPermutation = false;
        int []array = new int[M];
        for(int i = 0;i < M;i++)
            array[i] = i;
        permutation(array, 0, M);
        CircularLinkedList<Character> decryptedList = new CircularLinkedList<>();
        for(int i = 0;i < N;i++){
            decryptedList.addLast(encrypted.charAt(keyArray[i % M ]+ ((i / M) * M)));
        }
        return decryptedList;
    }

    public CircularLinkedList<Character> encrypt(CircularLinkedList<Character> list){
        Iterator<Character> iterator = list.iterator(true);
        CircularLinkedList<Character> newEncrypted = new CircularLinkedList<>();
        int cnt = 1;

        while (!list.isEmpty()){
            for(int i = 1;i < M - cnt;i++)
                iterator.next();
            newEncrypted.addLast(iterator.next());
            iterator.remove();
            cnt = 2;
        }
        return  newEncrypted;
    }

    private int N;
    private int M;
    private int I;

    private String encrypted;
    private String decryptedPart;
    private Boolean foundPermutation;
    private int[] keyArray;

    private void permutation(int a[], int i, int n)
    {
        if(!foundPermutation) {
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
            }
            else {
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

    class CircularLinkedListItertator<E> implements Iterator<E> {

        private Node current;
        private int iter;
        private boolean cyclical;
        public CircularLinkedListItertator(boolean cyclical){
            current = getNode(0);
            iter = -1;
            this.cyclical = cyclical;
        }

        @Override
        public boolean hasNext() {
            return (cyclical && current.getNext() != null) || (!cyclical && iter < size());
        }

        @Override
        public E next() {
            iter++;
            if(iter != 0)
                current = current.getNext();
            return (E) current.getElement();
        }

        @Override
        public void remove() {
            CircularLinkedList.this.remove(current);
        }
    }
    private int size;
    private Node header;
    private Node tail;
    public CircularLinkedList() {
        header = new Node(null, null);
        tail = new Node(null, null);
    }

    public Iterator<E> iterator(boolean cyclical){
        return new CircularLinkedListItertator<>(cyclical);
    }

    private Node<E> getNode(int i) {
        i = (size() + i) % size();
        Node<E> current = header;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }
        return current;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void addFirst(E item) {
        Node<E> newNode = new Node<>(item, header);
        if (tail.getElement() == null) {
            tail = newNode;
        }
        header = newNode;
        size++;
    }

    public void add(int i, E item) {
        i = (size() + i) % size();
        if (i == 0)
            addFirst(item);
        else {
            Node<E> cur = getNode(i);
            Node<E> newNode = new Node(item, cur);
            getNode(i-1).setNext(newNode);
            size++;
        }
    }

    public void addLast(E item) {
        if(isEmpty())
            addFirst(item);
        else {
            Node<E> newNode = new Node<>(item, header);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    public E get(int i) {
        i = (size() + i) % size();
        return getNode(i).getElement();
    }

    public E set(int i, E item) {
        i = (size() + i) % size();
        E oldVal = get(i);
        getNode(i).setElement(item);
        return oldVal;
    }

    public E remove(int i) {
        i = (size() + i) % size();
        Node<E> prevNode = getNode(i - 1);
        Node<E> curNode = prevNode.getNext();
        prevNode.setNext(curNode.getNext());
        if(curNode == header)
            header = curNode.getNext();
        if(curNode == tail)
            tail = prevNode;
        size--;
        return curNode.getElement();
    }
    public void remove(Node<E> node){
        Node<E> cur = header;
        int i = 0;
        while (node != cur){
            node = node.getNext();
            i++;
        }
        Node<E> prev = getNode(i - 1);
        prev.setNext(node.getNext());
    }
}



