package com.example.telegrambotv30.cache;

import com.example.telegrambotv30.botAPI.BotState;
import com.example.telegrambotv30.test.Test;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache{

    private Map<Integer, BotState> userBotState = new HashMap<>();
    private Map<Integer, Test> userTest = new HashMap<>();
    private Map<Integer, Long> userChatId = new HashMap<>();
    private Map<Integer, String> userException = new HashMap<>();

    @Override
    public void setUserCurrentBotState(int userId, BotState botState) {
        userBotState.put(userId, botState);
    }

    @Override
    public void setUserTest(int userId, Test test) {
        userTest.put(userId, test);
    }

    @Override
    public void setUserChatId(int userId, long chatId) {
        userChatId.put(userId, chatId);
    }

    @Override
    public void setUserException(int userId, String exception) {
        userException.put(userId, exception);
    }


    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = userBotState.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

    @Override
    public Test getUserTest(int userId) {
        return userTest.get(userId);
    }

    @Override
    public Long getUserChatId(int userId) {
        return userChatId.get(userId);
    }

    @Override
    public String getUserException(int userId) {
        return userException.get(userId);
    }


}