package ru.kas.myBudget.bots.telegram.dialogs.util;

import ru.kas.myBudget.bots.telegram.util.Container;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface DialogStepsContainer extends Container {
    Dialog retrieve(String identifier);
}
