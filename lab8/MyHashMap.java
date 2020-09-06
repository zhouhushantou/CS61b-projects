import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyHashMap<K,V> implements Map61B<K,V>{
    private int initialSize=16;
    private double loadFactor ;
    ArrayList<Node> table=new ArrayList<>();
    public class Node<K,V> {
        K key;
        V value;
        Node next=null;
        Node prev=null;
        //is the key empty
        public boolean isEmpty(){
            if (key==null)
                return true;
            return false;
        }
        //is it the last node of a linked list'
        public boolean isEnd(){
            if(key==null||next==null)
                return true;
            return false;
        }

    }
    public MyHashMap() {
        initialSize = 16;
        loadFactor = 0.75;
        for (int i = 0; i < initialSize; i++)
            table.add(new Node());
    }
    public MyHashMap(int initialSize){
        this.initialSize = initialSize;
        loadFactor = 0.75;
        for (int i = 0; i < initialSize; i++)
            table.add(new Node());
    }
    public MyHashMap(int initialSize, double loadFactor){
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        for (int i = 0; i < initialSize; i++)
            table.add(new Node());
    }
    /** Removes all of the mappings from this map. */
    @Override
    public void clear(){
        for (int i=0;i<initialSize;i++){
           table.set(i, new Node<K,V>());
        }
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (this.get(key) != null)
            return true;
        return false;
    }


    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key){
        Node<K,V> A=table.get(0);
        for (int i=0;i<initialSize;i++) {
            A = table.get(i);
            if(!A.isEmpty()) {
                if (A.key.equals(key)) {
                    return A.value;
                }
            }
            if (A.isEnd()) {
                continue;
            }
            while (!A.isEnd()) {
                if (A.isEmpty()){
                    break;
                }
                if (A.key.equals( key)) {
                    return A.value;
                }
                A = A.next;
                if (!A.isEmpty()) {
                    if (A.key.equals(key)) {
                        return A.value;
                    }
                }
            }
        }
        return  null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size(){
        int sum=0;
        Iterator<K> A=this.iterator();
        while(A.hasNext()){
            sum++;
            A.next();
        }
     return sum;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value){
        int N=Math.floorMod(key.hashCode(),initialSize);
        Node p=table.get(N);
        //if it is empty
        if (p.key==null) {
            p.key = key;
            p.value = value;
            return;
        }
        //if it is no empty, find the last node
        while (true) {
            //if the key is already there, update the value
            if (p.key.equals(key)) {
                p.value = value;
                return;
            }
            if (p.next==null)
                break;
             p = p.next;
        }
        //create a new node and add to the linked list
        Node nd=new Node();
        nd.key=key;
        nd.value=value;
        nd.prev=p;
        p.next=nd;
        if (((double)this.size()/initialSize)>loadFactor){
            this.resize();
        }
        return;
    }
   private void resize(){
       //ArrayList<Node> B=new ArrayList<>(initialSize*2);
       MyHashMap<K,V> C=new MyHashMap<>(this.initialSize*2,loadFactor);
       Iterator<K> ITR=this.iterator();
       K temp;
        while (ITR.hasNext()){
            temp=ITR.next();
            C.put(temp,this.get(temp));
        }
        this.table=C.table;
       this.initialSize=this.initialSize*2;
   }
    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet(){
        Set<K> B=new HashSet<>();
        Iterator<K> A=this.iterator();
        while(A.hasNext()){
             B.add(A.next());
        }
        return B;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key){
        throw new UnsupportedOperationException("not implemented!");
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value){
        throw new UnsupportedOperationException("not implemented!");
    }

    @Override
    public Iterator<K> iterator() {
        Iterator<K> A=new myIterator<>();
        return A;
    }
    public class myIterator<K> implements Iterator<K>{
        public Node<K,V> A=table.get(0);
        int i=0;
        boolean firstFlag=true;
        @Override
        public boolean hasNext() {
            Node B = A;
            if(firstFlag) {
                if(A!=null) {
                    if ((A.key != null) && (B == A)) {
                        return true;
                    }
                }
                if(A==null)
                {
                    return false;
                }
            }
            //traverse to the end of the linked list
            while (!B.isEnd()) {
                if (B.isEmpty()){
                    break;
                }
                B = B.next;
                if (!B.isEmpty()) {
                   return true;
                }
            }
            for (int j=i+1;j<initialSize;j++) {
                B= table.get(j);
                if(!B.isEmpty()) {
                    return true;
                }
                if (B.isEnd()) {
                    continue;
                }
                while (!B.isEnd()) {
                    if (B.isEmpty()){
                        break;
                    }
                    B= B.next;
                    if (!B.isEmpty()) {
                      return true;
                    }
                }
            }
            return false;
        }

        @Override
        public K next() {
            //if the first element is not null, return it and change the flag
            if (firstFlag&&A.key!=null) {
                firstFlag = false;
                return A.key;
            }
            firstFlag=false;

            while (!A.isEnd()) {
                if (A.isEmpty()){
                    break;
                }
                A = A.next;
                if (!A.isEmpty()) {
                    return A.key;
                }
            }

            while (i<initialSize) {
                i++;
                A = table.get(i);
                if(!A.isEmpty()) {
                    return A.key;
                }
                if (A.isEnd()) {
                    continue;
                }
                while (!A.isEnd()) {
                    if (A.isEmpty()){
                        break;
                    }
                    A = A.next;
                    if (!A.isEmpty()) {
                        return A.key;
                    }
                }
            }
              return null;
        }
    }
    public static void main(String[] args) {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
            //make sure put is working via containsKey and get
            assertTrue(null != b.get("hi" + i)
                    && b.containsKey("hi" + i));
        }
    }
}
