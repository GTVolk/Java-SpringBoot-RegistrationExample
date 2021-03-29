package ru.devvault.skilltest.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageId {
    private UUID id;
    private String topic;

    public MessageId(UUID id) { this.id = id; this.topic = null; }
}
