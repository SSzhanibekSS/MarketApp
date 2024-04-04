package com.market.MarketApp.reposicories;

import com.market.MarketApp.models.Product;
import com.market.MarketApp.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepository  extends JpaRepository<ProductImage, Integer> {

    Optional<ProductImage> findByProduct(Product product);
    void removeByProduct(Product product);
}
