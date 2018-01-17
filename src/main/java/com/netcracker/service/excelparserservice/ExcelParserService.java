package com.netcracker.service.excelparserservice;

import com.netcracker.entity.Book;

import java.util.List;

public interface ExcelParserService {
    List<Book> parse(String fileName);
}
