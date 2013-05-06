package fr.adrienlombard.clock;

import javax.swing.UIManager;

public class Main extends Thread{
	
	public Ihm i;
	
	public Main(Ihm i) {
		this.i = i;
	}
	
	public static void main(String[] args) {
		
		try {
			// Look & feel système
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			// Look & feel java par défaut
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		Main m = new Main(new Ihm(new Clock()));
		
		m.start();
	}
	
	public void run() { 
		
		while(true) {
			
			i.updateLabel();
			
			try {
				Main.sleep(1_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}
	
}
