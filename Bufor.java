package Lab5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JTextArea;

class Bufor
{
  private BlockingQueue<Integer> content;
  private int consumers;
  private int producers;
  private int size;
  private JTextArea out;
  private VisualPanel visualPanel;
  
  public Bufor(JTextArea out, VisualPanel visualPanel, int size, int consumers, int producers)
  {
    this.out = out;
    this.visualPanel = visualPanel;
    this.size = size;
    this.producers = producers;
    this.consumers = consumers;
    content = new ArrayBlockingQueue<Integer>(size, true);
    visualPanel.changeBufferSize(size);
  }
  
  public synchronized void get(int id) {
    out.append("Konsument #" + id + ": Chce cos skonsumowac.\n");
    out.setCaretPosition(out.getDocument().getLength());
    while (content.isEmpty()) {
      out.append("Konsument #" + id + ": Bufor jest pusty wiec czekam.\n");
      out.setCaretPosition(out.getDocument().getLength());
      visualPanel.prepareSituation((Integer[])content.toArray(new Integer[0]), consumers, producers, true, id, 1);
      visualPanel.repaint();
      try { Thread.sleep(50L); } catch (InterruptedException e1) {}
      try { wait();
      } catch (InterruptedException e) {} }
    visualPanel.prepareSituation((Integer[])content.toArray(new Integer[0]), consumers, producers, true, id, 2);
    visualPanel.repaint();
    try {
      Thread.sleep(150L);
      char t = (char)((Integer)content.take()).intValue();
      out.append("Konsument #" + id + ": Zabieram " + t + ".\n");
      out.setCaretPosition(out.getDocument().getLength());
    } catch (InterruptedException e) {}
    visualPanel.prepareSituation((Integer[])content.toArray(new Integer[0]), consumers, producers, true, id, 0);
    visualPanel.repaint();
    out.append("Konsument #" + id + ": Konsumuje.\n");
    out.setCaretPosition(out.getDocument().getLength());
    notifyAll();
  }
  
  public synchronized void put(int id, char stuff) {
    out.append("Producent #" + id + ": Chce oddac " + stuff + ".\n");
    out.setCaretPosition(out.getDocument().getLength());
    while (content.size() == size) {
      out.append("Producent #" + id + ": Bufor jest zajety - czekam.\n");
      out.setCaretPosition(out.getDocument().getLength());
      visualPanel.prepareSituation((Integer[])content.toArray(new Integer[0]), consumers, producers, false, id, 1);
      visualPanel.repaint();
      try {
        Thread.sleep(50L);
        wait();
      } catch (InterruptedException e) {}
    }
    try { content.put(Integer.valueOf(stuff)); } catch (InterruptedException e) {}
    visualPanel.prepareSituation((Integer[])content.toArray(new Integer[0]), consumers, producers, false, id, 2);
    visualPanel.repaint();
    try { Thread.sleep(150L); } catch (InterruptedException e) {}
    out.append("Producent #" + id + ": Oddalem " + stuff + ".\n");
    out.setCaretPosition(out.getDocument().getLength());
    visualPanel.prepareSituation((Integer[])content.toArray(new Integer[0]), consumers, producers, false, id, 0);
    visualPanel.repaint();
    out.append("Producent #" + id + ": Produkuje.\n");
    out.setCaretPosition(out.getDocument().getLength());
    notifyAll();
  }
}
