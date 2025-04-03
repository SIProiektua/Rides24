package domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User{
	private static final long serialVersionUID = 1L;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Ride> rides;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Car> cars;
	
	public Driver(String email, String name, String ID, String username, String password) {
		super(email, name, ID, username, password);
		cars = new ArrayList<Car>();
		rides = new ArrayList<Ride>();
	}
	public Driver(String email, String name) {
		super(email, name);
		cars = new ArrayList<Car>();
		rides = new ArrayList<Ride>();
	}
	public Driver() {
		
    }
	public String toString(){
		return getEmail()+";"+name+ " " + cars + rides;
	}
	

	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */

	public Ride addRide(String from, String to, Date date, int nPlaces, float price, Car car)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this, car);
        rides.add(ride);
        return ride;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	public void RemoveTraveler(Ride ride, Book book){
		try{
			Ride bidaia = rides.get(rides.indexOf(ride));
			bidaia.removeBook(book);
		}catch(Exception e){
			System.out.println("ERROR DRIVER.JAVA");
		}
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (getEmail() != other.getEmail())
			return false;
		return true;
	}
	public int compareTo(Driver drive) {
		int i = 0;
		if(drive.getEmail().equals(this.getEmail())&&(drive.getPassword().equals(this.getPassword()))&&drive.getUsername().equals(this.getUsername())&&this.getClass().getSimpleName().equals(drive.getClass().getSimpleName())) {
			i = 1;
		}
		return i;
	}
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}
	public void addCar(Car car) {
		cars.add(car);
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@SuppressWarnings("deprecation")
	public Ride findRide(String from, String to, Date date){
		for(Ride ri: rides){
			if(ri.getFrom().equals(from)&&ri.getTo().equals(to)){
				if((date.getHours()==ri.getDate().getHours())&&(date.getMinutes()==ri.getDate().getMinutes())){
					return ri;
				}
			}
		}
		return new Ride();
	}
	public List<Ride> getRides(){
		return rides;
	}
	public void removeRide(Ride r){
		rides.remove(r);
	}
}