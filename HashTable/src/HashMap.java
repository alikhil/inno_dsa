import java.util.Collection;
import java.util.Objects;

/**
 * Created by Alik Khilazhev on 06.10.2015.
 */
public class HashMap<Key,Val> implements Map<Key,Val> {

    public int size;

    public HashMap(int capacity){
        CAPACITY = capacity;
        entries = new Entry[capacity];
    }

    public HashMap(){
        this(16127);
    }

    private int CAPACITY;
    private int probeStep = 13;

    private Entry<Key,Val>[] entries;

    class Entry<Key,Val>{
        public Key key;
        public Val value;
        public int hash;
        public Entry(Key k, Val val, int hash){
            key = k;
            value = val;
            this.hash = hash;
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
        if(entries[entry.hash] == null || entries[entry.hash].key.equals(k) || entries[entry.hash].hash == -1) {
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
        entries[hash] = new Entry<>(null,null,-1);
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

    public Entry[] entrySet() {
        Entry[] newEntries = new Entry[size()];
        int i = 0;
        for(Entry entry: entries){
            if(entry != null && entry.hash != -1)
                newEntries[i++] = entry;
        }
        return newEntries ;
    }

    public Collection keySet() {
        return null;
    }

    public Collection values() {
        return null;
    }




}
