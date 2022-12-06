package komrachkov.anton.mybudget.bots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

/**
 * @author Anton Komrachkov
 * @since (03.12.2022)
 */

public class ToDoList {
    private final Deque<ToDo> toDoList;

    private boolean resultCommit;

    public ToDoList() {
        this.toDoList = new ArrayDeque<>();
        this.resultCommit = false;
    }

    public void addToDo(ExecuteMode executeMode, Update update, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        toDoList.addLast(new ToDo(executeMode, update, text, inlineKeyboardMarkup));
    }

    public void addToDo(ExecuteMode executeMode, Update update, String text) {
        toDoList.addLast(new ToDo(executeMode, update, text, null));
    }

    public void addToDo(ExecuteMode executeMode, Update update) {
        toDoList.addLast(new ToDo(executeMode, update, null, null));
    }

    public ToDo pollToDo() {
        return toDoList.pollFirst();
    }

    public boolean hasToDo() {
        return toDoList.size() > 0;
    }

    public ToDoList addAll(ToDoList addToDoList) {
        toDoList.addAll(addToDoList.getDeque());
        return this;
    }

    private Deque<ToDo> getDeque() {
        return toDoList;
    }

    public boolean isResultCommit() {
        return resultCommit;
    }

    public void setResultCommit(boolean resultCommit) {
        this.resultCommit = resultCommit;
    }

    public record ToDo(ExecuteMode executeMode, Update update, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
//        public long getUserId() {
//            return UpdateParameter.getUserId(update);
//        }
//
//        public long getChatId() {
//            return UpdateParameter.getChatId(update);
//        }
//
//        public int getMessageId() {
//            return UpdateParameter.getMessageId(update);
//        }
//
//        public Optional<String> getCallbackQueryId() {
//            return UpdateParameter.getCallbackQueryId(update);
//        }
    }
}
