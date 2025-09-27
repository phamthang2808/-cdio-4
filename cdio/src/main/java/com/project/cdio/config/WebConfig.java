package com.project.cdio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Thư mục uploads cùng cấp target (projectRoot/uploads)
        Path uploadDir = Paths.get("uploads");
        String absolute = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolute + "/");

        // Nếu FE có gọi kiểu http://host:8088/api/uploads/...
        registry.addResourceHandler("/api/uploads/**")
                .addResourceLocations("file:" + absolute + "/");
    }
}

