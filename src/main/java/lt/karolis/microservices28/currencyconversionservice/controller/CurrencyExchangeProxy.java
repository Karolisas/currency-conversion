package lt.karolis.microservices28.currencyconversionservice.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "${url.bla}") // application name tipically
@FeignClient(name = "currency-exchange") // to enable load balancing - remove url, leave just name
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to);
}
