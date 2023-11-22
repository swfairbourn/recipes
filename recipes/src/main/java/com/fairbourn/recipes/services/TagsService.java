package com.fairbourn.recipes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fairbourn.recipes.databaseAccessObjects.TagsDatabaseAccessObject;

@Service
public class TagsService {

	private final TagsDatabaseAccessObject tagsAccessObject;
	
	@Autowired
	public TagsService(@Qualifier("inMemoryTags") TagsDatabaseAccessObject tagsAccessObject) {
		this.tagsAccessObject = tagsAccessObject;
	}
	
	public List<String> getAllTags() {
		return tagsAccessObject.getAllTags();
	}
	
}
