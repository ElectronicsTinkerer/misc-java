import java.util.*;

/**
 * This class stores the entries using a key-key-key-value system to make it
 * easy to locate an entry via its page, category, and section (first-, second- and third-axes)
 *
 * @author <em>Zach Baldwin</em>
 * @version 2020.05.13
 *
 * Generics: K - The first key's type
 *           S - The second key's type
 *           U - The final key's type
 *           V - The type of the value to be stored
 */
public final class TripleHashedLinkedList<K, S, U, V> { // Probably could call this a LinkedHashCube

    /** The system to store the data for our collection */
    private Hashtable<K, Hashtable<S, Hashtable<U, LinkedList<V>>>> hashCube;

    /**
     * Returns a new entryCollection with a default of 11 (See: <a href="{@link}">{@link java.util.Hashtable}</a>)
     * slots per axis per entry (with three axes)
     */
    public TripleHashedLinkedList() {
        this.hashCube = new Hashtable<K, Hashtable<S, Hashtable<U, LinkedList<V>>>>();
    }

    /**
     * Get a list of the keys in the first axis of the TripleHashedLinkedList
     *
     * @return Returns a LinkedList of the keys in the first key set
     */
    public LinkedList<K> getFirstKeySet() {
        return getKeys(hashCube);
    }

    /**
     * Get a list of the keys in the second axis of the TripleHashedLinkedList that corresponding to a first key.
     *
     * @param firstKey The first-axis key defining the second-axis key set tp be gathered
     * @return Returns a LinkedList of the keys in the second-axis key set corresponding to the input key.
     *         Returns null if there is no second-axis set corresponding to the firstKey provided.
     */
    public LinkedList<S> getSecondKeySet(K firstKey) {
        return getKeys(hashCube.get(firstKey));
    }

    /**
     * Get a list of the keys for the third axis of the TripleHashedLinkedList that corresponds to the given
     * firstKey and secondKey
     *
     * @param firstKey  The first-axis key defining the specific plane in which to look for the second-axis key
     * @param secondKey The second-axis key that defines the "line" from which to pull the third-axis keys
     * @return Returns a LinkedList of the keys for the third-axis set corresponding to the provided first- and
     *         second-axes provided. Returns null if the first or second keys are not in the TripleHashedLinkedList
     */
    public LinkedList<U> getThirdKeySet(K firstKey, S secondKey) {

        // If the first key does not exist, return null
        if (hashCube.get(firstKey) == null)
            return null;

        // Otherwise, return the second key's line
        return getKeys(hashCube.get(firstKey).get(secondKey));
    }

    /**
     * Get the list of elements corresponding to a provided key triplet
     *
     * @param firstKey  The first-axis key corresponding to an element list
     * @param secondKey The second-axis key corresponding to an element list
     * @param thirdKey  The third-axis key corresponding to an element list
     * @return Returns the LinkedList storing the elements that the provided key triplet corresponds to.
     *         Returns null if any of the three keys are not in the TripleHashedLinkedList or if
     */
    public LinkedList<V> getElementList(K firstKey, S secondKey, U thirdKey) {

        // If the first or second key does not exist, return null
        if (hashCube.get(firstKey) == null || hashCube.get(firstKey).get(secondKey) == null)
            return null;

        // Return the LinkedList
        return hashCube.get(firstKey).get(secondKey).get(thirdKey);
    }

    /**
     * Add a value to the TripleHashedLinkedList at the provided key triplet position
     * This will not overwrite an element at that position, it will just "chain" them together
     *
     * @param firstKey  The first-axis key corresponding to an element
     * @param secondKey The second-axis key corresponding to an element
     * @param thirdKey  The third-axis key corresponding to an element
     * @param element   The element to be added to the front of the LinkedList at the key triplet's location
     */
    public void put(K firstKey, S secondKey, U thirdKey, V element) {

        // Only generate the sub-axes if needed
        hashCube.computeIfAbsent(firstKey, k -> new Hashtable<>());

        hashCube.get(firstKey).computeIfAbsent(secondKey, k -> new Hashtable<>());

        hashCube.get(firstKey).get(secondKey).computeIfAbsent(thirdKey, k -> new LinkedList<>());

        // Add the element
        hashCube.get(firstKey).get(secondKey).get(thirdKey).addFirst(element);
    }

    /**
     * Removes the entire collision list from the TripleHashedLinkedList at the specified key triplet,
     * removes the third-axis key for the collision list, and returns that list
     *
     * @param firstKey  The first-axis key corresponding to an element collision list
     * @param secondKey The second-axis key corresponding to an element collision list
     * @param thirdKey  The third-axis key corresponding to an element collision list
     * @return Returns the entire collision list corresponding to the provided key triplet.
     *         Returns null if that set of keys does not correspond to a collision list.
     */
    public LinkedList<V> remove(K firstKey, S secondKey, U thirdKey) {

        LinkedList<V> returnList = getElementList(firstKey, secondKey, thirdKey);

        // If the list was not null, make it null
        if (returnList != null)
            hashCube.get(firstKey).get(secondKey).remove(thirdKey);

        return returnList;
    }

    /**
     * Gets the keys for the provided Hashtable and returns them in a LinkedList
     *
     * @param hashTable The Hashtable to pull keys from
     * @return Returns a LinkedList of the keys for the provided Hashtable
     *         If the provided Hashtable is null, returns null.
     */
    private <T> LinkedList<T> getKeys(Hashtable<T, ?> hashTable) {

        // Check to make sure the provided hashtable is not null
        if (hashTable == null)
            return null;

        // Generate the LinkedList
        Enumeration<T> keys = hashTable.keys();
        LinkedList<T> keyList = new LinkedList<>();
        while (keys.hasMoreElements())
            keyList.addLast((keys.nextElement()));
        return keyList;
    }

//    /**
//     * This method returns an Interator, as required by the Iterable Interface. It iterates over the
//     * entries in the TripleHashedLinkedList. <b>Note:</b> The order is undefined.
//     *
//     * @return an iterator for the linked list
//     */
//    public Iterator<V> iterator() {
//        return new Iterator<V>() {
//
//            /* Stores the Iterator of first-axis keys */
//            private Iterator<K> firstKeySet = getFirstKeySet().iterator();
//            private K currentFirstKey;
//
//            /* Stores the Iterator of second-axis keys */
//            private Iterator<S> secondKeySet;
//
//            /* Stores the Iterator of third-axis keys */
//            private Iterator<U> thirdKeySet;
//
//            /* Stores the iterator of the collision lists */
//            private Iterator<V> collisionListIterator;
//
//            public boolean hasNext() {
//
//                if (!firstKeySet.hasNext()) {
//                    return false;
//                }
//                else {  // There is another element in the first axis
//
//                    if (secondKeySet == null)
//
//
//                }
//            }
//
//            public V next() {
//                return new V();
//            }
//        };
//    }
}
