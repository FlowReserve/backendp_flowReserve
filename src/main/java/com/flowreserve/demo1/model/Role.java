package com.flowreserve.demo1.model;

import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // Ej: "ROLE_USER", "ROLE_ADMIN"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role() {
        // Constructor vacío requerido por JPA
    }

    public Role(String name) {
        this.name = name;
    }
}
