package ru.kas.myBudget.bots.telegram.keyboards.util;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface DialogKeyboard extends Keyboard {
    Keyboard setUserId(long userId);

}
