package com.homich.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class UserController {

    @RequestMapping({"/api/me"})
    public Map<String, Object> user(Principal principal) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication authentication = (OAuth2Authentication) principal;
            map.put("authorities", authentication.getAuthorities());
        }
        return map;
    }

    @RequestMapping({"/"})
    public String hello(Principal principal) {
        return "Hello " + principal.getName();
    }
}
