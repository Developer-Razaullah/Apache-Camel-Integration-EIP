package com.camel.integrated.config;

import org.apache.camel.Configuration;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

@Configuration
public class CamelRestConfig extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);
    }
}
