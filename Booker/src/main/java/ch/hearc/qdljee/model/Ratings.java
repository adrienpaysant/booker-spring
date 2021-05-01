package ch.hearc.qdljee.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Ratings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Min(value = 0, message = "Value should be greater then then equal to 0")
	@Max(value = 5, message = "Value should be less then then equal to 5")
	private Integer value;

	@NotNull
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Books book;

	public Ratings() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User rater) {
		this.user = rater;
	}

	public Books getBook() {
		return book;
	}

	public void setBook(Books book) {
		this.book = book;
	}

	public Ratings(Integer value, Books book, User user) {
		super();
		this.value = value;
		this.user = user;
		this.book = book;
	}
}
