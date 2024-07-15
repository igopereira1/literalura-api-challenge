package com.literalura.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> className) throws JsonProcessingException {
        try {
            return objectMapper.readValue(json, className);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingException("Error converting data") {
            };
        }
    }
}
