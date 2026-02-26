package com.fairbourn.recipes.entities;

import jakarta.persistence.*;

/**
 * JPA entity for the nationalities table.
 */
@Entity
@Table(name = "nationalities")
public class NationalityEntity {

    @Id
    @Column(name = "nationality", nullable = false)
    private String nationality;

    public NationalityEntity() {}

    public NationalityEntity(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
