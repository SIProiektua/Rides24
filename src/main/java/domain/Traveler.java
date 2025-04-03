package domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
public class Traveler extends User {
	private static final long serialVersionUID = 1L;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private List<Book> books;
    
    
    
	public Traveler(String email, String name, String ID, String username, String password) {
		super(email, name, ID, username, password);
		books = new ArrayList<Book>();
	}
	public Traveler(String email, String name) {
		super(email, name);
		books = new ArrayList<Book>();
	}

	public List<Book> getBookList(){
		return books;
	}
	
	public void setBookList(List<Book>lb){
		this.books=lb;
	}
	public void addBook(Book book){
		this.books.add(book);
	}
	public void removeBook(Book book){
		this.books.remove(book);
	}

	public String toString(){
		return email+";"+name+books;
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
	
}
