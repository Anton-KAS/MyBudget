package ru.kas.myBudget.bots.telegram.dialogs;

import java.util.HashMap;
import java.util.Map;

public class DialogsMap {
    private static Map<Long, Map<String, String>> dialogsMap;

    private DialogsMap() {
    }

    private static void checkInstanceMap() {
        if (dialogsMap == null) dialogsMap = new HashMap<>();
    }

    public static Map<String, String> getDialogMap(long chatId) {
        checkInstanceMap();
        return dialogsMap.get(chatId);
    }

    public static Map<String, String> getDialogMapById(long chatId) {
        checkInstanceMap();
        return dialogsMap.get(chatId);
    }

    public static String getDialogStepById(long chatId, String stepId) {
        checkInstanceMap();
        if (dialogsMap.get(chatId) != null) return dialogsMap.get(chatId).get(stepId);
        return null;
    }

    public static void put(long chatId, String stepId, String data) {
        checkInstanceMap();
        Map<String, String> dialogMap = getDialogMapById(chatId);
        if (dialogMap == null) {
            dialogMap = new HashMap<>();
            putDialogMap(chatId, dialogMap);
        }
        dialogMap.put(stepId, data);
    }

    public static void putDialogMap(long chatId, Map<String, String> dialogMap) {
        checkInstanceMap();
        dialogsMap.put(chatId, dialogMap);
    }

    public static void replaceById(long chatId, String nameId, String text) {
        checkInstanceMap();
        Map<String, String> dialogMap = getDialogMap(chatId);
        if (dialogMap == null) return;
        if (dialogMap.get(nameId) != null) dialogMap.replace(nameId, text);
    }

    public static void remove(long chatId) {
        checkInstanceMap();
        dialogsMap.remove(chatId);
    }
}

