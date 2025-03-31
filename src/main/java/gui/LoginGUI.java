package gui;

import java.awt.EventQueue;
import java.awt.event.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import domain.*;

public class LoginGUI extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField UserL;
	private JPasswordField passwordField;
	private JButton jRegisterB;
	private SelectGUI b;
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		UserL = new JTextField();
		UserL.setBounds(149, 42, 240, 20);
		contentPane.add(UserL);
		UserL.setColumns(10);

		jRegisterB = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		jRegisterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SORTU BEHAR DA DATU BASEAN ERABILTZAILEA BILATZEKO ETA GERO INFORMAZIOA SAIOA
				// HASTEKO
				try {
					String s1 = new String(passwordField.getPassword());
					System.out.println("Usuario: " + UserL.getText() + " contraseña: " + s1);
					if (ApplicationLauncher.da.getPass(UserL.getText(), s1)) {
						User d = ApplicationLauncher.da.getUserByEmail(UserL.getText());
						String s = new String(ApplicationLauncher.da.getUserTypeByEmail(UserL.getText()));
						if (s.equals("Traveler")) {
							MainGUIt a = new MainGUIt((Traveler) d);
							a.setVisible(true);
							dispose();
						} else if (s.equals("Driver")) {
							MainGUI b = new MainGUI((Driver) d);
							b.setVisible(true);
							dispose();
						}
					}
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		jRegisterB.setBounds(39, 227, 169, 23);
		contentPane.add(jRegisterB);

		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		lblNewLabel.setBounds(18, 98, 78, 14);
		contentPane.add(lblNewLabel);

		lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Email"));
		lblNewLabel_2.setBounds(17, 45, 79, 14);
		contentPane.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(149, 95, 240, 20);
		contentPane.add(passwordField);

		JButton JRegisterBack = new JButton(Messages.getString("RegisterGUI.Back"));
		JRegisterBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = new SelectGUI();
				b.setVisible(true);
				dispose();
			}
		});
		JRegisterBack.setBounds(238, 228, 187, 23);
		contentPane.add(JRegisterBack);
	}

}
