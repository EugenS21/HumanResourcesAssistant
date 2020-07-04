package com.humanresources.assistant.restclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseUrl {

    public static String BASE_URL;

    @Value ("${app.baseUrl}")
    public void setNameStatic(String baseUrl) {
        BASE_URL = baseUrl;
    }
}
