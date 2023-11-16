package com.fairbourn.recipes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fairbourn.recipes.databaseAccessObjects.RecipeDatabaseAccessObject;
import com.fairbourn.recipes.databaseAccessObjects.implementation.postgres.recipes.PostgresRecipeDataAccessService;

@Configuration
public class ModelsConfig {

	@Autowired
	private JdbcTemplate jdbcTemplate;
    
    @Bean
    public RecipeDatabaseAccessObject recipeDatabaseAccessObject() {
    	return new PostgresRecipeDataAccessService(jdbcTemplate);
    }
	
}
