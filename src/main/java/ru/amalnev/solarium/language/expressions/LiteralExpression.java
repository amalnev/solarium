package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;

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
    public IValue evaluate(final ExecutionContext context)
    {
        return new Value(value);
    }
}
