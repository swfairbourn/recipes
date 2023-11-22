package com.fairbourn.recipes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fairbourn.recipes.services.NationalitiesService;

@RequestMapping("api/v1/nationalities")
@RestController
public class NationalitiesController {

	private final NationalitiesService nationalitiesService;
	
	@Autowired
	public NationalitiesController(NationalitiesService nationalitiesService) {
		this.nationalitiesService = nationalitiesService;
	}
	
	@GetMapping("/getAllNationalities")
	public List<String> getAllNationalities() {
		return nationalitiesService.getAllNationalities();
	}
}
