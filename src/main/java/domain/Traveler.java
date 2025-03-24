package domain;
import javax.persistence.*;


@Entity
public class Traveler extends User{
	private static final long serialVersionUID = 1L;

	public Traveler(String email, String name, String ID, String username, String password) {
		super(email, name, ID, username, password);
	}
	public Traveler(String email, String name) {
		super(email, name);
	}
	

	public String toString(){
		return email+";"+name+rides;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email != other.email)
			return false;
		return true;
	}
	public void addToRide(){
		//rides.add(this)
	}
}
