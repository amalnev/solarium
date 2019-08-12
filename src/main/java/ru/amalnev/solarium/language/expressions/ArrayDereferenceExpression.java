package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;

import java.util.List;

@Getter
@Setter
public class ArrayDereferenceExpression implements IExpression
{
    private IExpression arrayExpression;

    private IExpression indexExpression;

    @Override
    public Object evaluate(ExecutionContext context)
    {
        final List<Object> array = (List<Object>) arrayExpression.evaluate(context);
        final Integer index = (Integer) indexExpression.evaluate(context);
        return array.get(index);
    }
}
