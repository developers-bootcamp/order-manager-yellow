package com.yellow.ordermanageryellow.service;

import com.yellow.ordermanageryellow.exceptions.ObjectAlreadyExistException;
import com.yellow.ordermanageryellow.dao.ProductCategoryRepository;
import com.yellow.ordermanageryellow.model.AuditData;
import com.yellow.ordermanageryellow.model.ProductCategory;
import com.yellow.ordermanageryellow.model.RoleNames;
import com.yellow.ordermanageryellow.security.EncryptedData;
import com.yellow.ordermanageryellow.security.JwtToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private JwtToken jwtToken;

    public List<ProductCategory> findAll() {
        return this.productCategoryRepository.findAll();
    }

    public ProductCategory insert(ProductCategory newCategory) throws ObjectAlreadyExistException {
        if (this.productCategoryRepository.existsByname(newCategory.getName()))
            throw new ObjectAlreadyExistException("category name already exist");
        newCategory.setAuditData(new AuditData(LocalDate.now()));
        return this.productCategoryRepository.save(newCategory);
    }
    @SneakyThrows
    public void delete(String token,String categoryId) {
        System.out.println(RoleNames.ADMIN);
        String role= this.jwtToken.decryptToken(token, EncryptedData.ROLE);
        String company= this.jwtToken.decryptToken(token, EncryptedData.COMPANY);
        if (role != RoleNames.ADMIN.toString() || !company.equals(this.productCategoryRepository.findById(categoryId).orElse(null)))
            throw new NoPermissionException("You do not have permission to delete product category");
        this.productCategoryRepository.deleteById(categoryId);
    }

    public ProductCategory update(ProductCategory updatedCategory) {
        ProductCategory Category = this.productCategoryRepository.findById(updatedCategory.getId()).orElse(null);
        if (Category == null) {
            throw new NoSuchElementException("category is not found");
        }

        updatedCategory.setAuditData(new AuditData(Category.getAuditData().getCreateDate(), LocalDate.now()));
        return this.productCategoryRepository.save(updatedCategory);
    }
}
