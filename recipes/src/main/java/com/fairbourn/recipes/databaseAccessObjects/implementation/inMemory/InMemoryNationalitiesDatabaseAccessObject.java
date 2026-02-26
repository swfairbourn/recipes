package com.fairbourn.recipes.databaseAccessObjects.implementation.inMemory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.fairbourn.recipes.databaseAccessObjects.NationalitiesDatabaseAccessObject;

@Repository("inMemoryNationalities")
@Profile("!sqlite")
public class InMemoryNationalitiesDatabaseAccessObject implements NationalitiesDatabaseAccessObject {
	
	private static List<String> DB = new ArrayList<>();
	
	@Override
	public List<String> getAllNationalities() {
		if(DB.isEmpty()) {
			DB.add("American");
			DB.add("Italian");
			DB.add("Mexican");
			DB.add("Chinese");
			DB.add("Japanese");
			DB.add("Indian");
			DB.add("Thai");
			DB.add("French");
			DB.add("Greek");
			DB.add("Spanish");
		}
		return DB;
	}

}
