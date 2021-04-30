package ch.hearc.qdljee.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.hearc.qdljee.dto.BookDto;
import ch.hearc.qdljee.model.Books;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.repository.BookRepositery;

@Service
public class BookService {
	@Autowired
	BookRepositery bookRepository;

	private List<Books> books = null;

	public Page<Books> findPaginated(Pageable pageable) {
		books = getAllBooks();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Books> list;

		if (books.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, books.size());
			list = books.subList(startItem, toIndex);
		}

		Page<Books> bookPage = new PageImpl<Books>(list, PageRequest.of(currentPage, pageSize), books.size());

		return bookPage;
	}

	public List<Books> getAllBooks() {
		List<Books> books = new ArrayList<Books>();
		bookRepository.findAll().forEach(book -> books.add(book));
		return books;
	}

	public Books getBooksById(int id) {
		return bookRepository.findById(id).get();
	}

	public void saveOrUpdate(BookDto bookDto, String imageURL, User author) {
		Books book = new Books(bookDto.getTitle(), bookDto.getDescription(), bookDto.getEdition(), imageURL, author,
				bookDto.getReleaseDate());
		bookRepository.save(book);
	}

	public void delete(int id) {
		bookRepository.deleteById(id);
	}
}