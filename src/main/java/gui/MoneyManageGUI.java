package gui;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import domain.*;

public class MoneyManageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel tableModel;
	private MainGUI b;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MoneyManageGUI frame = new MoneyManageGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MoneyManageGUI(Driver driver) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 281);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton backButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.Back"));
		backButton.setBounds(233, 198, 175, 23);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = new MainGUI(driver);
				b.setVisible(true);
				dispose();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(backButton);
        String[] columnNames = {ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.ID"),
								ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.Participant"), 
								ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.Balance")};
		
		tableModel = new DefaultTableModel(columnNames, 0);
		for (int i = 0; i < driver.getMugimenduak().size(); i++) {
			Mugimendua mugimendua = driver.getMugimenduak().get(i);
            tableModel.addRow(new Object[]{mugimendua.getBook().getId(), mugimendua.getNondik().getName(), "+"+mugimendua.getZenbat()});
        }
		table = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(41, 31, 367, 157);
		contentPane.add(scrollPane);
		
		table.setBounds(41, 31, 367, 157);
		contentPane.add(table);
		JLabel lblCurrentIncome = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUI.Income")+": "); //$NON-NLS-1$ 
		lblCurrentIncome.setBounds(41, 0, 127, 17);
		contentPane.add(lblCurrentIncome);
		
		JLabel lblCurrentIncome_1 = new JLabel(driver.getBalance()+"");//$NON-NLS-1$
		lblCurrentIncome_1.setBounds(167, 2, 127, 17);
		contentPane.add(lblCurrentIncome_1);
		
		JButton addMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.OutMoney"));
		addMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int mQuant =  Integer.parseInt(JOptionPane.showInputDialog(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.AddMoney")));
					if(driver.getBalance()<mQuant){
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.AddMoneyError"), "Warning", JOptionPane.INFORMATION_MESSAGE);return;
					}else{
						driver.setBalance(driver.getBalance()-mQuant);
					}
				}catch(Exception err) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.AddMoneyError"), "Warning", JOptionPane.INFORMATION_MESSAGE);return;
				}
				lblCurrentIncome_1.setText(driver.getBalance() + "");
			}
		});
		addMoney.setBounds(30, 198, 175, 23);
		contentPane.add(addMoney);
	}
}
