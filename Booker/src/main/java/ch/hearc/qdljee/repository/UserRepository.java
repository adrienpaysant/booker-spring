package ch.hearc.qdljee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.qdljee.model.User;

/**
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
