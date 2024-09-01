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
public class MessageRoute extends RouteBuilder {

    private final String ROUTE_MESSAGE = "direct:message";
    private final String ROUTE_LIST_MESSAGE = "direct:messages";
    private final String BRIDGE_ENDPOINT = "?bridgeEndpoint=true";
    private final String REQUEST_LOG = "${header.X-Request-Id}";
    private final String RESPONSE_LOG = "${body}";

    @Autowired
    private UrlConfig config;

    @Override
    public void configure() throws Exception {
        configurePath();
        configureListOfData();
        configureData();
    }

    private void configurePath() {
        rest("/api")
                .get("/list/message")
                .to(ROUTE_LIST_MESSAGE);
        rest("/api")
                .post("/add/message")
                .to(ROUTE_MESSAGE);
    }

    private void configureListOfData() {
        from(ROUTE_LIST_MESSAGE)
                .routeId("messages")
                .validate(header("X-Request-Id").isNotNull())
                /*.setProperty("X-Request-Id").simple(REQUEST_LOG)*/
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
                .log(REQUEST_LOG)
                .to(config.getBaseEndpoint()+config.getMessages()+ BRIDGE_ENDPOINT)
                .log("Response: "+RESPONSE_LOG)
                .onException(Exception.class)
                .log("Error: ${exception.message}")
                .handled(true);
    }

    private void configureData() {
        from(ROUTE_MESSAGE)
                .routeId("message")
                .validate(header("X-Request-Id").isNotNull())
                .validate(header("data").isNotNull())
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
                .log(REQUEST_LOG)
                .to(config.getBaseEndpoint()+config.getMessage()+ BRIDGE_ENDPOINT)
                .log("Response: "+RESPONSE_LOG)
                .onException(Exception.class)
                .log("Error: ${exception.message}")
                .handled(true);
    }
}
