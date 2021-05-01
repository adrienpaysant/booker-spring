package ch.hearc.qdljee.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller with /login and /
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Controller
public class MainController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home() {

		return "index";
	}

}
