package ru.devvault.skilltest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Абстрактный класс реализующий общее сообщение
 * @param <T> - тип тела сообщения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message<T> {
    private MessageId messageId;
    private T messageBody;

    public Message(T messageBody) {
        this.messageId = new MessageId(null, null);
        this.messageBody = messageBody;
    }

    public Message(T messageBody, String topic) {
        this.messageId = new MessageId(null, topic);
        this.messageBody = messageBody;
    }
}
