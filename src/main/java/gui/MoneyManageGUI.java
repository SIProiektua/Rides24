package gui;

// import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import domain.*;
import businessLogic.*;

public class MoneyManageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Mugimendua selectedMugimendua;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
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
		DefaultComboBoxModel<Mugimendua> mugiemenduaInfo = new DefaultComboBoxModel<Mugimendua>();
		
		
		for (int i = 0; i < driver.getMugimenduak().size(); i++) {
			mugiemenduaInfo.addElement(driver.getMugimenduak().get(i));
		}

		JButton backButton = new JButton(Messages.getString("RegisterGUI.Back"));
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
        String[] columnNames = {"ID", "Participant", "Balance"};
		tableModel = new DefaultTableModel(columnNames, 0);
		for (int i = 0; i < driver.getMugimenduak().size(); i++) {
			Mugimendua mugimendua = driver.getMugimenduak().get(i);
            tableModel.addRow(new Object[]{mugimendua.getBook().getId(), mugimendua.getNondik().getName(), "+"+mugimendua.getZenbat()});
        }
		table = new JTable(tableModel);
		table.setBounds(41, 31, 367, 157);
		contentPane.add(table);
		
		JButton backButton_1 = new JButton("Back");
		backButton_1.setBounds(41, 198, 175, 23);
		contentPane.add(backButton_1);
		
		JLabel lblCurrentIncome = new JLabel("Income: "); //$NON-NLS-1$
		lblCurrentIncome.setBounds(41, 0, 127, 17);
		contentPane.add(lblCurrentIncome);
		
		JLabel lblCurrentIncome_1 = new JLabel(driver.getBalance()+""); //$NON-NLS-1$
		lblCurrentIncome_1.setBounds(167, 2, 127, 17);
		contentPane.add(lblCurrentIncome_1);
	}
}
