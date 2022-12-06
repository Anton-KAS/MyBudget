package komrachkov.anton.mybudget.bots.telegram.util;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public enum ExecuteMode {
    SEND,
    EDIT,
    DELETE,
    POPUP;

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    public static ExecuteMode getCommandExecuteMode() {
        return SEND;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    public static ExecuteMode getCallbackExecuteMode() {
        return EDIT;
    }
}
