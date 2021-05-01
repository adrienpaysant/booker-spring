package ch.hearc.qdljee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.qdljee.dto.RatingsDto;
import ch.hearc.qdljee.model.Ratings;
import ch.hearc.qdljee.repository.BookRepositery;
import ch.hearc.qdljee.repository.RatingsRepository;
import ch.hearc.qdljee.repository.UserRepository;

@Service
public class RatingsService {

	@Autowired
	RatingsRepository ratingsRepository;
	@Autowired
	BookRepositery booksRepository;
	@Autowired
	UserRepository userRepository;

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

	public float getRatingsGlobalValue(Integer bookId) {
		List<Ratings> bookRatings = getAllRatingsForABook(bookId);
		OptionalInt sum = bookRatings.parallelStream().mapToInt(r -> r.getValue()).reduce(Integer::sum);
		return sum.isPresent() ? sum.getAsInt()/ (float)bookRatings.size():7;
	}

	public int getRatingsUserValue(Integer bookId,Long userId) {
		List<Ratings> bookRatings = getAllRatingsForABook(bookId);
		Optional<Ratings> rating = bookRatings.parallelStream().filter(r -> r.getUser().getId()==userId).findFirst();
		return rating.isPresent()?rating.get().getValue():7;
	}
	
	public void saveOrUpdate(RatingsDto ratingsDto, Integer bookId,Long userId) {
		List<Ratings> all = getAllRatings();
		Optional<Ratings> rating = all.parallelStream().filter(r -> r.getBook().getId()==bookId && r.getUser().getId()==userId).findAny();
		if(rating.isPresent()) {
			rating.get().setValue(ratingsDto.getValue());
			ratingsRepository.save(rating.get());
		} else {
			Ratings newRating = new Ratings(ratingsDto.getValue(),booksRepository.findById(bookId).get(),userRepository.findById(userId).get());
			ratingsRepository.save(newRating);
		}
		
	}
}
