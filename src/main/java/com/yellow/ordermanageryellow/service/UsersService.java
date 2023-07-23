package com.yellow.ordermanageryellow.service;
import com.yellow.ordermanageryellow.dao.CompanyRepository;
import com.yellow.ordermanageryellow.dao.RolesRepository;
import com.yellow.ordermanageryellow.dao.UserRepository;
import com.yellow.ordermanageryellow.exceptions.NotValidStatusExeption;
import com.yellow.ordermanageryellow.exceptions.ObjectAlreadyExistException;
import com.yellow.ordermanageryellow.model.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsersService  {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RolesRepository rolesRepository;
    @Autowired
    private  CompanyRepository companyRepository;


    public  ResponseEntity<String> login(String email,String password){
      try {
          Users user = userRepository.findUserByEmail(email);
          if (user == null) {
              return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
          }
              if (user.getPassword().equals(password))
                  return new ResponseEntity<>(generateToken(user), HttpStatus.OK);
              else
                  return new ResponseEntity<>("wrong password", HttpStatus.UNAUTHORIZED);
      }
    catch (Exception e) {
        return new ResponseEntity<>("unexpected error ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    public String generateToken(Users user){
        return user.getCompanyId() + user.getId()+ user.getRoleId();
    }
    @SneakyThrows
    public Users signUp(String fullName,String companyName,String email,String password){

            Users user=new Users();
            user.setFullName(fullName);
            if(password.equals("")){
                throw new NotValidStatusExeption("password not  valid");
            }
            user.setPassword(password);
            if(!email.contains("@")){
                throw new NotValidStatusExeption("email not valid");
            }
            if (userRepository.existsByAddressEmail(email)) {
                throw new ObjectAlreadyExistException("this user allready exists");
            }
            Address address = new Address();
            user.setAddress(address);
            user.getAddress().setEmail(email);
            user.setRoleId(rolesRepository.getByName(RoleName.ADMIN));
            AuditData auditData = new AuditData();
            auditData.setCreateDate( LocalDate.now());
            auditData.setUpdateDate(LocalDate.now());
            user.setAuditData(auditData);
            if (companyRepository.existsByName(companyName)){
                throw new ObjectAlreadyExistException("company already exists");
            }
            Company company=new Company();
            company.setName(companyName);
            companyRepository.save(company);
            AuditData auditData1=new AuditData();
            auditData1.setCreateDate(LocalDate.now());
            auditData1.setUpdateDate(LocalDate.now());
            company.setAuditData(auditData1);
            user.setCompanyId(company);
            userRepository.save(user);
            return user;
    }
}