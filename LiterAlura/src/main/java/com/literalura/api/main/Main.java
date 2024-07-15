package com.literalura.api.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.literalura.api.dto.BookDataDTO;
import com.literalura.api.dto.BookResultsDTO;
import com.literalura.api.entity.Author;
import com.literalura.api.entity.Book;
import com.literalura.api.repository.AuthorRepository;
import com.literalura.api.repository.BookRepository;
import com.literalura.api.service.ApiClient;
import com.literalura.api.service.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiClient apiClient;
    private final DataConverter dataConverter;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final String GUTENDEX_API_URL = "https://gutendex.com/books/?search=";

    @Autowired
    public Main(ApiClient apiClient, DataConverter dataConverter, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.apiClient = apiClient;
        this.dataConverter = dataConverter;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() throws JsonProcessingException {
        int option = -1;
        while (option != 0) {
            String menu = """
                    1. Buscar livro pelo título na API Gutendex
                    0. Sair
                    """;
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    getBooks();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void getBooks() throws JsonProcessingException {
        try {
            BookResultsDTO bookResultsDTO = getBookData();
            Optional<BookDataDTO> bookDataDTO = bookResultsDTO.books().stream()
                    .max(Comparator.comparing(BookDataDTO::downloadCount));

            if (bookDataDTO.isPresent()) {
                BookDataDTO bookData = bookDataDTO.get();
                printBookDTO(bookData);

                String title = bookData.title();
                Optional<Book> bookOptional = bookRepository.findByTitleContainingIgnoreCase(title);

                if (bookOptional.isPresent()) {
                    System.out.println("Livro já registrado\n");
                } else {
                    saveBook(bookData);
                }
            } else {
                System.out.println("Nenhum livro encontrado\n");
            }
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage() + "\n");
        }
    }

    private BookResultsDTO getBookData() throws JsonProcessingException {
        System.out.println("Digite o nome do livro: ");
        String bookName = scanner.nextLine();
        String address = GUTENDEX_API_URL + bookName.toLowerCase().replace(" ", "%20").trim();
        String json = apiClient.getApiData(address);
        return dataConverter.getData(json, BookResultsDTO.class);
    }

    private void saveBook(BookDataDTO bookDataDTO) {
        Author author = new Author(bookDataDTO.authors().get(0));
        Optional<Author> authorOptional = authorRepository.findByNameEqualsIgnoreCase(author.getName());

        if (authorOptional.isPresent()) {
            author = authorOptional.get();
        } else {
            authorRepository.save(author);
        }

        Book book = new Book(bookDataDTO);
        book.setAuthor(author);
        bookRepository.save(book);
    }

    private void printBookDTO(BookDataDTO bookDataDTO) {
        System.out.println("-----------------------------------");
        System.out.println("---------------LIVRO---------------");
        System.out.println("Título: " + bookDataDTO.title());
        System.out.println("Autor: " + bookDataDTO.authors().get(0).name());
        System.out.println("Línguas: " + String.join(", ", bookDataDTO.languages()));
        System.out.println("Número de Downloads: " + bookDataDTO.downloadCount());
        System.out.println("-----------------------------------");
        System.out.println("\n");
    }
}