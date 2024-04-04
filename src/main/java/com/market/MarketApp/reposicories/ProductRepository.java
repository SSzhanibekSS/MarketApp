package com.market.MarketApp.reposicories;

import com.market.MarketApp.models.Person;
import com.market.MarketApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCreator(Person person);
    @Query(value = "select * from product where product_type = :type order by CASE WHEN :sort = 'product_cost' THEN product_cost END ASC, CASE WHEN :sort = 'product_name' THEN product_name END ASC;", nativeQuery = true)
    List<Product> findAllByProductTypeAndSort(String type, String sort);
    @Query(value = "SELECT * FROM product WHERE product_name LIKE %:product_nam% order by CASE WHEN :sort = 'product_cost' THEN product_cost END ASC, CASE WHEN :sort = 'product_name' THEN product_name END ASC;", nativeQuery = true)
    List<Product> findAllByProductNameAndSort(@Param("product_nam") String product_nam, @Param("sort") String sort);
    @Query(value = "SELECT * FROM product WHERE product_name LIKE %:product_nam% AND product_type = :type order by CASE WHEN :sort = 'product_cost' THEN product_cost END ASC, CASE WHEN :sort = 'product_name' THEN product_name END ASC;", nativeQuery = true)
    List<Product> findAllByProductNameAndProductTypeAndSort(@Param("type") String type,@Param("product_nam") String product_nam, @Param("sort") String sort);

}
