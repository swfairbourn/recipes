package com.fairbourn.recipes.databaseAccessObjects.implementation.inMemory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.fairbourn.recipes.databaseAccessObjects.TagsDatabaseAccessObject;

@Repository("inMemoryTags")
@Profile("!sqlite")
public class InMemoryTagsDatabaseAccessObject  implements TagsDatabaseAccessObject {

	private static List<String> DB = new ArrayList<>();
	
	@Override
	public List<String> getAllTags() {
		if(DB.isEmpty()) {
			DB.add("Breakfast");
			DB.add("Brunch");
			DB.add("Lunch");
			DB.add("Dinner");
			DB.add("Appetizer");
			DB.add("Side Dish");
			DB.add("Main Course");
			DB.add("Marinade");
			DB.add("Sauce");
			DB.add("Dessert");
			DB.add("Snack");
			DB.add("Soup");
			DB.add("Salad");
			DB.add("Pasta");
			DB.add("Chicken");
			DB.add("Beef");
			DB.add("Pork");
			DB.add("Seafood");
			DB.add("Slow Cooker");
			DB.add("Instant Pot");
			DB.add("Grilled");
			DB.add("Baked");
			DB.add("Quick & Easy");
			DB.add("Kid-Friendly");
		}
		return DB;
	}

}
