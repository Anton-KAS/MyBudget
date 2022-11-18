package ru.kas.myBudget.bots.telegram.util;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

import java.util.HashMap;
import java.util.Map;

public class ResponseWaitingMap {
    private static Map<Long, CommandNames> map;

    private ResponseWaitingMap() {
    }

    private static void checkMap() {
        if (map == null) {
            map = new HashMap<>();
        }
    }

    public static Map<Long, CommandNames> getResponseWaitingMap() {
        checkMap();
        return map;
    }

    public static void put(long chatId, CommandNames commandName) {
        checkMap();
        map.put(chatId, commandName);
    }

    public static void remove(long chatId) {
        checkMap();
        map.remove(chatId);
    }

    public static CommandNames get(long chatId) {
        checkMap();
        return map.get(chatId);
    }

    public static boolean contains(long chatId) {
        checkMap();
        return map.containsKey(chatId);
    }

}
