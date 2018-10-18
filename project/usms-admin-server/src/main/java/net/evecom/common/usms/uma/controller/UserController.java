package net.evecom.common.usms.uma.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${test.name}")
    private String name;

    @RequestMapping(value = "/findOne")
    public String findOne(Long id) {
        return "service-uma-183 success ";
    }
}
