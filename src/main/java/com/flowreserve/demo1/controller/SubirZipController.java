package com.flowreserve.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubirZipController {

    @GetMapping("/upload")
    public String mostrarFormularioUpload() {
        return "upload"; // Carga el archivo upload.html desde src/main/resources/templates
    }
}
