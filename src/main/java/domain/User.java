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
	protected String usertype;
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the usertype
	 */
	public String getUsertype() {
		return usertype;
	}
	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	/**
	 * @return the rides
	 */
	public List<Ride> getRides() {
		return rides;
	}
	/**
	 * @param rides the rides to set
	 */
	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@XmlIDREF

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	protected List<Ride> rides=new Vector<Ride>();


            //////////////////
            ///Constructors///
            //////////////////

	public User() {
		super();
		usertype = this.getClass().toString();
	}
	public User(String email, String name) {
        super();
		this.email = email;
		this.name = name;
		usertype = this.getClass().toString();
	}
	public User(String email, String name, String ID, String username, String password) {
        super();
		this.email = email;
		this.name = name;
        this.ID = ID;
        this.username=username;
		this.password=password;
		usertype = this.getClass().toString();
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

	public boolean isEmpty() {
		return email.length()==0&&name.length()==0&&ID.length()==0&&username.length()==0&&password.length()==0;
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
