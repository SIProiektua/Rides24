package domain;

// import domain.User;

// import java.util.List;
// import java.util.Vector;

// import javax.persistence.CascadeType;
// import javax.persistence.FetchType;
// import javax.persistence.Id;
// import javax.persistence.OneToMany;
// import javax.xml.bind.annotation.XmlID;
// import javax.xml.bind.annotation.XmlIDREF;

public class Traveler extends User{
	private static final long serialVersionUID = 1L;
	
	// @XmlID
	// @Id 
	// @XmlIDREF
	// /!\Igual no hay que comentar esto
	// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	// private List<Ride> rides=new Vector<Ride>();

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
}
