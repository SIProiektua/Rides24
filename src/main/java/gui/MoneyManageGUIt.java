package gui;

// import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import domain.*;

public class MoneyManageGUIt extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private MainGUIt b;
	private DefaultTableModel tableModel;
	private JTable table;

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
		setBounds(100, 100, 450, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);	

		 String[] columnNames = {ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.ID"), ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.Participant"), ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.Balance")};
			tableModel = new DefaultTableModel(columnNames, 0);
			for (int i = 0; i < t.getMugimenduak().size(); i++) {
				Mugimendua mugimendua = t.getMugimenduak().get(i);
	            tableModel.addRow(new Object[]{mugimendua.getBook().getId(), mugimendua.getNondik().getName(), "-" + mugimendua.getZenbat()});
	        }
			table = new JTable(tableModel);
			table.setBounds(41, 31, 367, 157);
			contentPane.add(table);

		JButton backButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = new MainGUIt(t);
				b.setVisible(true);
				dispose();
			}
		});
		backButton.setBounds(219, 207, 187, 23);
		contentPane.add(backButton);
		
		JLabel lblCurrentIncome = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUI.Income")+": "); //$NON-NLS-1$ 
		lblCurrentIncome.setBounds(41, 0, 127, 17);
		contentPane.add(lblCurrentIncome);
		
		JLabel lblCurrentIncome_1 = new JLabel(t.getBalance()+""); //$NON-NLS-1$
		lblCurrentIncome_1.setBounds(167, 2, 127, 17);
		contentPane.add(lblCurrentIncome_1);
		
		JButton addMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.OutMoney1"));
		addMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int mQuant =  Integer.parseInt(JOptionPane.showInputDialog(ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.AddMoney")));
					t.setBalance(t.getBalance() + mQuant);
				}catch(Exception err) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MoneyManagementGUIt.AddMoneyError"), "Warning", JOptionPane.INFORMATION_MESSAGE);return;
					
				}
				lblCurrentIncome_1.setText(t.getBalance() + "");
			}
		});
		addMoney.setBounds(32, 207, 175, 23);
		contentPane.add(addMoney);
		
		
	}
}
