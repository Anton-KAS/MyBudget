package komrachkov.anton.mybudget.bots.telegram.dialogs.util;

import komrachkov.anton.mybudget.bots.telegram.util.Container;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface DialogStepsContainer extends Container {
    Dialog retrieve(String identifier);
}
