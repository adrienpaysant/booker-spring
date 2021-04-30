package ch.hearc.qdljee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.qdljee.dto.BookDto;
import ch.hearc.qdljee.model.Books;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.repository.BookRepositery;

@Service
public class BookService {
	@Autowired
	BookRepositery bookRepository;

	public List<Books> getAllBooks() {
		List<Books> books = new ArrayList<Books>();
		bookRepository.findAll().forEach(book -> books.add(book));
		return books;
	}

	public Books getBooksById(int id) {
		return bookRepository.findById(id).get();
	}

	public void saveOrUpdate(BookDto bookDto, String imageURL,User author) {
		Books book = new Books(bookDto.getTitle(), bookDto.getDescription(), bookDto.getEdition(), imageURL,
				author, bookDto.getReleaseDate());
		bookRepository.save(book);
	}
	public void delete(int id) {
		bookRepository.deleteById(id);
	}
}