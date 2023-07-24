package com.yellow.ordermanageryellow.Dao;

import com.yellow.ordermanageryellow.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
	  @Query("{'adress.email' : ?0}")
   Users findUserByEmail(String email);
   @Query(value = "{'address.email': ?0}", exists = true)
   boolean existsByAddressEmail(String email);

   User getByAddressEmail(String email);

    

    @Query("{'fullName': {$regex: ?0, $options: 'i'}, 'companyId': ?1, 'roleId': ?2}")
    List<Users> findByFullNameContainingAndCompanyIdAndRoleId(String prefix, String companyId, String roleId);

    Page<Users> findAllByCompanyIdAndRoleId(String companyId, String roleId, Pageable pageable);
}