package ch.hearc.qdljee.repository;

import org.springframework.stereotype.Repository;

import ch.hearc.qdljee.model.Books;

import org.springframework.data.repository.CrudRepository;  

/**
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Repository
public interface BookRepositery extends CrudRepository<Books,Integer>{

}
