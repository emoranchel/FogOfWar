package net.edzero.fow.util;

public class Geometry {

  public static void circle(int cx, int cy, int radius, PointMarker pointMarker) {
    int d = (5 - radius * 4) / 4;
    int x = 0;
    int y = radius;
    do {
      pointMarker.mark(cx + x, cy + y);
      pointMarker.mark(cx + x, cy - y);
      pointMarker.mark(cx - x, cy + y);
      pointMarker.mark(cx - x, cy - y);
      pointMarker.mark(cx + y, cy + x);
      pointMarker.mark(cx + y, cy - x);
      pointMarker.mark(cx - y, cy + x);
      pointMarker.mark(cx - y, cy - x);
      if (d < 0) {
        d += 2 * x + 1;
      } else {
        d += 2 * (x - y) + 1;
        y--;
      }
      x++;
    } while (x <= y);

  }
}
