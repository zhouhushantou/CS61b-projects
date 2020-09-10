import bearmaps.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int size=0;
    private ArrayList<Node> AL=new ArrayList<>(10);
    private Set<T> key=new HashSet<>();
    public class Node{
        public T item;
        public double priority;
        public Node(T item, double priority){
            this.item=item;
            this.priority=priority;
        }
    }
    private int leftChild(int k){
        if ((2*k)<size)
            return 2*k;
        else
            return -1;  //there is no left child
    }

    private int rightChild(int k){
        if ((2*k+1)<size)
        return 2*k+1;
        else
            return -1;  //there is no right child
    }

    private int parent(int k){
        if((k/2)>0)
           return k/2;
        else
            return -1;
    }
    @Override
    public void add(T item, double priority) {
        //if it is already in the queue, throw a warning
         if (contains(item))
             throw new IllegalArgumentException("Warning: This item is already in the queue.");
         // add to the key set
        key.add(item);
         //add to the last
        size++;
        AL.add(size,new Node(item,priority));
        //pop up the queue
        int i=size;
        Node temp;
        while (i>0){
            if (parent(i)>0){
                if(AL.get(parent(i)).priority>priority) //if the parent node have smaller priority
                {
                    //exchange the parent and child
                    temp =AL.get(i);
                    AL.set(i,AL.get(parent(i)));
                    AL.set(parent(i),temp);
                }
            }
            i=parent(i);
        }
    }

    @Override
    public boolean contains(T item) {
        return key.contains(item);
    }

    @Override
    public T getSmallest() {
        T result=(T) new Object();
        return result;
    }

    @Override
    public T removeSmallest() {
        T result=(T) new Object();
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(Object item, double priority) {

    }
    public static void main(String[] args) {
        ArrayHeapMinPQ<String> A=new ArrayHeapMinPQ<>();
        A.add("hi",2);
        A.add("ti", 1);
    }
}
