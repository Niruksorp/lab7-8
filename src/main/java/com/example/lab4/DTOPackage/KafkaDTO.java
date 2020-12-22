package com.example.lab4.DTOPackage;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class KafkaDTO{

    String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long id;

    public KafkaDTO(Long i, String name) {
        this.id = i;
        this.name = name;
    }
}
