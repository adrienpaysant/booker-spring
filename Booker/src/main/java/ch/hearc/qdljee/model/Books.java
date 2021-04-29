package ch.hearc.qdljee.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Books {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min = 3)
	@Column(name = "title")
	private String title;

	@NotEmpty
	@Size(min = 3)
	@Column(name = "edition")
	private String edition;

	@NotEmpty
	@Size(min = 3)
	@Column(name = "image")
	private String image;

	@NotEmpty
	@Size(min = 3)
	@Column(name = "description")
	private String description;

	@OneToOne(cascade = CascadeType.ALL)
	private User author;

	@NotEmpty
	@Column(name = "releaseDate")
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

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getEdition() {
		return edition;
	}

	public final void setEdition(String edition) {
		this.edition = edition;
	}

	public final String getImage() {
		return image;
	}

	public final void setImage(String image) {
		this.image = image;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final User getAuthor() {
		return author;
	}

	public final void setAuthor(User author) {
		this.author = author;
	}

	public final Date getReleaseDate() {
		return releaseDate;
	}

	public final void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
