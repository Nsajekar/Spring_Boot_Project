package com.spring.main.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig { 
  
   public OpenAPI SwaggerApi() { 
	   return new OpenAPI()
               .info(new Info()
                       .title("Custom Swagger API")
                       .description("This is a custom Swagger configuration example")
                       .version("1.0.0")
                       .contact(new Contact()
                               .name("Your Name")
                               .email("your-email@example.com")
                               .url("https://example.com"))
                       .license(new License()
                               .name("Apache 2.0")
                               .url("https://www.apache.org/licenses/LICENSE-2.0")));
   }

}