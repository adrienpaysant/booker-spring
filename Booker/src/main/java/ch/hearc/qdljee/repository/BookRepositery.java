package ch.hearc.qdljee.repository;

import org.springframework.stereotype.Repository;

import ch.hearc.qdljee.model.Books;

import org.springframework.data.repository.CrudRepository;  


@Repository
public interface BookRepositery extends CrudRepository<Books,Integer>{

}
