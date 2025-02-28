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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		UserL = new JTextField();
		UserL.setBounds(149, 42, 240, 20);
		contentPane.add(UserL);
		UserL.setColumns(10);
		
		jRegisterB = new JButton("LOGIN");
		jRegisterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SORTU BEHAR DA DATU BASEAN ERABILTZAILEA BILATZEKO ETA GERO INFORMAZIOA SAIOA HASTEKO
				try {
					System.out.println("holalalalalho");
					System.out.print(passwordField.getText());
					System.out.println(ApplicationLauncher.da.getPass(UserL.getText(),passwordField.getText()));
					if(ApplicationLauncher.da.getPass(UserL.getText(),passwordField.getText())) {
						System.out.println("holalalalalho2");
						User d = ApplicationLauncher.da.getUserByEmail(UserL.getText());//Se queda aqui!!
						System.out.println("Esto no deberia ser null" + d.toString());
						String s =ApplicationLauncher.da.getUserTypeByEmail(UserL.getText());
						if(s.equals("Traveler")) {
							MainGUIt a = new MainGUIt((Traveler)d);
							a.setVisible(true);
						}else if(s.equals("Driver")) {
							MainGUI b = new MainGUI((Driver)d);
							b.setVisible(true);
						}else {
							throw new Exception();
						}
					}else {
						System.out.print("NO PASO DE AQUI");
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
		
		lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setBounds(38, 45, 58, 14);
		contentPane.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(149, 95, 240, 20);
		contentPane.add(passwordField);
	}

}
