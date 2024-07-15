package com.literalura.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDataDTO(
    @JsonAlias("name") String name,
    @JsonAlias("birth_year") Integer birthYear,
    @JsonAlias("death_year") Integer deathYear
) {};
