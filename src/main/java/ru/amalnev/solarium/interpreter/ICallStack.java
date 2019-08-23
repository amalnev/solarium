package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.language.statements.CompoundStatement;

public interface ICallStack
{
    CompoundStatement enterFunction(final String functionName, Object... args);

    Object exitFunction();

    void setReturnValue(final Object value);
}
