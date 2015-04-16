package net.edzero.fow.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GridPanel extends JPanel {

  private Image buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
  private int gridSize = 10;

  @Override
  public void paint(Graphics g) {
    super.paint(g); //To change body of generated methods, choose Tools | Templates.
    g.drawImage(buffer, 0, 0, null);
  }

  public <T> void set(T[][] data, ColorPicker<T> colorPicker) {
    int width = data.length;
    int height = width > 0 ? data[0].length : 1;
    buffer = new BufferedImage(width * gridSize, height * gridSize, BufferedImage.TYPE_INT_RGB);

    Graphics g = buffer.getGraphics();
    g.setColor(getBackground());
    g.fillRect(0, 0, width * gridSize, height * gridSize);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < data[x].length; y++) {
        Color c = colorPicker.pick(data[x][y]);
        if (c != null) {
          g.setColor(c);
          g.fillRect(x * gridSize, y * gridSize, gridSize, gridSize);
        }
      }
    }

    g.setColor(getForeground());
    for (int i = 1; i < width; i++) {
      g.drawLine(0, i * gridSize, width * gridSize, i * gridSize);
    }
    for (int i = 1; i < height; i++) {
      g.drawLine(i * gridSize, 0, i * gridSize, height * gridSize);
    }
  }

  public int getGridSize() {
    return gridSize;
  }

  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
  }

}
