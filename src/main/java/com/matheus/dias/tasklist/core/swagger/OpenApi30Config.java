package com.matheus.dias.tasklist.core.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Tasklist", version = "v1", description = "Created by Matheus Duarte Dias. See sources on GitHub "))
public class OpenApi30Config {

}