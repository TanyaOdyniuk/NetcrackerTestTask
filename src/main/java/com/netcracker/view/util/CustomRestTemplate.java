package com.netcracker.view.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
@Service
public class CustomRestTemplate extends RestTemplate {
    @Autowired
    private Logger logger;
    private static volatile CustomRestTemplate instance;

    public static CustomRestTemplate getInstance() {
        CustomRestTemplate localInstance = instance;
        if (localInstance == null) {
            synchronized (CustomRestTemplate.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CustomRestTemplate();
                }
            }
        }
        return localInstance;
    }

    public <T> T customGetForObject(String url, Class<T> responseType) throws RestClientException {
        return getForObject(getURL() + url, responseType);
    }

    private String getURL() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            String serverAddress = properties.getProperty("server.address");
            String serverPort = properties.getProperty("server.port");
            return "http://" + serverAddress + ":" + serverPort;
        } catch (IOException e) {
            logger.throwing("CustomRestTemplate.class", "getUrl", e);
        }
        return "";
    }
}
