package ru.amalnev.solarium.interpreter.memory;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;

public interface IArrayValue
{
    IValue getArrayElement(int elementIndex) throws InterpreterException;

    void setArrayElement(int elementIndex, IValue elementValue) throws InterpreterException;

    void addArrayElement(IValue element);

    int getArraySize() throws InterpreterException;

    void copy(IArrayValue other) throws InterpreterException;
}
