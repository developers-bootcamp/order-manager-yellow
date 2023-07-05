package com.yellow.ordermanageryellow.Dao;
import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface OrdersRepository extends MongoRepository<Orders, String> {
    public Page<Orders> findByCompanyId_IdAndOrderStatusIdAndEmployee(String token,String userId, String status,Pageable pageNumber);

}
