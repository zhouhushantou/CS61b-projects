import com.sun.jdi.Value;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable,V > implements Map61B<K,V> {
    public K key;
    public V value;
    public BSTMap<K, V> left = null;
    public BSTMap<K, V> right = null;
    public BSTMap<K, V> parent = null;

    public BSTMap(K keyIn, V ValueIn) {
        key = keyIn;
        value = ValueIn;
    }

    public BSTMap() {
        key = null;
        value = null;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        this.left = null;
        this.right = null;
        this.key = null;
        this.value = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (get(key) == null)
            return false;
        else
            return true;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (this.key == null) {
            return null;
        }
        if (this.key.equals(key))
            return this.value;
        if (this.key.compareTo(key) > 0) {
            if (left != null) {
                return left.get(key);
            } else {
                return null;
            }
        }
        if (this.key.compareTo(key) < 0) {
            if (right != null) {
                return right.get(key);
            } else {
                return null;
            }
        }
        return null;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        if (this.left != null && this.right == null) {
            return 1 + this.left.size();
        }
        if (this.right != null && this.left == null) {
            return 1 + this.right.size();
        }
        if (this.left != null && this.right != null) {
            return 1 + this.left.size() + this.right.size();
        }

        if (this.key != null)
            return 1;
        else
            return 0;

    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        //null root
        if (this.key == null) {
            this.key = key;
            this.value = value;
            return;
        }
        //if exist do nothing
        if (get(key) != null)
            return;
        //insert to left
        if (this.key.compareTo(key) > 0) {
            if (left != null) {
                left.put(key, value);
                return;
            } else {
                left = new BSTMap<>(key, value);
                left.parent = this;
                return;
            }
        }
        //insert to right
        if (this.key.compareTo(key) < 0) {
            if (right != null) {
                right.put(key, value);
                return;
            } else {
                right = new BSTMap<>(key, value);
                right.parent = this;
                return;
            }
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> A = new HashSet<>();
        if (this.key == null)
            return A;
        A.add(this.key);
        if (this.left != null && this.right != null) {
            A.addAll(this.left.keySet());
            A.addAll(this.right.keySet());
        }
        if (this.left == null && this.right != null) {
            A.addAll(this.right.keySet());
        }
        if (this.left != null && this.right == null) {
            A.addAll(this.left.keySet());
        }
        return A;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V result = (V) new Object();
        if (this.key==null){
            throw new IllegalArgumentException("empty tree");
        }
        if (this.key.equals(key)){
            //no child node removal
            if (this.left==null&&this.right==null) {
                if (this.parent==null){
                    V result1=this.value;
                    this.value=null;
                    this.key=null;
                    return result1;
                }
                if (this.parent.left!=null) {
                    if (this.parent.left == this)
                        this.parent.left = null;
                }
                else
                    this.parent.right = null;
                return this.value;
            }
            //with one child
            if (this.left!=null) {
                if (this.parent==null){
                    V result2=this.value;
                    this.key=this.left.key;
                    this.value=this.left.value;
                    this.left.remove(this.left.key);
                    return result2;
                }
                if (this.parent.left!=null) {
                    if (this == this.parent.left)
                        this.parent.left = this.left;
                }
                else {
                    this.parent.right = this.left;
                    this.left.parent=this.parent;
                }
                return this.value;
            }
            if (this.right!=null) {
                if (this.parent==null){
                    V result2=this.value;
                    this.key=this.right.key;
                    this.value=this.right.value;
                    this.right.remove(this.right.key);
                    return result2;
                }
                if(this.parent.left!=null) {
                    if (this.parent.left == this)
                        this.parent.left = this.right;
                }
                else {
                    this.parent.right = this.right;
                    this.right.parent=this.parent;
                }
                return this.value;
            }
            //with two children
            BSTMap<K,V> p=this.left;
            while(p.right!=null) {
                p = p.right;
            }
            this.key=p.key;
            this.value=p.value;
            p.remove(p.key);
        }
        if (this.key.compareTo(key) > 0) {
            if (this.left != null)
                result=this.left.remove(key);
            else
                throw new IllegalArgumentException("not find key");
        }
        if (this.key.compareTo(key) <0) {
            if (this.right != null)
                result=this.right.remove(key);
            else
                throw new IllegalArgumentException("not find key");
        }
        return result;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V result = (V) new Object();
        //no child

        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    public void printInOrder() {
        if (this.left != null)
            this.left.printInOrder();
        System.out.println("key: " + this.key + " value: " + this.value);
        if (this.right != null)
            this.right.printInOrder();
    }

    public static void main(String[] args) {
        BSTMap<Integer, Integer> A = new BSTMap<>(6, 1);
        A.put(1, 2);
        A.put(9, 3);
        A.put(7, 2);
        A.put(4, 2);
        A.put(5, 2);
        A.printInOrder();
    }
}
