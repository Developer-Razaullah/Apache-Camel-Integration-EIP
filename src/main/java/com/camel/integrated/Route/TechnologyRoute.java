package com.camel.integrated.Route;

import com.camel.integrated.config.UrlConfig;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TechnologyRoute extends RouteBuilder {

    @Autowired
    private UrlConfig config;

    @Override
    public void configure() throws Exception {
        configurePath();
        configureStudent();
    }

    private void configurePath() {
        rest("api")
                .get("/technology")
                .to("direct:technology");
    }

    private void configureStudent() {
        from("direct:technology")
                .routeId("student")
                .log("Request: ${body}")
                .toD(config.getTechnologyEndpoint()+"?bridgeEndpoint=true")
                .log("Response: ${body}")
                .onException(Exception.class)
                .log("Error: ${exception.message}")
                .handled(true);
    }
}
