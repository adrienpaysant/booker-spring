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

/**
 * 
 * @author Adrien Paysant and Joris Monnet
 *
 */
@Controller
public class ProfileController {

	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public ProfileController(UserService userService) {
		super();
		this.userService = userService;
	}

	/**
	 * Get profile page
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/profile")
	public String profile(Model model) {
		User now = Tools.getCurrentUser();
		model.addAttribute("user", now);// to display user infos
		System.out.println("last:  " + now.getLastName());
		System.out.println("first : " + now.getFirstName());
		model.addAttribute("lastNameDisp", now.getLastName());
		model.addAttribute("firstNameDisp", now.getFirstName());
		ProfilePageForm ppForm = new ProfilePageForm();
		model.addAttribute("pForm", ppForm);
		return "profile";
	}

	/**
	 * Update profile
	 * 
	 * @param model
	 * @param ppForm
	 * @return
	 */
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
		Collection<Role> userRoles = Tools.getCurrentUser().getRoles();
		boolean status = false;
		for (Role role : userRoles) {
			if (role.getName().equals("ROLE_ADMIN")) {
				status = true;
			}
		}
		if (!status) {
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
		}
		// first name & last name
		// first
		if (!ppForm.getFirstName().equals(now.getFirstName())) {
			if (!(ppForm.getFirstName().isEmpty() || ppForm.getFirstName().isBlank())) {
				now.setFirstName(ppForm.getFirstName());
				userService.save(now);
				if (message.equals("")) {
					message += "successFirstName";
				} else {
					message += "&successFirstName";
				}
			} else {
				if (message.equals("")) {
					message += "errorFirstName";
				} else {
					message += "&errorFirstName";
				}
			}
		}
		// last
		if (!ppForm.getLastName().equals(now.getLastName())) {
			if (!(ppForm.getLastName().isEmpty() || ppForm.getLastName().isBlank())) {
				now.setLastName(ppForm.getLastName());
				userService.save(now);
				if (message.equals("")) {
					message += "successLastName";
				} else {
					message += "&successLastName";
				}
			} else {
				if (message.equals("")) {
					message += "errorLastName";
				} else {
					message += "&errorLastName";
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
