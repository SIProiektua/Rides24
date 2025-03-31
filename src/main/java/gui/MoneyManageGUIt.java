package gui;

// import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import domain.*;
import businessLogic.*;

public class MoneyManageGUIt extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Mugimendua selectedMugimendua;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	private MainGUIt b;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MoneyManageGUIt frame = new MoneyManageGUIt(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MoneyManageGUIt(Traveler t) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<Mugimendua> moneyComboBox = new JComboBox<Mugimendua>();
		moneyComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				Book book = (Book) moneyComboBox.getSelectedItem();
				int id = book.getId();

				// Search in travelers' booklist the id we got.
//				for (int j = 0; j < driver.getMugimenduak().size(); j++) {
//					if (driver.getMugimenduak().get(j).getId() == id) {
//						selectedMugimendua = driver.getMugimenduak().get(j);
//					}
//				}
			}	
		});
		DefaultComboBoxModel<Mugimendua> mugiemenduaInfo = new DefaultComboBoxModel<Mugimendua>();
		for (int i = 0; i < t.getMugimenduak().size(); i++) {
			mugiemenduaInfo.addElement(t.getMugimenduak().get(i));
		}
		moneyComboBox.setModel(mugiemenduaInfo);
		moneyComboBox.setBounds(34, 10, 372, 53);
		contentPane.add(moneyComboBox);
		
		JButton addMoney = new JButton("Add money");
		addMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int mQuant =  Integer.parseInt(JOptionPane.showInputDialog(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.AddMoney")));
					t.setBalance(t.getBalance() + mQuant);
				}catch(Exception err) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.AddMoney"), "Warning", JOptionPane.INFORMATION_MESSAGE);return;
					
				}
				
			}
		});
		
		addMoney.setBounds(34, 75, 180, 27);
		contentPane.add(addMoney);

		JButton backButton = new JButton(Messages.getString("RegisterGUI.Back"));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = new MainGUIt(t);
				b.setVisible(true);
				dispose();
			}
		});
		backButton.setBounds(226, 77, 187, 23);
		contentPane.add(backButton);
	}
}
