package com.example.telegrambotv30.botAPI.createResponse;

import com.example.telegrambotv30.botAPI.BotState;
import com.example.telegrambotv30.cache.UserDataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CreateResponseImpl /*implements CreateResponse<SendMessage>*/ {

    private UserDataCache userDataCache;
    private CreateResponseText createResponceText;
    private CreateResponseButton createResponceButton;

    public CreateResponseImpl(UserDataCache userDataCache, CreateResponseText createResponceText, CreateResponseButton createResponceButton) {
        this.userDataCache = userDataCache;
        this.createResponceText = createResponceText;
        this.createResponceButton = createResponceButton;
    }

    public SendMessage getAnswer(Update update) {

        SendMessage reply = new SendMessage();
        int userId = 0;
        if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
        } else if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        }
        long chatId = userDataCache.getUserChatId(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        switch (botState) {
            case SHOW_MAIN_MENU:
                reply = new SendMessage(chatId, createResponceText.getShowMainMenu());
                reply.setReplyMarkup(createResponceButton.getShowMainMenu());
                userDataCache.setUserCurrentBotState(userId, BotState.TEST_SELECTION);
                userDataCache.setUserException(userId, null);
                break;
            case TEST_SELECTION:
                reply = new SendMessage(chatId, createResponceText.getTestSelection(userId));
                reply.setReplyMarkup(createResponceButton.getTestSelection(userId));
                if (userDataCache.getUserException(userId)==null) {
                    userDataCache.setUserCurrentBotState(userId, BotState.CONSENT_TEST);
                } else {
                    userDataCache.setUserException(userId, null);
                }
                break;
            case CONSENT_TEST:
                reply = new SendMessage(chatId, createResponceText.getConsentTest(userId));
                reply.setReplyMarkup(createResponceButton.getConsentTest(userId));
                userDataCache.setUserException(userId, null);
                break;
            case TEST:
                reply = new SendMessage(chatId, createResponceText.getTest(userId));
                reply.setReplyMarkup(createResponceButton.getTest(userId));
                userDataCache.setUserException(userId, null);
                break;
            case GETTING_RESULT:
                reply = new SendMessage(chatId, createResponceText.getResult(userId));
                reply.setReplyMarkup(createResponceButton.getResult(userId));
                userDataCache.setUserException(userId, null);
                break;
        }

        if (userDataCache.getUserException(userId)!=null) {
            reply.setText(userDataCache.getUserException(userId) + reply.getText());
            userDataCache.setUserException(userId, null);
        }

        return reply;
    }

}
