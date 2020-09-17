import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> bearSorted;
    private List<Bed> bedSorted;
    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        Pair<List<Bear>,List<Bed>> result;
        result=solve(bears,beds);
        bearSorted=result.first();
        bedSorted=result.second();
    }

    //Recursive solver
    private Pair<List<Bear>,List<Bed>> solve(List<Bear> bears,List<Bed> beds){
        Pair<List<Bear>,List<Bed>> result;
        //if there is only one point
        if (bears.size()<2){
            result=new Pair<>(bears,beds);
            return result;
        }
        List<Bear> bearLess=new ArrayList<Bear>();
        List<Bear> bearGreater=new ArrayList<Bear>();
        List<Bed> bedLess=new ArrayList<Bed>();
        List<Bed> bedGreater=new ArrayList<Bed>();

        //Partitioning point
        Bear bearPivot=bears.get(0);
        Bed bedPivot=beds.get(0);

        //find the bears pivot point which is same size as that of bed pivot point
        for (int i=0;i<bears.size();i++){
            if (bears.get(i).compareTo(bedPivot)==0){
                bearPivot=bears.get(i);
                break;
            }
        }

        //partition bears and beds
        partitionBears(bears,bearLess,bearGreater,bedPivot);
        partitionBeds(beds,bedLess,bedGreater,bearPivot);

        //recurse
        Pair<List<Bear>,List<Bed>> t1,t2;
        t1=solve(bearLess,bedLess);
        bearLess=t1.first();
        bedLess=t1.second();
        t2=solve(bearGreater,bedGreater);
        bearGreater=t2.first();
        bedGreater=t2.second();

        //return result
        List<Bear> r1=bearsConca(bearLess,bearGreater,bearPivot);
        List<Bed> r2=bedsConca(bedLess,bedGreater,bedPivot);
        result=new Pair(r1,r2);
        return result;
    }

    private List<Bear> bearsConca(List<Bear> bearLess,List<Bear> bearGreater,Bear bearPivot){
        List<Bear> result=new ArrayList<Bear>() ;
        result.addAll(bearLess);
        result.add(bearPivot);
        result.addAll(bearGreater);
        return result;
    }

    private List<Bed> bedsConca(List<Bed> bedLess,List<Bed> bedGreater,Bed bedPivot){
        List<Bed> result=new ArrayList<Bed>() ;
        result.addAll(bedLess);
        result.add(bedPivot);
        result.addAll(bedGreater);
        return result;
    }

    private void partitionBears(List<Bear> bears,List<Bear> bearLess,List<Bear> bearGreater,Bed bedPivot){
        //if there is only one point
        if (bears.size()<2){
            return;
        }
        //patitioning the bears
        Bear temp;
        for (int i=0;i<bears.size();i++){
            temp=bears.get(i);
            if (temp.compareTo(bedPivot)<0) {
                bearLess.add(temp);
                continue;
            }
            if (temp.compareTo(bedPivot)>0) {
                bearGreater.add(temp);
                continue;
            }
        }
    }

    private void partitionBeds(List<Bed> beds,List<Bed> bedLess,List<Bed> bedGreater,Bear bearPivot){
        //patitioning the beds
        Bed temp;
        for (int i=0;i<beds.size();i++){
            temp=beds.get(i);
            if (temp.compareTo(bearPivot)<0) {
                bedLess.add(temp);
                continue;
            }
            if (temp.compareTo(bearPivot)>0) {
                bedGreater.add(temp);
                continue;
            }
        }
    }


    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return bearSorted;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return bedSorted;
    }

    public static void main(String[] args) {
        ArrayList<Bear> bears = new ArrayList<>();
        ArrayList<Bed> beds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bears.add(new Bear(i));
            beds.add(new Bed(9 - i));
        }
        BnBSolver solver = new BnBSolver(bears, beds);
    }

}
