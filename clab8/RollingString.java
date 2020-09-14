import java.util.Deque;
import java.util.LinkedList;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;


    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;
    static final int CST=((UNIQUECHARS%PRIMEBASE)*UNIQUECHARS) % PRIMEBASE ;
    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    Deque<Character> A;
    int hashcode=0;
    public RollingString(String s, int length) {
        assert(s.length() == length);
        A=new LinkedList<>();
        for (int i=0;i<length;i++)
            A.add(s.charAt(i));
        hashcode=this.hashCode();
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        A.add(c);
        char old=A.removeFirst();
        hashcode=((hashcode+PRIMEBASE-old*CST) * UNIQUECHARS+c)%PRIMEBASE;
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        Deque<Character> B=new LinkedList<>(this.A);
        while (B.size()>0) {
            strb.append(B.removeFirst());
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return A.size();
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        String o1=o.toString();
        String thisString=this.toString();
        if (thisString.equals(o1))
                return true;
        return false;
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        long t=0;
        Deque<Character> B=new LinkedList<>(this.A);
        while (B.size()>0) {
            t=t*UNIQUECHARS+B.removeFirst();
        }
        return (int)(t%PRIMEBASE);
    }

    public static void main(String[] argm){
        RollingString test=new RollingString("hello",5);
       test.addChar('t');
       test.addChar('p');
    }
}
