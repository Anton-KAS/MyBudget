package komrachkov.anton.mybudget.bots.telegram.dialogs.util;

import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface CommandDialogNames extends CommandNames {
    String getStepTextPattern();

    String getStepIdText();
}
