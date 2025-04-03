package gui;

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
import businessLogic.*;


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
	BLFacade facade = ApplicationLauncher.getBusinessLogic();
	/**
	 * Launch the application.
	 */


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
				
				try {
					String s1 = new String(passwordField.getPassword());
					if (facade.getPass(UserL.getText(), s1)) {
						User d = facade.getUserByEmail(UserL.getText());
						if (d instanceof Traveler) {
							MainGUIt a = new MainGUIt((Traveler) d);
							a.setVisible(true);
							dispose();
						} else {
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

		JButton JRegisterBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
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
