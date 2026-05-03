package com.interview.assistant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI aiInterviewAssistantOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setEmail("contact@interview-assistant.com");
        contact.setName("AI Interview Assistant Team");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("AI Interview Assistant API")
                .version("2.0.0")
                .contact(contact)
                .description("Real-time AI-powered interview support system API")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
