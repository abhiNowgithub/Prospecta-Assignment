# Coding Challenges - Prospecta

This repository contains solutions for two coding challenges provided for the interview process with Prospecta. The challenges involve creating APIs for interacting with a fake store API and building a program to process CSV files with formulas.

## Problem 1: Product API

### Description
This problem involves creating a RESTful API using Java and Spring Boot to interact with the [Fake Store API](https://fakestoreapi.com/). The task includes two main endpoints:
1. **GET /api/products/category/{category}**: Retrieves a list of products based on the provided category.
2. **POST /api/products/add**: Adds a new product entry to the store.

### Solution Overview
The solution is structured into three main components:
- **Model**: Represents the `Product` object with properties like `id`, `title`, `price`, `description`, `category`, and `image`.
- **Service**: Contains the business logic for interacting with the Fake Store API.
- **Controller**: Exposes REST endpoints to retrieve products by category and add new products.

### Code Structure
- **Model (`Product.java`)**
  ```java
  package com.prospecta.problem1.Model;

  import lombok.Data;

  @Data
  public class Product {
      private Long id;
      private String title;
      private Double price;
      private String description;
      private String category;
      private String image;
  }

- **Service (`ProductService.java`)**
  ```java
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



- **Controller (`ProductController.java`)**
  ```java
  package com.prospecta.problem1.Controller;
  
  import com.prospecta.problem1.Model.Product;
  import com.prospecta.problem1.Service.ProductService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.*;
  
  import java.util.List;
  
  @RestController
  @RequestMapping("/api/products")
  public class ProductController {
      @Autowired
      private ProductService productService;
  
      @GetMapping("/category/{category}")
      public List<Product> getProductsByCategory(@PathVariable String category) {
          return productService.getProductsByCategory(category);
      }
  
      @PostMapping("/add")
      public Product addProduct(@RequestBody Product product) {
          return productService.addProduct(product);
      }
  }

## How to Run
- Clone the repository.
- Update the application.properties file with the fakestore.api.baseurl.
- Run the Spring Boot application.
- Access the API endpoints using:
- GET /api/products/category/{category} to get products by category.
- POST /api/products/add to add a new product.


## Problem 2: CSV Formula Evaluator

### Description
This problem involves creating a program in Java that reads a CSV file, processes any formulas within it, and writes the calculated values to an output CSV. The formulas can include basic arithmetic operations such as addition (+), subtraction (-), multiplication (*), and division (/).

### Solution Overview
##### The program:

- Reads the input CSV file.
- Processes each cell to determine if it contains a value or a formula.
- Evaluates formulas by referencing other cell values.
- Writes the computed results to an output CSV file.

### How to Run
- Provide the path to the input CSV file.
- Specify the output file path.
- Run the program, and it will generate the processed CSV with calculated formula values.

