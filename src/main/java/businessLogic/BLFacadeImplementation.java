package businessLogic;

import java.util.Date;
import java.util.List;
//import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

//import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Car;
import domain.Ride;
import domain.User;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager = new DataAccess();
	}

	public BLFacadeImplementation(DataAccess da) {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDepartCities() {
		List<String> departLocations = dbManager.getDepartCities();
		return departLocations;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDestinationCities(String from) {
		List<String> targetCities = dbManager.getArrivalCities(from);
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail, Car car)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
		Ride ride = dbManager.createRide(from, to, date, nPlaces, price, driverEmail, car);
		return ride;
	};

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Ride> getRides(String from, String to, Date date) {
		List<Ride> rides = dbManager.getRides(from, to, date);
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
		return dates;
	}

	public void cancelBook(int id) {
		dbManager.cancelBook(id);

	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.initializeDB();
	}

	@Override
	public void updateRide(Ride r) {
		dbManager.updateRide(r);

	}

	@Override
	public boolean getPass(String text, String s1) {

		return dbManager.getPass(text, s1);
	}

	@Override
	public void createUser(boolean b, String string, String text, String s, String text2, String text3) {
		dbManager.createUser(b, text, text2, s, s, text3);
	}

	@Override
	public void cancelRide(Ride r) {
		dbManager.cancelRide(r);

	}

	@Override
	public User getUserByEmail(String email) {
		return dbManager.getUserByEmail(email);
	}

	@Override
	public Ride getRideFromId(int id) {
		return dbManager.getRideFromId(id);
	}

}
