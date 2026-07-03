package com.ecommerce.product_service.service.interfaces;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.request.UpdateProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Category;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest request);

    ProductResponse getProduct(Long id);

    List<ProductResponse> getProducts();

    ProductResponse updateProduct(Long id, UpdateProductRequest request);

    ProductResponse deleteProduct(Long id);

    List<ProductResponse> getProductsByCategory(Category category);

    List<ProductResponse> searchProducts(String keyword);

    void reduceStock(Long productId, Integer quantity);

}