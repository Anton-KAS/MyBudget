package ru.kas.myBudget.bots.telegram.texts.commands;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.*;
import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.STAT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class HelpText implements MessageText {

    @Override
    public MessageText setUserId(Long userId) {
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
