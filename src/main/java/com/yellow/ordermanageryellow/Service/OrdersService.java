package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrdersService implements CommandLineRunner {
    private final OrdersRepository OrdersRepository;
    @Autowired
    public OrdersService(OrdersRepository OrdersRepository) {
        this.OrdersRepository = OrdersRepository;
    }
    @Override
    public void run(String... args) {
//        Orders Orders = new Orders("12",);
//      OrdersRepository.save(Orders);
          }

//    public void fill() {
//        AuditData d = new AuditData(LocalDate.now());
//        AuditData d1 = new AuditData(LocalDate.now());
//        AuditData d2 = new AuditData(LocalDate.of(2023,6,3), LocalDate.now());
//        AuditData d3 = new AuditData(LocalDate.of(2023,5,1),LocalDate.now());
//        List<Orders> orders = new ArrayList<Orders>();
//        Company company1=new Company("11", "PotoNeveYaakov", "88", d3);
//        Company company2=new Company("12", "PotoGeula", "88", d2);
//        Company company3=new Company("13", "Grafgik", "88", d2);
//        Roles role1=new Roles("101", name.Admin,"bos",d3);
//        Roles role2=new Roles("102",name.employee,"GOOD EMPLOYEE",d2);
//        Roles role3=new Roles("103",name.customer,"CUSTOMER",d1);
//        Users user1=new Users();
//        Users user2=new Users("1002","Yoram","1002",new Address(),role2,company1,d2);
//        Users user6=new Users("1006","Mendi","1006",new Address(),role2,company1,d2);
//        Users user7=new Users("1007","Morya","1007",new Address(),role2,company1,d2);
//        Users user3=new Users("1003","family Simoni","1003",new Address(),role3,company1,d1);
//        Users user4=new Users("1004","family Markoviz","1004",new Address(),role3,company1,d1);
//        Users user5=new Users("1005","family Chayimoviz","1005",new Address(),role3,company1,d1);
//        Product p=new Product("111");
//        Order_Items oi=new Order_Items(p,3.0,4.0);
//        List<Order_Items> Order_Items_list = new ArrayList<Order_Items>();
//        Order_Items_list.add(oi);
       // Order_Items_list.add(oi);
      //  Order_Items_list.add(oi);
        // for (int i = 200; i < 500; i++) {
          //  if (i % 3 == 0)
               // orders.add(new Orders(Integer.toString(i),user2,user3, i * 2,null,"1", company1, 143,LocalDate.now(),"2",true,d1));
//            else if (i % 3 == 1)
//                orders.add(new Orders(Integer.toString(i),user6,user4, i * 2,null,"1", company1, 263,LocalDate.now(),"1",true,d2));
//            else
           //orders.add(new Orders(Integer.toString(i),user7,user5, i * 2,null,"1", company1, 324,LocalDate.now(),"3",true,d1));
       // }
        //   System.out.println("eerr");
     
//       Orders Orders2=  new Orders("98765",null,null, 1 * 2,null,"1", null, 324,LocalDate.now(),"3",true,null);
       // System.out.println(Orders2.getClass().getDeclaredFields());

//        for (Field field : Orders2.getClass().getDeclaredFields()) {
//            // Print the value of the field
//            System.out.println(field.getName() + ": " + field.get(Orders2));
//        }
//
//        for (Field field : Orders2.getClass().getDeclaredFields()) {
//            // Print the value of the field
//            System.out.println(field.getName() + ": " + field.get(Orders2));
//        }
       // Orders Orders1 = new Orders("1234567",user1,user3, 12 * 2, Order_Items_list,"1", company1, 143,LocalDate.now(),"2",true,d1);
//        OrdersRepository.save(Orders2);


        // System.out.println(Orders1);
     //  Orders Orders = new Orders("13",);
    //OrdersRepository.save(Orders);

      //  OrdersRepository.saveAll(orders);
  //  }



}