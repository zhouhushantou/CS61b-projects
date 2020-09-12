package bearmaps;

import java.util.*;

public class NaivePointSet implements PointSet {
    ArrayList<Point> SP=new ArrayList<>();
    //constructor, push all the points into the point stack
    public NaivePointSet(List<Point> points){
          for(Point p :points){
              SP.add(p);
          }
    }
    @Override
    public Point nearest(double x, double y) {
        Point temp,result=new Point(0,0);
        double dist,mindist=1e10;
        for(int i=0;i<SP.size();i++){
            temp=SP.get(i);
            dist=Math.sqrt(Math.pow(temp.getX()-x,2)+Math.pow(temp.getY()-y,2));
            if (dist<mindist){
                mindist=dist;
                result=temp;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        ret.getX(); // evaluates to 3.3
        ret.getY(); // evaluates to 4.4
    }
}
