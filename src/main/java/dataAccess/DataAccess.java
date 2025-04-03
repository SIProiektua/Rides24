package dataAccess;

import domain.User;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Book;
import domain.Car;
import domain.Driver;
import domain.Mugimendua;
import domain.Ride;
import domain.Traveler;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	private EntityManager db;
	private EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess() {
		if (c.isDatabaseInitialized()) {
			String fileName = c.getDbFilename();

			File fileToDelete = new File(fileName);
			if (fileToDelete.delete()) {
				File fileToDeleteTemp = new File(fileName + "$");
				fileToDeleteTemp.delete();

				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if (c.isDatabaseInitialized())
			initializeDB();

		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "
				+ c.isDatabaseInitialized());
	}

	public DataAccess(EntityManager db) {
		this.db = db;
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}

			// Create drivers /!\ se debe cambiar
			Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez");
			Driver driver2 = new Driver("driver2@gmail.com", "Ane Gaztañaga");
			Driver driver3 = new Driver("driver3@gmail.com", "Test driver");
			driver1.setPassword("1111");
			driver2.setPassword("2222");
			driver3.setPassword("3333");
			driver1.setID("45678923C");
			driver2.setID("78965423H");
			driver3.setID("12345678F");

			// Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7, null);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year, month, 6), 4, 8, null);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, 4, null);
			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year, month, 7), 4, 8, null);
			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 3, 3, null);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 2, 5, null);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year, month, 6), 2, 5, null);
			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 14), 1, 3, null);

			db.merge(driver1);
			db.merge(driver2);
			db.merge(driver3);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Boolean b egitean registerren dago definituta zein user mota den hortaz
	// errezten da jakiteko den driver edo Traveler, gero informazio guztia
	// gordetzen da

	public void createUser(boolean b, String ID, String name, String pass, String username, String email) {
		db.getTransaction().begin();
		User u1;
		if (b == true) {
			Traveler t1 = new Traveler(email, name, ID, username, pass);
			u1 = (User) t1;
		} else {
			Driver d1 = new Driver(email, name, ID, username, pass);
			u1 = (User) d1;
		}
		
		db.merge(u1);
		db.getTransaction().commit();
	}

	public boolean getUser(String u) {
		TypedQuery<User> query = db.createQuery("SELECT DISTINCT r FROM User r WHERE r.name =:name", User.class);
		query.setParameter("name", u);
		List<User> results = query.getResultList();
		return !results.isEmpty();
	}

	public void cancelRide(Ride r) {
	    Ride ride = db.find(Ride.class, r.getRideNumber());
	    ArrayList<Book> reservas = new ArrayList<Book>(ride.getErreserbaLista());
	    db.getTransaction().begin();
	    for (Book b : reservas) {
	        Traveler traveler = b.getTraveler();
	        traveler.removeBook(b);
	        db.merge(traveler);
	        Iterator<Mugimendua> iterator = traveler.getMugimenduak().iterator();
	        while (iterator.hasNext()) {
	            Mugimendua mugimendua = iterator.next();
	            if (mugimendua.getBook().equals(b)) {
	                traveler.addBalance(mugimendua.getZenbat()); 
	                ride.getDriver().addBalance(-mugimendua.getZenbat()); 
	                db.merge(traveler);
	                db.merge(ride.getDriver());
		            iterator.remove(); 
	            }
	        }
	    }
	    ride.getDriver().removeRide(ride);
	    db.merge(ride.getDriver()); 
	    db.remove(ride); 
	    db.getTransaction().commit();
	}

	public String cancelBook(int bookingId) {
		db.getTransaction().begin(); // UNA SOLA TRANSACCIÓN AQUÍ
		Book booking = db.find(Book.class, bookingId);
		String msg = "Booking doesn't exist or couldn't be canceled.";

		if (booking == null) {
			db.getTransaction().rollback();
			return msg;
		}

		try {
			Ride ride = booking.getRide();
			Traveler traveler = booking.getTraveler();
			Driver driver = ride.getDriver();
			if (ride != null) {
				ride.setAvailablePlaces(ride.getAvailablePlaces() + booking.getSeats());
				ride.getErreserbaLista().remove(booking);
				driver.addBalance(-ride.getPrice());
				db.merge(ride);
			}
			if (traveler != null) {
				for(int i = 0; i<traveler.getMugimenduak().size(); i++) {
					if(traveler.getMugimenduak().get(i).getBook().equals(booking)) {
						traveler.removeMugimendua(traveler.getMugimenduak().get(i));
						break;
					}
				}
				for(int i = 0; i<driver.getMugimenduak().size(); i++) {
					if(driver.getMugimenduak().get(i).getBook().equals(booking)) {
						driver.removeMugimendua(driver.getMugimenduak().get(i));
						break;
					}
				}
				
				traveler.getBookList().remove(booking);
				traveler.addBalance(ride.getPrice());
				db.merge(driver);
				db.merge(traveler);
			}
			db.remove(booking);
			db.getTransaction().commit();
			msg = "Booking " + booking.toString() + " canceled successfully.";
		} catch (Exception e) {
			msg = "Error canceling booking: " + e.getMessage();
			db.getTransaction().rollback();
		}
		return msg;
	}

	public void updateRide(Ride r) {
		db.getTransaction().begin();
		r.setAvailablePlaces(r.getAvailablePlaces() - 1);
		db.merge(r);
		db.getTransaction().commit();
	}

	public User getUserByEmail(String u) {
		TypedQuery<User> query = db.createQuery("SELECT DISTINCT r FROM User r WHERE r.email = :email", User.class);
		query.setParameter("email", u);
		List<User> results = query.getResultList();
		if (!results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}


	public boolean getPass(String email, String password) {
		try {
			User user = db.find(User.class, email);
			String s = new String(user.getPassword());
			if (s.equals(password)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method returns all the cities where rides depart
	 *
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
		List<String> cities = query.getResultList();
		return cities;

	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 *
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from) {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",
				String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList();
		return arrivingCities;

	}

	/**
	 * This method creates a ride for a driver
	 *
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 *
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail, Car car)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverEmail
				+ " date " + date + "car" + car);
		try {
			// !\Puede que esta linea (↓) deba ser borrada
			if (getRides(from, to, date).size() > 0)
				return null;
			if (new Date().compareTo(date) > 0) {
				throw new RideMustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();

			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price, car);

			// next instruction can be obviated
			db.merge(driver);
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			db.getTransaction().commit();
			return null;
		}

	}


	/**
	 * This method retrieves the rides from two locations on a given date
	 *
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",
				Ride.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
		return rides;
	}

	public Ride findRides(String drive, String from, String to, Date date, int nPlaces, float price) {
		List<Ride> res = new ArrayList<Ride>();
		res = getRides(from, to, date);
		for (Ride r : res) {
			if (r.getDriver().getName().equals(drive) && r.getFrom().equals(from) && price == r.getPrice()
					&& r.getTo().equals(to)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 *
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",
				Date.class);

		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			res.add(d);
		}
		return res;
	}

	public void open() {
		String fileName = c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
			db = emf.createEntityManager();
		}
	}
	
	public void close() {
		db.close();
		System.out.println("DataAcess closed");
	}

	public Ride getRideFromId(int id) {
		try {
			return db.find(Ride.class, id);
		} catch (NoResultException e) {
			return null;
		}
	}
}