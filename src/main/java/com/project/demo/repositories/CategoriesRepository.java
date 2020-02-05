package com.project.demo.repositories;

import com.project.demo.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    List<Categories> findAll();
    Optional<Categories> findById(Long id);

    @Modifying
    @Query("UPDATE Categories c SET c.name = :name WHERE c.id = :id")
    void updateCategory(@Param("name") String name, @Param("id") Long id);

}
