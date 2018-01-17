package com.netcracker.service.bookservice.impl;

import com.netcracker.dao.AuthorRepository;
import com.netcracker.dao.BookRepository;
import com.netcracker.dao.PublishingHouseRepository;
import com.netcracker.entity.Author;
import com.netcracker.entity.Book;
import com.netcracker.entity.PublishingHouse;
import com.netcracker.service.bookservice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @Override
    public List<Book> getAllBooks() {
        return convertIterableToList(bookRepository.findAll());
    }

    @Override
    public List<Book> getBooksByAuthor(String authorInfo) {
        return bookRepository.findByAuthorInfo(authorInfo);
    }

    @Override
    public List<Book> getBooksByPublishingHouse(String publishingHouseInfo) {
        return bookRepository.findByPublishingHouseInfo(publishingHouseInfo);
    }

    @Override
    public List<Book> getBooksByAuthorAndPublishingHouse(String authorInfo, String publishingHouseInfo) {
        return bookRepository.findByAuthorInfoAndPublishingHouseInfo(authorInfo, publishingHouseInfo);
    }

    @Override
    public void addBooksToDB(List<Book> books) {
        for (Book book: books) {
            Author authorFromDB = authorRepository.findByAuthorInfo(book.getAuthor().getAuthorInfo());
            PublishingHouse publishingHouseFromDB = publishingHouseRepository.findByPublishingHouseInfo(book.getPublishingHouse().getPublishingHouseInfo());
            if(authorFromDB == null){
               authorFromDB = authorRepository.save(book.getAuthor());
            }
            book.setAuthor(authorFromDB);
            if(publishingHouseFromDB == null){
                publishingHouseFromDB = publishingHouseRepository.save(book.getPublishingHouse());
            }
            book.setPublishingHouse(publishingHouseFromDB);
            bookRepository.save(book);
        }
    }

    private List<Book> convertIterableToList(Iterable<Book> books){
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            result.add(book);
        }
        return result;
    }
}
