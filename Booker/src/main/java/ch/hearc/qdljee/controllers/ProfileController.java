package ch.hearc.qdljee.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.qdljee.Tools;
import ch.hearc.qdljee.dto.ProfilePageForm;
import ch.hearc.qdljee.model.Role;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.UserService;

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
		// password
		if (ppForm.ArePasswordsOK()) {
			now.setPassword(passwordEncoder.encode(ppForm.getNewPassword()));
			userService.save(now);
			message += "successPassword";
		} else {
			if (!ppForm.ArePasswordsEmpty()) {
				message += "errorPassword";
			}
		}
		// email
		if (Tools.isValidEmail(ppForm.getEmail())) {
			now.setEmail(ppForm.getEmail());
			userService.save(now);
			if (message.equals("")) {
				message += "successEmail";
			} else {
				message += "&successEmail";
			}

		} else {
			if (!(ppForm.getEmail().isEmpty() || ppForm.getEmail().isBlank())) {
				if (message.equals("")) {
					message += "errorEmail";
				} else {
					message += "&errorEmail";
				}
			}
		}
		// role
		if (ppForm.isChangingRole()) {
			Collection<Role> roles = now.getRoles();
			String roleName = "";
			for (Role role : roles) {
				roleName = role.getName();
			}
			if (roleName.equals("ROLE_READER")) {
				now.setRoles(Arrays.asList(new Role("ROLE_AUTHOR")));
			} else if (roleName.equals("ROLE_AUTHOR")) {
				now.setRoles(Arrays.asList(new Role("ROLE_READER")));
			}
			userService.save(now);
			return "redirect:/logout";
		}
		return "redirect:/profile/?" + message;
	}
}
