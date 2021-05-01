package ch.hearc.qdljee.dto;

import java.sql.Date;

/**
 * Comment Data transfer Object
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
public class CommentDto {
	private Integer bookId;
	private Date publicationDate;
	private String data;
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
