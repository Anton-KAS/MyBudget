package komrachkov.anton.mybudget.bots.telegram.texts.commands;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class UnknownText implements MessageText {
    @Override
    public MessageText setChatId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Не понимаю вас =(\n" +
                "Напишите /help чтобы узнать что я понимаю.";
    }
}
