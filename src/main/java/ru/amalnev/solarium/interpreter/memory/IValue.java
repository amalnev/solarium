package ru.amalnev.solarium.interpreter.memory;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;

public interface IValue
{
    Object getScalarValue();

    IValue getField(String fieldName) throws InterpreterException;

    IValue getArrayElement(int elementIndex) throws InterpreterException;

    String[] getFieldNames();

    boolean isScalar();

    boolean isArray();

    int getArraySize() throws InterpreterException;

    void setScalarValue(Object value) throws InterpreterException;

    void setField(String fieldName, IValue fieldValue) throws InterpreterException;

    void setArrayElement(int elementIndex, IValue elementValue) throws InterpreterException;

    void copy(IValue value) throws InterpreterException;

    void convertToArray();

    void convertToScalar();

    void addArrayElement(IValue element);
}
