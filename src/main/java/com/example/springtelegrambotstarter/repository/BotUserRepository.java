package com.example.springtelegrambotstarter.repository;

import com.example.springtelegrambotstarter.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {

    @Query(nativeQuery = true, value = "select * from bot_user where chat_id=:chatId")
    Optional<BotUser> findByChatId(Long chatId);
}