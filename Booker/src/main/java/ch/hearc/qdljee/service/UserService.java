package ch.hearc.qdljee.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import ch.hearc.qdljee.dto.UserDto;
import ch.hearc.qdljee.model.User;

/**
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
public interface UserService extends UserDetailsService {
	User save(UserDto registrationDto);

	Iterable<User> findAll();

	void makeAnAdmin();

	long count();

	Optional<User> findById(Long id);

	void save(User user);

	boolean existsById(Long id);

	boolean existsByEmail(String email);

	void deleteById(Long id);

}
