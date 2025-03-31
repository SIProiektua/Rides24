package domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Mugimendua> mugimenduak;
	public Book(int seats, String status) {
		setSeats(seats);
		setEgoera(status);
		nid++;
		bookId = nid;
	}
	public List<Mugimendua> getMugimenduak(){
		return mugimenduak;
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
}
