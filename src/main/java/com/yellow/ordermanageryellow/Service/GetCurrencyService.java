package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.Dao.CompanyRepository;
import com.yellow.ordermanageryellow.Dao.GetCurrencyRepository;
import com.yellow.ordermanageryellow.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class GetCurrencyService  {
    private final GetCurrencyRepository getCurrencyRepository;
    @Autowired
    public GetCurrencyService(GetCurrencyRepository getCurrencyRepository) {
        this.getCurrencyRepository = getCurrencyRepository;
    }
    public List<Currency> GetCurrency (){
        List<Currency> Currencies = new ArrayList<>();
        for (Currency value: Currency.values() ) {
            Currencies.add(value);
        }
        return Currencies;
    }
}
