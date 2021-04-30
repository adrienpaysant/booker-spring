package ch.hearc.qdljee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.qdljee.dto.ProfilePageForm;
import ch.hearc.qdljee.model.Role;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.service.UserService;

@SpringBootTest
public class BookerUserTests {

	@Autowired
	private UserService userService;

	@Test
	void testInsertUser() {
		assertEquals(0, userService.count());
		userService.save(new User("Adrien", "Paysant", "booker@testing.test", "password",
				Arrays.asList(new Role("ROLE_READER"))));
		assertEquals(1, userService.count());
	}

	@Test
	void testDeleteUser() {
		assertEquals(1, userService.count());
		User user = userService.findAll().iterator().next();
		userService.deleteById(user.getId());
		assertEquals(0, userService.count());
	}

	@Test
	void testEmailValidationWrong() {
		boolean status = false;
		if (Tools.isValidEmail("adrien.paysant")) {
			status = true;
		}
		assertEquals(false, status);
	}

	@Test
	void testEmailValidationGood() {
		boolean status = false;
		if (Tools.isValidEmail("adrien.paysant@he-arc.ch")) {
			status = true;
		}
		assertEquals(true, status);
	}

	@Test
	void testPasswordValidationGood() {
		ProfilePageForm pF = new ProfilePageForm();
		pF.setNewPassword("passwordGood");
		pF.setConfirmPassword("passwordGood");
		boolean status = false;
		if (pF.ArePasswordsOK()) {
			status = true;
		}
		assertEquals(true, status);
	}

	@Test
	void testPasswordValidationBadBlankOne() {
		ProfilePageForm pF = new ProfilePageForm();
		pF.setNewPassword("passwordGood");
		pF.setConfirmPassword(" ");
		boolean status = false;
		try {
			if (pF.ArePasswordsOK()) {
				status = true;
			}
		} catch (Exception e) {
			status = false;
		}

		assertEquals(false, status);
	}

	@Test
	void testPasswordValidationBadBlankBoth() {
		ProfilePageForm pF = new ProfilePageForm();
		pF.setNewPassword(" ");
		pF.setConfirmPassword(" ");
		boolean status = false;
		if (pF.ArePasswordsOK()) {
			status = true;
		}
		assertEquals(false, status);
	}

	@Test
	void testPasswordValidationBadEmptyOne() {
		ProfilePageForm pF = new ProfilePageForm();
		pF.setNewPassword(" ");
		boolean status = false;
		try {
			if (pF.ArePasswordsOK()) {
				status = true;
			}
		} catch (Exception e) {
			status = false;
		}

		assertEquals(false, status);
	}

	@Test
	void testPasswordValidationBadEmptyBoth() {
		ProfilePageForm pF = new ProfilePageForm();
		boolean status = false;
		try {
			if (pF.ArePasswordsOK()) {
				status = true;
			}
		} catch (Exception e) {
			status = false;
		}
		assertEquals(false, status);
	}

}
