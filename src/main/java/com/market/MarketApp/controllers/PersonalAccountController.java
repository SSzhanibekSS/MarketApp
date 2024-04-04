package com.market.MarketApp.controllers;

import com.market.MarketApp.models.Person;
import com.market.MarketApp.security.PersonDetails;
import com.market.MarketApp.services.ImageService;
import com.market.MarketApp.services.PersonService;
import com.market.MarketApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    private final PersonService personService;
    private final ImageService imageService;
    private final ProductService productService;
    @Autowired
    public PersonalAccountController(PersonService personService, ImageService imageService, ProductService productService) {
        this.personService = personService;
        this.imageService = imageService;
        this.productService = productService;
    }
    @GetMapping()
    public String cont(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        Person person = userDetails.getPerson();
        if(person.getRole().equals("ROLE_SELLER"))
            return "redirect:/account/seller";

        return "redirect:/account/buyer";
    }

    @GetMapping("/seller")
    public String sellerAccount(Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        Person person = userDetails.getPerson();

        String base64String = imageService.getImgageByPerson(person);
        model.addAttribute("person", person);
        model.addAttribute("base64String",base64String );
        model.addAttribute("products",productService.getAllPruductsForPerson(person));
        return "account/main_sell";
    }
    @GetMapping("/buyer")
    public String buyerAccount(@ModelAttribute("person")Person person, Model model) throws IOException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        person = userDetails.getPerson();

        String base64String = imageService.getImgageByPerson(person);

        model.addAttribute("person_real", person);
        model.addAttribute("base64String", base64String);
        return "account/main_buy";
    }

    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        Person person = userDetails.getPerson();

        imageService.uploadFile(file,person);
        return "redirect:/account/";
    }



}
