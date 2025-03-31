package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity 
public class Mugimendua {
	private static int idNum = 0;
	@Id
	private int id;
	private float zenbat;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private User nondik;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private User nora;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private User user; 
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Book book;
	
	public Mugimendua() {
		id = idNum;
		idNum++;
	}
	
	public User getNondik() {
		return nondik;
	}

	public void setNondik(User nondik) {
		this.nondik = nondik;
	}

	public User getNora() {
		return nora;
	}

	public void setNora(User nora) {
		this.nora = nora;
	}

	public float getZenbat() {
		return zenbat;
	}
	
	public void setZenbat(float zenbat) {
		this.zenbat = zenbat;
	}
	public Book getBook(){
		return book;
	}
	public void setBook(Book book){
		this.book = book;
	}
}
