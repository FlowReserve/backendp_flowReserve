package com.flowreserve.demo1.controller.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/method")
@PreAuthorize("denyAll()")
public class TestController {

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('CREATE')")
    public String hello(){
        return "create permissions";
    }
}
