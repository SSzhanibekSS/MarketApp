package com.market.MarketApp.controllers;

import com.azure.core.annotation.Get;
import com.market.MarketApp.models.Person;
import com.market.MarketApp.models.Product;
import com.market.MarketApp.security.PersonDetails;
import com.market.MarketApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final ProductService service;
    @Autowired
    public HomeController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    public String SearchMethod(@RequestParam(value = "search",required = false,defaultValue = "all") String search,
                               @RequestParam(value = "category", required = false, defaultValue = "all") String category,
                               @RequestParam(value = "sort", required = false, defaultValue = "def") String sort,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                               Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();

        Person person = userDetails.getPerson();

        model.addAttribute("person", person);
        model.addAttribute("page", page);
        if (!sort.equals("def")){
            List<Product> list = service.getCostom(search, category,sort);
            model.addAttribute("products",list);


            return "home";
        }
        List<Product> list = service.getAll(page);

        model.addAttribute("products", list);
        return "home";


    }

    @GetMapping("/more/{id}")
    public String moreInfo(@PathVariable("id") int id,
                           Model model) throws IOException {
        Product product = service.getProduct(id);
        model.addAttribute("shop", product.getCreator().getUsername());
        model.addAttribute("product", product);
        return "product/more_info_and_buy";
    }

}
