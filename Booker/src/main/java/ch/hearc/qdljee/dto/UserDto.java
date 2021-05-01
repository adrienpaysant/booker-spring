package ch.hearc.qdljee.dto;

/**
 * User Data Transfer Object
 * @author Adrien Paysant and Joris Monnet
 *
 */
public class UserDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean author;

	public UserDto() {

	}

	public UserDto(String firstName, String lastName, String email, String password, boolean author) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.author = author;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getAuthor() {
		return author;
	}

	public void setAuthor(boolean author) {
		this.author = author;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDto [firstname=");
		builder.append(firstName);
		builder.append(", lastname=");
		builder.append(lastName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", author=");
		builder.append(author);
		builder.append("]");
		return builder.toString();
	}

}
