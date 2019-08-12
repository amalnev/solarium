package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@AllArgsConstructor
public abstract class LiteralExpression<T> implements IExpression
{
    @Setter
    @Getter
    private T value;

    public String toString()
    {
        return value != null ? value.toString() : "null";
    }

    @Override
    public Object evaluate(final ExecutionContext context)
    {
        return value;
    }
}
