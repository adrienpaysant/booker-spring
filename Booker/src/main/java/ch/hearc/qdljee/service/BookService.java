package ch.hearc.qdljee.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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

/**
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Service
public class BookService {
	@Autowired
	BookRepositery bookRepository;

	/**
	 * Get books with pagination
	 * @param pageable
	 * @return
	 */
	public Page<Books> findPaginated(Pageable pageable) {
		List<Books> books = new LinkedList<Books>();
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

	
	/**
	 * Get books with pagination and search values
	 * @param pageable
	 * @param valueSearch
	 * @param criterSearch
	 * @return
	 */
	public Page<Books> findPaginated(PageRequest pageable, String valueSearch, String criterSearch) {
		System.out.println("val : " + valueSearch);
		System.out.println("crit : " + criterSearch);
		// filter on criter to search
		List<Books> books = new LinkedList<Books>();
		List<Books> tempBooks = getAllBooks();
		if (criterSearch.equals("none")) {
			findBooksByTitle(valueSearch, tempBooks, books);
		} else {
			switch (criterSearch) {
			case "author":
				for (Books tempBook : tempBooks) {
					if (tempBook.getAuthor().getFirstName().toLowerCase().contains(valueSearch.toLowerCase())
							|| tempBook.getAuthor().getLastName().toLowerCase().contains(valueSearch.toLowerCase()) || valueSearch.toLowerCase().contains(tempBook.getAuthor().getFirstName().toLowerCase())
							|| valueSearch.toLowerCase().contains(tempBook.getAuthor().getLastName().toLowerCase())) {
						books.add(tempBook);
					}
				}
				break;
			case "edition":
				for (Books tempBook : tempBooks) {
					if (tempBook.getEdition().toLowerCase().contains(valueSearch.toLowerCase())) {
						books.add(tempBook);
					}
				}
				break;
			case "title":
				findBooksByTitle(valueSearch, tempBooks, books);
				break;

			default:
				break;
			}
		}

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

	/**
	 * Simple search
	 * @param valueSearch
	 * @param tempBooks
	 * @param books
	 */
	private void findBooksByTitle(String valueSearch, List<Books> tempBooks, List<Books> books) {
		for (Books tempBook : tempBooks) {
			if (tempBook.getTitle().toLowerCase().contains(valueSearch.toLowerCase())) {
				books.add(tempBook);
			}
		}
	}

	public List<Books> getAllBooks() {
		List<Books> books = new ArrayList<Books>();
		bookRepository.findAll().forEach(book -> books.add(book));
		return books;
	}

	public Books getBooksById(int id) {
		return bookRepository.findById(id).get();
	}

	public void save(BookDto bookDto, String imageURL, User author) {
		Books book = new Books(bookDto.getTitle(), bookDto.getDescription(), bookDto.getEdition(), imageURL, author,
				bookDto.getReleaseDate());
		bookRepository.save(book);
	}

	public void update(BookDto bookDto, String imageURL, User author, int id) throws Exception {
		Optional<Books> book = bookRepository.findById(id);
		if (book.isEmpty())
			throw new Exception("no book to update");
		book.get().addAttributes(bookDto.getTitle(), bookDto.getDescription(), bookDto.getEdition(), imageURL!=null?imageURL:book.get().getImage(), author,
				bookDto.getReleaseDate());
		bookRepository.save(book.get());
	}

	public void delete(int id, String path) {
		String imageUrl = bookRepository.findById(id).get().getImage();
		try {
			File file = new File(path+imageUrl);
			file.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
		bookRepository.deleteById(id);
	}
}