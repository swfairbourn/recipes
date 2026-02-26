package com.fairbourn.recipes.repositories;

import com.fairbourn.recipes.entities.RecipeEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for recipes.
 * Only active when the "sqlite" profile is enabled.
 */
@Repository
@Profile("sqlite")
public interface RecipeRepository extends JpaRepository<RecipeEntity, String> {
}
