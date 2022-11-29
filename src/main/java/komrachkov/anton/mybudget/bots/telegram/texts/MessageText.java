package komrachkov.anton.mybudget.bots.telegram.texts;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface MessageText {
    MessageText setUserId(Long userId);

    String getText();

    default void checkUserIdSet(Long userId) {
        if (userId == null) throw new Error("userId is not set");
    }
}
