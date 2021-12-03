package com.example.telegrambotv30.botAPI;

import com.example.telegrambotv30.botAPI.createResponse.CreateResponseImpl;
import com.example.telegrambotv30.botAPI.exception.ExceptionUsers;
import com.example.telegrambotv30.botAPI.responseProcessing.ProcessingButton;
import com.example.telegrambotv30.botAPI.responseProcessing.ProcessingWord;
import com.example.telegrambotv30.cache.UserDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramFacade {
    private UserDataCache userDataCache;
    private final CreateResponseImpl createResponse;
    private final ProcessingWord processingWord;
    private final ProcessingButton processingButton;

    public TelegramFacade(UserDataCache userDataCache, CreateResponseImpl createResponse, ProcessingWord processingWord, ProcessingButton processingButton) {
        this.userDataCache = userDataCache;
        this.createResponse = createResponse;
        this.processingWord = processingWord;
        this.processingButton = processingButton;
    }

    public List<BotApiMethod<?>> handleUpdate(Update update) {
        List<BotApiMethod<?>> result = new ArrayList<>();

        //обработка входящего ответа
        try {
            if (update.hasCallbackQuery()) {
                hanleInputButton(update);
            } else if (update.getMessage() != null && update.getMessage().hasText()) {
                handleInputMessage(update);
            }
        } catch (ExceptionUsers e) {
            if (update.hasCallbackQuery()) {
                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
                answerCallbackQuery.setShowAlert(false);
                answerCallbackQuery.setText("Нажмите на кнопку последнего сообщения");
                result.add(answerCallbackQuery);
                return result;
            } else if (update.hasMessage()) {
                result.add(new SendMessage(update.getMessage().getChatId(), e.getMessage()));
            }
        }

        //создание исходящего сообщения
        result.add(createResponse.getAnswer(update));
        return result;
    }

    //начальная обработка сообщения
    private void handleInputMessage(Update update) {
        String inputMessage = update.getMessage().getText();
        int userId = update.getMessage().getFrom().getId();

        //создание нового юзера
        if (userDataCache.getUserChatId(userId)==null) {
            userDataCache.setUserChatId(userId, update.getMessage().getChatId());
            userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }

        //обработка команды
        switch (inputMessage) {
            case "/start":
                userDataCache.setUserChatId(userId, update.getMessage().getChatId());
                userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
        }

        //отправка на обработку сообщения
        processingWord.dataAcceptance(update);
    }

    //начальная обработка кнопки
    private void hanleInputButton(Update update) {
        processingButton.dataAcceptance(update);
    }
}
