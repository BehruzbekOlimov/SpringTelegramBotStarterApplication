package com.example.springtelegrambotstarter.controller;

import com.example.springtelegrambotstarter.entity.BotUser;
import com.example.springtelegrambotstarter.payload.PageableRequest;
import com.example.springtelegrambotstarter.payload.PageableResponse;
import com.example.springtelegrambotstarter.service.TelegramBotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v1/telegram")
@Slf4j
public class TelegramBotController {

    private final TelegramBotService telegramBotService;

    @PostMapping("/webhook")
    void getUpdate(@RequestBody Update update) {
        log.info(update.toString());
        telegramBotService.getUpdate(update);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN','DIRECTOR')")
    @GetMapping("/users")
    PageableResponse<BotUser> getAllUsers(PageableRequest request){
        return telegramBotService.getAll(request);
    }

}
