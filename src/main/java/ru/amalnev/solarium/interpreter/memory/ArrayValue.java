package ru.amalnev.solarium.interpreter.memory;

import ru.amalnev.solarium.interpreter.errors.IndexOutOfBoundsException;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.utils.CommaSeparatedList;
import ru.amalnev.solarium.language.utils.HashCodeBuilder;

import java.util.List;

public class ArrayValue implements IArrayValue
{
    private List<IValue> arrayValue = new CommaSeparatedList<>();

    @Override
    public IValue getArrayElement(int elementIndex) throws InterpreterException
    {
        if (arrayValue.size() <= elementIndex) throw new IndexOutOfBoundsException();
        return arrayValue.get(elementIndex);
    }

    @Override
    public void setArrayElement(int elementIndex, IValue elementValue) throws InterpreterException
    {
        if (arrayValue.size() <= elementIndex) throw new IndexOutOfBoundsException();
        arrayValue.set(elementIndex, elementValue);
    }

    @Override
    public void addArrayElement(IValue element)
    {
        arrayValue.add(element);
    }

    @Override
    public int getArraySize()
    {
        return arrayValue.size();
    }

    @Override
    public void copy(IArrayValue other) throws InterpreterException
    {
        arrayValue = new CommaSeparatedList<>();
        for (int i = 0; i < other.getArraySize(); i++)
        {
            final Value newValue = new Value();
            newValue.copy(other.getArrayElement(i));
            arrayValue.add(newValue);
        }
    }

    @Override
    public int hashCode()
    {
        final HashCodeBuilder builder = new HashCodeBuilder();
        arrayValue.forEach(element -> builder.append(element.hashCode()));
        return builder.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        try
        {
            if (obj == null) return false;
            if (!IArrayValue.class.isAssignableFrom(obj.getClass()))
                return false;

            final IArrayValue otherValue = (IArrayValue) obj;
            if (otherValue.getArraySize() != getArraySize())
                return false;

            for (int i = 0; i < getArraySize(); i++)
            {
                final IValue thisElement = getArrayElement(i);
                final IValue otherElement = otherValue.getArrayElement(i);

                if (thisElement == null)
                {
                    if (otherElement != null)
                        return false;
                }
                else
                {
                    if (!thisElement.equals(otherElement))
                        return false;
                }
            }

            return true;
        }
        catch (InterpreterException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString()
    {
        return "[" + arrayValue.toString() + "]";
    }
}
