package com.literalura.api.repository;

import com.literalura.api.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>{
    Optional<Author> findByNameEqualsIgnoreCase(String authorName);

    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birthYear AND a.deathYear")
    List<Author> findAuthorsAliveInYear(Integer year);
}
