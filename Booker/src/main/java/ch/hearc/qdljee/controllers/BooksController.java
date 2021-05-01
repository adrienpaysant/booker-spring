package ch.hearc.qdljee.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.BookService;
import ch.hearc.qdljee.service.CommentService;
import ch.hearc.qdljee.service.RatingsService;
import ch.hearc.qdljee.service.ShopUserDetails;

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
	private HttpServletRequest request;
	@Autowired
	private Environment environment;

//	@GetMapping
//	public String index(Model model) {
//		model.addAttribute("books", bookService.getAllBooks());
//		return "Books";
//	}

	@GetMapping("/mybooks")
	public String myBooks() {
		return "redirect:/Books/?valueSearch=" + Tools.getCurrentUser().getFullName() + "&criterSearch=author";
	}

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

	@GetMapping("/search")
	public String search(Model model, //
			@ModelAttribute("sForm") SearchDto sFrom, @RequestParam(required = true) String searchValue,
			@RequestParam(required = true) String searchCriter) {
		return "redirect:/Books/?valueSearch=" + searchValue + "&criterSearch=" + searchCriter;
	}

	@GetMapping("/{id}")
	public String details(Model model, @PathVariable("id") int id) {
		model.addAttribute("book", bookService.getBooksById(id));
		model.addAttribute("comForm", new CommentDto());
		model.addAttribute("userId", Tools.getCurrentUser().getId());
		model.addAttribute("comments", commentService.getAllCommentsForABook(id));
		model.addAttribute("ratingGlobalValue",ratingsService.getRatingsGlobalValue(id));
		model.addAttribute("ratingUserValue",ratingsService.getRatingsUserValue(id, Tools.getCurrentUser().getId()));
		return "Details";
	}

	@PostMapping("/{id}/rate")
	public String rate(Model model, @ModelAttribute("ratingsDto") RatingsDto ratingsDto, @PathVariable("id") int id) {
		ratingsService.saveOrUpdate(ratingsDto,id,Tools.getCurrentUser().getId());
		return "redirect:/Books/"+id;
	}
	
	@GetMapping("/create")
	public String getCreatePage(Model model) {
		model.addAttribute("addBookForm", new BookDto());
		return "createBookPage";
	}

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
		return "redirect:/Books";
	}

	@PostMapping("/{id}/delete")
	public String deleteBook(Model model, @PathVariable("id") int id) {
		String path = environment.getProperty("images.path");
		bookService.delete(id,path);
		return "redirect:/Books";
	}

	@GetMapping("/{id}/update")
	public String updateBookPage(Model model, @PathVariable("id") int id) {
		model.addAttribute("updateForm", new BookDto());
		model.addAttribute("book", bookService.getBooksById(id));
		return "updateBookPage";
	}

	@PostMapping("/{id}/update")
	public String updateBook(Model model, @PathVariable("id") int id, @ModelAttribute("Bookdto") BookDto bookDto) {
		String imageURL = null;
		if (bookDto.getImage() != null && bookDto.getImage().getOriginalFilename().contains("octet-stream")) {
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
		return "redirect:/Books/" + id;
	}

	@PostMapping("/{id}/createComment")
	public String createComment(Model model, @ModelAttribute("CommentDto") CommentDto comDto,
			@PathVariable("id") int id) {
		if (!(comDto.getData().isBlank() || comDto.getData().isEmpty())) {
			comDto.setPublicationDate(new Date(System.currentTimeMillis()));
			comDto.setBookId(id);
			commentService.save(comDto);
		}
		return "redirect:/Books/" + id;
	}

	@PostMapping("/{id}/comment/{comId}/update")
	public String updateComment(Model model, @PathVariable("id") int id, @PathVariable("comId") int comId,
			@ModelAttribute("CommentDto") CommentDto comDto) throws Exception {
		comDto.setPublicationDate(new Date(System.currentTimeMillis()));
		comDto.setBookId(id);
		commentService.update(comDto, comId);
		return "redirect:/Books/" + id;
	}

	@PostMapping("/{id}/comment/{comId}/delete")
	public String deleteComment(Model model, @PathVariable("id") int id, @PathVariable("comId") int comId) {
		commentService.delete(comId);
		return "redirect:/Books/" + id;
	}
}
