package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
//import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@Id 
	protected String email;
	protected String name;
    protected String ID;
	protected String username;
	protected String password;
	@XmlIDREF
    
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	protected List<Ride> rides=new Vector<Ride>();


            ////////////////
            //Constructors//
            ////////////////

	public User() {
		super();
	}
	public User(String email, String name) {
        super();
		this.email = email;
		this.name = name;
	}
	public User(String email, String name, String ID, String username, String password) {
        super();
		this.email = email;
		this.name = name;
        this.ID = ID;
        this.username=username;
		this.password=password;
	}
	

	        ///////////////////
            //Setters&Getters//
            ///////////////////

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return email+";"+name+rides;
	}
	

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
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
