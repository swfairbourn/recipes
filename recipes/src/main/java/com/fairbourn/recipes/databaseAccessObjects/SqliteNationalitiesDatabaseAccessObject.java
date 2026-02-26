package com.fairbourn.recipes.databaseAccessObjects;

import com.fairbourn.recipes.entities.NationalityEntity;
import com.fairbourn.recipes.repositories.NationalityRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SQLite-backed implementation of NationalitiesDatabaseAccessObject.
 * Active only when the "sqlite" Spring profile is enabled.
 */
@Repository
@Qualifier("inMemoryNationalities") // reuses the same qualifier so NationalitiesService needs no changes
@Profile("sqlite")
public class SqliteNationalitiesDatabaseAccessObject implements NationalitiesDatabaseAccessObject {

    private final NationalityRepository nationalityRepository;

    public SqliteNationalitiesDatabaseAccessObject(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    @Override
    public List<String> getAllNationalities() {
        return nationalityRepository.findAll()
                .stream()
                .map(NationalityEntity::getNationality)
                .sorted()
                .toList();
    }

    public boolean insertNationality(String nationality) {
        if (nationalityRepository.existsById(nationality)) return false;
        nationalityRepository.save(new NationalityEntity(nationality));
        return true;
    }

    public boolean deleteNationality(String nationality) {
        if (!nationalityRepository.existsById(nationality)) return false;
        nationalityRepository.deleteById(nationality);
        return true;
    }
}
