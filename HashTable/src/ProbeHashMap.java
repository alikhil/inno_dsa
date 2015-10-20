import java.util.*;

/**
 * Created by Alik Khilazhev on 06.10.2015.
 */
public class ProbeHashMap<Key,Val> implements Map<Key,Val> {

    public int size;

    public ProbeHashMap(int capacity){
        CAPACITY = capacity;
        entries = new Entry[capacity];
    }

    public ProbeHashMap(){
        this(16127);
    }

    private int CAPACITY;
    private int probeStep = 13;
    private int deletedFlag = -1;
    private Entry<Key,Val>[] entries;

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

    int getHash(Object k){
        return k.hashCode() % CAPACITY;
    }

    @Override
    public Val get(Key k) {
        int hash = getHash(k);
        if(entries[hash] != null)
            return entries[hash].value;
        return  null;
    }

    @Override
    public void put(Key k, Val v) {
        Entry<Key,Val> entry = new Entry<>(k, v, getHash(k));
        if(entries[entry.hash] == null || entries[entry.hash].key.equals(k) || entries[entry.hash].hash == deletedFlag) {
            if(entries[entry.hash] == null || entries[entry.hash].hash == -1)
                size++;
            entries[entry.hash] = entry;
        }
        else {
            int iter = entry.hash + probeStep;
            while(entries[iter % CAPACITY] != null){
                iter+= probeStep;
            }
            entries[iter % CAPACITY] = entry;
            size++;
        }

    }

    @Override
    public Val remove(Key k) {
        int hash = getHash(k);
        Val result = null;
        if(entries[hash] != null)
            result = entries[hash].value;
        entries[hash] = new Entry<>(null,null, deletedFlag);
        size--;
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Set<Key> keySet() {
        Set<Key> keys = new HashSet<>();
        for(Entry<Key,Val> entry: entries){
            if(entry != null && entry.hashCode() != deletedFlag)
                keys.add(entry.getKey());
        }
        return keys;
    }

    @Override
    public Collection<Val> values() {
        List<Val> values = new ArrayList<>();
        for(Entry<Key,Val> entry: entries){
            if(entry != null && entry.hashCode() != deletedFlag)
                values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<Key,Val>> entrySet() {
        Set<Map.Entry<Key,Val>> entrySet = new HashSet<>();
        for(Entry<Key,Val> entry: entries){
            if(entry != null && entry.hashCode() != deletedFlag)
                entrySet.add(entry);
        }
        return entrySet;
    }
}
