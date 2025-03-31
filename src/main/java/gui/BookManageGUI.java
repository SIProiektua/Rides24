package gui;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import domain.*;
import businessLogic.*;

public class BookManageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Book selectedBook;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	private MainGUIt b;

	/**
	 * Launch the application.
	 */

	public BookManageGUI(Traveler traveler) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		DefaultComboBoxModel<Book> booksInfo = new DefaultComboBoxModel<Book>();
		for (int i = 0; i < traveler.getBookList().size(); i++) {
			booksInfo.addElement(traveler.getBookList().get(i));
		}
		JComboBox<Book> bookComboBox = new JComboBox<Book>();
		
		bookComboBox.setModel(booksInfo);
		bookComboBox.setBounds(34, 10, 372, 53);
		contentPane.add(bookComboBox);

		Book book = (Book) bookComboBox.getSelectedItem();
		int id = book.getId();
		

		// Search in travelers' booklist the id we got.
		for (int j = 0; j < traveler.getBookList().size(); j++) {
			System.out.println("No entra al for");
			if (traveler.getBookList().get(j).getId() == id) {
				System.out.println("No entra al if");
				selectedBook = traveler.getBookList().get(j);
			}
		}
		JButton cancelBook = new JButton("Cancel Book");
		cancelBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.cancelBook(selectedBook.getId());
				booksInfo.removeAllElements();
			}
		});
		cancelBook.setBounds(34, 75, 180, 27);
		contentPane.add(cancelBook);

		JButton backButton = new JButton(Messages.getString("RegisterGUI.Back"));
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
