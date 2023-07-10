package com.yellow.ordermanageryellow.Dao;

import com.yellow.ordermanageryellow.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    @Query("{'address.email' : ?0}")
    Users findUserByEmail(String email);

    @Query("{'fullName': {$regex: ?0, $options: 'i'}, 'companyId': ?1, 'roleId': ?2}")
    List<Users> findByFullNameContainingAndCompanyIdAndRoleId(String prefix, String companyId, String roleId);

    Page<Users> findAllByCompanyIdAndRoleId(String companyId, String roleId, Pageable pageable);

}