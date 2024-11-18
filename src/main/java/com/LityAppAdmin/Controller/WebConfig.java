package com.LityAppAdmin.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configura el directorio donde se almacenan las imágenes
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); // Asegúrate de que la carpeta 'uploads' esté en el directorio raíz del proyecto
    }
}