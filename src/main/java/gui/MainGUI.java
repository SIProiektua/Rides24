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

    private Driver driver;
	private static final long serialVersionUID = 1L;
//	private JPanel contentPane = new JPanel();
	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonAddCar = null;


	
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton = new JRadioButton("English");
	private JRadioButton rdbtnNewRadioButton_1 =  new JRadioButton("Euskara");
	private JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();	
	private final JButton jButtonAddCar_1 = new JButton();
	private final JButton btnBack = new JButton("Back"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * This is the default constructor
	 */
	public MainGUI(Driver d) {
		super();
		driver=d;
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
						JFrame a = new FindRidesGUI((User) d,"MainGUI");
						a.setVisible(true);
						dispose();
					}
				});
				
				
						jButtonCreateQuery = new JButton();
						jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
						jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent e) {
								if(driver.getCars().size()==0){
									JOptionPane.showMessageDialog(null, "You must add a car first!", "Warning", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								JFrame a = new CreateRideGUI(driver);
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
						JFrame a = new AddCarGUI(driver);
						a.setVisible(true);
						dispose();
					}
				});
				jContentPane.add(jButtonAddCar);
		
				
				rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent arg0) {
						Locale.setDefault(new Locale("eus"));
						System.out.println("Locale: "+Locale.getDefault());
						paintAgain();
					}
				});
				buttonGroup.add(rdbtnNewRadioButton_2);
				
						
						rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
							@SuppressWarnings("deprecation")
							public void actionPerformed(ActionEvent e) {
								Locale.setDefault(new Locale("es"));
								System.out.println("Locale: "+Locale.getDefault());
								paintAgain();
							}
						});
						jButtonAddCar_1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								MoneyManageGUI b = new MoneyManageGUI(driver);
								b.setVisible(true);
								dispose();
							}
						});
						jButtonAddCar_1.setText((String) null);
						
						jContentPane.add(jButtonAddCar_1);
						buttonGroup.add(rdbtnNewRadioButton_1);
						
								panel = new JPanel();
								panel.add(rdbtnNewRadioButton_1);
								panel.add(rdbtnNewRadioButton_2);
								jContentPane.add(panel);
								
								

								
								rdbtnNewRadioButton.addActionListener(new ActionListener() {
									@SuppressWarnings("deprecation")
									public void actionPerformed(ActionEvent e) {
										Locale.setDefault(new Locale("en"));
										System.out.println("Locale: "+Locale.getDefault());
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
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + " - driver :"+driver.getName());

		
	}
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonAddCar.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AddCar"));

	}

} // @jve:decl-index=0:visual-constraint="0,0"

