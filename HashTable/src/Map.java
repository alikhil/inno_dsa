import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Alik Khilazhev on 06.10.2015.
 */

/**
 * Interface of map
 * @param <Key> Type of key for access value
 * @param <Val> Type of value storing in map
 */
public interface Map<Key,Val> {

    /**
     * Interface of entry storing in map. Consist of key and value
     * @param <Key> Type of key
     * @param <Val> Type of value
     */
    interface Entry<Key,Val>{
        /**
         * Getting key of entry
         * @return key of entry
         */
        Key getKey();

        /**
         * Getting value of entry
         * @return value of entry
         */
        Val getValue();

        /**
         * Setting value of entry
         * @param val value to set
         */
        void setValue(Val val);

        /**
         * Getting hash code of entry
         * @return hash code of entry
         */
        int hashCode();
    }

    /**
     * Getting value by key
     * @param k key
     * @return value for matching key
     */
    Val get(Key k);

    /**
     * Inserting entry with key and value
     * @param k key
     * @param v value
     */
    void put(Key k, Val v);

    /**
     * Removing entry from map by a key and returning value
     * @param k key
     * @return value
     */
    Val remove(Key k);

    /**
     * Getting count of entries in map
     * @return count of entries
     */
    int size();

    /**
     * Checking if map is empty
     * @return true if empty, otherwise false
     */
    boolean isEmpty();

    /**
     * Getting set of entries in map
     * @return set of entries
     */
    Set<Entry<Key,Val>> entrySet();

    /**
     * Getting set of keys in map
     * @return set of key
     */
    Set<Key> keySet();

    /**
     * Getting collection of values storing in map
     * @return Collection of values
     */
    Collection<Val> values();
}
