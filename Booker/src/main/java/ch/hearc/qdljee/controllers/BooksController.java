package ch.hearc.qdljee.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hearc.qdljee.dto.BookDto;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.BookService;
import ch.hearc.qdljee.service.ShopUserDetails;

@Controller
@CrossOrigin
@RequestMapping("/Books")
public class BooksController {

	@Autowired
	BookService bookService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private Environment environment;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "Books";
	}

	@GetMapping("/{id}")
	public String details(Model model, @PathVariable("id") int id) {
		model.addAttribute("book", bookService.getBooksById(id));
		return "Details";
	}

	@GetMapping("/create")
	public String getCreatePage(Model model) {
		model.addAttribute("addBookForm", new BookDto());
		return "CreateBookPage";
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
		bookService.saveOrUpdate(bookDto, imageURL, author);
		return "redirect:/Books";
	}

	@DeleteMapping("/{id}")
	public String deleteBook(Model model, @PathVariable("id") int id) {
		bookService.delete(id);
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
		String imageURL = "/static/images/" + bookDto.getTitle() + bookDto.getEdition() + "."
				+ bookDto.getImage().getContentType().substring(6);
		User author = ((ShopUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();
		bookService.saveOrUpdate(bookDto, imageURL, author);
		return "redirect:/Books/" + id;
	}
}
