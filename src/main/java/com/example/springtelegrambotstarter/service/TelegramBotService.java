package com.example.springtelegrambotstarter.service;


import com.example.springtelegrambotstarter.component.utills.TelegramBotUtils;
import com.example.springtelegrambotstarter.entity.BotUser;
import com.example.springtelegrambotstarter.enums.BotMenu;
import com.example.springtelegrambotstarter.enums.Language;
import com.example.springtelegrambotstarter.payload.PageableRequest;
import com.example.springtelegrambotstarter.payload.PageableResponse;
import com.example.springtelegrambotstarter.repository.BotUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@Service
@Slf4j
public class TelegramBotService {
    private final BotUserRepository botUserRepository;
    private final TelegramBotUtils botUtils;

    public void getUpdate(Update update){
        BotUser botUser = getBotUser(update);
        // Your code starts here
        botUtils.sendMessage("Hi, "+ botUser.getName(), botUser.getChatId(), false);
    }


    public BotUser getBotUser(Update update) {
        if (update.hasMessage()) {
            BotUser botUser = botUserRepository.findByChatId(update.getMessage().getChatId()).orElse(null);
            if (botUser == null) {
                botUser = new BotUser(
                        update.getMessage().getFrom().getId(),
                        update.getMessage().getFrom().getFirstName() + (update.getMessage().getFrom().getLastName() != null ? " " + update.getMessage().getFrom().getLastName() : ""),
                        BotMenu.MAIN,
                        update.getMessage().getFrom().getUserName(),
                        Language.EN
                );
                return botUserRepository.save(botUser);
            }
            return botUser;

        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BotUser not found!");
    }

    public BotUser getById(Long id) {
        return botUserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BotUser not found!"));
    }

    public PageableResponse<BotUser> getAll(PageableRequest request) {
        Page<BotUser> botUserPage = botUserRepository.findAll((request).toPageable());
        return new PageableResponse<>(botUserPage.getContent(), botUserPage.getTotalElements());
    }


}
