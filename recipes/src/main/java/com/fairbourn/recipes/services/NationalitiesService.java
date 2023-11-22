package com.fairbourn.recipes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fairbourn.recipes.databaseAccessObjects.NationalitiesDatabaseAccessObject;

@Service
public class NationalitiesService {
	
	private final NationalitiesDatabaseAccessObject nationalitiesAccessObject;
	
	@Autowired
	public NationalitiesService(@Qualifier("inMemoryNationalities") NationalitiesDatabaseAccessObject nationalitiesAccessObject) {
		this.nationalitiesAccessObject = nationalitiesAccessObject;
	}
	
	public List<String> getAllNationalities() {
		return nationalitiesAccessObject.getAllNationalities();
	}

}
