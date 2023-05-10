package com.example.singularitymanagement.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenDTO {
    private String token;
    private Long userID;

    @JsonCreator
    public TokenDTO(@JsonProperty("token") String token, @JsonProperty("userID") Long userID) {
        this.userID = userID;
        this.token = token;
    }
}
