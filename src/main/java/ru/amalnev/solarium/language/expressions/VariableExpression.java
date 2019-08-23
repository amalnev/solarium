package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.LValue;
import ru.amalnev.solarium.interpreter.RValue;

@Getter
@Setter
@AllArgsConstructor
public class VariableExpression implements IExpression
{
    private String name;

    public String toString()
    {
        return name;
    }

    @Override
    public LValue evaluate(final ExecutionContext context)
    {
        return new LValue(name, context);
    }
}
