import java.util.*;

/**
 * @author Alik Khilazhev
 * @email a.khilazhev@innopolis.ru
 * @date 20.10.15
 * In accordance with the academic honor, I (Alik Khilazhev) certify that
 * the answers here are my own work without copying of others and
 * solutions from Internet or other sources."
 */
public class LinkedHashMap<Key,Val> implements Map<Key,Val> {

    class Entry<Key,Val> implements Map.Entry<Key,Val>{
        private Key key;
        private Val value;
        private int hash;
        public Entry(Key k, Val val, int hash){
            key = k;
            value = val;
            this.hash = hash;
        }

        @Override
        public Key getKey() {
            return key;
        }

        @Override
        public Val getValue() {
            return value;
        }

        @Override
        public void setValue(Val val) {
            value = val;
        }

        @Override
        public  int hashCode(){
            return hash;
        }
    }


    private int CAPACITY;
    private int size;

    public LinkedHashMap(int capacity){
        CAPACITY = capacity;
        entries = new LinkedList[CAPACITY];
    }

    public LinkedHashMap(){
        this(16127);
    }

    private LinkedList<Entry<Key,Val>>[] entries;

    int getHash(Object k){
        return k.hashCode() % CAPACITY;
    }

    @Override
    public Val get(Key k) {
        int hash = getHash(k);
        List<Entry<Key,Val>> entriesList = entries[hash];
        Iterator<Entry<Key,Val>> iterator = entriesList.iterator();

        while (iterator.hasNext()){
            Entry<Key,Val> cur = iterator.next();
            if(cur.getKey().equals(k))
                return cur.getValue();
        }
        return  null;
    }

    @Override
    public void put(Key k, Val v) {

        int hash = getHash(k);
        List<Entry<Key,Val>> entriesList = entries[hash];

        Entry<Key,Val> entry = new Entry<>(k, v, hash);
        if(entriesList == null)
            entriesList = new LinkedList<>();
        if(entriesList.isEmpty()) {
            entriesList.add(entry);
            size++;
            return;
        }
       Iterator<Entry<Key,Val>> iterator = entriesList.iterator();
        while(iterator.hasNext()){
            Entry cur = iterator.next();
            if(cur.key.equals(k)) {
                cur.setValue(v);
                return;
            }
        }
        entriesList.add(entry);
        size++;
        return;
    }

    @Override
    public Val remove(Key k) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Set<Map.Entry<Key, Val>> entrySet() {
        return null;
    }

    @Override
    public Set<Key> keySet() {
        return null;
    }

    @Override
    public Collection<Val> values() {
        return null;
    }
}
