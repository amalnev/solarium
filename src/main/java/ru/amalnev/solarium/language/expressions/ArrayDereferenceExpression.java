package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.*;

import java.util.List;

@Getter
@Setter
public class ArrayDereferenceExpression implements IExpression
{
    private IExpression arrayExpression;

    private IExpression indexExpression;

    @Override
    public IValue evaluate(ExecutionContext context)
    {
        final IValue arrayExpressionValue = arrayExpression.evaluate(context);
        final IValue indexExpressionValue = indexExpression.evaluate(context);

        if(arrayExpressionValue instanceof LValue)
        {
            final LValue arrayExpressionLValue = (LValue) arrayExpressionValue;
            return new LValue(arrayExpressionLValue.getVariableName(), (Integer) indexExpressionValue.getValue(), context);
        }
        else
        {
            final List<Object> array = (List<Object>) arrayExpressionValue.getValue();
            final Integer index = (Integer) indexExpressionValue.getValue();
            return new RValue(array.get(index));
        }
    }
}
