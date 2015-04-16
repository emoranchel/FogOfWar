package net.edzero.fow.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import net.edzero.fow.FogOfWar;
import net.edzero.fow.util.Geometry;

public class BresenhamFogOfWar implements FogOfWar {

  private byte[][] data;
  private boolean[][] obstacles;
  private int width;
  private int height;

  @Override
  public void initialize(int width, int height) {
    this.width = width;
    this.height = height;
    obstacles = new boolean[width][height];
    data = new byte[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        data[x][y] = NON_VISIBLE;
      }
    }
  }

  @Override
  public void addObstacle(int x, int y) {
    obstacles[x][y] = true;
  }

  @Override
  public void addPointOfView(int cx, int cy, int distance) {
    Geometry.circle(cx, cy, distance, (int destinationX, int destinationY) -> {
      AtomicBoolean obstructed = new AtomicBoolean(false);
      Geometry.line(cx, cy, destinationX, destinationY, (int lineX, int lineY) -> {
        if (!obstructed.get()) {
          reveal(lineX, lineY);
          if (isObstacle(lineX, lineY)) {
            obstructed.set(true);
          }
        }
      });
    });
  }

  @Override
  public byte getValue(int x, int y) {
    return data[x][y];
  }

  private void reveal(int x, int y) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      data[x][y] = VISIBLE;
    }
  }

  private boolean isObstacle(int x, int y) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      return obstacles[x][y];
    }
    return true;
  }

}
