package com.udemy.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.udemy.springboot.form.app.editors.CountryPropertyEditor;
import com.udemy.springboot.form.app.editors.RolePropertyEditor;
import com.udemy.springboot.form.app.editors.UppercaseFieldsEditor;
import com.udemy.springboot.form.app.models.domain.Country;
import com.udemy.springboot.form.app.models.domain.Role;
import com.udemy.springboot.form.app.models.domain.User;
import com.udemy.springboot.form.app.services.CountryService;
import com.udemy.springboot.form.app.services.RoleService;

@Controller
@SessionAttributes("user") // This is used to persist data during the session (between requests)
public class FormController {
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CountryPropertyEditor countryEditor;
	
	@Autowired
	private RolePropertyEditor roleEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// **** This block is used to validate field birthday on the form
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false); // This is to be strict on the validation of the format of the date
		binder.registerCustomEditor(Date.class, "birthday", new CustomDateEditor(dateFormat, true));
		// ****
		
		// This line is to register the Editor to change to UpperCase all the fields type String 
		binder.registerCustomEditor(String.class, new UppercaseFieldsEditor());
		
		// With this we convert the id of the country to the Country object
		binder.registerCustomEditor(Country.class, countryEditor);
		
		// With this we convert the id of the Role to the Role object
		binder.registerCustomEditor(Role.class, roleEditor);
	}
	
	@ModelAttribute("gender")
	public List<String> gender(){
		return Arrays.asList("Man","Woman");
	}
	
	@ModelAttribute("countriesList")
	public List<Country> countriesList(){
		return countryService.list();
	}
	
	@ModelAttribute("rolesList")
	public List<Role> rolesList() {
		return roleService.list();
	}
	
	//Not already used
	/*
	@ModelAttribute("countries")
	public List<String> countries(){
		return Arrays.asList("Colombia","Mexico","EEUU", "Brasil");
	}

	//Not already used
	@ModelAttribute("countriesMap")
	public Map<String, String> countriesMap(){
		Map<String, String> countries = new HashMap<String, String>();
		countries.put("CO", "Colombia");
		countries.put("MX", "Mexico");
		countries.put("EU", "EEUU");
		countries.put("BR", "Brasil");
		
		return countries;
	}
	*/
	
	@GetMapping("/form")
	public String form(Model model) {
		User user = new User();
		user.setIdentifier("12.234.232-K");
		user.setName("Name Test");
		user.setLastname("Last Name test");
		user.setEnable(true);
		
		model.addAttribute("title", "Form Users");
		model.addAttribute("user", user);
		return "form";
	}
	
	/*
	// Option no optimized
	@PostMapping("/form")
	public String processForm(Model model, 
			@RequestParam String username, 
			@RequestParam String password, 
			@RequestParam String email) {
		
		User user = new User(username, password, email);
		
		model.addAttribute("title", "Form result");
		model.addAttribute("user", user);
		
		return "result";
	}
	*/
	
	// This option is valid only if the name of the fields in the form are the same as the POJO (Entity)
	// @Valid : This annotation is used to validate fields in the form. Validations are in the POJO
	// BindingResult: This handle validation from the POJO and Should be just after the object which is validated.
	@PostMapping("/form")
	public String processForm(@Valid User user, BindingResult result, Model model) {
		
		
		
		if(result.hasErrors()) {
			/*
			Map<String, String> errors = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errors.put(err.getField(), "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errors);
			*/
			model.addAttribute("title", "Form result");
			return "form";
		}
		
		return "redirect:/show";
	}
	
	/*
	 * @SessionAttribute(name="user", required = false) => This is used to take an attribute from the session and it's
	 * required here because this method is used from a redirect in previous method, so, we have to take the user object from 
	 * the session.
	 */
	@GetMapping("/show")
	public String show(@SessionAttribute(name="user", required = false) User user, Model model, SessionStatus status) {
		if(user == null) {
			return "redirect:/form";
		}
		model.addAttribute("title", "Form result");
		status.setComplete(); // With this, we indicate that attribute of the session can be deleted.
		return "result";
	}
	

}
