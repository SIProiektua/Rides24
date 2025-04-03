package gui;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
//import java.awt.event.*;
import domain.*;
import businessLogic.*;

public class BookManageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Book selectedBook;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	private MainGUIt b;
	private Traveler traveler;

	/**
	 * Launch the application.
	 */

	public BookManageGUI(Traveler t) {
		traveler = (Traveler) facade.getUserByEmail(t.getEmail());


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);


		DefaultComboBoxModel<Book> booksInfo = new DefaultComboBoxModel<Book>();
		for(Book book : traveler.getBookList()) {
			booksInfo.addElement(book);
		}
		JComboBox<Book> bookComboBox = new JComboBox<Book>();

		bookComboBox.setModel(booksInfo);
		bookComboBox.setBounds(34, 10, 372, 53);
		contentPane.add(bookComboBox);

		selectedBook = (Book) bookComboBox.getSelectedItem();
		bookComboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        selectedBook = (Book) bookComboBox.getSelectedItem();
		    }
		});

		JButton cancelBook = new JButton("Cancel Book");
		cancelBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.cancelBook(selectedBook.getId());
				traveler = (Traveler) facade.getUserByEmail(t.getEmail());
				booksInfo.removeAllElements();
				if(booksInfo.getSize()>=1) {
					for(Book book : traveler.getBookList()) {
						booksInfo.addElement(book);
					}
				} else {
					b = new MainGUIt(traveler);
					b.setVisible(true);
					dispose();
				}
			}
		});
		cancelBook.setBounds(34, 75, 180, 27);
		contentPane.add(cancelBook);

		JButton backButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = new MainGUIt(traveler);
				b.setVisible(true);
				dispose();
			}
		});
		backButton.setBounds(226, 77, 187, 23);
		contentPane.add(backButton);
	}
}