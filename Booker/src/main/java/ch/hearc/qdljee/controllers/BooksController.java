package ch.hearc.qdljee.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hearc.qdljee.dto.BookDto;

@Controller
@RequestMapping("/Books")
public class BooksController {

	@GetMapping
	public String index(Model model) {
		model.addAttribute("books");
		return "Books";
	}
	
	@GetMapping("/{id}")
	public String details(Model model,@PathVariable("id") int id){
		model.addAttribute("book");
		return "Details";
	}
	
	@GetMapping("/create")
	public String getCreatePage(Model model) {
		model.addAttribute("addBookForm",new BookDto());
		return "CreateBookPage";
	}
	
	@PostMapping("/create")
	public String createBook(Model model){
		return "redirect:/Books";
	}
	
	@PostMapping("/{id}/delete")
	public String deleteBook(Model model,@PathVariable("id") int id) {
		return "redirect:/Books";
	}
	
	@GetMapping("/{id}/update")
	public String updateBookPage(Model model,@PathVariable("id") int id) {
		model.addAttribute("updateForm");
		return "redirect:/Details";
	}
	
	@PostMapping("/{id}/update")
	public String updateBook(Model model,@PathVariable("id") int id) {
		return "redirect:/Details";
	}
}
