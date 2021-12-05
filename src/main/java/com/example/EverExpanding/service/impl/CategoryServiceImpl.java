package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Category;
import com.example.EverExpanding.model.view.PostViewModelSummary;
import com.example.EverExpanding.repository.CategoryRepository;
import com.example.EverExpanding.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveCategory(Category cat) {
        categoryRepository.save(cat);
    }
}
