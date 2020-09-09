package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int bucketNum;
        ArrayList<Integer> A=new ArrayList<Integer>(M);
        for (int i:A)
            A.set(i,0);
      for (Oomage o:oomages){
        bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
        A.set(bucketNum,A.get(bucketNum)+1);
      }
        for (int i:A)
        {
            if (A.get(i)<(oomages.size()/50))
                return false;
            if (A.get(i)>(oomages.size()/2.5))
                return false;
        }
        return true;
    }
}
