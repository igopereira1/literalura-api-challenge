package com.literalura.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDataDTO(
        @JsonAlias("id") Integer id,
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorDataDTO> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Integer downloadCount
) {}
