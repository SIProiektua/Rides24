package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mugimenduak {
	private static int idNum = 0;
	@Id
	private int id;
	private int zenbat;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Driver driver;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Traveler traveler;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Book book;

	public Mugimenduak() {
		id = idNum;
		idNum++;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the zenbat
	 */
	public int getZenbat() {
		return zenbat;
	}

	/**
	 * @param zenbat the zenbat to set
	 */
	public void setZenbat(int zenbat) {
		this.zenbat = zenbat;
	}

	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	/**
	 * @return the traveler
	 */
	public Traveler getTraveler() {
		return traveler;
	}

	/**
	 * @param traveler the traveler to set
	 */
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Mugimenduak [id=" + id + ", zenbat=" + zenbat + ", driver=" + driver + ", traveler=" + traveler
				+ ", book=" + book + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}


}