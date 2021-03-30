package ru.devvault.skilltest.dto;

/**
 * Класс реализующий сообщение-ответ на заявку одобрения от внешней системы
 */
public class CheckProfileResponseMessage extends Message<CheckProfileResponse> {
    public CheckProfileResponseMessage(CheckProfileResponse messageBody) {
        super(messageBody);
    }

    public CheckProfileResponseMessage(CheckProfileResponse messageBody, String topic) {
        super(messageBody, topic);
    }
}
