package ch.hearc.qdljee.dto;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * Book Data transfer Object
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
public class BookDto {
	private String title;
	private String edition;
	private MultipartFile image;
	private String description;
	private Date releaseDate;

	public BookDto() {
	}

	public BookDto(String title, String description, String edition, MultipartFile image, Date releaseDate) {
		super();
		this.title = title;
		this.description = description;
		this.edition = edition;
		this.image = image;
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

	public final MultipartFile getImage() {
		return image;
	}

	public final void setImage(MultipartFile image) {
		this.image = image;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Date getReleaseDate() {
		return releaseDate;
	}

	public final void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
