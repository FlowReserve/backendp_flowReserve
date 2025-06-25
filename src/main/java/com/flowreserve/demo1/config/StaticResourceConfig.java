package com.flowreserve.demo1.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer{
    @Value("${file.produccion-path}")
    private String produccionPath;

    @Value("${file.root-path}")
    private String rootPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ruta producción
        String produccionFilePath = "file:///" + produccionPath.replace("\\", "/");
        if (!produccionFilePath.endsWith("/")) {
            produccionFilePath += "/";
        }
        registry
                .addResourceHandler("/archivos-prod/**")
                .addResourceLocations(produccionFilePath)
                .setCachePeriod(0);

        // Ruta desarrollo (rootPath)
        String rootFilePath = "file:///" + rootPath.replace("\\", "/");
        if (!rootFilePath.endsWith("/")) {
            rootFilePath += "/";
        }
        registry
                .addResourceHandler("/archivos-dev/**")
                .addResourceLocations(rootFilePath)
                .setCachePeriod(0);
    }


}
