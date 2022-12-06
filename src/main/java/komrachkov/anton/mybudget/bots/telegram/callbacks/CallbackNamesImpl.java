package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;

/**
 * Список всех id Callback классов для {@link CallbackContainer}
 * и формирования CallbackData {@link CallbackIndex}
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

public enum CallbackNamesImpl implements CommandNames {
    MENU("menu"),
    ACCOUNTS("accounts"),
    ACCOUNT("acc"),
    CLOSE("close"),
    NOTHING("nothing"),
    NO("no");

    private final String name;

    CallbackNamesImpl(String callbackName) {
        this.name = callbackName;
    }

    @Override
    public String getName() {
        return name;
    }
}
