package com.example.springtelegrambotstarter.component.utills;

import com.example.springtelegrambotstarter.payload.TelegramPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class TelegramBotUtils {


    private final String BASE_URL = "https://api.telegram.org/bot";
    @Value("${app.telegrambot.token}")
    private String botToken;

    @Autowired
    private RestTemplate restTemplate;

    public void sendMessage(String message, Long chatId, boolean markdown) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(markdown);
            sendMessage.setChatId(chatId);
            sendMessage.setText(message);
            restTemplate.postForObject(BASE_URL + botToken + "/sendMessage", sendMessage, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            restTemplate.postForObject(BASE_URL + botToken + "/sendMessage", sendMessage, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(TelegramPhoto photo) {
        try {
            restTemplate.postForObject(BASE_URL + botToken + "/sendPhoto", photo, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAction(String action, Long chat_id) {
        try {
            restTemplate.postForObject(BASE_URL + botToken + "/sendChatAction?chat_id=" + chat_id + "&action=" + action, null, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}