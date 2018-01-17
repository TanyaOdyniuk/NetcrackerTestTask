package com.netcracker.service.monitoringservice.impl;

import com.netcracker.service.bookservice.BookService;
import com.netcracker.service.excelparserservice.ExcelParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

@Service
public class WatcherServiceImpl implements Runnable {
    @Autowired
    private Logger logger;
    @Autowired
    private ExcelParserService excelParserService;
    @Autowired
    private BookService bookService;
    private WatchService watchService;
    private Map<WatchKey, Path> keys;

    private String getDirectoryName() {
        String directoryName = "";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            directoryName = properties.getProperty("directory");
        } catch (IOException exception) {
            logger.throwing( "WatcherService.class", "getDirectoryName", exception);
        }
        return directoryName;
    }

    public WatcherServiceImpl() {
        try {
            keys = new HashMap<>();
            this.watchService = FileSystems.getDefault().newWatchService();
            register(Paths.get(getDirectoryName()));
        } catch (IOException e) {
            logger.throwing( "WatcherService.class", "WatcherService", e);
        }
    }

    private void register(Path dir) {
        try {
            WatchKey key = dir.register(watchService, ENTRY_CREATE);
            keys.put(key, dir);
        } catch (IOException e) {
            logger.throwing( "WatcherService.class", "register", e);
        }
    }

    @Override
    public void run() {
        for (;;) {
            try {
                WatchKey key = watchService.take();
                Thread.sleep(10000);
                Path dir = keys.get(key);
                if (dir == null) {
                    logger.info("WatchKey not recognized!!");
                    continue;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();

                    if (kind == OVERFLOW) {
                        continue;
                    }

                    Path name = ((WatchEvent<Path>) event).context();
                    Path child = dir.resolve(name);
                    if (kind == ENTRY_CREATE) {
                        String newFileName = child.toString();
                        if(newFileName.toLowerCase().endsWith(".xlsx")){
                            bookService.addBooksToDB(excelParserService.parse(newFileName));
                        }
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            } catch (InterruptedException e) {
                logger.throwing( "WatcherService.class", "run", e);
            }
        }
    }
}