package com.tanx.journal.Services;

import com.tanx.journal.cache.AppCache;
import com.tanx.journal.pojo.QuoteRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Configuration
public class QuotesApiService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${quotes.api.key}")
    private String API_KEY;
    @Autowired
    private AppCache appCache;



    public List<QuoteRes> getQuote(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<QuoteRes[]> res = restTemplate.exchange(appCache.getAppCache().get(AppCache.KEYS.QUOTES_API_URL.toString()), HttpMethod.GET, entity, QuoteRes[].class);
        return Objects.requireNonNull(
                List.of(res.getBody()),
                "Quote API returned null body"
        );

    }
}
