import java.util.Deque;
import java.util.LinkedList;

public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        StringBuilder strb = new StringBuilder();
        for(int i=0;i<pattern.length();i++){
            strb.append(input.charAt(i));
        }
        RollingString A=new RollingString(strb.toString(),pattern.length());
        RollingString B=new RollingString(pattern,pattern.length());
        if (A.equals(B))
            return 0;
        for(int i=pattern.length();i<input.length();i++){
            A.addChar(input.charAt(i));
            if (A.equals(B))
                return i-pattern.length()+1;
        }
        return -1;
    }

}
