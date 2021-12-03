package com.example.telegrambotv30.cache;

import com.example.telegrambotv30.botAPI.BotState;
import com.example.telegrambotv30.test.Test;

public interface DataCache {

    void setUserCurrentBotState(int userId, BotState botState);

    void setUserTest(int userId, Test test);

    void setUserChatId(int userId, long chatId);

    void setUserException(int userId, String exception);

    BotState getUsersCurrentBotState(int userId);

    Test getUserTest(int userId);

    Long getUserChatId(int userId);

    String getUserException(int userId);

}

