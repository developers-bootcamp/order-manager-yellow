package com.yellow.ordermanageryellow.dao;
import com.yellow.ordermanageryellow.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

   @Query("{'adress.email' : ?0}")
   Users findUserByEmail(String email);
   @Query(value = "{'address.email': ?0}", exists = true)
   boolean existsByAddressEmail(String email);
   @Autowired
   User getByAddressEmail(String email);
//   boolean existsByAddressEmail(String email);
}