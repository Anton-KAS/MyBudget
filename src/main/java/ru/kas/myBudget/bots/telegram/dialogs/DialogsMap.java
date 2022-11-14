package ru.kas.myBudget.bots.telegram.dialogs;

import java.util.HashMap;
import java.util.Map;

public class DialogsMap {
    private static DialogsMap instance;
    private final Map<Long, Map<String, String>> dialogsMap;

    private DialogsMap() {
        this.dialogsMap = new HashMap<>();
    }

    public static Map<Long, Map<String, String>> getDialogsMap() {
        if (instance == null) {
            instance = new DialogsMap();
        }
        return instance.dialogsMap;
    }

    public static Map<String, String> getDialogMap(long chatId) {
        if (instance == null) {
            return null;
        }
        return instance.dialogsMap.get(chatId);
    }

    public Map<String, String> getDialogMapById(long chatId) {
        if (instance == null) {
            return null;
        }
        return instance.dialogsMap.get(chatId);
    }

    public String getDialogStepById(long chatId, String stepId) {
        if (instance == null) {
            return null;
        }
        return instance.dialogsMap.get(chatId).get(stepId);
    }

    public static DialogsMap getDialogsMapClass() {
        if (instance == null) {
            instance = new DialogsMap();
        }
        return instance;
    }

    public void putDialogMap(long userId, Map<String, String> dialogMap) {
        DialogsMap.getDialogsMap().put(userId, dialogMap);
    }

    public DialogsMap replaceById(long chatId, String nameId, String text) {
        if (instance == null) {
            return null;
        }
        Map<String, String> dialogMap = getDialogMap(chatId);
        if (dialogMap == null) return null;
        if (dialogMap.get(nameId) != null) dialogMap.replace(nameId, text);
        return this;
    }

    public DialogsMap remove(long chatId) {
        if (instance == null) {
            return null;
        }
        instance.dialogsMap.remove(chatId);
        return instance;
    }

    @Override
    public String toString() {
        return "DialogsMap{" +
                "dialogsMap=" + dialogsMap +
                '}';
    }
}
