package ru.amalnev.solarium.interpreter.memory;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;

public interface IAssociativeValue
{
    IValue getField(String fieldName);

    void setField(String fieldName, IValue fieldValue);

    String[] getFieldNames();

    void copy(IAssociativeValue other) throws InterpreterException;
}
