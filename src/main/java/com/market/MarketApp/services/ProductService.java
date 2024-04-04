package com.market.MarketApp.services;

import com.market.MarketApp.models.Person;
import com.market.MarketApp.models.Product;
import com.market.MarketApp.reposicories.PersonRepository;
import com.market.MarketApp.reposicories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository repository;
    private final PersonRepository personRepository;
    private final ImageService imageService;

    @Autowired
    public ProductService(ProductRepository repository, PersonRepository personRepository, ImageService imageService) {
        this.repository = repository;
        this.personRepository = personRepository;
        this.imageService = imageService;
    }
    public List<Product> getAll(int page) throws IOException {
        List<Product> list = repository.findAll(PageRequest.of(page, 4)).getContent();
        for (Product prod: list){
            prod.setImage(imageService.getImageByProduct(prod));

        }
        return list;
    }
    @Transactional
    public void saveProduct(Product product,int id){
        Person person = personRepository.getById(id);
        person.setProducts(product);
        product.setCreator(person);
        repository.save(product);
    }
    @Transactional
    public void updateProduct(int id, Product product){
        product.setProductId(id);
        repository.save(product);
    }
    public Product getProduct(int id) throws IOException {
        Product product = repository.findById(id).get();
        product.setImage(imageService.getImageByProduct(product));
        return product;

    }


    public List<Product> getAllPruductsForPerson(Person person) throws IOException {
        List<Product> list = repository.findByCreator(person);
        for(Product pro:list){
            pro.setImage(imageService.getImageByProduct(pro));
        }

        return list;
    }


    public List<Product> getCostom(String search, String category, String sort) throws IOException {
        List<Product> list;
        if(search.equals("all") && !category.equals("all")){
            list = repository.findAllByProductTypeAndSort(category,sort);
        }else if (!search.equals("all") && !category.equals("all")){
            list = repository.findAllByProductNameAndProductTypeAndSort(category, search, sort);
        }else if(!search.equals("all") && category.equals("all")){
            list = repository.findAllByProductNameAndSort(search, sort);
        }
        else {
            list = repository.findAll();
        }

        for(Product pro:list){
            pro.setImage(imageService.getImageByProduct(pro));
        }
        return list;
}

}
