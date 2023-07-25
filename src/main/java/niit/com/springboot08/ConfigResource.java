package niit.com.springboot08;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigResource implements WebMvcConfigurer {
    @Value("${UPLOAD_DIR}")
    public String UPLOAD_DIR;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("uploads/**").addResourceLocations("file:" + UPLOAD_DIR);
    }
}
