package komrachkov.anton.mybudget.bots.telegram.util;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.AccountService;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;

import java.util.Arrays;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

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
