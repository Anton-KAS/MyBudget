package komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class SaveText implements MessageText {
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "всё сохранил";
    }
}
