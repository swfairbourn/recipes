package com.fairbourn.recipes.repositories;

import com.fairbourn.recipes.entities.NationalityEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("sqlite")
public interface NationalityRepository extends JpaRepository<NationalityEntity, String> {
}
