package com.camel.integrated.Route;

import com.camel.integrated.config.UrlConfig;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TechnologyRoute extends RouteBuilder {

    private final String ROUTE_TECHNOLOGY = "direct:technology";

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
                .to(ROUTE_TECHNOLOGY);
    }

    private void configureStudent() {
        from(ROUTE_TECHNOLOGY)
                .routeId("student")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
                .toD(config.getBaseEndpoint()+config.getTechnology()+"?bridgeEndpoint=true")
                .log("Response: ${body}")
                .onException(Exception.class)
                .log("Error: ${exception.message}")
                .handled(true);
    }
}
