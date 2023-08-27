package com.yellow.ordermanageryellow.service;

import com.mongodb.client.MongoCollection;
import com.yellow.ordermanageryellow.Dto.OrderDTO;
import com.yellow.ordermanageryellow.Dto.OrderMapper;
import com.yellow.ordermanageryellow.Dto.ProductDTO;
import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.Dao.ProductRepository;

import com.yellow.ordermanageryellow.exceptions.NotValidStatusExeption;
import com.yellow.ordermanageryellow.model.Discount;
import com.yellow.ordermanageryellow.model.Order_Items;
import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.model.Orders.status;
import com.yellow.ordermanageryellow.model.Product;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import com.yellow.ordermanageryellow.security.EncryptedData;
import com.yellow.ordermanageryellow.security.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChargingService chargingService=new ChargingService();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${pageSize}")
    private int pageSize;
    public Orders getOrderById(String id){
        return ordersRepository.findById(id).get();
    }

    public List<Orders> getOrders(String token,  boolean isCanceled, int pageNumber,  String sortBy,Map<String,Object>filters) {
        String companyId = jwtToken.decryptToken(token,EncryptedData.COMPANY);
        Pageable paging;
        if (sortBy != "") {
            Sort sort = Sort.by(sortBy).ascending();
            paging = PageRequest.of(pageNumber, pageSize, sort);
        } else {
            paging = PageRequest.of(pageNumber, pageSize);
        }
        Criteria criteria = Criteria.where("companyId.id").is(companyId);
        if(isCanceled){
            criteria.and("orderStatusId").is("cancelled");
        }
        else{
            criteria.and("orderStatusId").in("approved","charging","packing","New");
        }
        filters.forEach((key, val) -> {
            criteria.and(key).is(val);
        });
        Query query = new Query(criteria);
        query.with(paging);
        return mongoTemplate.find(query, Orders.class);
    }

       public String insert(Orders newOrder) {
            if (newOrder.getOrderStatusId() != status.New && newOrder.getOrderStatusId() != status.approved) {
                throw new NotValidStatusExeption("Order should be in status new or approve");
            }
            Orders order = ordersRepository.insert(newOrder);
         if(newOrder.getOrderStatusId() == status.approved)
            chargingService.chargingStep(order);
            return order.getId();
        }

    public boolean edit(Orders currencyOrder) {
        if (currencyOrder.getOrderStatusId() != status.cancelled && currencyOrder.getOrderStatusId() != status.approved) {
            throw new NotValidStatusExeption("You can only approve or cancel an order");
        }
        Optional<Orders> order = ordersRepository.findById(currencyOrder.getId());
        if (order.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (order.get().getOrderStatusId() != status.New || order.get().getOrderStatusId() != status.packing) {
            throw new NotValidStatusExeption("It is not possible to change an order that is not in status new or packaging");
        }
        if(order.get().getOrderStatusId() == status.approved)
            chargingService.chargingStep(order.get());
       ordersRepository.save(currencyOrder);
        return true;
    }
    public Map<String, HashMap<Double, Integer>> calculateOrderService( @RequestParam  Orders order) {
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();
        double total = 0;
        for (int i = 0; i < order.getOrderItems().stream().count(); i++) {
            Order_Items orderItem = order.getOrderItems().get(i);
            Optional<Product> p = productRepository.findById(orderItem.getProductId().getId());
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            double sum = 0;
            if (p.get().getDiscount() == Discount.FixedAmount) {

                sum = (p.get().getPrice()- p.get().getDiscountAmount()) * order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.get().getDiscountAmount());

            } else {
                sum = (p.get().getPrice() * p.get().getDiscountAmount()) / 100 * (100 - p.get().getDiscountAmount()) * order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.get().getDiscountAmount());
            }
            calculatedOrder.put(p.get().getId(), o);
            total += sum;
        }
        HashMap<Double, Integer> o = new HashMap<Double, Integer>();
        o.put(total, -1);
        calculatedOrder.put("-1", o);
        return calculatedOrder;
    }


}