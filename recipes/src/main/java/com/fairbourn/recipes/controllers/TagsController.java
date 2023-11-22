package com.fairbourn.recipes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fairbourn.recipes.services.TagsService;

@RequestMapping("api/v1/tags")
@RestController
public class TagsController {

	private final TagsService tagsService;
	
	@Autowired
	public TagsController(TagsService tagsService) {
		this.tagsService = tagsService;
	}
	
	@GetMapping("/getAllTags")
	public List<String> getAllTags() {
		return tagsService.getAllTags();
	}
}
