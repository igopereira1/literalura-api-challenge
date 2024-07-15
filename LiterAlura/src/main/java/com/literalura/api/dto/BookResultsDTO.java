package com.literalura.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookResultsDTO(
        @JsonAlias("results") List<BookDataDTO> books
) {};