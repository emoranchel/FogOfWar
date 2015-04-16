package net.edzero.fow.impl;

import net.edzero.fow.FogOfWar;
import net.edzero.fow.util.Geometry;

public class BresenhamFogOfWar implements FogOfWar {

  private byte[][] data;
  private int width;
  private int height;

  public void initialize(int width, int height) {
    this.width = width;
    this.height = height;
    data = new byte[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        data[x][y] = NON_VISIBLE;
      }
    }
  }

  public void addObstacle(int x, int y) {
  }

  public void addPointOfView(int cx, int cy, int distance) {
    Geometry.circle(cx, cy, distance, (int destinationX, int destinationY) -> {
      mark(destinationX, destinationY);
    });
  }

  public byte getValue(int x, int y) {
    return data[x][y];
  }

  private void mark(int x, int y) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      data[x][y] = VISIBLE;
    }
  }

}
