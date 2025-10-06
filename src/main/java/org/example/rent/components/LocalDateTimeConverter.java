package org.example.rent.components;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime>  {
    @Override
    public LocalDateTime convert(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        return LocalDateTime.parse(source, formatter);
    }
}
