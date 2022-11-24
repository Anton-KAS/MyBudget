package ru.kas.myBudget.bots.telegram.texts;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface MessageText {
    MessageText setUserId(Long userId);

    String getText();

    default void checkUserIdSet(Long userId) {
        if (userId == null) throw new Error("userId is not set");
    }
}
