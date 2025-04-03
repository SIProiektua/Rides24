package gui;

import javax.swing.*;
import domain.Driver;
import domain.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonAddCar = null;

	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton = new JRadioButton("English");
	private JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Castellano");
	private JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Euskera");
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JButton jButtonMovements = new JButton();
	private final JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton BcancelRide = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CancelRide"));

	/**
	 * This is the default constructor
	 */
	public MainGUI(Driver d) {
		super();
		this.setSize(495, 360);

		jContentPane = new JPanel();

		setContentPane(jContentPane);
		jContentPane.setLayout(new GridLayout(0, 1, 0, 0));
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		jContentPane.add(jLabelSelectOption);

		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI((User) d, "MainGUI");
				a.setVisible(true);
				dispose();
			}
		});

		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (d.getCars().size() == 0) {
					JOptionPane.showMessageDialog(null, "You must add a car first!", "Warning",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				JFrame a = new CreateRideGUI(d);
				a.setVisible(true);
				dispose();
			}
		});
		jContentPane.add(jButtonCreateQuery);
		jContentPane.add(jButtonQueryQueries);

		jButtonAddCar = new JButton();
		jButtonAddCar.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AddCar"));
		jButtonAddCar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new AddCarGUI(d);
				a.setVisible(true);
				dispose();
			}
		});
		jButtonMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MoneyManageGUI b = new MoneyManageGUI(d);
				b.setVisible(true);
				dispose();
			}
		});
		jButtonMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Movements"));

		jContentPane.add(jButtonMovements);
		jContentPane.add(jButtonAddCar);

		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);

		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				paintAgain();
			}
		});
		BcancelRide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RideManageGUI r = new RideManageGUI(d);
					r.setVisible(true);
					dispose();
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null,
							ResourceBundle.getBundle("Etiquetas").getString("RideManager.NoRides"), "Warning",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		jContentPane.add(BcancelRide);
		buttonGroup.add(rdbtnNewRadioButton_1);

		panel = new JPanel();
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		jContentPane.add(panel);

		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		panel.add(rdbtnNewRadioButton);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectGUI b = new SelectGUI();
				b.setVisible(true);
				dispose();
			}
		});

		panel.add(btnBack);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + " - driver :" + d.getName());

	}

	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonAddCar.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AddCar"));
		jButtonMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Movements"));

	}

}
