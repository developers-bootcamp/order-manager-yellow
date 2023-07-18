//package com.yellow.ordermanageryellow.service;
//
//
//import com.yellow.ordermanageryellow.dao.GetCurrencyRepository;
//import com.yellow.ordermanageryellow.model.Currency;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//
//public class GetCurrencyService {
//    private GetCurrencyRepository getCurrencyRepository;
//    @Autowired
//    public GetCurrencyService(GetCurrencyRepository getCurrencyRepository) {
//        this.getCurrencyRepository = getCurrencyRepository;
//    }
//    public List<Currency> GetCurrency (){
//        List<Currency> Currencies = new ArrayList<>();
//        for (Currency value: Currency.values() ) {
//            Currencies.add(value);
//        }
//        return Currencies;
//    }
//}
