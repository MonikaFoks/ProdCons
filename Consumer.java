package Lab5;

class Consumer extends Thread {
  private Bufor bufor;
  private int id;
  private boolean paused;
  
  public void play() { 
	  paused = false; 
  }
  
  public void pause() { 
	  paused = true;
  }
  
  public Consumer(Bufor bufor, int id) { 
	  this.bufor = bufor;
	  this.id = id;
  }
  
  public void run() {
    bufor.get(id);
    try { 
    	sleep((int)(Math.random() * 1000.0D) + 200); 
    } catch (InterruptedException e) {}
    
    while (paused) {
      try { 
    	  sleep(5L);
      }
      catch (InterruptedException e) {}
    }
  }
}
