package com.fairbourn.recipes.databaseAccessObjects.implementation.inMemory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fairbourn.recipes.databaseAccessObjects.NationalitiesDatabaseAccessObject;

@Repository("inMemoryNationalities")
public class InMemoryNationalitiesDatabaseAccessObject implements NationalitiesDatabaseAccessObject {
	
	private static List<String> DB = new ArrayList<>();
	
	@Override
	public List<String> getAllNationalities() {
		if(DB.isEmpty()) {
			DB.add("American");
			DB.add("French");
			DB.add("Italian");
		}
		return DB;
	}

}
