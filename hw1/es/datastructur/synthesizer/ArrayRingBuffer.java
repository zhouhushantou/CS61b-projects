package es.datastructur.synthesizer;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int cap;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb=(T[])new Object[capacity];
        first=0;
        last=0;
        fillCount=0;
        cap=capacity;
    }

    @Override
    public boolean equals(Object o){
        ArrayRingBuffer<T> other=  (ArrayRingBuffer<T>) o;
        if (other.fillCount()!=fillCount()){
            return false;
        }
       int i=first;
               while(i<last) {
                   if (!rb[i].equals(other.rb[i])) {
                       return false;
                   }
                   i++;
                   if (i==cap){
                       i=0;
                   }
               }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new myiterator();
    }

    private class myiterator implements Iterator<T>{
        public int i;
        public myiterator(){
            i=first;
        }
        public boolean hasNext(){
            return !(i==last);
        }
        public T next(){
            T returnItem=rb[i];
            i++;
            if (i==cap){
                i=0;
            }
            return returnItem;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if(isFull()){
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last]=x;
        last++;
        fillCount++;
        if (last==cap){
            last=0;
        }
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()){
            return null;
        }
        T x=rb[first];
        first++;
        if (first==cap){
            first=0;
        }
        fillCount--;
        return x;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()){
            return null;
        }
        return rb[first];
    }

    @Override
    public int capacity(){
        return cap;
    }

    @Override
    public int fillCount(){
        return fillCount;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
