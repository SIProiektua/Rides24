package gui;

import java.awt.EventQueue;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Book;
import domain.Driver;
import domain.Mugimendua;
import domain.Ride;
import domain.Traveler;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PaymentGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JTextArea prezioFinala;
	private JLabel kopurua;
	private JButton payTrip;
	private JLabel tZbkia;
	private JLabel iraungi;
	private JLabel cVV;
	private JButton cancelTrip;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Traveler t, Ride r) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentGUI frame = new PaymentGUI(t, r);
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
	public PaymentGUI(Traveler t, Ride r) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		prezioFinala = new JTextArea();
		prezioFinala.setBounds(312, 11, 112, 22);
		contentPane.add(prezioFinala);
		prezioFinala.setText(r.getPrice()+" ");

		kopurua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PaymentGUI.Kopurua")); //$NON-NLS-1$ //$NON-NLS-2$
		kopurua.setBounds(10, 16, 90, 14);
		contentPane.add(kopurua);

		payTrip = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PaymentGUI.Pay"));
		payTrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Driver d = r.getDriver();
				if(true/*t.getBalance()>r.getPrice()*/){
					t.setBalance(t.getBalance()-r.getPrice());
					d.setBalance(d.getBalance()+r.getPrice());
					//Create Book
					Book book = new Book(1,"CORRECT");
					book.setRide(r);
					book.setTraveler(t);
					t.addBook(book);

					//Create movement
					Mugimendua mugimendua = new Mugimendua();
					mugimendua.setNondik(t);
					mugimendua.setNora(d);
					mugimendua.setZenbat(r.getPrice());
					mugimendua.setBook(book);
					t.addMugimenduak(mugimendua);
					d.addMugimenduak(mugimendua);
					MainGUIt b = new MainGUIt(t);
					b.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "You don't have enough money to pay", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		payTrip.setBounds(118, 227, 89, 23);
		contentPane.add(payTrip);

		textField = new JTextField();
		textField.setBounds(10, 74, 414, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		tZbkia = new JLabel("");
		tZbkia.setBounds(174, 49, 90, 14);
		contentPane.add(tZbkia);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 143, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(325, 143, 99, 20);
		contentPane.add(passwordField);


		iraungi = new JLabel("");
		iraungi.setBounds(10, 118, 86, 14);
		contentPane.add(iraungi);

		cVV = new JLabel("");
		cVV.setBounds(354, 118, 33, 14);
		contentPane.add(cVV);

		cancelTrip = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PaymentGUI.Deny"));
		cancelTrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//MIRAR EL USUARIO ELIMINARLO DEL VIAJE Y VOLVER A SU PERFIL
				MainGUIt a = new MainGUIt(t);
				a.setVisible(true);
				dispose();
			}
		});
		cancelTrip.setBounds(241, 227, 89, 23);
		contentPane.add(cancelTrip);
	}
}
