package net.edzero.fow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FogOfWarWindow extends JFrame {
  private static final long serialVersionUID = 1L;
  private static final int SQUARE = 10;
  private Image mapImage;
  private Image fogImage;
  private int[][] is = new int[0][0];

  public FogOfWarWindow() {
    mapImage = new BufferedImage(FogOfWarMap.getWidth() * SQUARE, FogOfWarMap.getHeight() * SQUARE, BufferedImage.TYPE_INT_ARGB);
    fogImage = mapImage;
    Graphics g = mapImage.getGraphics();
    for (int x = 0; x < FogOfWarMap.getWidth(); x++) {
      for (int y = 0; y < FogOfWarMap.getHeight(); y++) {
        switch (FogOfWarMap.getValue(x, y)) {
        case 0:
          g.setColor(new Color(255, 255, 255));
          break;
        case 1:
          g.setColor(new Color(150, 100, 100));
          break;
        case 2:
          g.setColor(new Color(123, 150, 150));
          break;
        }
        g.fillRect(x * SQUARE, y * SQUARE, SQUARE, SQUARE);
        g.setColor(new Color(12, 15, 30));
        g.drawRect(x * SQUARE, y * SQUARE, SQUARE, SQUARE);
        g.setColor(Color.WHITE);
      }
    }
    this.setSize(mapImage.getWidth(null) + 20, mapImage.getHeight(null) + 40);
    this.setLayout(new BorderLayout());
    JPanel panel = new JPanel() {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(mapImage, 0, 0, null);
        g.drawImage(fogImage, 0, 0, null);
        for (int[] pl : is) {
          g.fillOval(pl[0] * SQUARE, pl[1] * SQUARE, SQUARE, SQUARE);
        }
      }
    };
    this.add(panel, BorderLayout.CENTER);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addKeyListener(new KeyListener() {
      public void keyTyped(KeyEvent e) {
      }

      public void keyReleased(KeyEvent e) {
      }

      public void keyPressed(KeyEvent e) {
        int thing[] = is[0];
        switch (e.getKeyCode()) {
        case KeyEvent.VK_RIGHT:
          thing[0]++;
          break;
        case KeyEvent.VK_LEFT:
          thing[0]--;
          break;
        case KeyEvent.VK_DOWN:
          thing[1]++;
          break;
        case KeyEvent.VK_UP:
          thing[1]--;
          break;
        }
        thing[0] = thing[0] < 0 ? 0 : (thing[0] >= FogOfWarMap.getWidth() ? FogOfWarMap.getWidth() - 1 : thing[0]);
        thing[1] = thing[1] < 0 ? 0 : (thing[1] >= FogOfWarMap.getHeight() ? FogOfWarMap.getHeight() - 1 : thing[1]);

      }
    });
  }

  public void paintFogOfWar(FogOfWar fog) {
    fogImage = new BufferedImage(fog.getWidth() * SQUARE, fog.getHeight() * SQUARE, BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics g = fogImage.getGraphics();
    for (int x = 0; x < fog.getWidth(); x++) {
      for (int y = 0; y < fog.getHeight(); y++) {
        switch (fog.getValue(x, y)) {
        case FogOfWar.NON_VISIBLE:
          g.setColor(new Color(0, 0, 0, 200));
          break;
        // case FogOfWar.OBSTRUCTED:
        // g.setColor(new Color(0, 0, 0, 140));
        // break;
        // case FogOfWar.DIM:
        // g.setColor(new Color(0, 0, 0, 80));
        // break;
        case FogOfWar.VISIBLE:
          g.setColor(new Color(0, 0, 0, 0));
          break;
        }
        g.fillRect(x * SQUARE, y * SQUARE, SQUARE, SQUARE);
      }
    }
    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          invalidate();
          repaint();
        }
      });
    } catch (InvocationTargetException e) {
    } catch (InterruptedException e) {
    }
  }

  public void setPlayerPositions(int[][] is) {
    this.is = is;
  }
}
