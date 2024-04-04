package com.market.MarketApp.controllers;

import com.market.MarketApp.models.Product;
import com.market.MarketApp.security.PersonDetails;
import com.market.MarketApp.services.ImageService;
import com.market.MarketApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;
    private final ImageService imageService;
    @Autowired
    public ProductController(ProductService service, ImageService imageService) {
        this.service = service;
        this.imageService = imageService;
    }

    @GetMapping("/add")
    public String addProduct(@ModelAttribute("product")Product product){
        return "product/add_product";
    }


    @PostMapping("/add")
    public String addProductPost(@ModelAttribute("product")@Valid Product product,
                                 BindingResult result){

        if(result.hasErrors()){
            return "product/add_product";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        service.saveProduct(product,userDetails.getPerson().getId() );
        return "redirect:/account";

    }


    @PatchMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id,
                              @ModelAttribute("product")@Valid Product product,
                              BindingResult result){
        if (result.hasErrors()){
            return "product/edit_product";
        }
        System.out.println("something");
        System.out.println(id);
        service.updateProduct(id,product);
        return "redirect:/account";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model,
                              @PathVariable("id")int id) throws IOException {
        Product product = service.getProduct(id);
        model.addAttribute("person", product.getCreator());
        model.addAttribute("product_id", id);
        model.addAttribute("product",product);
        return "product/edit_product";
    }
    @PostMapping("/uploadPhoto/{id}")
    public String uploadProductPhoto(@PathVariable("id") int id,
                                     @RequestParam("file")MultipartFile file) throws IOException {

        imageService.uploadProductFile(id,file);
        return "redirect:/product/edit/" + id;
    }

    @GetMapping("/buy/{id}")
    public String byu(@PathVariable("id") int id,
                      Model model) throws IOException {
        Product product = service.getProduct(id);
        product.setProductCount(product.getProductCount() - 1);
        service.updateProduct(id,product );
        model.addAttribute("shop", product.getCreator().getUsername());
        model.addAttribute("product", product);
        return "redirect:/home/more/"+id;
    }






}
