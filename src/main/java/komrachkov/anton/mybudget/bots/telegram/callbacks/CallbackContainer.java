package komrachkov.anton.mybudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.commands.MenuCommand;
import komrachkov.anton.mybudget.bots.telegram.util.Container;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.*;

/**
 * Контейнер для всех классов по списку {@link CallbackNamesImpl}
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class CallbackContainer implements Container {
    private final ImmutableMap<String, CommandController> callbackMap;
    private final CommandController unknownCommand;

    @Autowired
    public CallbackContainer(MenuCommand menuCommand, AccountsCallback accountsCallback, UnknownCallback unknownCallback,
                             AccountCallback accountCallback, CloseCallback closeCallback, NoCallback noCallback) {
        unknownCommand = unknownCallback;

        callbackMap = ImmutableMap.<String, CommandController>builder()
                .put(MENU.getName(), menuCommand)
                .put(ACCOUNTS.getName(), accountsCallback)
                .put(ACCOUNT.getName(), accountCallback)
                .put(CLOSE.getName(), closeCallback)
                .put(NO.getName(), noCallback)
                .build();
    }

    /**
     * Возращает класс для обработки CallbackData команды
     *
     * @param identifier String ID по списку {@link CallbackNamesImpl}
     * @return CommandController
     */
    @Override
    public CommandController retrieve(String identifier) {
        return callbackMap.getOrDefault(identifier, unknownCommand);
    }

    /**
     * Проверяет наличие класса в контейнере
     *
     * @param identifier String ID по списку {@link CallbackNamesImpl}
     * @return CommandController
     */
    @Override
    public boolean contains(String identifier) {
        return callbackMap.containsKey(identifier);
    }
}
