package com.homich.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String sayHello(Principal principal) {
        return "Hello " + principal.getName();
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
