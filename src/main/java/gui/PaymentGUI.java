package gui;

import java.awt.EventQueue;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PaymentGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textField;
	private JTextArea prezioFinala;
	private JLabel kopurua;
	private JButton payTrip;
	private JLabel iraungi;
	private JLabel cVV;
	private JButton cancelTrip;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();

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
				if(t.getBalance()>=r.getPrice()&&r.getAvailablePlaces()>0){
					t.setBalance(t.getBalance()-r.getPrice());
					d.setBalance(d.getBalance()+r.getPrice());

					//Create Book
					Book book = new Book(1,"CORRECT");
					book.setSeats(book.getSeats()-1);
					book.setRide(r);
					book.setTraveler(t);
					t.addBook(book);
					r.addBook(book);

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
					facade.updateRide(r);
				} else {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("PaymentGUI.PayError"), "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		payTrip.setBounds(118, 227, 89, 23);
		contentPane.add(payTrip);

		textField = new JTextArea();
		textField.setEditable(false);
		textField.setBounds(10, 74, 414, 102);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText("Nondik:" + r.getFrom() + "   Nora:" + r.getTo() + "   Gidaria:" + r.getDriver().getName() +
				"\nData:" + r.getDate().toString() + "   Prezioa: " + r.getPrice() );

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
