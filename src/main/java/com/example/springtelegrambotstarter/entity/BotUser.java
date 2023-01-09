package com.example.springtelegrambotstarter.entity;
import com.example.springtelegrambotstarter.entity.template.RootEntity;
import com.example.springtelegrambotstarter.enums.BotMenu;
import com.example.springtelegrambotstarter.enums.Language;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bot_user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BotUser extends RootEntity {
    @Column(nullable = false)
    private Long chatId;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private BotMenu menu = BotMenu.MAIN;
    private String username;
    private Language language = Language.EN;
}