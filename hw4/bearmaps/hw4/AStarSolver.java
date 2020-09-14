package bearmaps.hw4;
import bearmaps.proj2ab.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bearmaps.hw4.lectureexample.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private DoubleMapPQ<Vertex> A;
    private Map<Vertex,Double> distTo;
    private Map<Vertex,Vertex> edgeTo;
    boolean solved=false;
    boolean timeouted=false;
    float elapseTime;
    Vertex start1,end1;
    int numState;
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout){

        end1=end;
        start1=start;
        numState=0;
        A=new DoubleMapPQ<Vertex>();
        distTo=new HashMap<>();
        edgeTo=new HashMap<>();
        solved=false;
        timeouted= false;
        additem(input,start,start);
        //is start equal to end
        if(start.equals(end))
        {
            edgeTo.put(start,start);
            distTo.put(start,0.0);
            solved=true;
            return;
        }
        //solve the problem
        Vertex p,q;
        double w;
        List<WeightedEdge<Vertex>> NB;
        long tStart= System.currentTimeMillis();
        long tEnd;
        while (A.size()>0) {
            numState++;
            p=A.removeSmallest();
            NB=input.neighbors(p);
            for (WeightedEdge<Vertex> v : NB) {
                q=v.to();
                w=v.weight();
                if ((distTo.get(p)+w)<distTo.get(q)){
                    edgeTo.put(q,p);
                    distTo.put(q,distTo.get(p)+w);
                    if(A.contains(q))
                        A.changePriority(q,distTo.get(q)+input.estimatedDistanceToGoal(q,end));
                    else
                        A.add(q,distTo.get(q)+input.estimatedDistanceToGoal(q,end));
                }
                //if it is the goal
                if (q.equals(end)) {
                    solved=true;
                    break;
                }
                //timeout
                tEnd=System.currentTimeMillis();
                elapseTime=tEnd-tStart;
                if ((elapseTime/1000F)>timeout) {
                    timeouted = true;
                    break;
                }
            }
        }
    }

    //recursive item add function
    private void additem(AStarGraph<Vertex> input,Vertex start,Vertex vt) {
        if (!A.contains(vt)) {
            if (vt.equals(start)) {
                A.add(vt, 0);
                distTo.put(vt,0.0);
            }
            else {
                A.add(vt, Double.POSITIVE_INFINITY);
                distTo.put(vt, Double.POSITIVE_INFINITY);
            }

            List<WeightedEdge<Vertex>> NB = input.neighbors(vt);
            for (WeightedEdge<Vertex> v : NB) {
                additem(input, start, v.to());
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        if (solved)
            return SolverOutcome.SOLVED;
        if(timeouted)
            return SolverOutcome.TIMEOUT;
        return  SolverOutcome.UNSOLVABLE;
    }

    @Override
    public List<Vertex> solution() {
        List<Vertex> result=new ArrayList<>();
        if (!solved)
            return result;
        if(timeouted)
            return result;
        result.add(end1);
        Vertex p=end1;
        while (!p.equals(start1)){
            p=edgeTo.get(p);
            result.add(p);
        }
        List<Vertex> resultInverse=new ArrayList<>();
        for(int i=0;i<result.size();i++){
            resultInverse.add(result.get(result.size()-i-1));
        }
        return resultInverse;
    }

    @Override
    public double solutionWeight() {
        return distTo.get(end1);
    }

    @Override
    public int numStatesExplored() {
        return numState;
    }

    @Override
    public double explorationTime() {
        return elapseTime;
    }
    public static void main(String[] args) {
        WeightedDirectedGraph wdg = new WeightedDirectedGraph(6);
        /* Edges from vertex 0. */
        wdg.addEdge(0, 1, 50);
        wdg.addEdge(0, 2, 20);

        wdg.addEdge(1, 4, 20);

        wdg.addEdge(2, 3, 10);

        wdg.addEdge(3, 4, 70);

        wdg.addEdge(4, 3, 10);
        wdg.addEdge(4, 5, 100);


        int start = 0;
        int goal = 5;

        ShortestPathsSolver<Integer> solver = new AStarSolver<>(wdg, start, goal, 10);
        SolutionPrinter.summarizeSolution(solver, " => ");
    }
}
