import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by vula on 9/7/16.
 */
public class MArrayList<Item> implements Iterable<Item>, Collection<Item>, Cloneable {
    private Item[] myArray;
    private int length;

    public MArrayList() {
        myArray = (Item[]) new Object[1];
        length = 0;
    }

    MArrayList(Item[] src, int size) {
        this.myArray = (Item[]) new Object[size];
        this.length = size;
        for (int i=0; i < size; i++)
            myArray[i] = src[i];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i=0; i<this.length; i++)
            copy[i] = myArray[i];
        myArray = copy;
    }

    private void shiftRight(int index, int shift_length) {
        if (this.length + shift_length > myArray.length) resize(this.length + shift_length);
        for (int idx = this.length + shift_length - 1; idx > index; idx--)
            myArray[idx] = myArray[idx-shift_length];
    }

    public boolean add(Item i) {
        if (i == null) throw new NullPointerException("Fail to add null item");
        if (this.length == myArray.length) resize(this.length*2);
        myArray[this.length++] = i;
        length++;
        return contains(i);
    }

    public void add(int index, Item i) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        if (i == null) throw new NullPointerException("Fail to add null item");
        if (this.length == myArray.length) resize(this.length*2);
        for (int idx = this.length; idx > index; idx--)
            myArray[idx] = myArray[idx-1];
        length++;
        myArray[index] = i;
    }

    public boolean addAll(Collection<? extends Item> c) {
        if (c == null) throw new NullPointerException("Fail to add null collection");
        for (Item i : c)
            add(i);
        length+=c.size();
        return true;
    }

    public boolean addAll(int index, Collection<? extends Item> c) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        if (c == null) throw new NullPointerException("Fail to add null collection");
        shiftRight(index, c.size());
        for (Item i : c)
            myArray[index++] = i;
        length+=c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {
        myArray = (Item[]) new Object[1];
        length = 0;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public void ensureCapacity(int minCapacity) {
        if (myArray.length < minCapacity) resize(minCapacity);
    }

    public Item get(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        return myArray[index];
    }

    public int indexOf(Object o) {
        return -1;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int lastIndexOf(Object o) {
        for (int i = length-1; i >= 0; i--) {
            if (myArray[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public Item remove(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        Item result = myArray[index];
        for (int i=index; i < length-1; i++)
            myArray[i] = myArray[i+1];
        myArray[--this.length] = null;
        return result;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < length; i++) {
            if (myArray[i].equals(o)) {
                remove(i);
            }
        }
        --length;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean removeRange(int fromIndex, int toIndex) {

        return true;
    }


    public Item set(int index, Item item) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        return null;
    }

    public int size() {
        return length;
    }

    public MArrayList<Item> sublist(int fromIndex, int toIndex) {
        Item[] sl = (Item[]) new Object[toIndex - fromIndex];
        for (int i=fromIndex; i<toIndex; i++) sl[i - fromIndex] = myArray[i];
        return new MArrayList<Item>(sl, sl.length);
    }

    public Object[] toArray() {
        return this.myArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) throw new NullPointerException("Fail to add null item");
        if (!myArray.getClass().isInstance(a.getClass()))
            throw new ArrayStoreException("The runtime type of the specified array is not a supertype of the runtime type of every element in this list");

        if (a.length < this.length) a = (T[]) new Object[this.length];
        for (int i = 0; i < this.length; i++) {
            a[i] = (T) myArray[i];
        }
        return a;
    }

    public  void trimToSize() {
        resize(myArray.length);
    }


    @Override

    public Iterator<Item> iterator() {
        return null;
    }
}
