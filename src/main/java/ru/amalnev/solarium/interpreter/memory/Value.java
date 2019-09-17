package ru.amalnev.solarium.interpreter.memory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.errors.ArrayValueRequiredException;
import ru.amalnev.solarium.interpreter.errors.IndexOutOfBoundsException;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.utils.CommaSeparatedList;
import ru.amalnev.solarium.language.utils.HashCodeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class Value implements IValue
{
    @Getter
    @Setter
    private Object scalarValue = null;

    private List<IValue> arrayValue = null;

    private Map<String, IValue> fields = new HashMap<>();

    public Value(Object scalarValue)
    {
        this.scalarValue = scalarValue;
    }

    @Override
    public IValue getField(String fieldName)
    {
        IValue field = fields.get(fieldName);
        if (field == null)
        {
            field = new Value();
            fields.put(fieldName, field);
        }

        return field;
    }

    @Override
    public IValue getArrayElement(int elementIndex) throws InterpreterException
    {
        if (arrayValue == null) throw new ArrayValueRequiredException();
        if (arrayValue.size() <= elementIndex) throw new IndexOutOfBoundsException();
        return arrayValue.get(elementIndex);
    }

    @Override
    public String[] getFieldNames()
    {
        final String[] result = new String[fields.keySet().size()];
        return fields.keySet().toArray(result);
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
        return arrayValue.size();
    }

    @Override
    public void setField(String fieldName, IValue fieldValue) throws InterpreterException
    {
        fields.put(fieldName, fieldValue);
    }

    @Override
    public void setArrayElement(int elementIndex, IValue elementValue) throws InterpreterException
    {
        if (arrayValue == null) throw new ArrayValueRequiredException();
        if (arrayValue.size() <= elementIndex) throw new IndexOutOfBoundsException();
        arrayValue.set(elementIndex, elementValue);
    }

    @Override
    public void copy(IValue value) throws InterpreterException
    {
        scalarValue = null;
        arrayValue = null;
        fields = new HashMap<>();

        scalarValue = value.getScalarValue();
        if (value.isArray())
        {
            arrayValue = new CommaSeparatedList<>();
            for (int i = 0; i < value.getArraySize(); i++)
            {
                final Value newValue = new Value();
                newValue.copy(value.getArrayElement(i));
                arrayValue.add(newValue);
            }
        }

        for (final String fieldName : value.getFieldNames())
        {
            final Value newValue = new Value();
            newValue.copy(value.getField(fieldName));
            fields.put(fieldName, newValue);
        }

        assert this.equals(value);
        assert this.hashCode() == value.hashCode();
    }

    @Override
    public int hashCode()
    {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(scalarValue);
        if (arrayValue != null)
            arrayValue.forEach(element -> builder.append(element.hashCode()));

        for (final Map.Entry<String, IValue> fieldElement : fields.entrySet())
        {
            builder.append(fieldElement.getKey());
            builder.append(fieldElement.getValue().hashCode());
        }

        return builder.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        try
        {
            if (other == null) return false;
            if (!other.getClass().equals(this.getClass())) return false;
            final IValue otherValue = (IValue) other;
            if (!(isArray() == otherValue.isArray())) return false;
            if (!(isScalar() == otherValue.isScalar())) return false;

            if (scalarValue == null)
            {
                if (otherValue.getScalarValue() != null)
                    return false;
            }
            else
            {
                if (!scalarValue.equals(otherValue.getScalarValue()))
                    return false;
            }

            if (isArray())
            {
                if (arrayValue.size() != otherValue.getArraySize())
                    return false;

                for (int i = 0; i < arrayValue.size(); i++)
                {
                    if (!arrayValue.get(i).equals(otherValue.getArrayElement(i)))
                        return false;
                }
            }

            final String[] otherFields = otherValue.getFieldNames();
            if (fields.size() != otherFields.length) return false;
            for (final String fieldName : otherFields)
            {
                if (!fields.containsKey(fieldName))
                    return false;

                if (!fields.get(fieldName).equals(otherValue.getField(fieldName)))
                    return false;
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
        final StringBuilder builder = new StringBuilder();
        if (isScalar())
        {
            if (scalarValue == null)
            {
                builder.append("null");
            }
            else
            {
                builder.append(scalarValue.toString());
            }
        }
        else
        {
            builder.append("[");
            builder.append(arrayValue.toString());
            builder.append("]");
        }

        if (fields.size() > 0)
        {
            builder.append(" {");
            for (final Map.Entry<String, IValue> fieldElement : fields.entrySet())
            {
                builder.append(fieldElement.getKey());
                builder.append(" = ");
                builder.append(fieldElement.getValue().toString());
                builder.append("; ");
            }
            builder.append("}");
        }

        return builder.toString();
    }

    @Override
    public void convertToArray()
    {
        arrayValue = new CommaSeparatedList<>();
    }

    @Override
    public void convertToScalar()
    {
        arrayValue = null;
    }

    @Override
    public void addArrayElement(IValue element)
    {
        arrayValue.add(element);
    }
}