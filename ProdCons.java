package Lab5;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ProdCons extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private boolean started;
  private List<Producer> Producers = new ArrayList<Producer>();
  private List<Consumer> Consumers = new ArrayList<Consumer>();
  
  private JMenuBar menubar = new JMenuBar();
  private JPanel main = new JPanel();
  private VisualPanel visualPanel = new VisualPanel();
  
  private JTextArea proconData = new JTextArea();
  
  private Bufor bufor;
  private final JMenu[] menu = { new JMenu("Plik"), new JMenu("Pomoc") };
  private final JMenuItem[] items = { new JMenuItem("Zakoncz"), new JMenuItem("Informacje"), new JMenuItem("O programie"), new JMenuItem("Restart") };
  
  private final JLabel label1 = new MyLabel("Rozmiar bufora:");
  private final JLabel label2 = new MyLabel("Producenci:");
  private final JLabel label3 = new MyLabel("Konsumenci");
  
  private final JComboBox<Integer> buforN = new JComboBox<Integer>();
  private final JComboBox<Integer> ProducersN = new JComboBox<Integer>();
  private final JComboBox<Integer> ConsumersN = new JComboBox<Integer>();
  
  private final JButton start = new MyButton("Start");
  private final JButton stop = new MyButton("Pauza");
  
  private final Component hs1 = Box.createHorizontalStrut(1000);
  private final Component hs2 = Box.createHorizontalStrut(1000);
  private final Component hs3 = Box.createHorizontalStrut(1000);
  
  private final Component hdsvisualPanel = Box.createHorizontalStrut(600);
  private final Component vdsvisualPanel = Box.createVerticalStrut(280);
 
  class MyButton extends JButton{
		
		private static final long serialVersionUID = 1L;
		
		public MyButton(){
			this(null);
		}
		
		public MyButton(String text) {
			super(text);
			super.setContentAreaFilled(true);
			this.setBackground(Color.orange);
			this.setForeground(Color.white);
		}
	}
  
  class MyLabel extends JLabel {
		private static final long serialVersionUID = 1L;
		
		public MyLabel(){
			this(null);
		}
		
		public MyLabel(String text){
			super(text);
			this.setForeground(Color.white);
		}
	}
  
  public ProdCons() {
    super("ProdConsApliccation Java");
    setResizable(false);
    setSize(700, 700);
    setDefaultCloseOperation(3);
    setLocationRelativeTo(null);
    main.setBackground(Color.black);
    for (int i = 0; i < items.length; i++)
      items[i].addActionListener(this);
    menu[0].add(items[3]);
    menu[0].add(items[0]);
    menu[1].add(items[1]);
    menu[1].add(items[2]);
    for (int i = 0; i < menu.length; i++)
      menubar.add(menu[i]);
    setJMenuBar(menubar);
    for (int i = 1; i < 9; i++)
      buforN.addItem(Integer.valueOf(i));
    for (int i = 1; i < 8; i++) {
      ProducersN.addItem(Integer.valueOf(i));
      ConsumersN.addItem(Integer.valueOf(i));
    }
    buforN.addActionListener(this);
    ProducersN.addActionListener(this);
    ConsumersN.addActionListener(this);
    start.addActionListener(this);
    stop.addActionListener(this);
    main.add(label1);
    main.add(buforN);
    main.add(label2);
    main.add(ProducersN);
    main.add(label3);
    main.add(ConsumersN);
    main.add(hs1);
    proconData.setEditable(false);
    proconData.setFont(new Font("", 0, 12));
    proconData.setForeground(Color.WHITE);
    proconData.setColumns(32);
    proconData.setRows(16);
    proconData.setBackground(Color.BLACK);
    JScrollPane scrollPane = new JScrollPane(proconData);
    scrollPane.setHorizontalScrollBarPolicy(31);
    main.add(scrollPane);
    main.add(hs2);
    main.add(start);
    main.add(stop);
    main.add(hs3);
    visualPanel.add(vdsvisualPanel);
    visualPanel.add(hdsvisualPanel);
    main.add(visualPanel);
    setContentPane(main);
    visualPanel.setBackground(Color.BLACK);
    setVisible(true);
  }
  
  public void actionPerformed(ActionEvent evt) {
    Object src = evt.getSource();
    if (src == start) {
      if (!started) {
        bufor = new Bufor(proconData, visualPanel, ((Integer)buforN.getSelectedItem()).intValue(), ((Integer)ConsumersN.getSelectedItem()).intValue(), ((Integer)ProducersN.getSelectedItem()).intValue());
        for (int i = 1; i <= ((Integer)ProducersN.getSelectedItem()).intValue(); i++)
          Producers.add(new Producer(bufor, i));
        for (int i = 1; i <= ((Integer)ConsumersN.getSelectedItem()).intValue(); i++)
          Consumers.add(new Consumer(bufor, i));
        Producer p; for (Iterator<Producer> i$ = Producers.iterator(); i$.hasNext(); p.start()) p = (Producer)i$.next();
        Consumer k; for (Iterator<Consumer> i$ = Consumers.iterator(); i$.hasNext(); k.start()) k = (Consumer)i$.next();
        started = true;
        buforN.setEnabled(false);
        ProducersN.setEnabled(false);
        ConsumersN.setEnabled(false);
      } else { Producer p;
        for (Iterator<Producer> i$ = Producers.iterator(); i$.hasNext(); p.play()) p = (Producer)i$.next();
        Consumer k; for (Iterator<Consumer> i$ = Consumers.iterator(); i$.hasNext(); k.play()) k = (Consumer)i$.next();
      }
    }
    if (src == stop) { Producer p;
      for (Iterator<Producer> i$ = Producers.iterator(); i$.hasNext(); p.pause()) p = (Producer)i$.next();
      Consumer k; for (Iterator<Consumer> i$ = Consumers.iterator(); i$.hasNext(); k.pause()) k = (Consumer)i$.next();
    }
    if (src == items[3]) {
      dispose();
      new ProdCons();
    }
    if (src == items[0])
      dispose();
    if (src == items[1]) {
      JOptionPane.showMessageDialog(null, "Program symuluje dzialanie\nProducentow i Konsumentow."
      		+ "\n\nWatki (kulki) majace w danej chwili dostep do bufora sa kolorowane na czerwono."
      		+ "\n\nWstrzymanie symulacji nastepuje z krotkim opoznieniem. ", "Wprowadzenie", getDefaultCloseOperation());
    }
    
    if (src == items[2])
      JOptionPane.showMessageDialog(null, "Symulacja graficzna programu ProdCons\n Autor: Monika Foks", "O programie", getDefaultCloseOperation());
  }
  
  public static void main(String[] args) {
    new ProdCons();
  }
}
