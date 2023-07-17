package com.yellow.ordermanageryellow.controller;
import com.yellow.ordermanageryellow.Service.GetCurrencyService;
import com.yellow.ordermanageryellow.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")

@RequestMapping("/GetCurrency")
public class GetCurrencyController {
    private final GetCurrencyService getCurrencyService;

    @Autowired
    public GetCurrencyController(GetCurrencyService getCurrencyService){
        this.getCurrencyService=getCurrencyService;
    }

       @GetMapping("/")


    public List<Currency> GetCurrency(){
        return getCurrencyService.GetCurrency();
    }


}
