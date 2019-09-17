package ru.amalnev.solarium.interpreter.memory;

import lombok.Getter;
import lombok.Setter;

public class ScalarValue implements IScalarValue
{
    @Getter
    @Setter
    private Object scalarValue = null;

    @Override
    public void copy(IScalarValue other)
    {
        scalarValue = other.getScalarValue();
    }

    @Override
    public int hashCode()
    {
        if (scalarValue == null) return 0;
        return scalarValue.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!IScalarValue.class.isAssignableFrom(obj.getClass()))
            return false;

        final IScalarValue otherValue = (IScalarValue) obj;
        if (scalarValue == null)
            return otherValue.getScalarValue() == null;

        return scalarValue.equals(otherValue.getScalarValue());
    }

    @Override
    public String toString()
    {
        if (scalarValue == null) return "null";
        return scalarValue.toString();
    }
}
