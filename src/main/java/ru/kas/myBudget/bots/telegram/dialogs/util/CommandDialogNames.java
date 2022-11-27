package ru.kas.myBudget.bots.telegram.dialogs.util;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface CommandDialogNames extends CommandNames {
    String getStepTextPattern();

    String getStepIdText();
}
