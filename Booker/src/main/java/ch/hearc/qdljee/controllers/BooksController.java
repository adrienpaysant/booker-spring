package ch.hearc.qdljee.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.qdljee.Tools;
import ch.hearc.qdljee.dto.BookDto;
import ch.hearc.qdljee.dto.CommentDto;
import ch.hearc.qdljee.dto.RatingsDto;
import ch.hearc.qdljee.dto.SearchDto;
import ch.hearc.qdljee.model.Books;
import ch.hearc.qdljee.model.Role;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.BookService;
import ch.hearc.qdljee.service.CommentService;
import ch.hearc.qdljee.service.RatingsService;
import ch.hearc.qdljee.service.ShopUserDetails;

/**
 * Books controller, handle also comments and ratings
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Controller
@CrossOrigin
@RequestMapping("/Books")
public class BooksController {

	@Autowired
	BookService bookService;

	@Autowired
	CommentService commentService;

	@Autowired
	RatingsService ratingsService;

	@Autowired
	private Environment environment;

	/**
	 * Return the Books view with search value for author as the current user
	 * 
	 * @return
	 */
	@GetMapping("/mybooks")
	public String myBooks() {
		return "redirect:/Books/?valueSearch=" + Tools.getCurrentUser().getFullName() + "&criterSearch=author";
	}

	/**
	 * Return Books page with pagination and search results
	 * 
	 * @param model
	 * @param page
	 * @param size
	 * @param valueSearch
	 * @param criterSearch
	 * @return Books page
	 */
	@GetMapping
	public String listBooks(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam(required = false) String valueSearch,
			@RequestParam(required = false) String criterSearch) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);
		if (pageSize >= 8) {
			pageSize = 8;
		}
		if (valueSearch == null && criterSearch == null) {
			Page<Books> bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
			model.addAttribute("bookPage", bookPage);
			model.addAttribute("sForm", new SearchDto());
			int totalPages = bookPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
		} else {
			Page<Books> bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize), valueSearch,
					criterSearch);
			model.addAttribute("bookPage", bookPage);
			model.addAttribute("sForm", new SearchDto());
			int totalPages = bookPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
		}
		return "Books";
	}

	/**
	 * Return the Books page with the search criteria
	 * 
	 * @param model
	 * @param sFrom
	 * @param searchValue
	 * @param searchCriter
	 * @return
	 */
	@GetMapping("/search")
	public String search(Model model, //
			@ModelAttribute("sForm") SearchDto sFrom, @RequestParam(required = true) String searchValue,
			@RequestParam(required = true) String searchCriter) {
		return "redirect:/Books/?valueSearch=" + searchValue + "&criterSearch=" + searchCriter;
	}

	/**
	 * Return Details page of a book
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public String details(Model model, @PathVariable("id") int id) {
		model.addAttribute("book", bookService.getBooksById(id));
		model.addAttribute("comForm", new CommentDto());
		model.addAttribute("userId", Tools.getCurrentUser().getId());
		model.addAttribute("comments", commentService.getAllCommentsForABook(id));
		model.addAttribute("ratingGlobalValue", ratingsService.getRatingsGlobalValue(id));
		model.addAttribute("ratingUserValue", ratingsService.getRatingsUserValue(id, Tools.getCurrentUser().getId()));
		return "Details";
	}

	/**
	 * Rate a book
	 * 
	 * @param model
	 * @param ratingsDto
	 * @param id
	 * @return
	 */
	@PostMapping("/{id}/rate")
	public String rate(Model model, @ModelAttribute("ratingsDto") RatingsDto ratingsDto, @PathVariable("id") int id) {
		ratingsService.saveOrUpdate(ratingsDto, id, Tools.getCurrentUser().getId());
		return "redirect:/Books/" + id + "/?ratingSucces";
	}

	/**
	 * Get the create page for books
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/create")
	public String getCreatePage(Model model) {
		model.addAttribute("addBookForm", new BookDto());
		return "createBookPage";
	}

	/**
	 * Create a new book
	 * 
	 * @param model
	 * @param bookDto
	 * @return
	 */
	@PostMapping("/create")
	public String createBook(Model model, @ModelAttribute("BookDto") BookDto bookDto) {
		String path = environment.getProperty("images.path");
		String imageURL = bookDto.getTitle() + bookDto.getEdition() + "."
				+ bookDto.getImage().getContentType().substring(6);
		File image = new File(path + imageURL).getAbsoluteFile();
		try {
			InputStream is = bookDto.getImage().getInputStream();
			OutputStream out = new FileOutputStream(image);
			byte buf[] = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0)
				out.write(buf, 0, len);
			is.close();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		User author = ((ShopUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();
		bookService.save(bookDto, imageURL, author);
		return "redirect:/Books/?bookCreated";
	}

	/**
	 * Delete a book
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@PostMapping("/{id}/delete")
	public String deleteBook(Model model, @PathVariable("id") int id) {
		if (!validatePermForBook(id)) {
			return "redirect:/Books/?errorPermissions";
		}
		String path = environment.getProperty("images.path");
		bookService.delete(id, path);
		return "redirect:/Books";
	}

	/**
	 * Get the update form for the books
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/update")
	public String updateBookPage(Model model, @PathVariable("id") int id) {
		model.addAttribute("updateForm", new BookDto());
		model.addAttribute("book", bookService.getBooksById(id));
		return "updateBookPage";
	}

	/**
	 * Update a Book
	 * 
	 * @param model
	 * @param id
	 * @param bookDto
	 * @return
	 */
	@PostMapping("/{id}/update")
	public String updateBook(Model model, @PathVariable("id") int id, @ModelAttribute("Bookdto") BookDto bookDto) {
		if (!validatePermForBook(id)) {
			return "redirect:/Books/?errorPermissions";
		}

		String imageURL = null;
		if (bookDto.getImage() != null && !bookDto.getImage().getOriginalFilename().contains("octet-stream")) {
			String path = environment.getProperty("images.path");
			imageURL = bookDto.getTitle() + bookDto.getEdition() + "."
					+ bookDto.getImage().getContentType().substring(6);
			File image = new File(path + imageURL).getAbsoluteFile();
			try {
				InputStream is = bookDto.getImage().getInputStream();
				OutputStream out = new FileOutputStream(image);
				byte buf[] = new byte[1024];
				int len;
				while ((len = is.read(buf)) > 0)
					out.write(buf, 0, len);
				is.close();
				out.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		User author = ((ShopUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();
		try {
			bookService.update(bookDto, imageURL, author, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Books/" + id + "/?bookUpdated";
	}

	/**
	 * Add a new comment
	 * 
	 * @param model
	 * @param comDto
	 * @param id
	 * @return
	 */
	@PostMapping("/{id}/createComment")
	public String createComment(Model model, @ModelAttribute("CommentDto") CommentDto comDto,
			@PathVariable("id") int id) {
		if (!(comDto.getData().isBlank() || comDto.getData().isEmpty())) {
			comDto.setPublicationDate(new Date(System.currentTimeMillis()));
			comDto.setBookId(id);
			commentService.save(comDto);
		}
		return "redirect:/Books/" + id + "/?commentAdd";
	}

	/**
	 * Update a comment
	 * 
	 * @param model
	 * @param id
	 * @param comId
	 * @param comDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{id}/comment/{comId}/update")
	public String updateComment(Model model, @PathVariable("id") int id, @PathVariable("comId") int comId,
			@ModelAttribute("CommentDto") CommentDto comDto) throws Exception {
		if (!validatePermForComment(comId)) {
			return "redirect:/Books/" + id + "?errorPermissions";
		}
		comDto.setPublicationDate(new Date(System.currentTimeMillis()));
		comDto.setBookId(id);
		commentService.update(comDto, comId);
		return "redirect:/Books/" + id + "/?commentEdit";
	}

	/**
	 * Delete a comment
	 * 
	 * @param model
	 * @param id
	 * @param comId
	 * @return
	 */
	@PostMapping("/{id}/comment/{comId}/delete")
	public String deleteComment(Model model, @PathVariable("id") int id, @PathVariable("comId") int comId) {
		if (!validatePermForComment(comId)) {
			return "redirect:/Books/" + id + "?errorPermissions";
		}
		commentService.delete(comId);
		return "redirect:/Books/" + id + "/?commentDeleted";
	}

	/**
	 * Check if user has permissions for the comments
	 * 
	 * @param comId
	 * @return
	 */
	private boolean validatePermForComment(int comId) {
		Collection<Role> roles = Tools.getCurrentUser().getRoles();
		boolean status = false;
		for (Role role : roles) {
			if (commentService.getCommentsById(comId).getAuthor().equals(Tools.getCurrentUser())) {
				status = true;
			}
			if (role.getName().equals("ROLE_ADMIN")) {
				status = false;
			}
		}
		return !status;
	}

	/**
	 * Check if the user has the permissions for the book update/delete
	 * 
	 * @param id
	 * @return
	 */
	private boolean validatePermForBook(int id) {
		Collection<Role> roles = Tools.getCurrentUser().getRoles();
		boolean status = false;
		for (Role role : roles) {
			if (!role.getName().equals("ROLE_AUTHOR")
					&& !bookService.getBooksById(id).getAuthor().equals(Tools.getCurrentUser())) {
				status = true;
			}
			if (role.getName().equals("ROLE_ADMIN")) {
				status = false;
			}
		}
		return !status;
	}
}
