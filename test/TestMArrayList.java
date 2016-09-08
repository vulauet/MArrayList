import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

/**
 * Created by vula on 9/7/16.
 */
public class TestMArrayList {

    private MArrayList<String> mal1;
    private MArrayList<Integer> mal2;
    private Set<String> testSet1;
    private Set<Integer> testSet2;
    private Set<Integer> emptySet;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        mal1 = new MArrayList<>();
        mal2 = new MArrayList<>();
        testSet1 = new HashSet<>();
        testSet2 = new HashSet<>();
        emptySet = new HashSet<>();

        testSet1.add("first");
        testSet1.add("second");
        testSet1.add("third");

        testSet2.add(100);
        testSet2.add(Integer.MAX_VALUE);
        testSet2.add(Integer.MIN_VALUE);
        testSet2.add(0);

        mal1.addAll(testSet1);
        mal2.addAll(testSet2);
    }

    @Test
    public void testAdd() {
        // valid
        Assert.assertTrue(mal1.add("test"));
        Assert.assertTrue(mal2.add(100));
        // extreme case
        Assert.assertTrue(mal1.add(""));
        Assert.assertTrue(mal2.add(Integer.MIN_VALUE));
        Assert.assertTrue(mal2.add(Integer.MAX_VALUE));
        // auto test
    }

    @Test
    public void testAddInvalid() {
        // invalid
        exception.expect(NullPointerException.class);
        mal1.add(null);
        exception.expect(NullPointerException.class);
        mal1.add(0, null);
        exception.expect(IndexOutOfBoundsException.class);
        mal1.add(1, "out of bound");

        int oldSize = mal2.size();
        Assert.assertFalse(mal2.add(Integer.MAX_VALUE + 1));
        Assert.assertFalse(mal2.add(Integer.MIN_VALUE - 1));
        Assert.assertEquals(oldSize, mal2.size());
    }


    @Test(timeout=2000)
    public void testAddAll() {
        // invalid
        exception.expect(NullPointerException.class);
        mal1.addAll(null);
        exception.expect(NullPointerException.class);
        mal2.addAll(null);
        exception.expect(NullPointerException.class);
        mal1.addAll(0, testSet1);
        exception.expect(IndexOutOfBoundsException.class);
        mal1.addAll(-1, testSet1);
        exception.expect(IndexOutOfBoundsException.class);
        mal2.addAll(mal2.size(), testSet2);
        // valid
        Assert.assertTrue(mal1.addAll(testSet1));
        Assert.assertTrue(mal1.addAll(1, testSet1));
        Assert.assertTrue(mal2.addAll(testSet2));
        Assert.assertTrue(mal2.addAll(1, testSet2));
        // extreme case
        Assert.assertTrue(mal2.addAll(emptySet));
        // auto test
    }


    @Test(timeout=2000)
    public void testClear() {
        mal1.clear();
        mal2.clear();
        // invalid

        // valid
        Assert.assertEquals(0, mal1.size());
        Assert.assertEquals(0, mal2.size());
        exception.expect(IndexOutOfBoundsException.class);
        mal1.get(0);
        exception.expect(IndexOutOfBoundsException.class);
        mal2.get(0);
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testClone() throws CloneNotSupportedException {
        mal1.addAll(testSet1);
        mal2.addAll(testSet2);
        MArrayList<String> mal3 = (MArrayList<String>) mal1.clone();
        MArrayList<Integer> mal4 = (MArrayList<Integer>) mal2.clone();
        // invalid

        // valid
        Assert.assertEquals(mal1.size(), mal3.size());
        Assert.assertEquals(mal2.size(), mal4.size());
        for (int i = 0; i < mal1.size(); i++) Assert.assertEquals(mal1.get(i), mal3.get(i));
        for (int i = 0; i < mal2.size(); i++) Assert.assertEquals(mal2.get(i), mal4.get(i));
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testEnsureCapacity() {
        // invalid

        // valid
        mal1.ensureCapacity(100);
        mal2.ensureCapacity(100);
        Assert.assertTrue(mal1.toArray().length >= 100);
        Assert.assertTrue(mal2.toArray().length >= 100);
        // extreme case

        // auto test
        Random rd = new Random();
        for (int i = 0; i < 100; i++) {
            int n = rd.nextInt(10000) + 1;
            mal1.ensureCapacity(n);
            mal2.ensureCapacity(n);
            // Check capacity
            Assert.assertTrue(mal1.toArray().length >= n);
            Assert.assertTrue(mal2.toArray().length >= n);
        }

    }

    @Test(timeout=2000)
    public void testGet() {
        // invalid
        exception.expect(IndexOutOfBoundsException.class);
        mal1.get(mal1.size());
        exception.expect(IndexOutOfBoundsException.class);
        mal2.get(mal2.size());
        // valid
        mal1.add("last element");
        Assert.assertEquals(mal1.get(mal1.size()-1), "last element");
        mal2.add(18740);
        Assert.assertEquals(mal2.get(mal2.size()-1), (Integer) 18740);
        // extreme case

        // auto test
        for (int i = 0; i < 1000; i++) {
            Integer toAdd = (int) Math.random()*mal2.size();
            mal2.add(toAdd, toAdd);
            Assert.assertEquals(mal2.get(toAdd), toAdd);
        }
    }

    @Test(timeout=2000)
    public void testIndexOf() {
        // invalid

        // valid
        if (mal1.size() < 1) mal1.addAll(testSet1);
        if (mal2.size() < 1) mal2.addAll(testSet2);
        Assert.assertEquals(-1, mal1.indexOf("fourth"));
        Assert.assertEquals(-1, mal2.indexOf("fourth"));
        Assert.assertEquals(-1, mal2.indexOf(98989));
        Assert.assertEquals(0, mal1.indexOf(mal1.get(0)));
        Assert.assertEquals(0, mal2.indexOf(mal2.get(0)));
        Assert.assertEquals(mal1.size()-1, mal1.indexOf(mal1.get(mal1.size()-1)));
        Assert.assertEquals(mal2.size()-1, mal2.indexOf(mal2.get(mal2.size()-1)));
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testLastIndexOf() {
        // invalid

        // valid
        if (mal1.size() < 1) mal1.addAll(testSet1);
        if (mal2.size() < 1) mal2.addAll(testSet2);
        Assert.assertEquals(-1, mal1.lastIndexOf("fourth"));

        mal1.add("fourth");
        mal1.add("fifth");
        mal1.add("fourth");

        Assert.assertEquals(mal1.size() - 1 , mal1.lastIndexOf("fourth"));
        Assert.assertEquals(mal1.indexOf("fifth"), mal1.lastIndexOf("fifth"));
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testIsEmpty() {
        // invalid

        // valid
        if (mal1.size() < 1) mal1.addAll(testSet1);
        if (mal2.size() < 1) mal2.addAll(testSet2);
        Assert.assertFalse(mal1.isEmpty());
        Assert.assertFalse(mal2.isEmpty());
        mal1.clear();
        mal2.clear();
        Assert.assertTrue(mal1.isEmpty());
        Assert.assertTrue(mal2.isEmpty());
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testRemove() {
        // invalid
        exception.expect(IndexOutOfBoundsException.class);
        mal1.remove(mal1.size());
        exception.expect(IndexOutOfBoundsException.class);
        mal2.remove(-1);
        // valid
        int len = mal1.size();
        mal1.add("to be removed");
        Assert.assertEquals(mal1.remove(mal1.size() - 1), "to be removed");
        Assert.assertEquals(len, mal1.size());
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testContains() {
        // invalid

        // valid
        Assert.assertFalse(mal1.contains("abcxyz"));
        Assert.assertTrue(mal1.contains("first"));
        mal1.add("abcxyz");
        Assert.assertTrue(mal1.contains("abcxyz"));
        mal1.remove(mal1.size() - 1);
        Assert.assertFalse(mal1.contains("abcxyz"));
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testRemoveAll() {
        // invalid
        exception.expect(ClassCastException.class);
        mal1.removeAll(testSet2);
        exception.expect(NullPointerException.class);
        mal1.removeAll(null);

        // extreme case

        // auto test
    }

    @Test(timeout = 2000)
    public void testRemoveAllValid() {
        mal1.removeAll(testSet1);
        for (String s : testSet1) Assert.assertFalse(mal1.contains(s));
    }

    @Test(timeout=2000)
    public void testRemoveRange() {
        // invalid
        exception.expect(IndexOutOfBoundsException.class);
        mal2.removeRange(1, mal2.size());
        exception.expect(IndexOutOfBoundsException.class);
        mal2.removeRange(-1, mal2.size()-1);
        exception.expect(IndexOutOfBoundsException.class);
        mal1.removeRange(1, mal1.size());
        exception.expect(IndexOutOfBoundsException.class);
        mal1.removeRange(mal1.size() - 1, 1);

        // valid
        if (mal1.size() < 3) mal1.addAll(testSet1);
        int oldSize = mal1.size();
        MArrayList<String> mal3 = mal1.sublist(1, mal1.size() - 1);
        mal1.removeRange(1, mal1.size() - 1);
        Assert.assertEquals(mal1.size(), oldSize - (mal1.size() - 2));


        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testRetainAll() {
        // invalid
        exception.expect(ClassCastException.class);
        mal1.retainAll(testSet2);
        exception.expect(NullPointerException.class);
        mal1.retainAll(null);

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testRetainAllValid() {
        mal1.clear();
        mal1.addAll(testSet1);
        mal1.add("to be removed");
        mal1.add("tbr");
        mal1.retainAll(testSet1);

        Assert.assertFalse(mal1.contains("to be removed"));
        Assert.assertFalse(mal1.contains("tbr"));
        Assert.assertEquals(mal1.size(), testSet1.size());
        Assert.assertTrue(mal1.containsAll(testSet1));
    }

    @Test(timeout=2000)
    public void testSet() {
        // invalid
        exception.expect(IndexOutOfBoundsException.class);
        mal1.set(mal1.size() + 1, "Should not be set");

        // valid
        String oldVal = mal1.get(mal1.size() - 1);
        int oldSize = mal1.size();

        Assert.assertEquals(mal1.set(mal1.size()-1, "Should be set"), mal1.get(mal1.size() - 1));
        Assert.assertEquals(mal1.set(mal1.size()-1, "Should return"), "Should return");
        Assert.assertFalse(mal1.contains(oldVal));
        Assert.assertEquals(mal1.size(), oldSize);
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testSize() {
        // invalid

        // valid
        MArrayList<String> mal3 = new MArrayList<>();
        Assert.assertEquals(0, mal3.size());
        try {
            mal3 = (MArrayList<String>) mal1.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(mal1.size(), mal3.size());

        mal3.add("new entry");
        Assert.assertEquals(mal1.size() + 1, mal3.size());
        mal3.clear();
        Assert.assertEquals(0, mal3.size());
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testSublist() {
        // invalid
        if (mal1.size() < 2) mal1.addAll(testSet1);
        exception.expect(IndexOutOfBoundsException.class);
        mal1.sublist(-1, mal1.size() - 1);
        exception.expect(IndexOutOfBoundsException.class);
        mal1.sublist(0, mal1.size());
        exception.expect(IllegalArgumentException.class);
        mal1.sublist(mal1.size() - 1, 0);

        // valid
        MArrayList<String> mal3 = mal1.sublist(1, mal1.size() - 1);
        Assert.assertEquals(mal3.size(), mal1.size() - 2);
        for (int i = 0; i < mal3.size(); i++)
            Assert.assertEquals(mal3.get(i), mal1.get(i + 1));


        // extreme case
        Assert.assertEquals(mal1.get(0), mal1.sublist(0,0).get(0));

        // auto test
    }

    @Test(timeout=2000)
    public void testToArray() {
        // invalid
        exception.expect(NullPointerException.class);
        mal1.toArray(null);

        Integer[] a = {0, 1, 2};

        exception.expect(ArrayStoreException.class);
        mal1.toArray(a);

        // valid
        String[] sArray = (String[]) mal1.toArray();
        for (int i = 0; i < mal1.size(); i++) Assert.assertEquals(sArray[i], mal1.get(i));


        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testTrimToSize() {
        // invalid

        // valid
        for (int i = 0; i < 1000; i++) mal2.add(i);
        int oldLength = mal2.toArray().length;
        mal2.trimToSize();

        Assert.assertEquals(mal2.size(), mal2.toArray().length);
        Assert.assertFalse(oldLength == mal2.size());

        // extreme case

        // auto test
    }

    @Test(timeout = 2000)
    public void testIterator() {
        // init
        MArrayList<String> mal3 = new MArrayList<>();
        Iterator it = mal3.iterator();

        // invalid
        Assert.assertFalse(it.hasNext());
        exception.expect(NoSuchElementException.class);
        it.next();
        exception.expect(NoSuchElementException.class);
        it.remove();

        // valid
        mal3.add("test remove");
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(mal3.get(0), it.next());
        Assert.assertFalse(it.hasNext());

        it.remove();
        Assert.assertEquals(0, mal3.size());
        exception.expect(NoSuchElementException.class);
        it.remove();
        exception.expect(NoSuchElementException.class);
        it.next();

        mal3.addAll(testSet1);
        Assert.assertTrue(it.hasNext());

        MArrayList<String> mal4 = new MArrayList<>();
        while (it.hasNext()) {
            String s = (String) it.next();
            Assert.assertTrue(mal3.contains(s));
            mal4.add(s);
        }
        Assert.assertTrue(mal3.containsAll(mal4));
        Assert.assertTrue(mal4.containsAll(mal3));
        Assert.assertTrue(mal4.containsAll(testSet1));


        // extreme case
        while (it.hasNext()) it.remove();
        Assert.assertEquals(0, mal3.size());
        exception.expect(NoSuchElementException.class);
        it.remove();

        // auto test
    }

    @Test(timeout = 2000)
    public void testListIterator() {
        MArrayList<String> mal3 = new MArrayList<>();
        ListIterator lit1 = mal3.listIterator();
        ListIterator lit2;

        // invalid
        exception.expect(NoSuchElementException.class);
        lit1.next();
        lit1.previous();
        exception.expect(IndexOutOfBoundsException.class);
        lit2 = mal3.listIterator(5);

        // valid
        Assert.assertFalse(lit1.hasNext());
        Assert.assertFalse(lit1.hasPrevious());

        mal3.addAll(testSet1);

        Assert.assertTrue(lit1.hasNext());
        Assert.assertEquals(1, lit1.nextIndex());
        Assert.assertEquals(lit1.next(), mal3.get(0));
        Assert.assertTrue(lit1.hasPrevious());
        Assert.assertEquals(0, lit1.previousIndex());
        Assert.assertEquals(lit1.previous(), mal3.get(0));
        Assert.assertEquals(lit1.next(), lit1.previous());


        for (int i = 0; i < 100; i++) mal3.add(String.valueOf(i));
        lit2 = mal3.listIterator(5);
        Assert.assertTrue(lit2.hasNext());
        Assert.assertTrue(lit2.hasPrevious());
        Assert.assertEquals(lit2.previous(), lit2.next());

        lit2.add("Should be added");
        Assert.assertEquals("Should be added", lit2.next());
        String s = (String) lit2.next();
        lit2.previous();
        lit2.set("Should be set");
        Assert.assertEquals("Should be set", s);

        Assert.assertEquals(5, lit2.previousIndex());
        Assert.assertEquals(7, lit2.nextIndex());

        // extreme
        while (lit2.hasNext()) lit2.next();
        Assert.assertEquals(lit2.nextIndex(), mal3.size());

        while (lit2.hasPrevious()) lit2.previous();
        Assert.assertEquals(lit2.previousIndex(), -1);

        // auto test
    }



}
