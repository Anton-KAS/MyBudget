package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StopKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StopText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class StopCommand extends CommandControllerImpl {
    @Autowired
    public StopCommand(TelegramUserService telegramUserService, StopText messageText, StopKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode() {
        this.defaultExecuteMode = ExecuteMode.getCommandExecuteMode();
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        ToDoList toDoList = super.execute(update, executeMode);
        telegramUserService.findById(UpdateParameter.getUserId(update)).ifPresent(
                user -> {
                    user.setActive(false);
                    telegramUserService.save(user);
                }
        );
        return toDoList;
    }
}
