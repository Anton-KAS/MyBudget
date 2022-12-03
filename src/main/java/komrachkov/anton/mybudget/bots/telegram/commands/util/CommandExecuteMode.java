package komrachkov.anton.mybudget.bots.telegram.commands.util;

import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since (03.12.2022)
 */

public class CommandExecuteMode {
    public static ExecuteMode getExecuteMode() {
        return ExecuteMode.SEND;
    }
}
