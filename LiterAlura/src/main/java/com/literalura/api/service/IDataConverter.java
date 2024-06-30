package com.literalura.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IDataConverter {
    <T> T getData(String json, Class<T> className) throws JsonProcessingException;
}
