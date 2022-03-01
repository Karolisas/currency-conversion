package lt.karolis.microservices28.currencyconversionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConvertionController {

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to,
                                              @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);
        CurrencyConversion body = responseEntity.getBody();
//        return new CurrencyConversion(1L, from, to, quantity, BigDecimal.ONE, BigDecimal.ONE, "");
        return new CurrencyConversion(body.getId(), body.getFrom(), body.getTo(), body.getConversionMultiple(), quantity, quantity.multiply(body.getConversionMultiple()), body.getEnvironment() + " restTemplate");
    }

    @Autowired
    CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrencyfeign(@PathVariable String from, @PathVariable String to,
                                                   @PathVariable BigDecimal quantity) {

        CurrencyConversion body = currencyExchangeProxy.convertCurrency(from, to);
//        return new CurrencyConversion(1L, from, to, quantity, BigDecimal.ONE, BigDecimal.ONE, "");
        return new CurrencyConversion(body.getId(), body.getFrom(), body.getTo(), body.getConversionMultiple(), quantity, quantity.multiply(body.getConversionMultiple()), body.getEnvironment() + " feign");
    }
}
