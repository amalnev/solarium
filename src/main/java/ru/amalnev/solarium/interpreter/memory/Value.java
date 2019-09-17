package ru.amalnev.solarium.interpreter.memory;

import lombok.NoArgsConstructor;
import ru.amalnev.solarium.interpreter.errors.ArrayValueRequiredException;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.utils.HashCodeBuilder;

@NoArgsConstructor
public class Value implements IValue
{
    private ScalarValue scalarValue = new ScalarValue();

    private ArrayValue arrayValue = null;

    private AssociativeValue associativeValue = new AssociativeValue();

    public Value(Object scalarValue)
    {
        this.scalarValue.setScalarValue(scalarValue);
    }

    @Override
    public IValue getField(String fieldName)
    {
        return associativeValue.getField(fieldName);
    }

    @Override
    public IValue getArrayElement(int elementIndex) throws InterpreterException
    {
        if (arrayValue == null) throw new ArrayValueRequiredException();
        return arrayValue.getArrayElement(elementIndex);
    }

    @Override
    public String[] getFieldNames()
    {
        return associativeValue.getFieldNames();
    }

    @Override
    public void copy(IAssociativeValue other) throws InterpreterException
    {
        associativeValue.copy(other);
    }

    @Override
    public boolean isScalar()
    {
        return arrayValue == null;
    }

    @Override
    public boolean isArray()
    {
        return !isScalar();
    }

    @Override
    public int getArraySize() throws InterpreterException
    {
        if (arrayValue == null) throw new ArrayValueRequiredException();
        return arrayValue.getArraySize();
    }

    @Override
    public void copy(IArrayValue other) throws InterpreterException
    {
        convertToArray();
        arrayValue.copy(other);
    }

    @Override
    public void setField(String fieldName, IValue fieldValue)
    {
        associativeValue.setField(fieldName, fieldValue);
    }

    @Override
    public void setArrayElement(int elementIndex, IValue elementValue) throws InterpreterException
    {
        if (arrayValue == null) throw new ArrayValueRequiredException();
        arrayValue.setArrayElement(elementIndex, elementValue);
    }

    @Override
    public void copy(IValue value) throws InterpreterException
    {
        scalarValue.copy(value);

        arrayValue = null;
        if (value.isArray())
        {
            arrayValue = new ArrayValue();
            arrayValue.copy(value);
        }

        associativeValue.copy(value);

        assert this.equals(value);
        assert this.hashCode() == value.hashCode();
    }

    @Override
    public int hashCode()
    {
        final HashCodeBuilder builder = new HashCodeBuilder();
        if (isScalar()) builder.append(scalarValue.hashCode());
        if (isArray()) builder.append(arrayValue.hashCode());
        builder.append(associativeValue.hashCode());

        return builder.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (!other.getClass().equals(this.getClass())) return false;
        final IValue otherValue = (IValue) other;
        if (!(isArray() == otherValue.isArray())) return false;
        if (!(isScalar() == otherValue.isScalar())) return false;
        if (isScalar() && !scalarValue.equals(otherValue)) return false;
        if (isArray() && !arrayValue.equals(otherValue)) return false;
        if (!associativeValue.equals(associativeValue)) return false;

        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        if (isScalar())
        {
            builder.append(scalarValue.toString());
        }
        else
        {
            builder.append(arrayValue.toString());
        }

        builder.append(associativeValue.toString());
        return builder.toString();
    }

    @Override
    public void convertToArray()
    {
        arrayValue = new ArrayValue();
    }

    @Override
    public void convertToScalar()
    {
        arrayValue = null;
    }

    @Override
    public void addArrayElement(IValue element)
    {
        arrayValue.addArrayElement(element);
    }

    @Override
    public Object getScalarValue()
    {
        return scalarValue.getScalarValue();
    }

    @Override
    public void setScalarValue(Object value)
    {
        scalarValue.setScalarValue(value);
    }

    @Override
    public void copy(IScalarValue other)
    {
        convertToScalar();
        scalarValue.copy(other);
    }
}