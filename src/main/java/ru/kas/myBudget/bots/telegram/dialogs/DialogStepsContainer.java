package ru.kas.myBudget.bots.telegram.dialogs;

import ru.kas.myBudget.bots.telegram.util.Container;

public interface DialogStepsContainer extends Container {
    Dialog retrieve(String identifier);
}
