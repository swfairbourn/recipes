package com.fairbourn.recipes.databaseAccessObjects.implementation.inMemory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fairbourn.recipes.databaseAccessObjects.TagsDatabaseAccessObject;

@Repository("inMemoryTags")
public class InMemoryTagsDatabaseAccessObject  implements TagsDatabaseAccessObject {

	private static List<String> DB = new ArrayList<>();
	
	@Override
	public List<String> getAllTags() {
		if(DB.isEmpty()) {
			DB.add("Breakfast");
			DB.add("Lunch");
			DB.add("Dinner");
			DB.add("Dessert");
			DB.add("Beef");
			DB.add("Instant Pot");
		}
		return DB;
	}

}
