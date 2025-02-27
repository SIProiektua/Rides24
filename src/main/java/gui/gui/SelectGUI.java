package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectGUI extends JFrame {

	private JPanel contentPane;
	//private static boolean b=true;
	private JButton RegisterB;
	private JButton LoginB;
	private JButton queryButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectGUI frame = new SelectGUI();
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
	public SelectGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ApplicationLauncher.da.close();
			}
			
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RegisterB = new JButton("REGISTER");
		RegisterB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI a = new RegisterGUI();
				a.setVisible(true);
			}
		});
		RegisterB.setBounds(80, 11, 283, 51);
		contentPane.add(RegisterB);
		
		LoginB = new JButton("LOGIN");
		LoginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI b = new LoginGUI();
				b.setVisible(true);
			}
		});
		
		LoginB.setBounds(80, 199, 283, 51);
		contentPane.add(LoginB);
		
		queryButton = new JButton("QUERY RIDES");
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindRidesGUI c = new FindRidesGUI();
				c.setVisible(true);
			}
		});
		
					
		queryButton.setBounds(80, 106, 283, 51);
		contentPane.add(queryButton);
	
		
	}
}
