public class Main {

    static POINT p;
    static double error = 0.001;
  
    public static void main(String[] args) {
        q1();
    }

    public static void q1() {
        FofX f1 = x -> x * x;
        FofX f2 = x -> Math.exp(x);

        double compare1 = 1.0 / 3.0;
        double compare2 = Math.E - 1;

        System.out.println("f1 = x -> x * x");
        ff(compare1, f1);

        System.out.println("f2 = x -> Math.exp(x)");
        ff(compare2, f2);  
    }
  
    public static void ff(double compare, FofX f) {
      for (POINT point : POINT.values()) {  // compute each point
        System.out.println(point);
        func1(point, compare, f);
        func2(point, compare, f);
        System.out.println("----------");
      }
    }
  
    public static void func1(POINT point, double compare, FofX f) {
      int i = 0;
      double sum = 0;
      for (i = 0; Math.abs(compare - sum) >= error; i++) {
        sum = integrate1(f, 0, 1, i, point);
      }
      System.out.println(sum + " " + (i - 1));
    }
  
    public static void func2(POINT point, double compare, FofX f) {
      int i = 0;
      double sum = 0;
      for (i = 0; Math.abs(compare - sum) >= error; i++) {
        sum = integrate2(f, 0, 1, i, point);
      }
      System.out.println(sum + " " + (i - 1));
    }
  
    static double integrate1(
      FofX f,
      double a,
      double b,
      int interval,
      POINT point
    ) {
      double sum = 0;
      double dx = (b - a) / interval;
      for (int i = 0; i < interval; i++) {
        switch (point) {
          case LEFT:
            sum += f.eval(a + i * dx) * dx;
            break;
          case MID:
            sum += f.eval(a + i * dx + dx / 2) * dx;
            break;
          case RIGHT:
            sum += f.eval(a + i * dx + dx) * dx;
            break;
        }
      }
      return sum;
    }
  
    static double integrate2(
      FofX f,
      double a,
      double b,
      int interval,
      POINT point
    ) {
      double sum = 0;
      double d = b - a;
      double xi = 0;
      double factor = interval * (interval + 1) / 2;
      for (int i = 0; i < interval; i++) {
        double dxi = d * (interval - i) / factor;
        switch (point) {
          case LEFT:
            sum += f.eval(xi) * dxi;
            break; // left point
          case MID:
            sum += f.eval(xi + dxi / 2) * dxi;
            break; // mid point
          case RIGHT:
            sum += f.eval(xi + dxi) * dxi;
            break; // right point
        }
        xi += dxi;
      }
      return sum;
    }
  }
  