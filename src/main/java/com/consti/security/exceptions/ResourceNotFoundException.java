package com.consti.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;

    private String fieldName;

    private String exist;

    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String exist, Object fieldValue) {
        super(String.format("El %s con: %s='%s' %s esta registrado en la base de datos",resourceName,fieldName,exist,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.exist = exist;
        this.fieldValue = fieldValue;
    }
}
