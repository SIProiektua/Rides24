package gui;

// import java.text.DateFormat;
import java.util.*;
//import java.util.List;

import javax.swing.*;
//import javax.swing.text.JTextComponent;

import com.toedter.calendar.JCalendar;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Car;
import domain.Driver;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private Driver driver;
	private JTextField fieldOrigin=new JTextField();
	private JTextField fieldDestination=new JTextField();
	
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo")); 
	private JLabel jLabelSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price"));

	//private JComboBox<String> comboBoxCar = new JComboBox<String>();
	DefaultComboBoxModel<String> kotxeenMatrikula = new DefaultComboBoxModel<String>();
	
	private JTextField jTextFieldSeats = new JTextField();
	private JTextField jTextFieldPrice = new JTextField();

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
	private JButton jButtonBack = new JButton(Messages.getString("RegisterGUI.Back"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private MainGUI b;
	//private List<Date> datesWithEventsCurrentMonth;
	private Car car;


	public CreateRideGUI(Driver driver) {
		this.driver=driver;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelSeats.setBounds(new Rectangle(6, 119, 173, 20));
		jTextFieldSeats.setBounds(new Rectangle(149, 119, 60, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 159, 173, 20));
		jTextFieldPrice.setBounds(new Rectangle(149, 159, 60, 20));

		jCalendar.setBounds(new Rectangle(300, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 263, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);
		jLabelError.setBounds(new Rectangle(260, 214, 320, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = new MainGUI(driver);
				b.setVisible(true);
				dispose();
			}
		});
		jButtonBack.setSize(130, 30);
		jButtonBack.setLocation(290, 264);

		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldSeats, null);

		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jLabelOrigin, null);
		

		

		this.getContentPane().add(jCalendar, null);
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);

		
		
		
		//BLFacade facade = ApplicationLauncher.getBusinessLogic();
		//datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar.getDate());		
		
		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(298, 16, 140, 25);
		getContentPane().add(jLabRideDate);
		
		jLabelDestination.setBounds(6, 81, 73, 16);
		getContentPane().add(jLabelDestination);
		
		
		fieldOrigin.setBounds(110, 53, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		
		fieldDestination.setBounds(110, 81, 130, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);
		
		
		
		
		
		
		JComboBox<String> comboBoxCar = new JComboBox<String>();
		comboBoxCar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//Selects the driver's car based on plate number
				for(int i = 0;i<driver.getCars().size(); i++) {
					if(driver.getCars().get(i).getNumberPlate().equals(e.getItem())) {
						car = driver.getCars().get(i);
					}
				}
			}
		});
		DefaultComboBoxModel<String> kotxeenMatrikula = new DefaultComboBoxModel<String>();
		//selects all driver's car's plate numbers.
		for(int i=0; i<driver.getCars().size();i++) {
			kotxeenMatrikula.addElement(driver.getCars().get(i).getNumberPlate());
		}
		comboBoxCar.setModel(kotxeenMatrikula);
		comboBoxCar.setBounds(110, 191, 130, 26);
		
		
		
		
		
		
		JLabel carLabel = new JLabel((String) "Kotxea aukeratu");
		carLabel.setBounds(new Rectangle(6, 159, 173, 20));
		carLabel.setBounds(6, 191, 109, 20);
		getContentPane().add(carLabel);
		
		getContentPane().add(comboBoxCar);
		 //Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					//DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);					
	
					}
					jCalendar.setCalendar(calendarAct);

					/*
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);
						if (Locale.getDefault().equals(new Locale("es")))
							offset += 4;
						else
							offset += 5;
				*/
				//Component o = (Component) jCalendar.getDayChooser().getDayPanel().getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}}});
		
	}	 
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		jLabelMsg.setText("");
		String error=field_Errors();
		if (error!=null) 
			jLabelMsg.setText(error);
		else
			try {
				BLFacade facade = ApplicationLauncher.getBusinessLogic();
				int inputSeats = Integer.parseInt(jTextFieldSeats.getText());
				float price = Float.parseFloat(jTextFieldPrice.getText());

				
				@SuppressWarnings("unused")
				Ride r=facade.createRide(fieldOrigin.getText(), fieldDestination.getText(), UtilDate.trim(jCalendar.getDate()), inputSeats, price, driver.getEmail(), car); 
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));

			} catch (RideMustBeLaterThanTodayException e1) {
				jLabelMsg.setText(e1.getMessage());
			} catch (RideAlreadyExistException e1) {
				jLabelMsg.setText(e1.getMessage());
			}

		}
	

	
	private String field_Errors() {
		
		try {
			if ((fieldOrigin.getText().length()==0) || (fieldDestination.getText().length()==0) || (jTextFieldSeats.getText().length()==0) || (jTextFieldPrice.getText().length()==0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
			else {

				// trigger an exception if the introduced string is not a number
				int inputSeats = Integer.parseInt(jTextFieldSeats.getText());

				if (inputSeats <= 0) {
					return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SeatsMustBeGreaterThan0");
				}
				else {
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0) 
						return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");
					
					else 
						return null;
						
				}
			}
		} catch (java.lang.NumberFormatException e1) {

			return  ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");		
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;

		}
	}
}
