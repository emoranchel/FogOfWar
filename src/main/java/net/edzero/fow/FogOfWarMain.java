package net.edzero.fow;

import java.util.Random;

public class FogOfWarMain {
  private static final Random random = new Random();

  public static void main(String[] args) {
    final FogOfWarWindow window = new FogOfWarWindow();
    window.setVisible(true);
    new Thread(new Runnable() {
      public void run() {
        int[][] things = new int[1][3];
        for (int[] thing : things) {
          thing[0] = 20;
          thing[1] = 20;
          thing[2] = random.nextInt(10) + 5;
        }
        window.setPlayerPositions(things);
        while (true) {
          boolean first = true;
          for (int[] thing : things) {
            if (!first) {
              move(thing);
            }
            first = false;
          }
          long calculusTime = System.currentTimeMillis();
          FogOfWar fogOfWar = new FogOfWar(FogOfWarMap.getWidth(), FogOfWarMap.getHeight());
          for (int mx = 0; mx < FogOfWarMap.getWidth(); mx++) {
            for (int my = 0; my < FogOfWarMap.getHeight(); my++) {
              if (FogOfWarMap.isObstacle(mx, my)) {
                fogOfWar.addObstacle(mx, my);
              }
            }
          }
          for (int[] thing : things) {
            fogOfWar.addRevealer(thing[0], thing[1], thing[2]);
          }
          calculusTime = System.currentTimeMillis() - calculusTime;
          long paintTime = System.currentTimeMillis();
          window.paintFogOfWar(fogOfWar);
          paintTime = System.currentTimeMillis() - paintTime;
          if (calculusTime != 0) {
            System.out.println(String.format("Fog calculus: %5d Paint: %5d", calculusTime, paintTime));
          }
          try {
            Thread.sleep(25);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }

      private void move(int[] thing) {
        switch (random.nextInt(8)) {
        case 0:
          thing[0]++;
          break;
        case 1:
          thing[0]--;
          break;
        case 2:
          thing[1]++;
          break;
        case 3:
          thing[1]--;
          break;
        case 4:
          thing[0]--;
          thing[1]--;
          break;
        case 5:
          thing[0]++;
          thing[1]--;
          break;
        case 6:
          thing[0]--;
          thing[1]++;
          break;
        case 7:
          thing[1]++;
          thing[0]++;
          break;
        }
        thing[0] = thing[0] < 0 ? 0 : (thing[0] >= FogOfWarMap.getWidth() ? FogOfWarMap.getWidth() - 1 : thing[0]);
        thing[1] = thing[1] < 0 ? 0 : (thing[1] >= FogOfWarMap.getHeight() ? FogOfWarMap.getHeight() - 1 : thing[1]);
      }
    }).start();
  }
}
