package domain;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User{
	private static final long serialVersionUID = 1L;

	public Driver(String email, String name, String ID, String username, String password) {
		super(email, name, ID, username, password);
	}
	public Driver(String email, String name) {
		super(email, name);
	}
	public Driver() {
		
    }
	public String toString(){
		return email+";"+name+rides;
	}
	

	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */

	public Ride addRide(String from, String to, Date date, int nPlaces, float price)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this);
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
	public void RemoveTraveler(Ride ride, Traveler traveler){
		try{
			Ride bidaia = rides.get(rides.indexOf(ride));
			bidaia.removeTraveler(traveler);
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
		if (email != other.email)
			return false;
		return true;
	}
	public int compareTo(Driver drive) {
		int i = 0;
		if(drive.getName().equals(this.getName())&&drive.getEmail().equals(this.getEmail())&&(drive.getPassword().equals(this.getPassword()))&&drive.getUsername().equals(this.getUsername())&&this.getUsertype().equals(drive.getUsertype())) {
			i = 1;
		}
		return i;
	}
}