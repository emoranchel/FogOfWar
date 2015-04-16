package net.edzero.fow.util;

public class Geometry {

  public static void circle(int cx, int cy, int radius, PointMarker pointMarker) {
    int d = (5 - radius * 4) / 4;
    int x = 0;
    int y = radius;
    do {
      markSegments(pointMarker, cx, x, cy, y);
      if (d < 0) {
        d += 2 * x + 1;
      } else {
        d += 2 * (x - y) + 1;
        y--;
        markSegments(pointMarker, cx, x, cy, y);
      }
      x++;
    } while (x <= y);
  }

  private static void markSegments(PointMarker pointMarker, int cx, int x, int cy, int y) {
    pointMarker.mark(cx + x, cy + y);
    pointMarker.mark(cx + x, cy - y);
    pointMarker.mark(cx - x, cy + y);
    pointMarker.mark(cx - x, cy - y);
    pointMarker.mark(cx + y, cy + x);
    pointMarker.mark(cx + y, cy - x);
    pointMarker.mark(cx - y, cy + x);
    pointMarker.mark(cx - y, cy - x);
  }

  public static void line(int x1, int y1, int x2, int y2, PointMarker marker) {
    // delta of exact value and rounded value of the dependant variable
    int d = 0;

    int dy = Math.abs(y2 - y1);
    int dx = Math.abs(x2 - x1);

    int dy2 = (dy << 1); // slope scaling factors to avoid floating
    int dx2 = (dx << 1); // point

    int ix = x1 < x2 ? 1 : -1; // increment direction
    int iy = y1 < y2 ? 1 : -1;

    if (dy <= dx) {
      for (;;) {
        marker.mark(x1, y1);
        if (x1 == x2) {
          break;
        }
        x1 += ix;
        d += dy2;
        if (d > dx) {
          y1 += iy;
          d -= dx2;
        }
      }
    } else {
      for (;;) {
        marker.mark(x1, y1);
        if (y1 == y2) {
          break;
        }
        y1 += iy;
        d += dx2;
        if (d > dy) {
          x1 += ix;
          d -= dy2;
        }
      }
    }
  }
}
