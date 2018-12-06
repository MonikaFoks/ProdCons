package Lab5;

class Producer extends Thread
{
  private char stuff = 'A';
  private Bufor bufor;
  private int id;
  private boolean paused;
  
  public void play() { 
	  paused = false; 
  }
  
  public void pause() { 
	  paused = true; 
  }
  
  public Producer(Bufor bufor, int id) { 
	  this.bufor = bufor;
	  this.id = id;
  }
  
  public void run() {
    if (stuff > 'Z') stuff = 'A';
    bufor.put(id, stuff++);
    try { sleep((int)(Math.random() * 1000.0D) + 200); } catch (InterruptedException e) {}
    while (paused) {
      try { sleep(5L);
      }
      catch (InterruptedException e) {}
    }
  }
}
