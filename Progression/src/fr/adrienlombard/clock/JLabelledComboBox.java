package fr.adrienlombard.clock;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JLabelledComboBox extends JPanel {

	private static final long serialVersionUID = 8894517924093970189L;
	private JLabel label;
	private JComboBox<Object> combo;
	
	public JLabelledComboBox() {
		this.label = new JLabel();
		this.combo = new JComboBox<Object>();
		init();
	}
	
	public JLabelledComboBox(String lblText) {
		this.label = new JLabel(lblText);
		this.combo = new JComboBox<Object>();
		init();
	}
	
	public JLabelledComboBox(Object[] objects) {
		this.label = new JLabel();
		this.combo = new JComboBox<Object>(objects);
		init();
	}
	
	public JLabelledComboBox(String lblText, Object[] objects) {
		this.label = new JLabel(lblText);
		this.combo = new JComboBox<Object>(objects);
		init();
	}
	
	public JLabelledComboBox(JLabel label, JComboBox<Object> combo) {
		this.label = label;
		this.combo = combo;
		init();
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		this.add(this.label, BorderLayout.WEST);
		this.add(this.combo, BorderLayout.EAST);
	}
	
	public void setLabelText(String text) {
		this.label.setText(text);
	}
	
	public String getLabelText() {
		return this.label.getText();
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JComboBox<Object> getCombo() {
		return combo;
	}

	public void setCombo(JComboBox<Object> combo) {
		this.combo = combo;
	}
	
}
