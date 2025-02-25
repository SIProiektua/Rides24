package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
					
				}catch (Exception exc) {
					
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
