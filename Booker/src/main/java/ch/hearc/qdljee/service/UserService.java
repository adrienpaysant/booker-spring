package ch.hearc.qdljee.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.web.dot.UserDto;

public interface UserService extends UserDetailsService {
	User save(UserDto registrationDto);
}
