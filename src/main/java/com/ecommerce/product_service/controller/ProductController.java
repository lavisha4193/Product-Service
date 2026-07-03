package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.request.UpdateProductRequest;
import com.ecommerce.product_service.dto.request.UpdateStockRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Add Product
     */
    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.addProduct(request));
    }

    /**
     * Get Product By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long id) {

        return ResponseEntity.ok(productService.getProduct(id));
    }

    /**
     * Get All Products
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {

        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Update Product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request));
    }

    /**
     * Delete Product (Soft Delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.deleteProduct(id));
    }

    /**
     * Get Products By Category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable Category category) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(category));
    }

    /**
     * Search Products
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                productService.searchProducts(keyword));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> reduceStock(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStockRequest request) {

        productService.reduceStock(
                id,
                request.getQuantity());

        return ResponseEntity.ok().build();

    }

}