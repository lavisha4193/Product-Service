package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.request.UpdateProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.exception.ProductNotFoundException;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse addProduct(ProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(Category.valueOf(request.getCategory()))
                .brand(request.getBrand())
                .sku(generateSku())
                .build();

        product = productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    public ProductResponse getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getProducts() {

        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long id,
                                         UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(Category.valueOf(request.getCategory()));
        product.setBrand(request.getBrand());

        product = productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    public ProductResponse deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        product.setActive(false);

        product = productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Category category) {

        return productRepository.findByCategoryAndActiveTrue(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {

        return productRepository
                .findByNameContainingIgnoreCaseAndActiveTrue(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void reduceStock(Long productId, Integer quantity) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found"));

        if (!product.isActive()) {
            throw new RuntimeException(
                    "Product is inactive.");
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException(
                    "Insufficient stock.");
        }

        product.setStockQuantity(
                product.getStockQuantity() - quantity);

        productRepository.save(product);

    }

    /**
     * Convert Product Entity to ProductResponse DTO.
     */
    private ProductResponse mapToResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .brand(product.getBrand())
                .active(product.isActive())
                .build();
    }

    /**
     * Generate Product SKU.
     * Example : PRD-1720000000000
     */
    private String generateSku() {
        return "PRD-" + System.currentTimeMillis();
    }

}