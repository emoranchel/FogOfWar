package net.edzero.fow.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.edzero.fow.util.Geometry;

public class GridTester {

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setSize(500, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    GridPanel gridPanel = new GridPanel();
    frame.add(gridPanel, BorderLayout.CENTER);

    gridPanel.setGridSize(3);
    gridPanel.setForeground(new Color(23, 49, 88));
    gridPanel.setBackground(new Color(102, 96, 180));
    frame.setVisible(true);
    final AtomicInteger radius = new AtomicInteger(3);
    final AtomicInteger delta = new AtomicInteger(1);
    final int size = 300;
    new Thread(() -> {
      while (true) {
        Integer[][] data = new Integer[size][size];
        Geometry.circle(size / 2, size / 2, radius.get(), (xCircle, yCircle) -> {
          Geometry.line(size / 2, size / 2, xCircle, yCircle, (xLine, yLine) -> {
            data[xLine][yLine] = data[xLine][yLine] == null ? 15 : data[xLine][yLine] + 15;
          });
          data[xCircle][yCircle] = -999999;
        });

        gridPanel.set(data, (Integer t) -> t == null ? null : (t > 255 ? Color.RED : (t < 0 ? Color.CYAN : new Color(t, t, t))));
        gridPanel.invalidate();
        frame.repaint();

        radius.addAndGet(delta.get());
        if (radius.get() > (size / 2) - 3) {
          delta.set(-1);
        }
        if (radius.get() < 5) {
          delta.set(1);
        }

        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
          Logger.getLogger(GridTester.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }).start();

  }
}
