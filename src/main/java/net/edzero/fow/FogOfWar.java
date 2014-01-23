package net.edzero.fow;

public interface FogOfWar {
  public final static byte VISIBLE = 4;
  public final static byte NON_VISIBLE = 1;
  public final static byte NULL = 0;


  void initialize(int width, int height);
  void addObstacle(int x, int y);
  void addPointOfView(int x, int y, int distance);
  byte getValue(int x, int y);
          
}
