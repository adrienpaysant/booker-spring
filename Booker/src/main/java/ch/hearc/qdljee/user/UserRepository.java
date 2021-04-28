package ch.hearc.qdljee.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.qdljee.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
