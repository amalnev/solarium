package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;

@Getter
@Setter
@AllArgsConstructor
public class Variable implements IExpression
{
    private String name;

    public String toString()
    {
        return name;
    }

    @Override
    public Object evaluate(final ExecutionContext context)
    {
        return context.getLocalVariable(name).getValue();
    }
}
