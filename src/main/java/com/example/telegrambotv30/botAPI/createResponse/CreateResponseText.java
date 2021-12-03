package com.example.telegrambotv30.botAPI.createResponse;

import com.example.telegrambotv30.cache.UserDataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Component
public class CreateResponseText implements CreateResponse<String>{

    private final UserDataCache userDataCache;

    public CreateResponseText(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    @Override
    public String getShowMainMenu() {
        return "Добрый день. \n" +
                "Выберите какой тест хотите пройти или введите номер теста.\n" +
                "1. Тест Бека.";

    }

    @Override
    public String getTestSelection(int userId) {
        return userDataCache.getUserTest(userId).getStartMessage();
    }


    @Override
    public String getTest(int userId) {
        return userDataCache.getUserTest(userId).getQuest();
    }

    @Override
    public String getResult(int userId) {
        return userDataCache.getUserTest(userId).getResult();
    }

    @Override
    public String getConsentTest(int userId) {
        return userDataCache.getUserTest(userId).getStartMessage();
    }

}
