package com.market.MarketApp.models;

import javax.persistence.*;

@Entity
@Table(name = "product_image_meta")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id")
    private int metaId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_format")
    private String fileFormat;
    @Column(name = "container_name")
    private String containerName;
    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;
    public ProductImage(){

    }

    public ProductImage(String fileName, String fileFormat, String containerName) {
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.containerName = containerName;
    }

    public int getMetaId() {
        return metaId;
    }

    public void setMetaId(int metaId) {
        this.metaId = metaId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
