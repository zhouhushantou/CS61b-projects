import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    PriorityQueue<Flight> pqStart,pqEnd;
    Comparator<Flight> functionStart = (Flight arg1, Flight arg2) -> (arg1.startTime()-arg2.startTime());
    Comparator<Flight> functionEnd = (Flight arg1, Flight arg2) -> (arg1.endTime()-arg2.endTime());
    int startTime;
    int endTime;
    int numMax=0;
    int numtemp;
    Flight tp,tps,tpe;
    public FlightSolver(ArrayList<Flight> flights) {
        pqStart=new PriorityQueue<Flight>(flights.size(),functionStart);
        pqEnd=new PriorityQueue<Flight>(flights.size(),functionEnd);
        for(Flight item:flights){
            pqStart.add(item);
            pqEnd.add(item);
        }
        while (pqStart.size()>0) {
            tps = pqStart.poll();
            tpe = pqEnd.poll();
            startTime = tps.startTime();
            endTime = tpe.endTime();
            PriorityQueue<Flight> startCopy = new PriorityQueue<Flight>(pqStart);
            numtemp =tps.passengers();
            while (startCopy.size() > 0) {
                tp = startCopy.poll();
                if (tp.startTime() > endTime)
                    break;
                numtemp+=tp.passengers();
            }
            if (numtemp>numMax)
                numMax=numtemp;
        }
    }

    public int solve() {
        return numMax;
    }
}
