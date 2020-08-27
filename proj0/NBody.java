public class NBody {
    public static double readRadius(String addr){
        In in = new In(addr);
        in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String addr){
        Body [] bodyinfo;
        In in = new In(addr);
        int N=in.readInt();  /*number of bodies*/
        bodyinfo=new Body[N];
        in.readDouble();
        for(int i=0;i<N;i++){
            bodyinfo[i]=new Body(0,0,0,0,0,"test");
            bodyinfo[i].xxPos=in.readDouble();
            bodyinfo[i].yyPos=in.readDouble();
            bodyinfo[i].xxVel=in.readDouble();
            bodyinfo[i].yyVel=in.readDouble();
            bodyinfo[i].mass=in.readDouble();
            bodyinfo[i].imgFileName=in.readString();
        }
        return bodyinfo;
    }

    public static void main(String[] args){
        double t,T,dt,R;
        String backgroundimage="./images/starfield.jpg";
        Body[] bds;
        T=Double.parseDouble(args[0]);
        dt=Double.parseDouble(args[1]);
        R=readRadius(args[2]);
        bds=readBodies(args[2]);
        StdDraw.setScale(-R, R);
        StdDraw.picture(0,0,backgroundimage);
        StdDraw.show();
        for (int i=0;i< bds.length;i++){
            bds[i].draw();
        }
        StdDraw.enableDoubleBuffering();
        int waitTimeMilliseconds = 10;
        t=0;
        double [] xForces=new double [bds.length];
        double [] yForces=new double [bds.length];
        while (t<T){
            StdDraw.picture(0,0,backgroundimage);
            for (int i=0;i< bds.length;i++){

                xForces[i]=bds[i].calcNetForceExertedByX(bds);
                yForces[i]=bds[i].calcNetForceExertedByY(bds);
                bds[i].update(dt,xForces[i],yForces[i]);
                bds[i].draw();
            }
            StdDraw.show();
            t+=dt;
            StdDraw.pause(waitTimeMilliseconds);
            StdDraw.clear();
        }

    }
}
