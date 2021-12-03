package com.example.telegrambotv30.botAPI.createResponse;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CreateResponse<T> {

    T getShowMainMenu();
    T getTestSelection(int userId);
    T getTest(int userId);
    T getResult(int userId);
    T getConsentTest(int userId);
}
