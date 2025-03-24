package gui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import domain.Driver;
import domain.Traveler;
import domain.User;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

public class RegisterGUI extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField userName;
	private JTextField email;
	private JPasswordField passwordField;
	private Boolean state = false;
	private MainGUI a;
	private SelectGUI b;
	private JButton JRegisterB;
	private JTextField NA;
	private JTextField Name;
	private JLabel lblNewLabel_4;

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JRadioButton rdbtnDriver = new JRadioButton(Messages.getString("RegisterGUI.1")); // JRadioButton //$NON-NLS-1$
																							// rdbtnNewRadioButton_1 =
																							// new
																							// JRadioButton(Messages.getString("RegisterGUI.1"));
		JRadioButton rdbtnTraveler = new JRadioButton(Messages.getString("RegisterGUI.2")); //$NON-NLS-1$

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
			@SuppressWarnings("deprecation")
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					rdbtnDriver.hide();
					state = true;
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					rdbtnDriver.show();
				}
			}
		});
		rdbtnTraveler.setBounds(35, 193, 109, 23);
		contentPane.add(rdbtnTraveler);

		rdbtnDriver.addItemListener(new ItemListener() {
			@SuppressWarnings("deprecation")
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					rdbtnTraveler.hide();
					state = false;
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					rdbtnTraveler.show();
				}
			}
		});
		rdbtnDriver.setBounds(292, 193, 109, 23);
		contentPane.add(rdbtnDriver);
		JRegisterB = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.RegisterB")); //$NON-NLS-1$
		JRegisterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(email.getText());
					User user = ApplicationLauncher.da.getUserByEmail(email.getText());

					if (user.getEmail().equals(email.getText()) || user.getID().equals(NA.getText())) {
						System.out.print("User Already Exists on the App");
						return;
					}
				} catch (Exception er) {
					System.out.print("New user, wellcome to the app!");
				}
				System.out.println(state);
				b = new SelectGUI();
				if (state == true) {
					String s = new String(passwordField.getPassword());
					Traveler t = new Traveler(email.getText(), Name.getText(), NA.getText().toString(),
							userName.getText(), s);
					ApplicationLauncher.da.createUser(true, NA.getText().toString(), Name.getText(),
							s, userName.getText(), email.getText());
					b.setVisible(true);
					dispose();
				} else {
					String d = new String(passwordField.getPassword());
					Driver r = new Driver(email.getText(), Name.getText(), NA.getText().toString(), userName.getText(),
							d);
					ApplicationLauncher.da.createUser(false, NA.getText().toString(), Name.getText(),
							d, userName.getText(), email.getText());
					b.setVisible(true);
					dispose();
				}
			}
		});
		// a.pack();
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
