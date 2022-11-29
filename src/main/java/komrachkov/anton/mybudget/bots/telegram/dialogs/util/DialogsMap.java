package komrachkov.anton.mybudget.bots.telegram.dialogs.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.DIALOG_ID;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class DialogsMap {
    private static Map<Long, DialogState> instance;

    private DialogsMap() {
    }

    private static void checkInstanceMap() {
        if (instance == null) instance = new HashMap<>();
    }

    public static Map<String, String> getDialogMap(long chatId) {
        checkInstanceMap();
        if (instance.get(chatId) != null) return instance.get(chatId).getDialogState();
        return null;
    }

    public static String getDialogStepById(long chatId, String stepId) {
        checkInstanceMap();
        if (instance.get(chatId) != null) return instance.get(chatId).getStep(stepId);
        return null;
    }

    public static void put(long chatId, String stepId, String data) {
        checkInstanceMap();
        Map<String, String> dialogMap = getDialogMap(chatId);
        if (dialogMap == null) {
            dialogMap = new HashMap<>();
            putDialogMap(chatId, dialogMap);
        }
        dialogMap.put(stepId, data);
    }

    public static void putDialogMap(long chatId, Map<String, String> dialogMap) {
        checkInstanceMap();
        instance.put(chatId, new DialogState(chatId, dialogMap));
    }

    public static void replaceById(long chatId, String nameId, String text) {
        checkInstanceMap();
        Map<String, String> dialogMap = getDialogMap(chatId);
        if (dialogMap == null) return;
        if (dialogMap.get(nameId) != null) dialogMap.replace(nameId, text);
    }

    public static void remove(long chatId) {
        checkInstanceMap();
        instance.remove(chatId);
    }
}

/**
 * @author Anton Komrachkov
 * @since 0.4 (29.11.2022)
 */
class DialogState {
    private final long chatId;
    private final String dialogId;
    private Map<String, String> dialogState;
    private DialogState subDialog;

    DialogState(long chatId, String dialogId) {
        this.chatId = chatId;
        this.dialogId = dialogId;
        this.dialogState = new HashMap<>();
    }

    DialogState(long chatId, Map<String, String> dialogState) {
        this.chatId = chatId;
        this.dialogId = dialogState.get(DIALOG_ID.getId());
        this.dialogState = dialogState;
    }

    String getStep(String stepId) {
        return dialogState.get(stepId);
    }

    Map<String, String> getDialogState() {
        return dialogState;
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

