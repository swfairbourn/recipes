package com.fairbourn.recipes.entities;

import jakarta.persistence.*;

/**
 * JPA entity for the tags table.
 */
@Entity
@Table(name = "tags")
public class TagEntity {

    @Id
    @Column(name = "tag", nullable = false)
    private String tag;

    public TagEntity() {}

    public TagEntity(String tag) {
        this.tag = tag;
    }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
}
