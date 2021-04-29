package ch.hearc.qdljee.dto;

import java.sql.Date;

import ch.hearc.qdljee.model.User;

public class BookDto {
	private String title;
	private String edition;
	private String image;
	private String description;
	private User author;
	private Date releaseDate;

	public BookDto() {
	}

	public BookDto(String title, String description, String edition, String image, User author, Date releaseDate) {
		super();
		this.title = title;
		this.description = description;
		this.edition = edition;
		this.image = image;
		this.author = author;
		this.releaseDate = releaseDate;
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
