package com.camel.integrated.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class Header {
    private String header;
}
