package ru.amalnev.solarium.language.expressions;

import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

public class ArrayLiteral implements IExpression
{
    @Setter
    private List<IExpression> elements = new ArrayList<>();

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        if (elements.size() > 0)
        {
            builder.append("[");
            for (int i = 0; i < elements.size(); i++)
            {
                builder.append(elements.get(i).toString());
                if (i != elements.size() - 1)
                {
                    builder.append(", ");
                }
            }
            builder.append("]");
        }

        return builder.toString();
    }

    @Override
    public Object evaluate(ExecutionContext context)
    {
        final List<Object> values = new ArrayList<>();
        elements.forEach(element -> values.add(element.evaluate(context)));
        return values;
    }
}
