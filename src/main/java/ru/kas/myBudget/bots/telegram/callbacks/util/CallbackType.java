package ru.kas.myBudget.bots.telegram.callbacks.util;

/**
 * Типы для формирования Callback Data (см. {@link CallbackIndex})
 *
 * @since 0.2
 * @author Anton Komrachkov
 */

public enum CallbackType {
    NORMAL("cb"),
    DIALOG("dg");

    private final String id;

    CallbackType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
