package com.example.springtelegrambotstarter.component.utills;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@AllArgsConstructor
@Component
public class MessageSender {

    private final TelegramBotUtils botUtils;

    public void sendMessage(Long chatId, String message) {
        botUtils.sendMessage(message, chatId, false);
    }

    public void sendMessage(SendMessage message) {
        botUtils.sendMessage(message);
    }

    public void sendAction(String action, Long chatId) {
        botUtils.sendAction(action, chatId);
    }
}
