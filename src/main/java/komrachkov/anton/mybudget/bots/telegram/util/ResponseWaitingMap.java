package komrachkov.anton.mybudget.bots.telegram.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class ResponseWaitingMap {
    private static Map<Long, CommandNames> instanceMap;

    private ResponseWaitingMap() {
    }

    private static void checkInstanceMap() {
        if (instanceMap == null) instanceMap = new HashMap<>();
    }

    public static Map<Long, CommandNames> getMap() {
        checkInstanceMap();
        return instanceMap;
    }

    public static void put(long chatId, CommandNames commandName) {
        checkInstanceMap();
        instanceMap.put(chatId, commandName);
    }

    public static void remove(long chatId) {
        checkInstanceMap();
        instanceMap.remove(chatId);
    }

    public static CommandNames get(long chatId) {
        checkInstanceMap();
        return instanceMap.get(chatId);
    }

    public static boolean contains(long chatId) {
        checkInstanceMap();
        return instanceMap.containsKey(chatId);
    }

}
