package ch.hearc.qdljee.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Books {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message="Please enter a title")
	@Column(name = "title")
	private String title;

	@NotEmpty(message="Please enter an edition")
	@Column(name = "edition")
	private String edition;

	@NotEmpty(message="invalid image")
	@Column(name="image")
	private String image;

	@NotEmpty(message = "Please enter a description")
	@Column(name = "description")
	private String description;

	@NotNull
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User author;

	@NotNull(message="Please enter a date")
	@Column(name = "releaseDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date releaseDate;

	public Books() {

	}

	public Books(String title, String description, String edition, String image, User author, Date releaseDate) {
		super();
		this.title = title;
		this.description = description;
		this.edition = edition;
		this.image = image;
		this.author = author;
		this.releaseDate = releaseDate;
	}
	
	public void addAttributes(String title, String description, String edition, String image, User author, Date releaseDate) {
		this.title = title;
		this.description = description;
		this.edition = edition;
		if(image !=null)this.image = image;
		this.author = author;
		this.releaseDate = releaseDate;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
