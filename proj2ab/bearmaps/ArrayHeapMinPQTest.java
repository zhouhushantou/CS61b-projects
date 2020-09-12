package bearmaps;

import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
     public void timetest() {
        ArrayHeapMinPQ<String> A = new ArrayHeapMinPQ<>();
        Random rand = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++)
            A.add("hi" + i, rand.nextInt(301));
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start)/1000.0 +  " seconds.");
    }
}
