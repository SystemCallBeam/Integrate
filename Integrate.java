class Integrate {

  public interface FofX {
    double eval(double x);
  }

  //static final int LEFT_POINT = 0;    // 46340
  /* S: 0.3333225435965304
    S2: 1.0000000002328302
    S: 0.33332254382936394
    S2: -1.0001424300858102 */
  //static final int MID_POINT = 1;     // 46340 ++
  /* S: 0.3333333332945263
    S2: 0.6666781757554726 
    S: 0.3333333332945291
    S2: -0.6667731303816206 */
  //static final int RIGHT_POINT = 2;   // 46340
  /* S: 0.33334412322536083
    S2: 0.3333448424997584 
    S: 0.333344122992524
    S2: -0.33339232050826 */
  public enum POINT {
    LEFT_POINT,
    MID_POINT,
    RIGHT_POINT,
  }

  static POINT p;

  static double integrate(
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
        case LEFT_POINT:
          sum += f.eval(a + i * dx) * dx;
          break;
        case MID_POINT:
          sum += f.eval(a + i * dx + dx / 2) * dx;
          break;
        case RIGHT_POINT:
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
        case LEFT_POINT:
          sum += f.eval(xi) * dxi; // left point
        case MID_POINT:
          sum += f.eval(xi + dxi / 2) * dxi; // mid point
        case RIGHT_POINT:
          sum += f.eval(xi + dxi) * dxi; // right point
      }
      xi += dxi;
    }
    return sum;
  }

  public static void main(String args[]) {
    FofX f = x -> x * x;
    FofX f2 = x -> Math.pow(Math.E, x);
    // int interval = args.length>0?Integer.parseInt(args[0]):100;
    double compare1 = (1.0 / 3.0);
    double compare2 = Math.E - 1;

    int i = 0;
    POINT point = POINT.LEFT_POINT;
    double sum = integrate2(f, 0, 1, 1, point), error = 0.001;
    System.out.println(sum);
    for (i = 40000; Math.abs(compare1 - sum) >= error; i++) {
      sum = integrate2(f, 0, 1, i, point);
      System.out.println(sum);
    }
    System.out.println("S: " + sum + "\tI: " + i);
    System.out.println("++++++++++++++");

    /* func1(f, compare1);
    func1(f2, compare2); */
  }

  public static void func1(FofX f, double compare) {
    func(f, POINT.LEFT_POINT, compare);
    func(f, POINT.MID_POINT, compare);
    func(f, POINT.RIGHT_POINT, compare);
  }

  public static void func(FofX f, POINT point, double compare) {
    int i = 1;
    double error = 0.001, sum;
    System.out.println(point);

    sum = integrate(f, 0, 1, i, point);
    for (i = 1; Math.abs(compare - sum) >= error; i++) {
      sum = integrate(f, 0, 1, i, point);
    }
    System.out.println("S: " + sum + "\tI: " + i);

    System.out.println("--------------");

    sum = integrate2(f, 0, 1, 1, point);
    System.out.println(sum);
    for (i = 1; Math.abs(compare - sum) >= error; i++) {
      sum = integrate2(f, 0, 1, i, point);
    }
    System.out.println("S: " + sum + "\tI: " + i);
    System.out.println("++++++++++++++");
  }
}
