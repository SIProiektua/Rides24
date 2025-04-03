package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Book {
	private static int nid=0;
	@Id
	private int bookId;
	private int seats;
	private String status;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Traveler traveler;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Ride ride;
	public Book(int seats, String status) {
		setSeats(seats);
		setEgoera(status);
		nid++;
		bookId = nid;
	}
	
	public int getId() {
		return bookId;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getEgoera() {
		return status;
	}
	public void setEgoera(String egoera) {
		this.status = egoera;
	}
	public Traveler getTraveler() {
		return traveler;
	}
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	public Ride getRide() {
		return ride;
	}
	public void setRide(Ride ride) {
		this.ride = ride;
	}
	@Override
	public String toString(){
		return("ID = " + bookId + ". Seats = " + seats + ride.toString());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return bookId == other.bookId;
	}
}
