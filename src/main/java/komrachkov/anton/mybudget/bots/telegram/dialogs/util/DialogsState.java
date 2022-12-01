package komrachkov.anton.mybudget.bots.telegram.dialogs.util;

import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;

import java.util.*;

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

    public static Optional<String> getByStepId(long chatId, String stepId) {
        Optional<DialogState> dialogState = getDialogState(chatId);
        if (dialogState.isEmpty()) return Optional.empty();

        Optional<String> stepValue = dialogState.get().getByStepId(stepId);
        if (stepValue.isEmpty()) return Optional.empty();
        return stepValue;
    }

    public static Optional<String> getCurrentDialogId(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return Optional.empty();
        return Optional.of(dialogStateOpt.get().getDialogId());
    }

    public static Optional<String> getDialogStepById(long chatId, String stepId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return Optional.empty();
        return dialogStateOpt.get().getByStepId(stepId);
    }

    public static void put(long chatId, CommandNames commandName, String stepId, String data) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        DialogState dialogState;
        if (dialogStateOpt.isEmpty()) {
            dialogState = new DialogState(chatId, commandName);
            putDialogState(chatId, dialogState);
        } else {
            dialogState = dialogStateOpt.get();
        }
        dialogState.put(stepId, data);
    }

    public static void put(long chatId, String stepId, String data) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return;
        dialogStateOpt.get().put(stepId, data);
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
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return;
        dialogStateOpt.get().replace(nameId, text);
    }

    public static boolean hasDialogs(long chatId) {
        return instance.containsKey(chatId);
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

    public static String stateToString(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return "dialog for " + chatId + " not exist";
        return dialogStateOpt.get().toString();
    }

    public static int getStateSize(long chatId) {
        Optional<DialogState> dialogStateOpt = getDialogState(chatId);
        if (dialogStateOpt.isEmpty()) return 0;
        return dialogStateOpt.get().getSize();
    }
}

/**
 * @author Anton Komrachkov
 * @since 0.4 (29.11.2022)
 */
class DialogState {
    private final long chatId;
    private final CommandNames commandName;
    private Map<String, String> dialogState;

    DialogState(long chatId, CommandNames commandName) {
        this.chatId = chatId;
        this.commandName = commandName;
        this.dialogState = new HashMap<>();
    }

    DialogState(long chatId, CommandNames commandName, Map<String, String> dialogState) {
        this.chatId = chatId;
        this.commandName = commandName;
        this.dialogState = dialogState;
    }

    Optional<String> getByStepId(String stepId) {
        if (dialogState.containsKey(stepId)) return Optional.of(dialogState.get(stepId));
        return Optional.empty();
    }

    Map<String, String> getDialogState() {
        return dialogState;
    }

    void setDialogState(Map<String, String> dialogState) {
        this.dialogState = dialogState;
    }

    public long getChatId() {
        return chatId;
    }

    public String getDialogId() {
        return commandName.getName();
    }

    public CommandNames getCommandName() {
        return commandName;
    }

    public int getSize() {
        return dialogState.size();
    }

    public void put(String stepId, String data) {
        dialogState.put(stepId, data);
    }

    public void replace(String stepId, String data) {
        dialogState.replace(stepId, data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogState that = (DialogState) o;
        return chatId == that.chatId && commandName.equals(that.commandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, commandName);
    }

    @Override
    public String toString() {
        return "DialogState{" +
                "chatId=" + chatId +
                ", commandName=" + commandName +
                ", dialogState=" + dialogState +
                '}';
    }
}

