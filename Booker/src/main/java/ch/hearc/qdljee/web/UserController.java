package ch.hearc.qdljee.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hearc.qdljee.Tools;
import ch.hearc.qdljee.repository.UserRepository;
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
		if (registrationDto.getFirstName().length() >= 3 && registrationDto.getLastName().length() >= 3
				&& Tools.isValidEmail(registrationDto.getEmail())
				&& userService.existsByEmail(registrationDto.getEmail())) {
			userService.save(registrationDto);
			return "redirect:/register?success";
		} else {
			return "redirect:/register?error";
		}
	}
}