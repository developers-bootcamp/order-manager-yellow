package com.yellow.ordermanageryellow.Dao;

import com.yellow.ordermanageryellow.model.AuditData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditDataRepository extends MongoRepository<AuditData,String> {
}
