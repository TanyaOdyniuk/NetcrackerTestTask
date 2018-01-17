package com.netcracker.dao;

import com.netcracker.entity.Book;
import org.springframework.data.jpa.repository.Query;
import  org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, BigInteger> {
    @Query("select b from Book b where b.author.authorInfo = :authorInfo and b.publishingHouse.publishingHouseInfo = :publishingHouseInfo")
    List<Book> findByAuthorInfoAndPublishingHouseInfo(@Param("authorInfo") String authorInfo, @Param("publishingHouseInfo")  String publishingHouseInfo);

    @Query("select b from Book b where b.author.authorInfo = :authorInfo")
    List<Book> findByAuthorInfo(@Param("authorInfo") String authorInfo);

    @Query("select b from Book b where b.publishingHouse.publishingHouseInfo = :publishingHouseInfo")
    List<Book> findByPublishingHouseInfo(@Param("publishingHouseInfo")  String publishingHouseInfo);
}