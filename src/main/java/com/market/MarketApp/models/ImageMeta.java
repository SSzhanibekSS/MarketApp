package com.market.MarketApp.models;

import javax.persistence.*;
import javax.websocket.OnOpen;

@Entity
@Table(name = "meta_image")
public class ImageMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_format")
    private String fileFormat;
    @Column(name = "container_name")
    private String containerName;
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id" )
    private Person owner;

    public ImageMeta(){}
    public ImageMeta(String fileName, String fileFormat, String containerName) {
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.containerName = containerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
