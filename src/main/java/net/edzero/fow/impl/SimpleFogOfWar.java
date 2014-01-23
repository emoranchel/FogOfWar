/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.edzero.fow.impl;

import net.edzero.fow.FogOfWar;
import static net.edzero.fow.FogOfWar.NON_VISIBLE;
import static net.edzero.fow.FogOfWar.NULL;
import static net.edzero.fow.FogOfWar.VISIBLE;

/**
 *
 * @author Eduardo
 */
public class SimpleFogOfWar implements FogOfWar {

  private int width;
  private int height;
  private byte[][] data;
  private boolean[][] obstacles;

  public void initialize(int width, int height) {
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

  public void addPointOfView(int x, int y, int dist) {
    reveal(x, y, dist, true, true, false, false, 0);
    reveal(x, y, dist, true, false, false, true, 0);
    reveal(x, y, dist, false, false, true, true, 0);
    reveal(x, y, dist, false, true, true, false, 0);
  }

  private boolean reveal(int x, int y, int i, boolean top, boolean right, boolean bottom, boolean left, int iteration) {
    if (iteration > width || iteration > height) {
      return false;
    }
    if (inRange(x, y)) {
      data[x][y] = VISIBLE;
      if (i == 0 || obstacles[x][y]) {
        return false;
      }
      if (left) {
        left = reveal(x - 1, y, i - 1, top, false, bottom, left, iteration + 1);
      }
      if (right) {
        right = reveal(x + 1, y, i - 1, top, right, bottom, false, iteration + 1);
      }
      if (bottom) {
        bottom = reveal(x, y - 1, i - 1, false, right, bottom, left, iteration + 1);
      }
      if (top) {
        top = reveal(x, y + 1, i - 1, top, right, false, left, iteration + 1);
      }
      return true;
    }
    return false;
  }

  private boolean inRange(int x, int y) {
    return x >= 0 && y >= 0 && x < width && y < height;
  }

  // private boolean isInRange(int x, int y, int dist, int tx, int ty) {
  // return dist < 0 || (between(tx, x, dist) && between(ty, y, dist) &&
  // exact(x, y, tx, ty, dist));
  // }
  //
  // private boolean between(int pointA, int pointB, int dist) {
  // return Math.abs(pointA - pointB) < dist;
  // }
  //
  // private boolean exact(double x1, double y1, double x2, double y2, double
  // dist) {
  // return Math.round(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2),
  // 2))) < dist;
  // }

}
