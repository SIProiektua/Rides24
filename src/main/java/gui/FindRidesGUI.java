package gui;

import businessLogic.BLFacade;

import configuration.UtilDate;
import domain.Driver;
//import domain.Ride;
import domain.Traveler;
import domain.User;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class FindRidesGUI extends JFrame {
	private static final long serialVersionUID = 1L;


	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Rides")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JFrame b;

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = Calendar.getInstance();
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;
	BLFacade facade = ApplicationLauncher.getBusinessLogic();


	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver"), 
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces"), 
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Price")
	};


	public FindRidesGUI(User d, String s) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.FindRides"));

		jLabelEventDate.setBounds(new Rectangle(457, 6, 140, 25));
		jLabelEvents.setBounds(172, 228, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(193, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				jButton2_actionPerformed(e);
			}
		});
		
		List<String> origins=facade.getDepartCities();

		for(String location:origins) originLocations.addElement(location);

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelDestination.setBounds(6, 81, 73, 16);
		getContentPane().add(jLabelOrigin);// kotxe bat gehitu (honek eragina izango du bidaia sortzerakoan)

		getContentPane().add(jLabelDestination);

		jComboBoxOrigin.setModel(originLocations);
		jComboBoxOrigin.setBounds(new Rectangle(103, 50, 172, 20));


		List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
		for(String aciti:aCities) {
			destinationCities.addElement(aciti);
		}

		
		
		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				destinationCities.removeAllElements();

				List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					destinationCities.addElement(aciti);
				}
				tableModelRides.getDataVector().removeAllElements();
				tableModelRides.fireTableDataChanged();
			}
		});


		
		jComboBoxDestination.setModel(destinationCities);
		jComboBoxDestination.setBounds(new Rectangle(103, 80, 172, 20));
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));


				datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);

			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jComboBoxOrigin, null);
		this.getContentPane().add(jComboBoxDestination, null);


		tableModelRides = new DefaultTableModel(null, columnNamesRides);
		this.getContentPane().add(jCalendar1, null);
		scrollPaneEvents.setBounds(new Rectangle(172, 257, 346, 150));
		scrollPaneEvents.setViewportView(tableRides);
		
		tableRides.setModel(tableModelRides);
		tableModelRides.setDataVector(null, columnNamesRides);
		tableModelRides.setColumnCount(4); // another column added to allocate ride objects
		tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPaneEvents, null);
		datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
		
		
		jCalendar1.setBounds(new Rectangle(300, 50, 225, 150));
		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent propertychangeevent){

				if (propertychangeevent.getPropertyName().equals("locale")){
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")){
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					//DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							jCalendar1.setCalendar(calendarAct);
						}
						tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
						tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
						tableRides.getColumnModel().getColumn(2).setPreferredWidth(30);
					}
				}

				//
				
				try {
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					
					tableModelRides.setDataVector(null, columnNamesRides);
					tableModelRides.setColumnCount(4); // another column added to allocate ride objects
					List<domain.Ride> rides=facade.getRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),UtilDate.trim(jCalendar1.getDate()));

					if (rides.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NoRides")+ ": "+dateformat1.format(calendarAct.getTime()));
					else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Rides")+ ": "+dateformat1.format(calendarAct.getTime()));
					for (domain.Ride ride:rides){
						Vector<Object> row = new Vector<Object>();
						row.add(ride.getDriver().getName());
						row.add(ride.getnPlaces());
						row.add(ride.getPrice());
						row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
						tableModelRides.addRow(row);		
					}
					datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
					paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
				tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable
			}
		});
				//
		
	


		JButton JBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		JBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(s.equals("SelectGUI")){
					b = new SelectGUI();
				}else if(s.equals("MainGUI")){
					b = new MainGUI((Driver) d);
				}else if(s.equals("MainGUIt")){
					b = new MainGUIt((Traveler) d);
				}
				b.setVisible(true);
				dispose();
			}
		});
		JBack.setBounds(335, 421, 130, 30);
		this.getContentPane().add(JBack);
	}
	
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public FindRidesGUI(String string) {
				this.getContentPane().setLayout(null);
				this.setSize(new Dimension(700, 500));
				this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.FindRides"));

				jLabelEventDate.setBounds(new Rectangle(457, 6, 140, 25));
				jLabelEvents.setBounds(172, 228, 259, 16);

				this.getContentPane().add(jLabelEventDate, null);
				this.getContentPane().add(jLabelEvents);

				jButtonClose.setBounds(new Rectangle(193, 419, 130, 30));

				jButtonClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						jButton2_actionPerformed(e);
					}
				});
				List<String> origins=facade.getDepartCities();

				for(String location:origins) originLocations.addElement(location);

				jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
				jLabelDestination.setBounds(6, 81, 73, 16);
				getContentPane().add(jLabelOrigin);

				getContentPane().add(jLabelDestination);

				jComboBoxOrigin.setModel(originLocations);
				jComboBoxOrigin.setBounds(new Rectangle(103, 50, 172, 20));


				List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					destinationCities.addElement(aciti);
				}

				
				
				jComboBoxOrigin.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						destinationCities.removeAllElements();
						

						List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
						for(String aciti:aCities) {
							destinationCities.addElement(aciti);
						}
						tableModelRides.getDataVector().removeAllElements();
						tableModelRides.fireTableDataChanged();
					}
				});


				
				jComboBoxDestination.setModel(destinationCities);
				jComboBoxDestination.setBounds(new Rectangle(103, 80, 172, 20));
				jComboBoxDestination.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {

						paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));


						datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
						paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);

					}
				});

				this.getContentPane().add(jButtonClose, null);
				this.getContentPane().add(jComboBoxOrigin, null);
				this.getContentPane().add(jComboBoxDestination, null);


				tableModelRides = new DefaultTableModel(null, columnNamesRides);
				this.getContentPane().add(jCalendar1, null);
				scrollPaneEvents.setBounds(new Rectangle(172, 257, 346, 150));
				scrollPaneEvents.setViewportView(tableRides);
				
				tableRides.setModel(tableModelRides);
				tableModelRides.setDataVector(null, columnNamesRides);
				tableModelRides.setColumnCount(4); // another column added to allocate ride objects
				tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
				tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable

				this.getContentPane().add(scrollPaneEvents, null);
				datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
				
				
				jCalendar1.setBounds(new Rectangle(300, 50, 225, 150));
				// Code for JCalendar
				jCalendar1.addPropertyChangeListener(new PropertyChangeListener(){
					public void propertyChange(PropertyChangeEvent propertychangeevent){

						if (propertychangeevent.getPropertyName().equals("locale")){
							jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
						} else if (propertychangeevent.getPropertyName().equals("calendar")){
							calendarAnt = (Calendar) propertychangeevent.getOldValue();
							calendarAct = (Calendar) propertychangeevent.getNewValue();
							// DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
							int monthAnt = calendarAnt.get(Calendar.MONTH);
							int monthAct = calendarAct.get(Calendar.MONTH);
							if (monthAct!=monthAnt) {
								if (monthAct==monthAnt+2) { 
									jCalendar1.setCalendar(calendarAct);
								}
								tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
								tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
								tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
								tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable
							}
						}

						//
						
						try {
							DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
							
							tableModelRides.setDataVector(null, columnNamesRides);
							tableModelRides.setColumnCount(4); // another column added to allocate ride objects
							List<domain.Ride> rides=facade.getRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),UtilDate.trim(jCalendar1.getDate()));

							if (rides.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NoRides")+ ": "+dateformat1.format(calendarAct.getTime()));
							else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Rides")+ ": "+dateformat1.format(calendarAct.getTime()));
							for (domain.Ride ride:rides){
								Vector<Object> row = new Vector<Object>();
								row.add(ride.getDriver().getName());
								row.add(ride.getnPlaces());
								row.add(ride.getPrice());
								row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
								tableModelRides.addRow(row);		
							}
							datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
							paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
						} catch (Exception e1) {

							e1.printStackTrace();
						}
						tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
						tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
						tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
						tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable
					}
				});
						//
				
			


				JButton JRegisterBack = new JButton(Messages.getString("RegisterGUI.Back"));
				JRegisterBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						b = new SelectGUI();
						b.setVisible(true);
						dispose();
					}
				});
				JRegisterBack.setBounds(335, 421, 130, 30);
				this.getContentPane().add(JRegisterBack);
	}



	@SuppressWarnings("deprecation")
	public static void paintDaysWithEvents(JCalendar jCalendar,List<Date> datesWithEventsCurrentMonth, Color color) {
		// For each day with events in current month, the background color for that day is changed to cyan.
		Calendar calendar = jCalendar.getCalendar();
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d:datesWithEventsCurrentMonth){
			calendar.setTime(d);
			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}
		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

