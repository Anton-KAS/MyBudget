package komrachkov.anton.mybudget.bots.telegram.texts.callback;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class CancelDialogText implements MessageText {
    public CancelDialogText() {
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
