package ch.hearc.qdljee.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.qdljee.model.Ratings;

@Repository
public interface RatingsRepository extends CrudRepository<Ratings, Integer> {

}
