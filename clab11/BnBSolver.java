import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        List<Bear> bearLess=new ArrayList<Bear>();
        List<Bear> bearGreater=new ArrayList<Bear>();
        List<Bed> bedLess=new ArrayList<Bed>();
        List<Bed> bedGreater=new ArrayList<Bed>();
        Bear bearPivot;
        Bed bedPivot=beds.get(0);

        for (int i=0;i<bears.size();i++){
            if (bears.get(i).compareTo(bedPivot)){
                bearPivot=bears.get(i);
                break;
            }
        }
        bears=solvedBears(bears,bearPivot, bedPivot,bearLess,bearGreater);
        beds=solvedBears(beds,bearPivot, bedPivot,bedLess,bedGreater);
        BnBSolver(bearLess,bedLess);
        BnBSolver(bearGreater,bedGreater);

    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears(List<Bear> bears,Bear bearPivot,Bed bedPivot,
                                  List<Bear> bearLess,List<Bear> bearGreater) {
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
        return bearLess.addAll(bearPivot).addAll(bearGreater);
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return null;
    }

}
