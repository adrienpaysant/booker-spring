package ch.hearc.qdljee.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.qdljee.Tools;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.web.dot.ProfilePageForm;

@Controller
public class ProfileController {
	@GetMapping("/profile")
	public String profile(Model model) {
		User now = Tools.getCurrentUser();
		model.addAttribute("user", now);// to display user infos
		ProfilePageForm ppForm = new ProfilePageForm();
		model.addAttribute("pForm", ppForm);
		return "profile";
	}

	@PostMapping("/profile/update")
	public String profileUpdate(Model model, //
			@ModelAttribute("pForm") ProfilePageForm ppForm) {

		return "redirect:/profile/?success";
	}
}
