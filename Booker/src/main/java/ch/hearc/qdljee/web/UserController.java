package ch.hearc.qdljee.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hearc.qdljee.service.UserService;
import ch.hearc.qdljee.web.dot.UserDto;


@Controller
@RequestMapping("/register")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
	public UserDto userDto() {
		return new UserDto();
	}

	@GetMapping
	public String showRegistrationForm() {
		return "register";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserDto registrationDto) {
		userService.save(registrationDto);
		return "redirect:/register?success";
	}
}