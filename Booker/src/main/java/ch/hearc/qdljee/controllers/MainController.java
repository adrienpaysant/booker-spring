package ch.hearc.qdljee.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.hearc.qdljee.Tools;

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

	@GetMapping("/profile")
	public String profile() {
		//TODO add content
		return "profile";
	}

}
