package ru.devvault.skilltest.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс реализующий содержание email сообщения (тело + прикрепления)
 */
@Data
@AllArgsConstructor
public class EmailContent {
    private String content;
    private List<Object> attachments;

    public EmailContent() {
        this.content = "";
        this.attachments = Collections.emptyList();
    }

    public EmailContent(String content) {
        this.content = content;
        this.attachments = Collections.emptyList();
    }
}
