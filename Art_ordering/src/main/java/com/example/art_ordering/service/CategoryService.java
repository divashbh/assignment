package com.example.art_ordering.service;

import com.example.art_ordering.entity.Category;
import com.example.art_ordering.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository CategoryRepository;
    public void addCategory(Category category){
        CategoryRepository.save(category);
    }
    public List<Category> getAllCategory(){
        return CategoryRepository.findAll();
    }
    public void removeCategoryById(int id) {
        CategoryRepository.deleteById(id);
    }

    public Optional<Category> getCategoryById(int id){
        return CategoryRepository.findById(id);
    }



}
