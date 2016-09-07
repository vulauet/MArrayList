import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
    }

    @Test
    public void testAdd() {
        // invalid
        exception.expect(NullPointerException.class);
        mal1.add(null);
        exception.expect(NullPointerException.class);
        mal1.add(0, null);
        exception.expect(IndexOutOfBoundsException.class);
        mal1.add(1, "out of bound");
        Assert.assertFalse(mal2.add(Integer.MAX_VALUE + 1));
        Assert.assertFalse(mal2.add(Integer.MIN_VALUE - 1));

        // valid
        Assert.assertTrue(mal1.add("test"));
        Assert.assertTrue(mal2.add(100));
        // extreme case
        Assert.assertTrue(mal1.add(""));
        Assert.assertTrue(mal2.add(Integer.MIN_VALUE));
        Assert.assertTrue(mal2.add(Integer.MAX_VALUE));
        // auto test

    }


    @Test(timeout=2000)
    public void testAddAll() {
        // invalid
        exception.expect(NullPointerException.class);
        mal1.addAll(null);
        exception.expect(NullPointerException.class);
        mal2.addAll(null);
//        exception.expect(IndexOutOfBoundsException.class);
//        exception.expect(IndexOutOfBoundsException.class);
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
        for (int i = 0; i < mal1.size(); i++) {
            Assert.assertEquals(mal1.get(i), mal3.get(i));
        }

        for (int i = 0; i < mal2.size(); i++) {
            Assert.assertEquals(mal2.get(i), mal4.get(i));
        }
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testEnsureCapacity() {
        // invalid

        // valid

        // extreme case

        // auto test
        Random rd = new Random();
        for (int i = 0; i < 100; i++) {
            int n = rd.nextInt(10000) + 1;
            mal1.ensureCapacity(n);
            mal2.ensureCapacity(n);
            // Check capacity
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

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testIsEmpty() {
        // invalid

        // valid
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
        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testRemoveAll() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testRemoveRange() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testRetainAll() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testSet() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testSize() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testSublist() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testToArray() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

    @Test(timeout=2000)
    public void testTrimToSize() {
        // invalid

        // valid

        // extreme case

        // auto test
    }

}
