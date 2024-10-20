package com.prospecta.problem1.Service;

import com.prospecta.problem1.Model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Value("${fakestore.api.baseurl}")
    private String fakeStoreApiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Product> getProductsByCategory(String category) {

        String url = fakeStoreApiBaseUrl + "/products/category/" + category;

        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);

        return Arrays.asList(response.getBody());
    }

    public Product addProduct(Product product) {
        String url = fakeStoreApiBaseUrl + "/products";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        ResponseEntity<Product> response = restTemplate.exchange(url, HttpMethod.POST, request, Product.class);
        return response.getBody();
    }

}
