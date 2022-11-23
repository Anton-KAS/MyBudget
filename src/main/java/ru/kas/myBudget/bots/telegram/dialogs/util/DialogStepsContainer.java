package ru.kas.myBudget.bots.telegram.dialogs.util;

import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.util.Container;

public interface DialogStepsContainer extends Container {
    Dialog retrieve(String identifier);
}
