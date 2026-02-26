package com.fairbourn.recipes.repositories;

import com.fairbourn.recipes.entities.TagEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("sqlite")
public interface TagRepository extends JpaRepository<TagEntity, String> {
}
