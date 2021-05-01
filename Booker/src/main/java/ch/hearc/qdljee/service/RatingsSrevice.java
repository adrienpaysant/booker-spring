package ch.hearc.qdljee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.qdljee.dto.RatingsDto;
import ch.hearc.qdljee.model.Books;
import ch.hearc.qdljee.model.Ratings;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.repository.BookRepositery;
import ch.hearc.qdljee.repository.RatingsRepository;

@Service
public class RatingsSrevice {

	@Autowired
	RatingsRepository ratingsRepository;

	public List<Ratings> getAllRatings() {
		List<Ratings> ratings = new ArrayList<>();
		ratingsRepository.findAll().forEach(r -> ratings.add(r));
		return ratings;
	}

	public List<Ratings> getAllRatingsForABook(Integer bookId) {
		List<Ratings> all = getAllRatings();
		List<Ratings> bookRatings = new ArrayList<Ratings>();
		for (Ratings rating : all) {
			if (rating.getBook().getId() == bookId) {
				bookRatings.add(rating);
			}
		}
		return bookRatings;
	}

	public int getRatingsValue(Integer bookId) {
		List<Ratings> bookRatings = getAllRatingsForABook(bookId);
		return bookRatings.parallelStream().mapToInt(r -> r.getValue()).reduce(Integer::sum).getAsInt()
				/ bookRatings.size();
	}

	public void save(RatingsDto ratingsDto, User author, Books book) {
		Ratings rating = new Ratings(ratingsDto.getValue(), author, book);
		ratingsRepository.save(rating);
	}
	
	public void update(RatingsDto ratingsDto, int id) {
	 Ratings rating = ratingsRepository.findById(id).get();
	 rating.setValue(ratingsDto.getValue());
		ratingsRepository.save(rating);
	}
}
