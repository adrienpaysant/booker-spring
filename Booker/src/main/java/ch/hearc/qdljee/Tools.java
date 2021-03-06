package ch.hearc.qdljee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.ShopUserDetails;

/**
 * Tools class
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
public class Tools {

	/**
	 * @return current authenticated User
	 */
	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ShopUserDetails sUDetails = (ShopUserDetails) auth.getPrincipal();
		return sUDetails.getUser();
	}

	/**
	 * Check if a given string is an email valid
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean isValidEmail(String email) {

		final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		Pattern pattern;

		// non-static Matcher object because it's created from the input String
		Matcher matcher;
		// initialize the Pattern object
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
