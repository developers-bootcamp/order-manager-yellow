package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yellow.ordermanageryellow.Service.TopProductService;

import java.util.Map;

@RestController
@RequestMapping("/graph")
public class TopProductController {
    private final TopProductService topProductService;

    @Autowired
    public TopProductController(TopProductService topProductService){
        this.topProductService=topProductService;
    }
    @GetMapping("/topProductService")
    public ResponseEntity<Map<String, Map<String, Integer>>> topProduct(){
        return topProductService.TopSoldProduct();
    }
}
