package com.fairbourn.recipes.databaseAccessObjects.implementation.postgres.recipes;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.fairbourn.recipes.databaseAccessObjects.RecipeDatabaseAccessObject;
import com.fairbourn.recipes.model.Recipe;
import com.fairbourn.recipes.model.Ingredient;

@Repository("postgres")
public class PostgresRecipeDataAccessService implements RecipeDatabaseAccessObject{

	private JdbcTemplate jdbcTemplate;
	
	public PostgresRecipeDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertRecipe(UUID id, Recipe recipe) {
		System.out.println("Inserting recipe into postgres: " + recipe.getTitle());
		String sql = "INSERT INTO recipes (id, title, rating, instructions, directions, nationality, tags) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, id, recipe.getTitle(), recipe.getRating(), recipe.getIngredients(), recipe.getDirections(), recipe.getNationality(), recipe.getTags());
        return rowsAffected > 0;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		String sql = "SELECT * FROM recipes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Recipe(
            rs.getObject("id", UUID.class),
            rs.getString("title"),
            rs.getInt("rating"),
            (List<Ingredient>) rs.getObject("ingredients", ArrayList.class),
            rs.getString("directions"),
            rs.getString("nationality"),
            (List<String>) rs.getObject("tags", ArrayList.class)
        ));
	}
	
	 @Override
	    public Optional<Recipe> getRecipeById(UUID id) {
	        String sql = "SELECT * FROM transactions WHERE id = ?";
	        PreparedStatementCreator psc = connection -> {
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setObject(1, id);
	            return ps;
	        };
	        ResultSetExtractor<Optional<Recipe>> rse = rs -> {
	            if (rs.next()) {
	            	Recipe recipe = new Recipe(
	        			rs.getObject("id", UUID.class),
	                    rs.getString("title"),
	                    rs.getInt("rating"),
	                    (List<Ingredient>) rs.getObject("ingredients", ArrayList.class),
	                    rs.getString("directions"),
	                    rs.getString("nationality"),
	                    (List<String>) rs.getObject("tags", ArrayList.class)
	                );
	                return Optional.of(recipe);
	            } else {
	                return Optional.empty();
	            }
	        };
	        return jdbcTemplate.query(psc, rse);
	    }

	    @Override
	    public boolean deleteRecipeById(UUID id) {
	        String sql = "DELETE FROM recipes WHERE id = ?";
	        int rowsAffected = jdbcTemplate.update(sql, id);
	        return rowsAffected > 0;
	    }

	    @Override
	    public boolean updateRecipeById(UUID id, Recipe recipe) {
	        String sql = "UPDATE recipes SET title = ?, rating = ?, instructions = ?, directions = ?, nationality = ?, tags = ? WHERE id = ?";
	        int rowsAffected = jdbcTemplate.update(sql, recipe.getTitle(), recipe.getRating(), recipe.getIngredients(), recipe.getDirections(), recipe.getNationality(), recipe.getTags(), id);
	        return rowsAffected > 0;
	    }
}
