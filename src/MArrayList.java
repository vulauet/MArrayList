import java.util.*;

/**
 * Created by vula on 9/7/16.
 */
public class MArrayList<Item> extends AbstractList<Item> implements Iterable<Item>, Collection<Item>, Cloneable {
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
        System.arraycopy(myArray, 0, copy, 0, this.length);
        myArray = copy;
    }

    private void shiftRight(int index, int shift_length) {
        if (this.length + shift_length > myArray.length) resize(this.length + shift_length);
        System.arraycopy(myArray, index + 1 - shift_length, myArray, index + 1, this.length + shift_length - 1 - index);
    }

    @Override
    public boolean add(Item i) {
        if (i == null) throw new NullPointerException("Fail to add null item");
        if (this.length == myArray.length) resize(myArray.length*2);
        myArray[this.length++] = i;
        return contains(i);
    }

    @Override
    public void add(int index, Item i) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        if (i == null) throw new NullPointerException("Fail to add null item");
        if (this.length == myArray.length) resize(this.length*2);
        System.arraycopy(myArray, index, myArray, index + 1, this.length - index);
        this.length++;
        myArray[index] = i;
    }

    @Override
    public boolean addAll(Collection<? extends Item> c) {
        ensureCapacity(c.size());
        for (Item i : c) add(i);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Item> c) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        if (c == null) throw new NullPointerException("Fail to add null collection");
        ensureCapacity(c.size() + this.length);
        shiftRight(index, c.size());
        for (Item i : c)
            myArray[index++] = i;
        this.length+=c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (this.size() == 0 || c.size() == 0) return true;
        for (Object o : c)
            if (myArray[0].getClass()!=o.getClass())
                throw new ClassCastException("Incompatible input type");
        for (Object o : c)
            if (contains(o)) remove(o);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (this.size() == 0) return true;
        for (Object o : c)
            if (myArray[0].getClass()!=o.getClass())
                throw new ClassCastException("Incompatible input type");
        for (int i = 0; i < length; i++) {
            if (!c.contains(myArray[i])) {
                remove(i);
                --i;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        myArray = (Item[]) new Object[1];
        this.length = 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
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

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; i++)
            if (myArray[i].equals(o)) return i;
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length-1; i >= 0; i--)
            if (myArray[i].equals(o)) return i;
        return -1;
    }

    @Override
    public Item remove(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        Item result = myArray[index];
        for (int i=index; i < length-1; i++)
            myArray[i] = myArray[i+1];
        myArray[--this.length] = null;
        return result;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < length; i++) if (myArray[i].equals(o)) remove(i);
        --length;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) if (!contains(o)) return false;
        return true;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= size() || toIndex > size() || toIndex < fromIndex)
            throw new IndexOutOfBoundsException("Index out of bounds");
        System.arraycopy(myArray, fromIndex + (toIndex - fromIndex), myArray, fromIndex, size() - (toIndex - fromIndex) - fromIndex);
        this.length = this.length - (toIndex - fromIndex);
    }


    @Override
    public Item set(int index, Item item) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index out of bounds");
        return null;
    }

    public int size() {
        return length;
    }

    public MArrayList<Item> sublist(int fromIndex, int toIndex) {
        Item[] sl = (Item[]) new Object[toIndex - fromIndex];
        System.arraycopy(myArray, fromIndex, sl, 0, toIndex - fromIndex);
        return new MArrayList<>(sl, sl.length);
    }

    @Override
    public Object[] toArray() {
        return this.myArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (!myArray.getClass().isInstance(a.getClass()))
            throw new ArrayStoreException("The runtime type of the specified array is not a supertype of the runtime type of every element in this list");

        if (a.length < this.length) a = (T[]) new Object[this.length];
        for (int i = 0; i < this.length; i++) a[i] = (T) myArray[i];
        return a;
    }

    public void trimToSize() {
        resize(this.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (int i = 0; i < size() - 1; i++) sb.append(myArray[i].toString()).append(", ");
        sb.append(myArray[myArray.length - 1].toString());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Iterator<Item> iterator() {
        return new MALIterator();
    }

    @Override
    public ListIterator<Item> listIterator() {
        return new MALListIterator();
    }

    @Override
    public ListIterator<Item> listIterator(int index) {
        System.out.println(index);
        if (index >= length || index < 0) throw new IndexOutOfBoundsException("Index out of bounds");
        return new MALListIterator(index);
    }

    private class MALIterator implements Iterator<Item> {

        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Iterate in an empty array list");
            return myArray[count++];
        }

        @Override
        public void remove() {
            if (!hasNext() || count < 1) throw new NoSuchElementException("No element to remove");
            MArrayList.this.remove(--count);
        }
    }

    private class MALListIterator implements ListIterator<Item> {

        private int curIndex;

        public MALListIterator() {
            curIndex = 0;
        }

        public MALListIterator(int index) {
            curIndex = index;
        }

        @Override
        public boolean hasNext() {
            return curIndex < length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Iterate in an empty array list");
            return myArray[curIndex++];
        }

        @Override
        public boolean hasPrevious() {
            return curIndex > 0;
        }

        @Override
        public Item previous() {
            if (!hasPrevious()) throw new NoSuchElementException("Iterate in an empty array list");
            return myArray[curIndex--];
        }

        @Override
        public int nextIndex() {
            if (hasNext())
                return curIndex+1;
            return length;
        }

        @Override
        public int previousIndex() {
            if (hasPrevious()) return curIndex - 1;
            else return -1;
        }

        @Override
        public void remove() {
            MArrayList.this.remove(--curIndex);
        }

        @Override
        public void set(Item item) {
            MArrayList.this.set(curIndex, item);
        }

        @Override
        public void add(Item item) {
            MArrayList.this.add(curIndex, item);
        }
    }
}

/* TODO
- iterator
- removeRange
- containsAll
- indexOf
- retainAll
- removeAll
- listIterator
 */