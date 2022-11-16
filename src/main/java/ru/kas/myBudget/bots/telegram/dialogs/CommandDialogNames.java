package ru.kas.myBudget.bots.telegram.dialogs;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

public interface CommandDialogNames extends CommandNames {
    String getDialogTextPattern();
    String getDialogIdText();
}
