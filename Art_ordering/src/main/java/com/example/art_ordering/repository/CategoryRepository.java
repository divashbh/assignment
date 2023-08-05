package com.example.art_ordering.repository;


import com.example.art_ordering.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer > {
}
