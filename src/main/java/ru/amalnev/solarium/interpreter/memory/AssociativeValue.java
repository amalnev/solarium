package ru.amalnev.solarium.interpreter.memory;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.utils.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class AssociativeValue implements IAssociativeValue
{
    private Map<String, IValue> fields = new HashMap<>();

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
    public void setField(String fieldName, IValue fieldValue)
    {
        fields.put(fieldName, fieldValue);
    }

    @Override
    public String[] getFieldNames()
    {
        final String[] result = new String[fields.keySet().size()];
        return fields.keySet().toArray(result);
    }

    @Override
    public void copy(IAssociativeValue other) throws InterpreterException
    {
        fields.clear();
        for (final String fieldName : other.getFieldNames())
        {
            final Value newValue = new Value();
            newValue.copy(other.getField(fieldName));
            fields.put(fieldName, newValue);
        }
    }

    @Override
    public int hashCode()
    {
        final HashCodeBuilder builder = new HashCodeBuilder();
        for (final Map.Entry<String, IValue> fieldElement : fields.entrySet())
        {
            builder.append(fieldElement.getKey());
            builder.append(fieldElement.getValue().hashCode());
        }

        return builder.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!IAssociativeValue.class.isAssignableFrom(obj.getClass()))
            return false;

        final IAssociativeValue otherValue = (IAssociativeValue) obj;
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

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
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
}
