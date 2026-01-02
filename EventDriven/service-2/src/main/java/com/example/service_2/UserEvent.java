package com.example.service_2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEvent {
    // "c" = create, "u" = update, "d" = delete
    private String op;

    private String id;
    private String email;

    @JsonProperty("full_name")
    private String fullName;
}