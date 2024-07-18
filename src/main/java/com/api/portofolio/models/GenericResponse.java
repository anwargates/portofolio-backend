package com.api.portofolio.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private int statusCode;
    private String message;
    private Object data;
}
