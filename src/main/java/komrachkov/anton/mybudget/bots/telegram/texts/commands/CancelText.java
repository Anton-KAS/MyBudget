package komrachkov.anton.mybudget.bots.telegram.texts.commands;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class CancelText implements MessageText {
    public CancelText() {
    }

    @Override
    public MessageText setChatId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Всё отменил...";
    }
}
