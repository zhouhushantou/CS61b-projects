import edu.princeton.cs.algs4.Queue;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("dfd");
        tas.enqueue("Ptar");
        tas.enqueue("Itai");
        QuickSort A=new QuickSort();
        Assert.assertTrue(isSorted(A.quickSort(tas)));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("dfd");
        tas.enqueue("Ptar");
        tas.enqueue("Itai");
        MergeSort A=new MergeSort();
        Assert.assertTrue(isSorted(A.mergeSort(tas)));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
