package ru.kas.myBudget.bots.telegram.texts;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.*;
import static ru.kas.myBudget.bots.telegram.commands.CommandName.STAT;

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
                START.getCommandName(),
                MENU.getCommandName(),
                STOP.getCommandName(),
                STAT.getCommandName());
    }
}
