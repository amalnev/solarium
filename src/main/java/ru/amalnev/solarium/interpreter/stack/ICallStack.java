package ru.amalnev.solarium.interpreter.stack;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.language.statements.CompoundStatement;

public interface ICallStack
{
    CompoundStatement enterFunction(final String functionName, IValue... args) throws InterpreterException;

    IValue exitFunction() throws InterpreterException;

    void setReturnValue(final IValue value);
}
