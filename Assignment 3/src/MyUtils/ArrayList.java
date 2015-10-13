package MyUtils;
/**
 * Created by Alik Khilazhev on 01.09.2015.
 */
public class ArrayList<Type> implements List<Type> {
    public static final int CAPACITY = 32;
    private Type[] data;
    private int size = 0;

    public ArrayList(){
        this(CAPACITY);
    }

    public ArrayList(int capacity){
        data = (Type[]) new Object[capacity];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size > 0;
    }

    public Type get(int i) {
        return data[i];
    }

    public Type set(int i, Type item){
        Type temp = data[i];
        data[i] = item;
        return temp;
    }

    public void add(int i, Type item){
        for(int j = size;j >= i + 1;j--){
            data[j] = data[j - 1];
        }
        data[i] = item;
        size++;
    }
    public Type remove(int i){
        Type temp = data[i];
        for(int j = i; j < size;j++){
            data[j] = data[j + 1];
        }
        size--;
        return temp;
    }
}
