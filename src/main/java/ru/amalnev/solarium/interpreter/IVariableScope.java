package ru.amalnev.solarium.interpreter;

public interface IVariableScope
{
    void defineScalar(String name);

    void defineArray(String name);

    Object getValue(String name);

    Object getValue(String name, Integer index);

    <T> T getValue(String name, Class<T> valueClass);

    <T> T getValue(String name, Integer index, Class<T> valueClass);

    void setValue(String name, Object value);

    void setValue(String name, Integer index, Object value);
}
