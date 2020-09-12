package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KDTreeTest {
    @Test
    public void randomtest() {
        ArrayList<Point> A = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            A.add(new Point(rand.nextDouble(), rand.nextDouble()));
        }
        KDTree B = new KDTree(A);
        NaivePointSet C= new NaivePointSet(A);
        double x,y;
        for (int i = 0; i < 100; i++){
            x= rand.nextDouble();
            y= rand.nextDouble();
            //System.out.println(i+" th test input x: "+x+" y: "+y);
            assertEquals(C.nearest(x,y),B.nearest(x,y));
        }
    }

    @Test
    public void randomTimeTest() {
        ArrayList<Point> A = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            A.add(new Point(rand.nextDouble(), rand.nextDouble()));
        }
        KDTree B = new KDTree(A);
        NaivePointSet C= new NaivePointSet(A);
        double x,y;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++){
            x= rand.nextDouble();
            y= rand.nextDouble();
            B.nearest(x,y);
        }
        long end = System.currentTimeMillis();
        long kdTime=end-start;

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++){
            x= rand.nextDouble();
            y= rand.nextDouble();
            C.nearest(x,y);
        }
        end = System.currentTimeMillis();
        long naiveTime=end-start;
        assertTrue(kdTime / naiveTime < 0.1);
    }
}
