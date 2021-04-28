package ch.hearc.qdljee;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.ShopeUserDetails;

public class Tools {

	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ShopeUserDetails sUDetails = (ShopeUserDetails) auth.getPrincipal();
		return sUDetails.getUser();
	}
}
