package com.project.demo.repositories;

import com.project.demo.entities.Categories;
import com.project.demo.entities.Toys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToysRepository extends JpaRepository<Toys, Long> {
    List<Toys> findAll();
    Optional<Toys> findById(Long id);
}
