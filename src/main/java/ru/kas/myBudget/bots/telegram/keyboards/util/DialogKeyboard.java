package ru.kas.myBudget.bots.telegram.keyboards.util;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface DialogKeyboard extends Keyboard {
    Keyboard setUserId(long userId);

}
