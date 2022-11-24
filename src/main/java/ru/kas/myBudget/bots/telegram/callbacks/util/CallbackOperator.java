package ru.kas.myBudget.bots.telegram.callbacks.util;

/**
 * Операторы для формирования Callback Data (см. {@link CallbackIndex})
 *
 * @since 0.2
 * @author Anton Komrachkov
 */

public enum CallbackOperator {
    SAVE("save"),
    CANCEL("cancel"),
    DELETE("delete");

    private final String id;

    CallbackOperator(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
