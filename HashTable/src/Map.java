import java.util.Collection;

/**
 * Created by Alik Khilazhev on 06.10.2015.
 */
public interface Map<Key,Val> {
    Val get(Key k);
    void put(Key k, Val v);
    Val remove(Key k);
    int size();
    boolean isEmpty();
   // Collection entrySet();
   // Collection keySet();
   // Collection values();
}
