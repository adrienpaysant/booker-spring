package ch.hearc.qdljee.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.hearc.qdljee.model.Role;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.repository.UserRepository;
import ch.hearc.qdljee.web.dot.UserDto;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserDto registrationDto) {
		System.out.println(registrationDto);
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
