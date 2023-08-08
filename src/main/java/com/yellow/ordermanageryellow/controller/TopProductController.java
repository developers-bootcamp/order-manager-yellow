package com.yellow.ordermanageryellow.controller;


import com.yellow.ordermanageryellow.Dto.TopProductDTO;
import com.yellow.ordermanageryellow.Exception.NoDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import com.yellow.ordermanageryellow.service.TopProductService;
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/graph")
public class TopProductController {
    private final TopProductService topProductService;

    @Autowired
    public TopProductController(TopProductService topProductService) {
        this.topProductService = topProductService;
    }

    @GetMapping("/topProduct")
    public ResponseEntity topProduct() {
        try {
            List<TopProductDTO> topProducts = topProductService.topSoldProduct();
            return ResponseEntity.status(HttpStatus.OK).body(topProducts);
        } catch (NoDataException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

