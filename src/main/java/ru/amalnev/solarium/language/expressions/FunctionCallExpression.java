package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.RValue;

import java.util.LinkedList;
import java.util.List;

public class FunctionCallExpression implements IExpression
{
    @Getter
    @Setter
    private List<IExpression> functionCallArguments = new LinkedList<>();

    @Getter
    @Setter
    private String functionName;

    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(functionName);
        builder.append("(");

        for (int i = 0; i < functionCallArguments.size(); i++)
        {
            builder.append(functionCallArguments.get(i));
            if (i != functionCallArguments.size() - 1)
            {
                builder.append(", ");
            }
        }

        builder.append(")");
        return builder.toString();
    }

    @Override
    public RValue evaluate(final ExecutionContext context)
    {
        final Object[] functionCallArgumentValues = new Object[functionCallArguments.size()];
        for (int i = 0; i < functionCallArguments.size(); i++)
        {
            functionCallArgumentValues[i] = functionCallArguments.get(i).evaluate(context).getValue();
        }

        context.enterFunction(functionName, functionCallArgumentValues).execute(context);
        return new RValue(context.exitFunction());
    }
}
