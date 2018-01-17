package com.netcracker.service.excelparserservice.impl;

import com.netcracker.entity.Author;
import com.netcracker.entity.Book;
import com.netcracker.entity.PublishingHouse;
import com.netcracker.exception.ExcelParserException;
import com.netcracker.service.excelparserservice.ExcelParserService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ExcelFileParserServiceImpl implements ExcelParserService {
    @Autowired
    private Logger logger;
    public List<Book> parse(String fileName) {
        List<Book> books = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(fileName)) {
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workBook.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();
            while (it.hasNext()) {
                Book tempBook = new Book();
                Author tempAuthor = new Author();
                PublishingHouse tempPublishingHouse = new PublishingHouse();
                Row row = it.next();
                tempPublishingHouse.setPublishingHouseInfo(row.getCell(0).getStringCellValue());
                tempAuthor.setAuthorInfo(row.getCell(1).getStringCellValue());
                tempBook.setBookInfo(row.getCell(2).getStringCellValue());
                tempBook.setAuthor(tempAuthor);
                tempBook.setPublishingHouse(tempPublishingHouse);
                books.add(tempBook);
            }
        } catch (IOException exception){
            logger.throwing("ExcelFileParserServiceImpl.class", "parse", exception);
            throw new ExcelParserException("Ошибка чтения файла", exception);
        }
        return books;
    }
}
