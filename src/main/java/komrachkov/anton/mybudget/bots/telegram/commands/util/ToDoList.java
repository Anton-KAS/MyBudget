package komrachkov.anton.mybudget.bots.telegram.commands.util;

import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

/**
 * @author Anton Komrachkov
 * @since (03.12.2022)
 */

public class ToDoList {
    private final Deque<ToDo> toDoList;

    public ToDoList() {
        this.toDoList = new ArrayDeque<>();
    }

    public void addToDo(ExecuteMode executeMode, Update update, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        toDoList.addLast(new ToDo(executeMode, update, text, inlineKeyboardMarkup));
    }

    public ToDo pollToDo() {
        return toDoList.pollFirst();
    }

    public boolean hasToDo() {
        return toDoList.size() > 0;
    }

    public class ToDo {
        private final ExecuteMode executeMode;
        private final Update update;
        private final String text;
        private final InlineKeyboardMarkup inlineKeyboardMarkup;

        private ToDo(ExecuteMode executeMode, Update update, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
            this.executeMode = executeMode;
            this.update = update;
            this.text = text;
            this.inlineKeyboardMarkup = inlineKeyboardMarkup;
        }

        public ExecuteMode getExecuteMode() {
            return executeMode;
        }

        public Update getUpdate() {
            return update;
        }

        public String getText() {
            return text;
        }

        public InlineKeyboardMarkup getInlineKeyboardMarkup() {
            return inlineKeyboardMarkup;
        }
    }
}
