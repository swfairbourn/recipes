package com.fairbourn.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("name", "World");
		return "home";
	}
	
}
