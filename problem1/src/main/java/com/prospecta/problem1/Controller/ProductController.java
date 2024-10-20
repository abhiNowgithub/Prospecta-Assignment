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
