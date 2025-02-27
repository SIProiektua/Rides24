package gui;

import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import dataAccess.*;
import domain.*;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField UserL;
	private JPasswordField passwordField;
	private boolean state=false;
	private JButton jRegisterB;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ApplicationLauncher.da.close();
			}
			
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jRegisterB = new JButton("LOGIN");
		jRegisterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SORTU BEHAR DA DATU BASEAN ERABILTZAILEA BILATZEKO ETA GERO INFORMAZIOA SAIOA HASTEKO
				try {
					if(ApplicationLauncher.da.getPass(UserL.getText(),passwordField.getText())==true) {
						if(ApplicationLauncher.da.getUserType(UserL.getName())=="Traveler") {
							MainGUIt a = new MainGUIt((Traveler)ApplicationLauncher.da.getUser2(UserL.getName()));
							a.setVisible(true);
						}else if(ApplicationLauncher.da.getUserType(UserL.getName())=="Driver") {
							MainGUI b = new MainGUI((Driver)ApplicationLauncher.da.getUser2(UserL.getName()));
							b.setVisible(true);
						}else {
							throw new Exception();
						}
					}
				}catch (Exception exc) {
					exc.getMessage();
				}
			}
		});
		jRegisterB.setBounds(39, 227, 350, 23);
		contentPane.add(jRegisterB);
		
		lblNewLabel = new JLabel("PASSWORD");
		lblNewLabel.setBounds(38, 98, 58, 14);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setBounds(38, 45, 58, 14);
		contentPane.add(lblNewLabel_2);
		
		UserL = new JTextField();
		UserL.setBounds(149, 42, 240, 20);
		contentPane.add(UserL);
		UserL.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(149, 95, 240, 20);
		contentPane.add(passwordField);
	}

}
