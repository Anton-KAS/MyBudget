package komrachkov.anton.mybudget.bots.telegram.texts.dialogs;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class DeleteConfirmDialogText implements MessageText {
    @Override
    public MessageText setChatId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Удалить раз и навсегда?";
    }
}
