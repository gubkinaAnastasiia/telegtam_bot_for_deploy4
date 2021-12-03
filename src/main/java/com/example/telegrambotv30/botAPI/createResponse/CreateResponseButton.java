package com.example.telegrambotv30.botAPI.createResponse;

import com.example.telegrambotv30.botAPI.ButtonState;
import com.example.telegrambotv30.cache.UserDataCache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateResponseButton implements CreateResponse<InlineKeyboardMarkup>{

    private final UserDataCache userDataCache;

    public CreateResponseButton(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    @Override
    public InlineKeyboardMarkup getShowMainMenu() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonTestBeck = new InlineKeyboardButton().setText("Тест Бека");
        buttonTestBeck.setCallbackData(ButtonState.TEST_BECK.name());

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonTestBeck);
//        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
//        keyboardButtonList1.add(buttonTestBeck);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getTestSelection(int userId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        buttonYes.setCallbackData(ButtonState.YES.name()).setText("Да");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonNo.setCallbackData(ButtonState.NO.name()).setText("Нет");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonNo);
        keyboardButtonList1.add(buttonYes);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getTest(int userId) {
        return userDataCache.getUserTest(userId).getKeybord();
    }

    @Override
    public InlineKeyboardMarkup getResult(int userId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonOk = new InlineKeyboardButton();
        buttonOk.setCallbackData(ButtonState.OK.name()).setText("Ок");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonOk);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getConsentTest(int userId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        buttonYes.setCallbackData(ButtonState.YES.name()).setText("Да");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonNo.setCallbackData(ButtonState.NO.name()).setText("Нет");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonNo);
        keyboardButtonList1.add(buttonYes);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }


}
