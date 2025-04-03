package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import domain.*;
import businessLogic.*;

public class RideManageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Ride selectedRide;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	Driver driver;

	/**
	 * Launch the application.
	 */

	public RideManageGUI(Driver d){
		driver = (Driver) facade.getUserByEmail(d.getEmail());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultComboBoxModel<Ride> ridesInfo = new DefaultComboBoxModel<Ride>();
		for(Ride ride : driver.getRides()) {
			ridesInfo.addElement(ride);
		}
		JComboBox<Ride> rideComboBox = new JComboBox<Ride>();

		rideComboBox.setModel(ridesInfo);
		rideComboBox.setBounds(34, 10, 372, 53);
		contentPane.add(rideComboBox);
		
			rideComboBox.setSelectedIndex(0);
			selectedRide = (Ride) rideComboBox.getSelectedItem();

			JButton cancelRide = new JButton("Cancel Ride");
			cancelRide.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					facade.cancelRide(selectedRide);
					driver = (Driver) facade.getUserByEmail(d.getEmail());
					MainGUI b = new MainGUI(driver);
					b.setVisible(true);
					dispose();
					
				}
			});
			cancelRide.setBounds(34, 75, 180, 27);
			contentPane.add(cancelRide);

			JButton backButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
			backButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					MainGUI b = new MainGUI(driver);
					b.setVisible(true);
					dispose();
				}
			});
			backButton.setBounds(226, 77, 187, 23);
			contentPane.add(backButton);
		
		
	}	
}