package ru.amalnev.solarium.library.json;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

@FunctionName("toJSON")
@FunctionArguments("value")
public class ToJson extends AbstractNativeFunction
{
    private String convert(IValue value) throws InterpreterException
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("{\n\".\" : ");
        if (value.isScalar())
        {
            final Object scalarValue = value.getScalarValue();
            if (scalarValue == null)
            {
                builder.append("null");
            }
            else if (scalarValue instanceof Integer || scalarValue instanceof Boolean)
            {
                builder.append(scalarValue.toString());
            }
            else
            {
                builder.append("\"");
                builder.append(scalarValue.toString());
                builder.append("\"");
            }
        }
        else
        {
            builder.append("[");
            for (int i = 0; i < value.getArraySize(); i++)
            {
                builder.append(convert(value.getArrayElement(i)));
                if (i != value.getArraySize() - 1)
                {
                    builder.append(", ");
                }
            }
            builder.append("]");
        }

        builder.append(",\n");

        for (final String fieldName : value.getFieldNames())
        {
            builder.append("\"");
            builder.append(fieldName);
            builder.append("\" : ");
            builder.append(convert(value.getField(fieldName)));
            builder.append(",\n");
        }

        builder.deleteCharAt(builder.length() - 2);
        builder.append("}");

        return builder.toString();
    }

    @Override
    public void call(ExecutionContext context) throws InterpreterException
    {
        final IValue value = context.findVariable("value");
        setReturnValue(context, convert(value));
    }
}
