package ru.amalnev.solarium.interpreter.memory;

public interface IScalarValue
{
    Object getScalarValue();

    void setScalarValue(Object value);

    void copy(IScalarValue other);
}
