package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.RValue;

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
    public RValue evaluate(final ExecutionContext context)
    {
        return new RValue(value);
    }
}
