package com.flowreserve.demo1.controller.BasicSecure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/basic-secure")
public class BasicSecureController {

    @GetMapping("/test")
    public String testEndpoint() {
        return "✅ Acceso exitoso usando Basic Auth";
    }
}