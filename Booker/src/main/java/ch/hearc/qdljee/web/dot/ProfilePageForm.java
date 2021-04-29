package ch.hearc.qdljee.web.dot;

import ch.hearc.qdljee.Tools;
import ch.hearc.qdljee.model.User;

public class ProfilePageForm {

	public String newPassword;
	public String confirmPassword;
	public String email;
	public String lastName;
	public String firstName;
	public boolean author;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isAuthor() {
		return author;
	}

	public void setAuthor(boolean author) {
		this.author = author;
	}

	public boolean ArePasswordsOK() {

		User now = Tools.getCurrentUser();
		if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
			return false;
		}
		if (!newPassword.equals(confirmPassword) || newPassword.isBlank() || confirmPassword.isBlank()) {
			return false;
		}
		return true;
	}

	public boolean ArePasswordsEmpty() {
		return  newPassword.isEmpty() && confirmPassword.isEmpty();
	}
}
