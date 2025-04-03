package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Car;
import domain.Driver;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class AddCarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField plateInput;
	private JTextField licenceInput;
	private MainGUI b;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCarGUI frame = new AddCarGUI(new Driver(null,null,null,null,null));
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
	public AddCarGUI(Driver driver) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 337, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel plateLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.plate"));
		plateLabel.setBounds(38, 12, 150, 30);
		contentPane.add(plateLabel);
		
		JLabel licenceLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.insurance"));
		licenceLabel.setBounds(38, 54, 150, 30);
		contentPane.add(licenceLabel);
		
		plateInput = new JTextField();
		plateInput.setBounds(200, 16, 114, 21);
		contentPane.add(plateInput);
		plateInput.setColumns(10);
		
		licenceInput = new JTextField();
		licenceInput.setBounds(200, 58, 114, 21);
		contentPane.add(licenceInput);
		licenceInput.setColumns(10);
		
		JButton btnAddCar = new JButton("Add");
		btnAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(plateInput.getText().trim().length()==0) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.plateError"), "Warning", JOptionPane.INFORMATION_MESSAGE);return;
					}
					if(licenceInput.getText().trim().length()==0) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.insuranceError"), "Warning", JOptionPane.INFORMATION_MESSAGE);return;
					}
					for(int i = 0; i<driver.getCars().size(); i++) {
						if(driver.getCars().get(i).getNumberPlate().equals(plateInput.getText())){
							JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.plateRepeatError"), "Warning", JOptionPane.INFORMATION_MESSAGE);return;
						}
					}
					Car car = new Car(plateInput.getText().trim(),licenceInput.getText());
					if(!driver.getCars().contains(car)) {
						driver.addCar(car);
					}
					
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		btnAddCar.setBounds(39, 117, 106, 27);
		contentPane.add(btnAddCar);
		
		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = new MainGUI(driver);
				b.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(163, 117, 106, 27);
		contentPane.add(btnBack);
	}
}
