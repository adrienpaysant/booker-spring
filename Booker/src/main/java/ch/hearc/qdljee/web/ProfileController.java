package ch.hearc.qdljee.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.qdljee.Tools;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.UserService;
import ch.hearc.qdljee.web.dot.ProfilePageForm;

@Controller
public class ProfileController {

	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public ProfileController(UserService userService) {
		super();
		this.userService = userService;
	}

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
		User now = Tools.getCurrentUser();
		String message = "";
		if (ppForm.ArePasswordsOK()) {
			now.setPassword(passwordEncoder.encode(ppForm.getNewPassword()));
			userService.save(now);
			message += "successPassword";
		} else {
			if (!ppForm.ArePasswordsEmpty()) {
				message += "errorPassword";
			}
		}
		if (Tools.isValidEmail(ppForm.getEmail())) {
			now.setEmail(ppForm.getEmail());
			userService.save(now);
			if (message == "") {
				message += "successEmail";
			} else {
				message += "&successEmail";
			}

		} else {
			if (!(ppForm.getEmail().isEmpty() || ppForm.getEmail().isBlank())) {
				if (message == "") {
					message += "errorEmail";
				} else {
					message += "&errorEmail";
				}
			}
		}
		return "redirect:/profile/?" + message;
	}
}
