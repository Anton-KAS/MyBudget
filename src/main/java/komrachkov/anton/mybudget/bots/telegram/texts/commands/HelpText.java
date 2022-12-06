package komrachkov.anton.mybudget.bots.telegram.texts.commands;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.*;
import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.STAT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class HelpText implements MessageText {

    @Override
    public MessageText setChatId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return String.format("""
                        <b>Доступные команды:</b>

                        %s - начать работу со мной
                        %s - открыть меню бота
                        %s - приостановить работу со мной
                        %s - количество активных пользователей бота
                        """,
                START.getName(),
                MENU.getName(),
                STOP.getName(),
                STAT.getName());
    }
}
