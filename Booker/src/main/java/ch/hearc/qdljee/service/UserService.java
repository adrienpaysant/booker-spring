package ch.hearc.qdljee.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import ch.hearc.qdljee.dto.UserDto;
import ch.hearc.qdljee.model.User;

public interface UserService extends UserDetailsService {
	User save(UserDto registrationDto);
	Iterable<User> findAll();

	long count();

	Optional<User> findById(Long id);

	void save(User user);

	boolean existsById(Long id);
	boolean existsByEmail(String email);
	
	void deleteById(Long id);

}
