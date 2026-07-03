package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.Category;
import com.ecommerce.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find product by SKU.
     */
    Optional<Product> findBySku(String sku);

    /**
     * Check if SKU already exists.
     */
    boolean existsBySku(String sku);

    /**
     * Find products by category.
     */
    List<Product> findByCategory(Category category);

    /**
     * Search products by name (case-insensitive).
     */
    List<Product> findByNameContainingIgnoreCase(String keyword);

    /**
     * Get only active products.
     */
    List<Product> findByActiveTrue();

    /**
     * Get active products by category.
     */
    List<Product> findByCategoryAndActiveTrue(Category category);

    /**
     * Search only active products.
     */
    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String keyword);

    /**
     * Find all products belonging to the specified brand.
     */
    List<Product> findByBrand(String brand);

    /**
     * Find products whose price falls within the specified range.
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find products with stock quantity below the specified threshold.
     * Useful for inventory monitoring and low-stock alerts.
     */
    List<Product> findByStockQuantityLessThan(Integer quantity);

    /**
     * Retrieve all inactive (soft-deleted) products.
     * Intended for administrative purposes.
     */
    List<Product> findByActiveFalse();

    /**
     * Retrieve the 10 most recently added products.
     * Results are ordered by creation timestamp in descending order.
     */
    List<Product> findTop10ByOrderByCreatedAtDesc();
}