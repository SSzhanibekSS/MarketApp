package com.market.MarketApp.controllers;

import com.market.MarketApp.models.Person;
import com.market.MarketApp.services.PersonService;
import com.market.MarketApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {


    private final PersonService service;
    private final PersonValidator validator;
    @Autowired
    public AuthenticationController(PersonService service, PersonValidator validator) {
        this.service = service;
        this.validator = validator;
    }


    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/reg")
    public String registration(@ModelAttribute("person")Person person){
        return "auth/reg";
    }

    @PostMapping("/reg")
    public String processRegistration(@ModelAttribute("person") @Valid Person person,
                      BindingResult result){

        validator.validate(person, result);
        if (result.hasErrors()){
            return "auth/reg";
        }
        service.register(person);
        return "redirect:/auth/login";
    }
}
