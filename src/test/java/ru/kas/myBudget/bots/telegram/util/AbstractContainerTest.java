package ru.kas.myBudget.bots.telegram.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Arrays;

abstract public class AbstractContainerTest {
    protected Container container;
    protected CommandNames[] commandNames;
    protected CommandController unknownCommand;

    protected BotMessageService botMessageServiceMock = Mockito.mock(BotMessageService.class);
    protected TelegramUserService telegramUserServiceMock = Mockito.mock(TelegramUserService.class);
    protected AccountService accountServiceMock = Mockito.mock(AccountService.class);
    protected Keyboard keyboardMock = Mockito.mock(Keyboard.class);
    protected MessageText messageTextMock = Mockito.mock(MessageText.class);

    protected static String TEST_UNKNOWN_COMMAND = "/testUnknownCommand";

    protected abstract void setContainer();
    protected abstract void setNames();
    protected abstract void setUnknownCommand();

    @BeforeEach
    public void init() {
        setContainer();
        setNames();
        setUnknownCommand();
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(commandNames).forEach(commandName -> {
            System.out.println("Command name: " + commandName.getName());
            CommandController command = container.retrieve(commandName.getName());
            Assertions.assertNotEquals(unknownCommand.getClass(), command.getClass());
        });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //when
        CommandController command = container.retrieve(TEST_UNKNOWN_COMMAND);

        //then
        Assertions.assertEquals(unknownCommand.getClass(), command.getClass());
    }
}
