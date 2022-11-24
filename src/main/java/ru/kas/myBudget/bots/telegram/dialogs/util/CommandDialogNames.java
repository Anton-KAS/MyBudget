package ru.kas.myBudget.bots.telegram.dialogs.util;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface CommandDialogNames extends CommandNames {
    String getStepTextPattern();
    String getStepIdText();
}
