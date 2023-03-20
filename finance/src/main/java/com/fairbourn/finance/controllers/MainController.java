package com.fairbourn.finance.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("name", "Shawn");
		return "home";
	}
	
	@GetMapping("/budget")
	public String getBudgetPage(Model model) {
		return "budget";
	}
}
