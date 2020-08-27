import java.lang.Math;
public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    /*The first constructor*/
    public Body(double xP, double yP, double xV,
                double yV, double m, String img){
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }

    /*The second constructor*/
    public  Body(Body b){
        xxPos=b.xxPos;
        yyPos=b.yyPos;
        xxVel=b.xxVel;
        yyVel=b.yyVel;
        mass=b.mass;
        imgFileName=b.imgFileName;
    }

    /*Calculate the distance between two bodies*/
    public double calcDistance(Body b){
        return  Math.sqrt(Math.pow(xxPos-b.xxPos,2)+Math.pow(yyPos-b.yyPos,2));
    }

    /*Calculate the force between two bodies*/
    public double calcForceExertedBy(Body b){
        double G=6.67e-11;
        return G*mass*b.mass/Math.pow(this.calcDistance(b),2);
    }

    /*Caculate the x direction force*/
    public double calcForceExertedByX(Body b){
        return calcForceExertedBy(b)*(b.xxPos-xxPos)/calcDistance(b);
    }

    /*Caculate the y direction force*/
    public double calcForceExertedByY(Body b){
        return calcForceExertedBy(b)*(b.yyPos-yyPos)/calcDistance(b);
    }

    /*net x force from all bodies*/
    public double calcNetForceExertedByX(Body [] allBodys){
        int i=0;
        double F=0;
        while (i< allBodys.length){
            if (!this.equals(allBodys[i])) {
                F += calcForceExertedByX(allBodys[i]);
            }
            i++;
        }
        return F;
    }

    /*net y force from all bodies*/
    public double calcNetForceExertedByY(Body [] allBodys){
        int i=0;
        double F=0;
        while (i< allBodys.length){
            if (!this.equals(allBodys[i])) {
                F += calcForceExertedByY(allBodys[i]);
            }
            i++;
        }
        return F;
    }

    /*upadate the velocity and position*/
    public void update(double dt,double Fx, double Fy){
        xxVel+=dt*(Fx/mass);
        yyVel+=dt*(Fy/mass);
        xxPos+=xxVel*dt;
        yyPos+=yyVel*dt;
    }

    public void draw(){
        StdDraw.picture(xxPos,yyPos,"./images/"+imgFileName);
        StdDraw.show();
    }
}
