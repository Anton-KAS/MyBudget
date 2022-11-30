package komrachkov.anton.mybudget.bots.telegram.dialogs.util;

import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;

import java.util.*;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.DIALOG_ID;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class DialogsState {
    private static Map<Long, LinkedList<DialogState>> instance;

    private DialogsState() {
    }

    private static void checkInstanceMap() {
        if (instance == null) instance = new HashMap<>();
    }

    public static Optional<DialogState> getDialogState(long chatId) {
        checkInstanceMap();
        LinkedList<DialogState> dialogState = instance.get(chatId);
        if (dialogState == null || dialogState.size() == 0) return Optional.empty();
        return Optional.of(instance.get(chatId).getLast());
    }

    public static Optional<Map<String, String>> getDialogStateMap(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return Optional.empty();
        return Optional.of(dialogStateOpt.get().getDialogState());
    }

    public static Optional<String> getCurrentDialogId(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return Optional.empty();
        return Optional.of(dialogStateOpt.get().getDialogId());
    }

    public static Optional<String> getDialogStepById(long chatId, String stepId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return Optional.empty();
        return Optional.of(dialogStateOpt.get().getStep(stepId));
    }

    public static void put(long chatId, CommandNames commandName, String stepId, String data) {
        Optional<Map<String, String>> dialogStateMapOpt = getDialogStateMap(chatId);
        Map<String, String> dialogStateMap;
        if (dialogStateMapOpt.isEmpty()) {
            dialogStateMap = new HashMap<>();
            putDialogStateMap(chatId, commandName, dialogStateMap);
        } else {
            dialogStateMap = dialogStateMapOpt.get();
        }
        dialogStateMap.put(stepId, data);
    }

    public static void putDialogState(long chatId, DialogState dialogState) {
        checkInstanceMap();
        LinkedList<DialogState> dialogStates = instance.get(chatId);
        if (dialogStates == null) {
            dialogStates = new LinkedList<>();
        }

        dialogStates.remove(dialogState);
        dialogStates.add(dialogState);
        instance.put(chatId, dialogStates);
    }

    public static void putDialogStateMap(long chatId, CommandNames commandName, Map<String, String> dialogStateMap) {
        DialogState dialogState = new DialogState(chatId, commandName, dialogStateMap);
        putDialogState(chatId, dialogState);
    }

    public static void replaceById(long chatId, String nameId, String text) {
        Optional<Map<String, String>> dialogMapOpt = getDialogStateMap(chatId);
        if (dialogMapOpt.isEmpty()) return;
        if (dialogMapOpt.get().get(nameId) != null) dialogMapOpt.get().replace(nameId, text);
    }

    public static void removeAllDialogs(long chatId) {
        checkInstanceMap();
        instance.remove(chatId);
    }

    public static void removeDialog(long chatId) {
        checkInstanceMap();

        LinkedList<DialogState> dialogStates = instance.get(chatId);
        if (dialogStates == null) return;

        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return;

        dialogStates.remove(dialogStateOpt.get());
    }

    public static boolean hasDialog(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        return dialogStateOpt.isPresent();
    }

    public static Optional<String> getDialogId(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return Optional.empty();
        return Optional.of(dialogStateOpt.get().getDialogId());
    }

    public static Optional<CommandNames> getCommandName(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return Optional.empty();
        return Optional.of(dialogStateOpt.get().getCommandName());
    }
}

/**
 * @author Anton Komrachkov
 * @since 0.4 (29.11.2022)
 */
class DialogState {
    private final long chatId;
    private final CommandNames commandName;
    private final String dialogId;
    private Map<String, String> dialogState;
    private DialogState subDialog;

    DialogState(long chatId, CommandNames commandName, String dialogId) {
        this.chatId = chatId;
        this.commandName = commandName;
        this.dialogId = dialogId;
        this.dialogState = new HashMap<>();
    }

    DialogState(long chatId, CommandNames commandName, String dialogId, Map<String, String> dialogState) {
        this.chatId = chatId;
        this.commandName = commandName;
        this.dialogId = dialogId;
        this.dialogState = dialogState;
    }

    DialogState(long chatId, CommandNames commandName, Map<String, String> dialogState) {
        this.chatId = chatId;
        this.commandName = commandName;
        this.dialogId = dialogState.get(DIALOG_ID.getId());
        this.dialogState = dialogState;
    }

    String getStep(String stepId) {
        return dialogState.get(stepId);
    }

    Map<String, String> getDialogState() {
        return dialogState;
    }

    boolean hasSubDialog() {
        return subDialog != null;
    }

    void removeSubDialog() {
        this.subDialog = null;
    }

    void setDialogState(Map<String, String> dialogState) {
        this.dialogState = dialogState;
    }

    DialogState getSubDialog() {
        return subDialog;
    }

    void setSubDialog(DialogState subDialog) {
        this.subDialog = subDialog;
    }

    public long getChatId() {
        return chatId;
    }

    public String getDialogId() {
        return dialogId;
    }

    public CommandNames getCommandName() {
        return commandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogState that = (DialogState) o;
        return chatId == that.chatId && dialogId.equals(that.dialogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, dialogId);
    }
}

