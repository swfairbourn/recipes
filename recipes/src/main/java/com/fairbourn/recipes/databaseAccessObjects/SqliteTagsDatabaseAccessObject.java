package com.fairbourn.recipes.databaseAccessObjects;

import com.fairbourn.recipes.entities.TagEntity;
import com.fairbourn.recipes.repositories.TagRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SQLite-backed implementation of TagsDatabaseAccessObject.
 * Active only when the "sqlite" Spring profile is enabled.
 */
@Repository
@Qualifier("inMemoryTags") // reuses the same qualifier so TagsService needs no changes
@Profile("sqlite")
public class SqliteTagsDatabaseAccessObject implements TagsDatabaseAccessObject {

    private final TagRepository tagRepository;

    public SqliteTagsDatabaseAccessObject(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<String> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagEntity::getTag)
                .sorted()
                .toList();
    }

    public boolean insertTag(String tag) {
        if (tagRepository.existsById(tag)) return false;
        tagRepository.save(new TagEntity(tag));
        return true;
    }

    public boolean deleteTag(String tag) {
        if (!tagRepository.existsById(tag)) return false;
        tagRepository.deleteById(tag);
        return true;
    }
}
