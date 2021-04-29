package ch.hearc.qdljee.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hearc.qdljee.dto.BookDto;
import ch.hearc.qdljee.service.BookService;

@Controller
@RequestMapping("/Books")
public class BooksController {

	@Autowired
	BookService bookService;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("books",bookService.getAllBooks());
		return "Books";
	}

	@GetMapping("/{id}")
	public String details(Model model, @PathVariable("id") int id) {
		model.addAttribute("book",bookService.getBooksById(id));
		return "Details";
	}

	@GetMapping("/create")
	public String getCreatePage(Model model) {
		model.addAttribute("addBookForm", new BookDto());
		return "CreateBookPage";
	}

	@PostMapping("/create")
	public String createBook(Model model, @ModelAttribute("BookDto") BookDto bookDto) {
		bookService.saveOrUpdate(bookDto);
		return "redirect:/Books";
	}

	@DeleteMapping("/{id}")
	public String deleteBook(Model model, @PathVariable("id") int id) {
		bookService.delete(id);
		return "redirect:/Books";
	}

	@GetMapping("/{id}/update")
	public String updateBookPage(Model model, @PathVariable("id") int id) {
		model.addAttribute("updateForm",new BookDto());
		model.addAttribute("book",bookService.getBooksById(id));
		return "redirect:/Books/"+id;
	}

	@PutMapping("/{id}/update")
	public String updateBook(Model model,@PathVariable("id") int id,@ModelAttribute("Bookdto") BookDto bookDto) {
		bookService.saveOrUpdate(bookDto);
		return "redirect:/Books/"+id;
	}
}
