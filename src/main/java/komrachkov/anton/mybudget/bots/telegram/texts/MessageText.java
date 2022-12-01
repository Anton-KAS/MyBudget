package komrachkov.anton.mybudget.bots.telegram.texts;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface MessageText {
    MessageText setChatId(Long userId);

    String getText();

    default void checkUserIdSet(Long chatId) {
        if (chatId == null) throw new Error("chatId is not set");
    }
}
