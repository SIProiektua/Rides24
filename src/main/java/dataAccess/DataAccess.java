package dataAccess;

import domain.User;
import java.io.File;
//import java.net.NoRouteToHostException;
//import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

		//close();

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
		System.out.println("se guardará: " + u1.toString());
		db.merge(u1);
		db.getTransaction().commit();
	}

	public void aCar(User r, Car c){
		if(db.find(User.class, r)!=null){
			Driver s = (Driver)db.find(User.class, r);
			s.addCar(c);
			db.merge(s);
			db.getTransaction().commit();
		}else{
			System.out.println("THIS CAR ALREADY EXISTS!");
		}
	}

	public void saveBooking(Book b, Traveler t) {
	    db.getTransaction().begin();
		List<Book> lbook =  t.getBookList();
		if(!lbook.contains(b)){
			lbook.add(b);
		}
	    db.merge(t);  
	    db.getTransaction().commit();
	}

	public User getUserByPlate(String plate){
		TypedQuery<User> query = db.createQuery("SELECT DISTINCT r FROM User r", User.class);
		List<User> user = query.getResultList();
		Iterator<User> it = user.iterator();
		while(it.hasNext()){
			User us = it.next();
			if(us instanceof Driver){
				Driver dr = (Driver) us;
				List<Car> carlista = dr.getCars();
				for(Car c: carlista){
					if(c.getNumberPlate().equals(plate)){
						return us;
					}
				}
			}
		}
		return new User();
	}

	public void paying(Driver d, Traveler t, Mugimendua m){
		Mugimendua m1 = db.find(Mugimendua.class, m);
		d.addBalance(m1.getZenbat());
		t.addBalance(0 - m1.getZenbat());
		db.merge(d);
		db.merge(t);
		db.getTransaction().commit();
	}

	public String getUserType(String u) {
		TypedQuery<User> query = db.createQuery("SELECT DISTINCT r FROM User r WHERE r.name = :name", User.class);
		query.setParameter("name", u);
		List<User> user = query.getResultList();
		if (user.isEmpty()) {
			System.out.println("FATAL ERROR UserType");
		}
		return user.get(0).getClass().getSimpleName();
	}

	public String getUserTypeByEmail(String u) {
		 User u1 = db.find(User.class,u);
		 System.out.println("Tipo de usuario: " + u1.getClass().getSimpleName());
		  return u1.getClass().getSimpleName();
	}
	
	
	public boolean getUser(String u) {
		TypedQuery<User> query = db.createQuery("SELECT DISTINCT r FROM User r WHERE r.name =:name",
				User.class);
		query.setParameter("name", u);
		List<User> results = query.getResultList();
		if (!results.isEmpty()) {
			System.out.println("funca");
			return true;
		}
		System.out.println(results.isEmpty());
		return false;
	}

	public User getUser2(String u) {
		TypedQuery<User> query = db.createQuery("SELECT DISTINCT r FROM User r WHERE r.name = :name", User.class);
		query.setParameter("name", u);
		List<User> results = query.getResultList();
		if (!results.isEmpty()) {
			System.out.println("ERROR");
			return results.get(0);
		}
		return null;
	}
	
	public String cancelRide(Ride r) {
    	Ride f = db.find(Ride.class, r.getRideNumber());
    	String s = "Ride couldn't be canceled because it doesn't exist.";
    	if (f != null) {
     	   db.getTransaction().begin();
      	   try {
            	List<Book> bookingList = new ArrayList<>(f.getErreserbaLista());
            		for (Book b : bookingList) {
             		   Traveler t = b.getTraveler();
              			if (t != null) {
               			    t.getBookList().remove(b);
                 		    t.getRides().remove(f);
                		    db.merge(t);
               			}
                		f.getErreserbaLista().remove(b);
                		db.remove(b);
            		}
            	db.remove(f);
            	db.getTransaction().commit();
            	s = "Ride " + r.toString() + " canceled successfully.";
        	} catch (Exception e) {
            db.getTransaction().rollback();
            s = "Ride couldn't be canceled due to an internal error: " + e.getMessage();
       	 	}
    	}
    	return s;
	}

	public boolean hasEnoughBalance(Traveler t, float i){
		return i<db.find(Traveler.class, t).getBalance();
	}

	public void addBalance(Traveler t, float i) {
    	db.getTransaction().begin();
    		try {
        		Traveler found = db.find(Traveler.class, t.getEmail());
        		if (found != null) {
            		found.setBalance(found.getBalance() + i); // Sumamos al balance actual
            		db.merge(found); // Actualizamos en la base de datos
            		db.getTransaction().commit();
        		} else {
            		db.getTransaction().rollback();
            		System.out.println("Traveler not found.");
        		}
    		} catch (Exception e) {
        		db.getTransaction().rollback();
        		System.out.println("Error while adding balance: " + e.getMessage());
    		}
	}

	public List<Ride> bueltatuBukatutakoRideak(){
		//TypedQuery<Ride> query = db.createQuery("SELECT DISTINCT r FROM Ride r", Ride.class);
		//List<Ride> lride = query.getResultList();
		//List<Ride> lrideend = new ArrayList<Ride>();
//		for(Ride r: lride){
			//if(r.getDate().equals())
//		}
		return null;
	}


	public List<Mugimendua> getMovements(String id, String bookcode){
		Book b = db.find(Book.class,bookcode);
		return b.getMugimenduak();
	}
	 
	public String cancelBooking(int bookingId) {
    		Book booking = db.find(Book.class, bookingId);
    		String msg = "Booking doesn't exist or couldn't be canceled.";

    		if (booking == null) return msg;

    		db.getTransaction().begin();
    		try {
    			Ride ride = booking.getRide();
    			Traveler traveler = booking.getTraveler();
     				if (ride != null) {
    	 		       ride.setAvailablePlaces(ride.getAvailablePlaces() + booking.getSeats());
      	    	       ride.getErreserbaLista().remove(booking);
        		       db.merge(ride);
        			}
        			if (traveler != null) {
            			traveler.getBookList().remove(booking);
            			db.merge(traveler);
        			}
        		db.remove(booking);
        		db.getTransaction().commit();
        		msg = "Booking " + booking.toString() + " canceled successfully.";
    		} catch (Exception e) {
    		    db.getTransaction().rollback();
    		    msg = "Error canceling booking: " + e.getMessage();
    		}
    	return msg;
	}



	public User getUserByEmail(String u) {
		TypedQuery<User> query = db.createQuery("SELECT DISTINCT r FROM User r WHERE r.email = :email", User.class);
		query.setParameter("email", u);
		List<User> results = query.getResultList();
		if (!results.isEmpty()) {
			System.out.println("ERROR");
			return results.get(0);
		}
		return null;
	}

	public Traveler getTravelerByEmail(String email) {
	    try {
	        return (Traveler) db.find(User.class, email);
	    } catch (Exception e) {
	        System.out.println("Error en getUserByEmail: " + e.getMessage());
	        return null;
	    }
	}

	public Driver getDriverByEmail(String email) {
	    try {
	        return (Driver) db.find(User.class, email);
	    } catch (Exception e) {
	        System.out.println("Error en getUserByEmail: " + e.getMessage());
	        return null;
	    }
	}


	public boolean getPass(String email, String password) {
	    try {
	        User user = db.find(User.class, email);
	        System.out.println(email);
	        System.out.println("User: " + user.toString());
	        String s = new String(user.getPassword());
	        System.out.println("User Password: " + s);
	        System.out.println("Password: " + password);
	        System.out.println(user.isEmpty());
	        System.out.println(user.getPassword().equals(password));
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

	public String isBooked(String bookId){
		Book b = db.find(Book.class, bookId);
		if(b!=null){
			return b.getEgoera();
		}
		return "ERROREA";
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
		System.out.println(">> DataAccess: getRides=> from= " + from + " to= " + to + " date " + date);

		List<Ride> res = new ArrayList<>();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",
				Ride.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
		for (Ride ride : rides) {
			res.add(ride);
		}
		return res;
	}

	public void updateRides(Book b, Ride r) {
		String s1 = r.getFrom();
		String s2 = r.getTo();
		Date s3 = r.getDate();
		int j = -1;
		List<Ride> oldRide = getRides(s1, s2, s3);
		System.out.println("There are " + oldRide.size() + " rides");
		for (int i = 0; i < oldRide.size(); i++) {
			if (oldRide.get(i).getRideNumber() == r.getRideNumber()) {
				j = i;
				break;
			}
		}
		if (j > -1) {
			Ride oldR = oldRide.get(j);
			oldR.addBook(b);
			if (oldR.getnPlaces() > 0) {
				oldR.setAvailablePlaces((int) oldR.getnPlaces() - 1);
			} else {
				System.out.println("ERROR NOT FOUND SEATS");
			}
		}
	}

	public void bookRides(Book b, Ride r) throws Exception {
		if (r.getnPlaces() > 1.00) {
			updateRides(b, r);
			System.out.print(b.getId() + "You have booked " + r.getDriver() + " 's travel from" + r.getFrom() + " to "
					+ r.getTo());
		} else {
			throw new Exception();
		}
	}

	public Ride findRides(String drive, String from, String to, Date date, int nPlaces, float price) {
		Ride f = new Ride();
		if (getUserType(drive).equals("Driver")) {
			//User n = getUser2(drive);
			List<Ride> res = new ArrayList<>();
			res = getRides(from, to, date);
			for (Ride r : res) {
				
				if (r.getDriver().getName().equals(drive) && r.getFrom().equals(from) && price == r.getPrice()
						&& r.getTo().equals(to)) {
					f = r;

				}
			}
		}
		return f;
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
		List<Date> res = new ArrayList<>();

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
		System.out.println("DataAccess opened => isDatabaseLocal: " + c.isDatabaseLocal());

	}

	public void close() {
		db.close();
		System.out.println("DataAcess closed");
	}

	public static String generateRandomString(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder resultado = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            resultado.append(caracteres.charAt(indice));
        }

        return resultado.toString();
    }

}
