package fr.adrienlombard.clock;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Ihm extends JFrame implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = 2254900294420453430L;
	
	private JLabel l;
	private JButton b, sauve;
	private SystemTray tray;
	private TrayIcon trayIcon;
	private MenuItem openItem, exitItem;
	private JLabelledComboBox heureDebut, heureFin, heureDebutPause, heureFinPause;
	private JPanel configPane;
	private Clock clock;
	
	public Ihm(Clock clock) {
		super("Clock");
		this.clock = clock;
		l = new JLabel("", JLabel.CENTER);
		b = new JButton("Quitter");
		
		String[] heures = 	{"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30",
							 "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", 
							 "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", 
							 "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};
		
		heureDebut = new JLabelledComboBox("Heure de début", heures);
		heureFin = new JLabelledComboBox("Heure de fin", heures);
		heureDebutPause = new JLabelledComboBox("Début de la pause", heures);
		heureFinPause = new JLabelledComboBox("Fin de la pause", heures);
		sauve = new JButton("Sauvegarder");
		
		heureDebut.getCombo().addActionListener(this);
		heureFin.getCombo().addActionListener(this);
		heureDebutPause.getCombo().addActionListener(this);
		heureFinPause.getCombo().addActionListener(this);
		
		GridLayout config = new GridLayout(3, 2, 5, 5);
		
		configPane = new JPanel();
		
		configPane.setLayout(config);
		
		configPane.add(heureDebut);
		
		configPane.add(heureDebutPause);
		
		configPane.add(heureFin);
		
		configPane.add(heureFinPause);
		
		configPane.add(sauve);
		
		this.getContentPane().add(configPane, BorderLayout.NORTH);
		
		this.setSize(320, 240);
		this.getContentPane().add(l, BorderLayout.CENTER);
		this.getContentPane().add(b, BorderLayout.SOUTH);
		b.setSize(100, 60);
		
		b.addActionListener(this);
		sauve.addActionListener(this);
		
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		config(chargeConfig());
		
		this.setResizable(false);
		this.setVisible(true);		
	}
	
	public void updateLabel() {
		
		String text = clock.getProgression();
		
		l.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		
		l.setText(text);
		
		if(trayIcon != null) {
			
			text = text.replace("<html>", "");
			text = text.replace("</html>", "");
			text = text.replace("<p style=\"text-align:center;\">", "");
			text = text.replace("</p>", "\n");
			
			trayIcon.setToolTip(text);
		}
	}
	
	public void windowActivated(WindowEvent arg0) {}

	public void windowClosed(WindowEvent arg0) {}

	public void windowClosing(WindowEvent arg0) {}

	public void windowDeactivated(WindowEvent arg0) {}

	public void windowDeiconified(WindowEvent arg0) {
		
		tray.remove(trayIcon);
		
		this.setVisible(true);
	}

	public void windowIconified(WindowEvent arg0) {

		if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
		
		if (trayIcon == null) {
		
			final PopupMenu popup = new PopupMenu();
			
	       	trayIcon = null;
			try {
				trayIcon = new TrayIcon(ImageIO.read(new File("res/clock.png")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        
			if(trayIcon != null) {
			
				tray = SystemTray.getSystemTray();
		       
		        // Create a pop-up menu components
				openItem = new MenuItem("Ouvrir");
		        exitItem = new MenuItem("Exit");
		       
		        //Add components to pop-up menu
		        popup.add(openItem);
		        popup.addSeparator();
		        popup.add(exitItem);
		       
		        openItem.addActionListener(this);
		        exitItem.addActionListener(this);
		        
		        trayIcon.setPopupMenu(popup);
		       
		        try {
		            tray.add(trayIcon);
		        } catch (AWTException e) {
		            System.out.println("TrayIcon could not be added.");
		        }

		        updateLabel();
		        trayIcon.addActionListener(this);
			}
		}
		else {
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		
		if(trayIcon != null) {
			this.setVisible(false);
		}
		
	}

	public void windowOpened(WindowEvent arg0) {}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.b || e.getSource() == exitItem) {
			System.exit(0);
		}
		if(e.getSource() == openItem || e.getSource() == trayIcon)  {
			int state = this.getState();
			
			state &= ~Frame.ICONIFIED;
			
			this.setExtendedState(state);
			
			this.toFront();
			this.repaint();
			
			this.windowDeiconified(null);
		}
		if(e.getSource() == sauve) {
			sauveConfig();
		}
		if(e.getSource() == heureDebut || e.getSource() == heureFin) {
			manageCombos();
		}

	}
	
	public void sauveConfig() {
		
		Properties props = new Properties();
		
		props.setProperty("heureDebut", this.heureDebut.getCombo().getSelectedItem().toString());
		props.setProperty("heureFin", this.heureFin.getCombo().getSelectedItem().toString());
		props.setProperty("heureDebutPause", this.heureDebutPause.getCombo().getSelectedItem().toString());
		props.setProperty("heureFinPause", this.heureFinPause.getCombo().getSelectedItem().toString());
		
		try {
			FileOutputStream out = new FileOutputStream("appProperties");
			
			props.store(out, "");
			
			out.close();
			
			config(props);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Properties chargeConfig() {
		
		Properties props = new Properties();
		
		try {
			
			FileInputStream in = new FileInputStream("appProperties");
			
			props.load(in);
			
			in.close();
			
			return props;
			
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	public void config(Properties props) {
		if(props != null) {
			
			heureDebut.getCombo().setSelectedItem(props.getProperty("heureDebut"));
			heureFin.getCombo().setSelectedItem(props.getProperty("heureFin"));
			heureDebutPause.getCombo().setSelectedItem(props.getProperty("heureDebutPause"));
			heureFinPause.getCombo().setSelectedItem(props.getProperty("heureFinPause"));
			
			StringTokenizer stDeb = new StringTokenizer(props.getProperty("heureDebut"), ":");
			StringTokenizer stFin = new StringTokenizer(props.getProperty("heureFin"), ":");
			StringTokenizer stDebPau = new StringTokenizer(props.getProperty("heureDebutPause"), ":");
			StringTokenizer stFinPau = new StringTokenizer(props.getProperty("heureFinPause"), ":");
			
			clock.setDebut(new Heure(Integer.parseInt(stDeb.nextToken()), Integer.parseInt(stDeb.nextToken())));
			clock.setFin(new Heure(Integer.parseInt(stFin.nextToken()), Integer.parseInt(stFin.nextToken())));
			clock.setDebutPause(new Heure(Integer.parseInt(stDebPau.nextToken()), Integer.parseInt(stDebPau.nextToken())));
			clock.setFinPause(new Heure(Integer.parseInt(stFinPau.nextToken()), Integer.parseInt(stFinPau.nextToken())));
			clock.calculeSecondesJournee();
			
		}
	}
	
	public void manageCombos() {
		
		
		
	}

}
