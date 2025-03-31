package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton RegisterB;
	private JButton LoginB;
	private JButton queryButton;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectGUI frame = new SelectGUI();
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
	public SelectGUI() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		RegisterB = new JButton();
		RegisterB.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectGUI.Register"));
		RegisterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI a = new RegisterGUI();
				a.setVisible(true);
				dispose();
			}
		});
		RegisterB.setBounds(80, 11, 283, 51);
		contentPane.add(RegisterB);

		LoginB = new JButton();
		LoginB.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectGUI.Login"));
		LoginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI b = new LoginGUI();
				b.setVisible(true);
				dispose();
			}
		});

		LoginB.setBounds(80, 135, 283, 51);
		contentPane.add(LoginB);

		queryButton = new JButton();
		queryButton.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectGUI.QueryRides"));
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindRidesGUI c = new FindRidesGUI("SelectGUI");
				c.setVisible(true);
				dispose();
			}
		});

		queryButton.setBounds(80, 73, 283, 51);
		contentPane.add(queryButton);


		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);

		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);

		panel = new JPanel();
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		panel.setBounds(10, 201, 414, 49);
		contentPane.add(panel);

	}
	private void paintAgain() {
		RegisterB.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectGUI.Register"));
		LoginB.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectGUI.Login"));
		queryButton.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectGUI.QueryRides"));
	}
}
