package com.literalura.api.repository;

import com.literalura.api.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>{
    Optional<Author> findByNameEqualsIgnoreCase(String authorName);
}
