package es.datastructur.synthesizer;
import org.junit.Test;
/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void SomeTest() {
        ArrayRingBuffer<Double> x = new ArrayRingBuffer<>(5);
        x.enqueue(33.1);
        x.enqueue(44.8);
        x.enqueue(62.3);

        ArrayRingBuffer<Double> y = new ArrayRingBuffer<>(5);
        y.enqueue(33.1);
        y.enqueue(44.8);
        y.enqueue(44.8);
        boolean k=x.equals(y);
        System.out.println(k);
        double expected=33.1;
        double s=x.dequeue();
        org.junit.Assert.assertEquals(expected,s,0.01);
    }
}
