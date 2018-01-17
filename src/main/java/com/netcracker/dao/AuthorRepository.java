package com.netcracker.dao;

import com.netcracker.entity.Author;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface AuthorRepository extends CrudRepository<Author, BigInteger> {
    Author findByAuthorInfo(String authorInfo);
}
