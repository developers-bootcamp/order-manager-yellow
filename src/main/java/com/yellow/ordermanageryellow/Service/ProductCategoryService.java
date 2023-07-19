package com.yellow.ordermanageryellow.service;

import com.yellow.ordermanageryellow.dao.CompanyRepository;
import com.yellow.ordermanageryellow.dao.RolesRepository;
import com.yellow.ordermanageryellow.dao.UserRepository;
import com.yellow.ordermanageryellow.exceptions.NoPermissionException;
import com.yellow.ordermanageryellow.exceptions.ObjectAlreadyExistException;
import com.yellow.ordermanageryellow.dao.ProductCategoryRepository;
import com.yellow.ordermanageryellow.model.*;
import com.yellow.ordermanageryellow.security.EncryptedData;
import com.yellow.ordermanageryellow.security.JwtToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ProductCategory> findAll() {
        return this.productCategoryRepository.findAll();
    }

    public ProductCategory insert(ProductCategory newCategory)  throws ObjectAlreadyExistException{
        if (this.productCategoryRepository.existsByname(newCategory.getName()))
            throw new ObjectAlreadyExistException("category name already exist");
        newCategory.setAuditData(new AuditData(LocalDateTime.now()));
        return this.productCategoryRepository.save(newCategory);
    }
@SneakyThrows
    public void delete(String token,String categoryId) throws NoPermissionException {
        String role= this.jwtToken.decryptToken(token, EncryptedData.ROLE);
        String company= this.jwtToken.decryptToken(token, EncryptedData.COMPANY);
        ProductCategory categoryFromDb = this.productCategoryRepository.findById(categoryId).orElse(null);
        if (categoryFromDb == null) {
            throw new NoSuchElementException("category is not found");
        }
        String companyOfCategory = categoryFromDb.getCompanyId().getId();
        Roles wholeRole = rolesRepository.findById(role).orElse(null);
        if( !wholeRole.getName().equals(RoleNames.ADMIN)|| !company.equals(companyOfCategory))
            throw new NoPermissionException("You do not have permission to delete product category");
        this.productCategoryRepository.deleteById(categoryId);
    }
    @SneakyThrows
    public ProductCategory update(String token,ProductCategory updatedCategory) throws NoPermissionException {
        String role= this.jwtToken.decryptToken(token, EncryptedData.ROLE);
        String company= this.jwtToken.decryptToken(token, EncryptedData.COMPANY);
        ProductCategory categoryFromDb = this.productCategoryRepository.findById(updatedCategory.getId()).orElse(null);
        if (categoryFromDb == null) {
            throw new NoSuchElementException("category is not found");
        }
        String companyOfCategory = categoryFromDb.getCompanyId().getId();
        Roles wholeRole = rolesRepository.findById(role).orElse(null);
        if( !wholeRole.getName().equals(RoleNames.ADMIN)|| !company.equals(companyOfCategory))
            throw new NoPermissionException("You do not have permission to update product category");
        updatedCategory.setAuditData(new AuditData(categoryFromDb.getAuditData().getCreateDate(), LocalDate.now()));
        return this.productCategoryRepository.save(updatedCategory);
    }
    //This function has to be deleted, Just for trying the token
    public String fill() {
        AuditData d = new AuditData(LocalDate.now(),LocalDate.now());
        Company c = new Company("7", "AAAAAAAAA", "55", d);
        companyRepository.save(c);
        Roles roles = new Roles("3", RoleNames.ADMIN, "cust", d);
        rolesRepository.save(roles);
        Address a = new Address("0580000000", "mezada 7", "aaa");
        Users user = new Users("8", "Q", "q", a, roles, c, d);
        userRepository.save(user);
        ProductCategory productCategory = new ProductCategory("6", "bc", "yu", c, d);
        productCategoryRepository.save(productCategory);
        ProductCategory productCategory1 = new ProductCategory("7", "as", "vg", c, d);
        productCategoryRepository.save(productCategory1);
        return jwtToken.generateToken(user);
    }
}
