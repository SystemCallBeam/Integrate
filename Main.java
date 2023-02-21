public class Main {
    static POINT p;
    public static void main(String[] args) {
        FofX f = x -> x*x;
        double compare = 1.0/3.0;
        func1(POINT.LEFT, compare, f);
    }
    public static void func1(POINT point, double compare, FofX f1) {
        POINT p = point;
        FofX f = f1;
        int i = 0;
        double error = 0.001, sum = 0;
        for (i = 0; Math.abs(compare-sum) >= error; i++) {
            sum = integrate1(f, 0, 1, i, p);
        }
        System.out.println(sum + " " + i);
    }

    public static void func2(POINT point, double compare, FofX f1) {
        POINT p = point;
        FofX f = f1;
        int i = 0;
        double error = 0.001, sum = 0;
        for (i = 0; Math.abs(compare-sum) >= error; i++) {
            sum = integrate1(f, 0, 1, i, p);
        }
        System.out.println(sum + " " + i);
    }

    static double integrate1(FofX f, double a, double b, int interval, POINT point) {
        double sum=0;
        double dx=(b-a)/interval;
        for(int i=0; i<interval; i++) {
            switch(point) {
                case LEFT: sum+=f.eval(a+i*dx)*dx; break;
                case MID: sum+=f.eval(a+i*dx+dx/2)*dx; break;
                case RIGHT: sum+=f.eval(a+i*dx+dx)*dx; break;
            }
        }
        return sum;
    }

    static double integrate2(FofX f, double a, double b, int interval, POINT point) {
        double sum=0;
        double d = b-a;
        double xi=0;
        double factor=interval*(interval+1)/2;
        for(int i=0; i<interval; i++) {
            double dxi = d*(interval-i)/factor;
            switch(point) {
                case LEFT: sum+=f.eval(xi)*dxi;   break;       // left point
                case MID: sum+=f.eval(xi+dxi/2)*dxi;  break;    // mid point
                case RIGHT: sum+=f.eval(xi+dxi)*dxi;   break;   // right point
            }
            xi+=dxi;
        }
        return sum;
    }
}
