package ru.amalnev.solarium.interpreter.memory;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;

public interface IValue extends IScalarValue, IArrayValue, IAssociativeValue
{
    boolean isScalar();

    boolean isArray();

    void convertToArray();

    void convertToScalar();

    void copy(IValue other) throws InterpreterException;
}
