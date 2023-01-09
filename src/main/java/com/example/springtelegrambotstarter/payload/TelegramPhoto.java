package com.example.springtelegrambotstarter.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramPhoto {
    String photo;
    String caption;
    Long chat_id;
}