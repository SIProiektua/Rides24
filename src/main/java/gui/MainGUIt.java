package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import domain.Traveler;
import domain.User;

import java.awt.GridLayout;

@SuppressWarnings("deprecation")
public class MainGUIt extends JFrame {

	private Traveler travel;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JRadioButton rdbtnNewRadioButton = new JRadioButton("English");
	private JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Español");
	private JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Euskera");
	private JPanel panel;
	private JButton jButtonManageBooks;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonMovements = new JButton();

	protected JLabel jLabelSelectOption;
	private JButton btnBack;

	public MainGUIt(Traveler t) {
		super();
		travel = t;
		this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);

		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.BookRide"));
		jButtonCreateQuery.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				BookRideGUI a = new BookRideGUI(t);
				a.setVisible(true);
				dispose();
			}
		});
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(0, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonCreateQuery);

		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);

		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				paintAgain();
			}
		});

		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.QueryRides"));
		jButtonQueryQueries.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				FindRidesGUI b = new FindRidesGUI((User) t, "MainGUIt");
				b.setVisible(true);
				dispose();
			}
		});
		jContentPane.add(jButtonQueryQueries);

		jButtonManageBooks = new JButton();
		jButtonManageBooks.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.ManageBooks"));
		jContentPane.add(jButtonManageBooks);
		jButtonManageBooks.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (!travel.getBookList().isEmpty()) {
					BookManageGUI b = new BookManageGUI(travel);
					b.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							ResourceBundle.getBundle("Etiquetas").getString("BookManageGUI.BookError"), "Warning",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		jButtonMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MoneyManageGUIt b = new MoneyManageGUIt(t);
				b.setVisible(true);
				dispose();
			}
		});
		jButtonMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Movements"));

		jContentPane.add(jButtonMovements);
		buttonGroup.add(rdbtnNewRadioButton_2);

		panel = new JPanel();
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		jContentPane.add(panel);

		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectGUI b = new SelectGUI();
				b.setVisible(true);
				dispose();
			}
		});
		panel.add(btnBack);
		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + " - travel :"
				+ travel.getName());

	}

	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.SelectOption"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.BookRide"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.QueryRides"));
		jButtonManageBooks.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIt.ManageBooks"));
		jButtonMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Movements"));
	}
}
