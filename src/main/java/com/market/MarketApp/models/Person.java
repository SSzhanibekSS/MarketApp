package com.market.MarketApp.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 4, max = 20, message = "Username should be between 4 and 20 characters")
    private String username;
    @Column(name = "role")
    @NotEmpty
    private String role;
    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "year_of_birth")
    @Min(value = 2001, message = "Year of birth should be more than 2000")
    private int yearOfBirth;

    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "city")
    @NotEmpty
    private String city;

    @OneToOne(mappedBy = "owner",cascade = CascadeType.REMOVE)
    private ImageMeta imageMeta;
    @OneToMany(mappedBy = "creator")
    private List<Product> products;

    public Person(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yaerOfBirth) {
        this.yearOfBirth = yaerOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ImageMeta getImageMeta() {
        return imageMeta;
    }

    public void setImageMeta(ImageMeta imageMeta) {
        this.imageMeta = imageMeta;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", email='" + email + '\'' +
                '}';
    }
}
