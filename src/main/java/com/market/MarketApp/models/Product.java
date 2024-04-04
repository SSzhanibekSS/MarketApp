package com.market.MarketApp.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @NotEmpty
    @Column(name = "product_name")
    private String productName;
    @Min(value = 1, message = "Product cost should be more then 0")
    @Column(name = "product_cost")
    private int productCost;
    @Min(value = 1, message = "Product count should be more then 0")
    @Column(name = "product_count")
    private int productCount;
    @Column(name = "product_type")
    private String productType;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Person creator;
    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE)
    private ProductImage productImage;

    @Transient
    private String image;

    public Product(){

    }

    public Product(String productName, int productCost, int productCount, String product_type) {
        this.productName = productName;
        this.productCost = productCost;
        this.productCount = productCount;
        this.productType = product_type;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCost() {
        return productCost;
    }

    public void setProductCost(int productCost) {
        this.productCost = productCost;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String product_type) {
        this.productType = product_type;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

}
