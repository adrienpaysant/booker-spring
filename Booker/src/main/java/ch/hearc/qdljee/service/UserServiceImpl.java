package ch.hearc.qdljee.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.hearc.qdljee.dto.UserDto;
import ch.hearc.qdljee.model.Role;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.repository.UserRepository;

/**
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
		makeAnAdmin();
	}

	@Override
	public void makeAnAdmin() {
		// check if admin
		List<User> allUser = userRepository.findAll();
		boolean status = false;
		for (User user : allUser) {
			if (user.getEmail().equals("admin@booker.ch")) {
				status = true;
			}
		}
		if (!status) {
			User user = new User("admin", "admin", "admin@booker.ch",new  BCryptPasswordEncoder().encode("admin"),
					Arrays.asList(new Role("ROLE_ADMIN")));
			this.userRepository.save(user);
		}
	}

	@Override
	public User save(UserDto registrationDto) {
		String role;
		if (registrationDto.getAuthor()) {
			role = "ROLE_AUTHOR";
		} else {
			role = "ROLE_READER";
		}
		User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role(role)));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new ShopUserDetails(user);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public Optional<User> findById(Long id) {
		return (Optional<User>) userRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public boolean existsByEmail(String email) {
		return null == userRepository.findByEmail(email);
	}

}
