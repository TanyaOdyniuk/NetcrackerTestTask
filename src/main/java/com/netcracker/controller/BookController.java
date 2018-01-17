package com.netcracker.controller;

import com.netcracker.entity.Book;
import com.netcracker.service.bookservice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/filter")
    public List<Book> getBooksByAuthorAndPublisingHouse(@RequestParam(value = "authorInfo") String authorInfo, @RequestParam(value = "publishingHouseInfo") String publishingHouseInfo) {
        if (authorInfo.isEmpty()) {
            if (publishingHouseInfo.isEmpty()) {
                return bookService.getAllBooks();
            } else {
                return bookService.getBooksByPublishingHouse(publishingHouseInfo);
            }
        } else {
            if (publishingHouseInfo.isEmpty()) {
                return bookService.getBooksByAuthor(authorInfo);
            } else {
                return bookService.getBooksByAuthorAndPublishingHouse(authorInfo, publishingHouseInfo);
            }
        }
    }

    @GetMapping("/authorfilter")
    public List<Book> getBooksByAuthor(@RequestParam(value = "authorInfo") String authorInfo) {
        if (authorInfo.isEmpty()) {
            return bookService.getAllBooks();
        }
        return bookService.getBooksByAuthor(authorInfo);
    }

    @GetMapping("/publishinghousefilter")
    public List<Book> getBooksByPublisingHouse(@RequestParam(value = "publishingHouseInfo") String publishingHouseInfo) {
        if (publishingHouseInfo.isEmpty()) {
            return bookService.getAllBooks();
        }
        return bookService.getBooksByPublishingHouse(publishingHouseInfo);
    }
}
