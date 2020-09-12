package bearmaps;

import bearmaps.ExtrinsicMinPQ;
import bearmaps.PrintHeapDemo;import bearmaps.TimingTestDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int size=0;
    private ArrayList<Node> AL;
    private Map<T,Integer> key=new HashMap<>();
    //constructor
    public ArrayHeapMinPQ(){
        AL=new ArrayList<>();
        AL.add(new Node((T)"root",-1));
    }
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

         //add to the last
        size++;
        AL.add(new Node(item,priority));
        popup(size);
        // add to the key set
        key.put(item,size);
    }

    private void popup(int j){
        //pop up the queue
        int i=j;
        Node temp;
        while (i>0){
            if (parent(i)>0){
                if(AL.get(parent(i)).priority>AL.get(i).priority) //if the parent node have smaller priority
                {
                    //exchange the parent and child
                    swap(i,parent(i));
                }
            }
            i=parent(i);
        }
    }

    private void falldown(int j) {
        //falldown the queue
        int i = j;
        Node temp;
        int left, right;
        while (i <=size) {
            left = leftChild(i);
            right = rightChild(i);
            //if there is no child
            if ((left == -1) && (right == -1))
                return;

            //if there is only left child
            if (right == -1) {
                if (AL.get(left).priority>AL.get(i).priority)
                    return;
                swap(i,left);
                i = left;
                continue;
            }
            //if there is only right child
            if (left == -1) {
                if (AL.get(right).priority>AL.get(i).priority)
                    return;
                swap(i,right);
                i = right;
                continue;
            }
            //if both children are with higher priority then it
            if ((AL.get(left).priority>AL.get(i).priority) && (AL.get(right).priority>AL.get(i).priority) )
                return;
            //if there are two children
            if (AL.get(left).priority > AL.get(right).priority) //if right node is smaller
            {
                //exchange the parent and child
                swap(i,right);
                i = right;
                continue;
            }
            else//if left node is smaller
            {
                //exchange the parent and child
                swap(i,left);
                i = left;
                continue;
            }
        }
    }

    private void swap(int i,int j){
        //swap two node
        key.put(AL.get(i).item,j);
        key.put(AL.get(j).item,i);
        Node temp = AL.get(i);
        AL.set(i, AL.get(j));
        AL.set(j, temp);
    }

    @Override
    public boolean contains(T item) {
        return key.containsKey(item);
    }

    @Override
    public T getSmallest() {
        T result=AL.get(1).item;
        return result;
    }

    @Override
    public T removeSmallest() {
        Node temp;
        T result=AL.get(1).item;
        if (size<1)
            return null;
        if (size>1) {
            swap(1,size);
            AL.remove(size);
            size--;
            falldown(1);
        }
        key.remove(result);
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(Object item, double priority) {
       int i=key.get(item);
       AL.set(i,new Node((T)item,priority));
       falldown(i);
       popup(key.get(item));
    }
    public static void main(String[] args) {
        ArrayHeapMinPQ<String> A=new ArrayHeapMinPQ<>();
        Random rand=new Random();
        for (int i=0;i<300;i++)
           A.add("hi"+i, rand.nextInt(301));

        int depth = ((int) (Math.log(A.AL.size()) / Math.log(2)));
        int level = 0;
        int itemsUntilNext = (int) Math.pow(2, level);
        for (int j = 0; j < depth; j++) {
            System.out.print(" ");
        }

        for (int i = 1; i < A.AL.size(); i++) {
            System.out.printf("%.0f",A.AL.get(i).priority);
            if (i == itemsUntilNext) {
                System.out.println();
                level++;
                itemsUntilNext += Math.pow(2, level);
                depth--;
                for (int j = 0; j < depth; j++) {
                    System.out.print("    ");
                }
            }
        }
        System.out.println();
    }
}
