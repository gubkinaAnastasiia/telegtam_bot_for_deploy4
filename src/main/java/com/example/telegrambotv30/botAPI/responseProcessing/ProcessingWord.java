package com.example.telegrambotv30.botAPI.responseProcessing;

import com.example.telegrambotv30.botAPI.BotState;
import com.example.telegrambotv30.botAPI.exception.ExceptionUsers;
import com.example.telegrambotv30.cache.UserDataCache;
import com.example.telegrambotv30.test.Test;
import com.example.telegrambotv30.test.TestBeck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ProcessingWord implements ResponceProcessing{

    @Autowired
    private UserDataCache userDataCache;

    @Override
    public void dataAcceptance(Update update) {

        Message message = update.getMessage();
        int userId = message.getFrom().getId();
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        switch (botState) {
            case TEST_SELECTION:
                Test test = getTest(message.getText());
                if (test == null) {
                    userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                    userDataCache.setUserException(userId, "Вы ввели некоректные данные в выборе теста. Повторите ввод.\n");
                    throw new ExceptionUsers("Вы ввели некоректные данные в выборе теста. Повторите ввод.\n");
                } else {
                    userDataCache.setUserTest(userId, test);
                    userDataCache.setUserCurrentBotState(userId, BotState.CONSENT_TEST);
                }
                break;
            case CONSENT_TEST:
                if (message.getText().equals("да")) {
                    userDataCache.setUserCurrentBotState(userId, BotState.TEST);
                } else if (message.getText().equals("нет")) {
                    userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                } else {
                    userDataCache.setUserException(userId, "Вы ввели некоректные данные в подтверждении прохождения теста. Повторите ввод.\n");
                    throw new ExceptionUsers("Вы ввели некоректные данные в подтверждении прохождения теста. Повторите ввод.\n");
                }
                break;
            case TEST:
                if (userDataCache.getUserTest(userId).checkAnswer(message.getText())) {
                    userDataCache.getUserTest(userId).setResult(message.getText());
                    if (!userDataCache.getUserTest(userId).hasNextQuest()) {
                        userDataCache.setUserCurrentBotState(userId, BotState.GETTING_RESULT);
                    }
                } else {
                    userDataCache.setUserException(userId, "Вы ввели некоректные данные при введении ответа на тест. Повторите ввод.\n");
                    throw new ExceptionUsers("Вы ввели некоректные данные при введении ответа на тест. Повторите ввод.\n");
                }
                break;
            case GETTING_RESULT:
                userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
        }
    }

    @Override
    public Test getTest(String answer) {
        Test test = null;
        switch (answer) {
            case "1": test = new TestBeck();
        }
        return test;
    }

    @Override
    public void getAnswer(int userId, String answer) {
        userDataCache.getUserTest(userId).setResult(answer);
    }
}
