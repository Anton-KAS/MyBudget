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

    public static DialogsMap getDialogsMapClass() {
        if (instance == null) {
            instance = new DialogsMap();
        }
        return instance;
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
