package MyUtils;
/**
 * Created by Alik Khilazhev on 01.09.2015.
 */
public interface List<Type> {

    /**
     * Returns size of List
     * */
    int size();

    /**
     * Returns if list is empty
     * */
    boolean isEmpty();

    /**
     * Adding value to index
     * @param item Value for add to list
     * @param i index where need to add
     * */
    void add(int i, Type item);

    /**
     * Getting value from list
     * @param i index of the value
     * */
    Type get(int i);

    /**
     * Updating value
     * @param i index of the value
     * @param item new value
     * */
    Type set(int i, Type item);

    /**
     * Removing item in index
     * @param i index of value
     * */
    Type remove(int i);
}

