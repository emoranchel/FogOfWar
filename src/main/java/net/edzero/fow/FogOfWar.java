package net.edzero.fow;

public class FogOfWar {
  public enum Direction {
    N, S, W, E
  }

  public final static byte VISIBLE = 4;
  public final static byte NON_VISIBLE = 1;
  public final static byte NULL = 0;

  private final int width;
  private final int height;
  private final byte[][] data;
  private boolean[][] obstacles;

  public FogOfWar(int width, int height) {
    this.width = width;
    this.height = height;
    data = new byte[width][height];
    obstacles = new boolean[width][height];
  }

  public void addObstacle(int x, int y) {
    obstacles[x][y] = true;
  }

  public byte getValue(int x, int y) {
    byte value = data[x][y];
    return value == NULL ? NON_VISIBLE : value;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void addRevealer(int cx, int cy, int radius) {
    circle(cx, cy, radius);
  }

  private void circle(int cx, int cy, int radius) {
    int d = 3 - (2 * radius);
    int x = 0;
    int y = radius;
    do {
      endPoint(cx, cy, cx + x, cy + y);
      endPoint(cx, cy, cx + x, cy - y);
      endPoint(cx, cy, cx - x, cy + y);
      endPoint(cx, cy, cx - x, cy - y);
      endPoint(cx, cy, cx + y, cy + x);
      endPoint(cx, cy, cx + y, cy - x);
      endPoint(cx, cy, cx - y, cy + x);
      endPoint(cx, cy, cx - y, cy - x);
      if (d < 0) {
        d = d + (4 * x) + 6;
      } else {
        d = d + 4 * (x - y) + 10;
        y--;
      }
      x++;
    } while (x <= y);
  }

  private void endPoint(int cx, int cy, int x, int y) {
    // putpixel(x, y);
    lineFrom(cx, cy, x, y);
  }

  public void lineFrom(int x, int y, int x2, int y2) {
    int w = x2 - x;
    int h = y2 - y;
    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
    if (w < 0)
      dx1 = -1;
    else if (w > 0)
      dx1 = 1;
    if (h < 0)
      dy1 = -1;
    else if (h > 0)
      dy1 = 1;
    if (w < 0)
      dx2 = -1;
    else if (w > 0)
      dx2 = 1;
    int longest = Math.abs(w);
    int shortest = Math.abs(h);
    if (!(longest > shortest)) {
      longest = Math.abs(h);
      shortest = Math.abs(w);
      if (h < 0)
        dy2 = -1;
      else if (h > 0)
        dy2 = 1;
      dx2 = 0;
    }
    int numerator = longest >> 1;
    int lx, ly;
    for (int i = 0; i <= longest; i++) {
      lx = x;
      ly = y;
      putpixel(x, y);
      if (obstacle(x, y)) {
        return;
      }
      numerator += shortest;
      if (!(numerator < longest)) {
        numerator -= longest;
        x += dx1;
        y += dy1;
      } else {
        x += dx2;
        y += dy2;
      }
      if (lx != x && ly != y) {
        if (obstacle(x, ly) && obstacle(lx, y)) {
          return;
        }
      }
    }
  }

  private boolean obstacle(int x, int y) {
    if (x > 0 && x < width - 1 && y > 0 && y < height - 1) {
      return obstacles[x][y];
    }
    return true;
  }

  private void putpixel(int x, int y) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      data[x][y] = VISIBLE;
    }
  }
}
