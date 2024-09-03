package com.camel.integrated.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class MapToInputStreamProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<?, ?> map = exchange.getIn().getBody(LinkedHashMap.class);
        if (map != null) {
            String json = convertMapToJson(map);
            InputStream inputStream = new ByteArrayInputStream(json.getBytes());
            exchange.getIn().setBody(inputStream);
        }
    }

    private String convertMapToJson(LinkedHashMap<?, ?> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }
}
