package com.yellow.ordermanageryellow.service;
import com.yellow.ordermanageryellow.dao.ProductCategoryRepository;
import com.yellow.ordermanageryellow.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService implements CommandLineRunner {
    private final ProductCategoryRepository ProductCategoryRepository;
    @Autowired
    public ProductCategoryService(ProductCategoryRepository ProductCategoryRepository) {
        this.ProductCategoryRepository = ProductCategoryRepository;
    }
    @Override
    public void run(String... args) {
        ProductCategory ProductCategory = new ProductCategory("12");
        ProductCategoryRepository.save(ProductCategory);
    }
}