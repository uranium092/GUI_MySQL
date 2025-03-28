package app;
import java.awt.*;

import javax.swing.*;
public class View extends JFrame{
	public static void main(String[] args) {
		new View();
	}
	private JComboBox<String>menu;
	private JPanel tableContainer;
	private JButton deleteButton;
	public View(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(700,100,500,400);
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		menu=new JComboBox<String>();
		tableContainer=new JPanel();
		tableContainer.setLayout(new BorderLayout());
		panel.add(menu,BorderLayout.NORTH);
		panel.add(tableContainer,BorderLayout.CENTER);
		JPanel optionsContainer=new JPanel();
		deleteButton=new JButton("Delete");
		optionsContainer.add(deleteButton);
		panel.add(optionsContainer, BorderLayout.SOUTH);
		add(panel);
		new Bridge(this);
		setVisible(true);
	}
	
	public JComboBox<String> getMenu(){
		return menu;
	}
	
	public JPanel getTableContainer() {
		return tableContainer;
	}
	
	public JButton getDeleteButton() {
		return deleteButton;	
		
	}
	
}