package Lab5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JPanel;

class VisualPanel extends JPanel
{
  private static final long serialVersionUID = 1L;
  private int bufferSize;
  private Integer[] bufferContent;
  private int cquan;
  private int pquan;
  private boolean cacti;
  private int cpid;
  
  VisualPanel() {}
  
  private int[] cProgresses = new int[7];
  private int[] pProgresses = new int[7];
  
  public void changeBufferSize(int s) { bufferSize = s; }
  
  public void prepareSituation(Integer[] c, int d, int e, boolean f, int g, int h) {
    bufferContent = c;
    cquan = d;
    pquan = e;
    cacti = f;
    cpid = (g - 1);
    if (cacti) {
      cProgresses[cpid] = h;
    } else
      pProgresses[cpid] = h;
  }
  
  public void drawSituation(Graphics g) {
    int box = 36;
    Graphics2D g2 = (Graphics2D)g;
    Stroke t = g2.getStroke();
    g2.setColor(Color.GRAY);
    g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    g2.setStroke(new BasicStroke(2.0F));
    g2.setColor(Color.MAGENTA);
    if (bufferSize > 0) {
      if (bufferContent.length == bufferSize) {
        g2.fillRect(getWidth() / 2 - bufferSize * box / 2, getHeight() / 2 - box / 2, bufferSize * box, box);
      } else
        g2.drawRect(getWidth() / 2 - bufferSize * box / 2, getHeight() / 2 - box / 2, bufferSize * box, box);
      g2.setColor(Color.BLACK);
      String s = "";
      for (int i = bufferSize - 1; i >= 0; i--)
        if (i < bufferContent.length) {
          s = s + "|" + (char)bufferContent[i].intValue() + "|";
        } else
          s = s + "| |";
      g2.setFont(new Font("Monospaced", 1, 20));
      g2.drawString(s, getWidth() / 2 - bufferSize * box / 2, getHeight() / 2 + 5);
    }
    for (int i = 0; i < pquan; i++) { int w;
      if (pProgresses[i] == 2) {
        w = 130; } else {
        if (pProgresses[i] == 1) {
          w = 80;
        } else
          w = 0; }
      g2.setColor(Color.GREEN);
      if ((!cacti) && (cpid == i))
        g2.setColor(Color.RED);
      g2.fillOval(w, getHeight() / 2 - pquan * box / 2 + i * box, box, box);
    }
    for (int i = 0; i < cquan; i++) { 
      int w;
      if (cProgresses[i] == 2) {
        w = getWidth() - 130 - box; } else { 
        if (cProgresses[i] == 1) {
          w = getWidth() - 80 - box;
        } else
          w = getWidth() - box; }
      g2.setColor(Color.BLUE);
      if ((cacti) && (cpid == i))
        g2.setColor(Color.RED);
      g2.fillOval(w, getHeight() / 2 - cquan * box / 2 + i * box, box, box);
    }
    g2.setStroke(t);
  }
  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    drawSituation(g);
  }
}
