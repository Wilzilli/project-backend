package com.raizesnordeste.project_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Raízes do Nordeste",
        version = "1.0",
        description = "API REST para gestão de pedidos e pagamentos mock multicanal.",
        contact = @Contact(name = "Raízes do Nordeste")
    ),
    servers = @Server(url = "http://localhost:8080", description = "Ambiente local")
)
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class OpenApiConfig {
}
