package com.shop.onlineshop.controller;

import com.shop.onlineshop.model.entities.User;
import com.shop.onlineshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

//	@GetMapping({"index", "/get-user-list"})
//	public List<User> index() {
//		return userService.findAllUser();
//	}

	@GetMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@GetMapping("/signup")
	public ModelAndView signup() {
		ModelAndView mv = new ModelAndView("signup");
		mv.addObject("user",new User());
		return new ModelAndView("signup");
	}

	@GetMapping("error")
	public String error() {
		return "error";
	}

	@PostMapping("/signup")
	public ModelAndView registerNewPage(@ModelAttribute("user") @Valid User user,
						 BindingResult result, Model model) {
		logger.info("New user {}", user);

		if (result.hasErrors()) {
			return new ModelAndView("signup");
		}
		Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Pattern upperCasePatten = Pattern.compile("[A-Z ]");
		Pattern lowerCasePatten = Pattern.compile("[a-z ]");
		Pattern digitCasePatten = Pattern.compile("[0-9 ]");
		Pattern firstPattern = Pattern.compile("^[A-Z-]");
		Pattern emailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

		logger.info("New user {}", user);
		if (!emailPattern.matcher(user.getEmail()).find()) {
			result.rejectValue("email", "", "Hyphen “-” and dot “.” isn't allowed at the start and end of the domain-part,\n" +
					"No consecutive dots!");
			return new ModelAndView("signup");
		}
		if (!firstPattern.matcher(user.getFirstName()).find()) {
			result.rejectValue("firstName", "", "FirstName must have starts with one uppercase character!");
			return new ModelAndView("signup");
		}
		if (!firstPattern.matcher(user.getLastName()).find()) {
			result.rejectValue("lastName", "", "LastName must have starts with one uppercase character!");
			return new ModelAndView("signup");
		}
		if (user.getPassword().length() < 8) {
			result.rejectValue("password", "", "Minimum length of password is 8");
			return new ModelAndView("signup");
		}
		if (!specialCharPatten.matcher(user.getPassword()).find()) {
			result.rejectValue("password", "", "Password must have at least one special character!");
			return new ModelAndView("signup");
		}
		if (!upperCasePatten.matcher(user.getPassword()).find()) {
			result.rejectValue("password", "", "Password must have at least one uppercase character!");
			return new ModelAndView("signup");
		}
		if (!lowerCasePatten.matcher(user.getPassword()).find()) {
			result.rejectValue("password", "", "Password must have at least one lowercase character!");
			return new ModelAndView("signup");
		}
		if (!digitCasePatten.matcher(user.getPassword()).find()) {
			result.rejectValue("password", "", "Password must have at least one digit character!");
			return new ModelAndView("signup");
		}
		if (user.getFirstName().length() < 4) {
			result.rejectValue("firstName", "", "Minimum length of firstName is 4");
			return new ModelAndView("signup");
		}
		if (user.getFirstName().isEmpty()) {
			//result.rejectValue("","","Please enter all data");
			result.rejectValue("firstName", "", "Please enter first name");
			return new ModelAndView("signup");
		}
		if (user.getLastName().length() < 4) {
			result.rejectValue("lastName", "", "Minimum length of secondName is 4");
			return new ModelAndView("signup");
		}
		if (user.getLastName().isEmpty()) {
			result.rejectValue("lastName", "", "Please enter last name");
			return new ModelAndView("signup");
		}

		if (user.getEmail().isEmpty()) {
			result.rejectValue("email", "", "Please enter email");
			return new ModelAndView("signup");
		}

		if (user.getPassword().isEmpty()) {
			result.rejectValue("password", "", "Please enter password");
			return new ModelAndView("signup");
		}

		if (user.getFirstName().equals(user.getLastName())) {
			result.rejectValue("lastName", "", "First name and Last name must be different");
			return new ModelAndView("signup");
		}
		User checkEmail = userService.findByEmail(user.getEmail());
		if (checkEmail != null) {
			result.rejectValue("email", "", "This email already exists");
			return new ModelAndView("signup");
		}
		userService.save(user);
		return new ModelAndView("login");
	}
}