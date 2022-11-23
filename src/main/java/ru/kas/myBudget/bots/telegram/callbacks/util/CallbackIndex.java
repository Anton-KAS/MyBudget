package ru.kas.myBudget.bots.telegram.callbacks.util;

public enum CallbackIndex {
    //Order is important! CALLBACK DATA: TYPE__FROM__TO__OPERATION__OPERATION_DATA__OPERATION_EXTRA_DATA
    TYPE,
    FROM,
    TO,
    OPERATION,
    OPERATION_DATA,
    OPERATION_EXTRA_DATA
}
