package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Driver;
import domain.Traveler;
import exceptions.EmptyImputException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import dataAccess.*;

public class RegisterGUI extends JFrame {
	private JPanel contentPane;
	private JTextField userName;
	private JTextField email;
	private JPasswordField passwordField;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton;
	private Boolean state=false;
	private MainGUI a;
	private MainGUIt b;
	private JButton JRegisterB;
	private JTextField NA;
	private JTextField Name;
	private JLabel lblNewLabel_4;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JRadioButton rdbtnDriver = new JRadioButton(Messages.getString("RegisterGUI.1")); //$NON-NLS-1$JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(Messages.getString("RegisterGUI.1")); //$NON-NLS-1$
		JRadioButton rdbtnTraveler = new JRadioButton(Messages.getString("RegisterGUI.2"));  //$NON-NLS-1$
		userName = new JTextField();
		userName.setBounds(189, 11, 213, 20);
		contentPane.add(userName);
		userName.setColumns(10);
		email = new JTextField();
		email.setBounds(189, 53, 213, 20);
		contentPane.add(email);
		email.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(189, 95, 213, 20);
		contentPane.add(passwordField);
		
	
		rdbtnTraveler.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					rdbtnDriver.enable(false);
					state=true;
				}
			}
		});
		rdbtnTraveler.setBounds(35, 193, 109, 23);
		contentPane.add(rdbtnTraveler);
		
		rdbtnDriver.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					rdbtnTraveler.enable(false);
				}
			}
		});
		rdbtnDriver.setBounds(292, 193, 109, 23);
		contentPane.add(rdbtnDriver);
		JRegisterB = new JButton(Messages.getString("RegisterGUI.3")); //$NON-NLS-1$
		JRegisterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(email.getText().equals(Name.getText()) && NA.getText().equals(userName.getText()) && passwordField.getText().equals(NA.getText())) {
					//throw new EmptyImputException();
				}else {
					if(state==true) {
						b = new MainGUIt(new Traveler(email.getText(),Name.getText(),NA.getText(),userName.getText(), passwordField.getText()));
						ApplicationLauncher.da.createUser(state, email.getText(),Name.getText(),NA.getText(),userName.getText(), passwordField.getText());
					}else {
						a = new MainGUI(new Driver(email.getText(),Name.getText(),NA.getText(),userName.getText(), passwordField.getText()));
						ApplicationLauncher.da.createUser(state, email.getText(),Name.getText(),NA.getText(),userName.getText(), passwordField.getText());
						a.setVisible(true);
					}
				}
			}
		});
		//a.pack();
		JRegisterB.setBounds(39, 227, 350, 23);
		contentPane.add(JRegisterB);
		
		JLabel lblNewLabel = new JLabel(Messages.getString("RegisterGUI.4")); //$NON-NLS-1$
		lblNewLabel.setBounds(38, 98, 58, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("RegisterGUI.5")); //$NON-NLS-1$
		lblNewLabel_1.setBounds(38, 56, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(Messages.getString("RegisterGUI.6")); //$NON-NLS-1$
		lblNewLabel_2.setBounds(38, 14, 58, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(Messages.getString("RegisterGUI.lblNewLabel_3.text")); //$NON-NLS-1$
		lblNewLabel_3.setBounds(39, 143, 24, 14);
		contentPane.add(lblNewLabel_3);
		
		NA = new JTextField();
		NA.setColumns(10);
		NA.setBounds(189, 126, 213, 20);
		contentPane.add(NA);
		
		Name = new JTextField();
		Name.setColumns(10);
		Name.setBounds(189, 166, 213, 20);
		contentPane.add(Name);
		
		lblNewLabel_4 = new JLabel(Messages.getString("RegisterGUI.lblNewLabel_4.text")); //$NON-NLS-1$
		lblNewLabel_4.setBounds(35, 168, 61, 20);
		contentPane.add(lblNewLabel_4);
	}
}
