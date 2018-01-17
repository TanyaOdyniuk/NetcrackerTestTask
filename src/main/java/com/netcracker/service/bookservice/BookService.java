package com.netcracker.service.bookservice;

import com.netcracker.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    List<Book> getBooksByAuthor(String authorInfo);
    List<Book> getBooksByPublishingHouse(String publishingHouseInfo);
    List<Book> getBooksByAuthorAndPublishingHouse(String authorInfo, String publishingHouseInfo);
    void addBooksToDB(List<Book> books);
}
