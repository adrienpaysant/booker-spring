package ch.hearc.qdljee.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.qdljee.model.Comments;

/**
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Repository
public interface CommentRepository extends CrudRepository<Comments, Integer> {

}
