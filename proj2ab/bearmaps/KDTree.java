package bearmaps;

import java.util.List;

public class KDTree {
    Node root;
    int size = 0;
    double minDist = 1e10;

    //constructor
    public KDTree(List<Point> points) {
        //build the root node first
        root = new Node(points.get(0), true);
        for (int i = 1; i < points.size(); i++) {
            Node newNode = new Node(points.get(i), true);
            insert(newNode);
        }
    }

    //insert a node to the present kd tree
    private void insert(Node newNode) {
        Node temp = root;
        //traversal the tree to add new Node
        while (temp != null) {
            if (temp.kd) {
                //if present node is left&right divide
                if (newNode.p.getX() >= temp.p.getX()) {
                    //if new node is at the right side of the present node
                    if (temp.right == null) {
                        temp.right = newNode;
                        temp.right.kd = false;
                        size++;
                        return;
                    } else {
                        temp = temp.right;
                    }
                } else {
                    if (temp.left == null) {
                        temp.left = newNode;
                        temp.left.kd = false;
                        size++;
                        return;
                    } else {
                        temp = temp.left;
                    }
                }
            } else {
                //if present node is up&down divide
                if (newNode.p.getY() >= temp.p.getY()) {
                    //if new node is at the up side of the present node
                    if (temp.right == null) {
                        temp.right = newNode;
                        temp.right.kd = true;
                        size++;
                        return;
                    } else {
                        temp = temp.right;
                    }
                } else {
                    if (temp.left == null) {
                        temp.left = newNode;
                        temp.left.kd = true;
                        size++;
                        return;
                    } else {
                        temp = temp.left;
                    }
                }
            }
        }
    }

    public class Node {
        Point p;
        boolean kd; //if it is true, then it is divide left&right, if it is false, then it is up&down
        Node left = null;
        Node right = null;

        public Node(Point pt, boolean kdin) {
            p = pt;
            kd = kdin;
        }
    }

    public Point nearest(double x, double y) {
        minDist=1e10;
        return findNearest(root, x, y);
    }

    private Point findNearest(Node in, double x, double y) {
        double dist,badsideDist;
        dist = distance(in.p.getX(), in.p.getY(), x, y);
        if (dist < minDist)
            minDist = dist;
        //if there is no child, return the present node
        if ((in.left == null) && (in.right == null))
            return in.p;
        //if there is only left node, recurse
        Node goodside, badside;
        Point t1=null, t2=null, t=null;
        if (in.kd) {
            if (x >= in.p.getX()) {
                goodside = in.right;
                badside = in.left;
                badsideDist=x-in.p.getX();
            } else {
                goodside = in.left;
                badside = in.right;
                badsideDist=in.p.getX()-x;
            }
        } else {
            if (y >= in.p.getY()) {
                goodside = in.right;
                badside = in.left;
                badsideDist=y-in.p.getY();
            } else {
                goodside = in.left;
                badside = in.right;
                badsideDist=in.p.getY()-y;
            }
        }

        if (goodside != null)
            t1 = findNearest(goodside, x, y);
        if ((badside != null) &&(badsideDist<=minDist))
            t2= findNearest(badside, x, y);
        if((t1==null)&&(t2==null))
            return in.p;
        if (t2!=null){
            if (t1!=null) {
                if (distance(t1.getX(), t1.getY(), x, y) < distance(t2.getX(), t2.getY(), x, y))
                    t = t1;
                else
                    t = t2;
            }
            else{
                t=t2;
            }
            if (distance(t.getX(), t.getY(), x, y) < distance(in.p.getX(), in.p.getY(), x, y))
                return t;
            else
                return in.p;
        }
        else{
            if (distance(t1.getX(), t1.getY(), x, y) < distance(in.p.getX(), in.p.getY(), x, y))
                return t1;
            else
                return in.p;
        }

    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(4, 4);
        Point p6 = new Point(1, 5);

        KDTree A = new KDTree(List.of(p1, p2, p3,p4,p5,p6));
        Point np=A.nearest(0,7);
    }
}
