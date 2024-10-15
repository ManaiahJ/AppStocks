package com.manusoft.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

@SecuritySchemes({
        @SecurityScheme(
                name = "ClientTocken",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "X-Client-Key"
        ),
        @SecurityScheme(
                name = "AuthorizationTocken",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "Authorization"
        )
})
//@Configuration
public class OpenConfigApi {
    @Value("${swagger.host:manusofttechnologies.com}")
    private String host;

    /**
     * Creates and returns a GroupedOpenApi object for the public API.
     * This groups packages "com.manusoft.controller" and "com.manusoft.admin.controller".
     *
     * @return GroupedOpenApi object for the public API
     */
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.manusoft.controller", "com.manusoft.admin.controller")
                .build();
    }

    /**
     * This method creates a custom OpenAPI object with specified contact information, security requirements, and servers.
     *
     * @return custom OpenAPI object
     */

    public OpenAPI customeOpenApi() {
        Contact contact = new Contact();
        contact.email("support@mastockalerts.in");
        contact.url("https://manusoft.com");
        contact.setName("Manusoft alerts");

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("AuthorizationTocken");
        securityRequirement.addList("ClientTocken");

        Server localServer = new Server().url("http://localhost:8080/stocks").description("Local sever REST API");
        Server localServer1 = new Server().url("http://localhost:8888").description("Local sever REST API");
        Server demoServer = new Server().url(host).description("Demo server REST API");
        List<Server> listServers = Arrays.asList(localServer1, localServer, demoServer);


        return new OpenAPI().
                info(new Info().title("MAStock alerts REST API")
                        .version("1.0.0")
                        .termsOfService("Terms of services")
                        .contact(contact)
                        .license(new License().name("License of API").url("API license URL"))
                        .description("APIs for SSStock Alerts"))
                .addSecurityItem(securityRequirement)
                .servers(listServers);

    }
}
