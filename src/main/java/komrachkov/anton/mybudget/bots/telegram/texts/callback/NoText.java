package komrachkov.anton.mybudget.bots.telegram.texts.callback;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class NoText implements MessageText {
    @Override
    public MessageText setChatId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Что-то пошло не так =(";
    }
}
