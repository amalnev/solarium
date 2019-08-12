package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.operators.IUnaryOperator;

@Getter
@Setter
public class UnaryOperationExpression implements IExpression
{
    private IExpression operand;

    private IUnaryOperator operator;

    @Override
    public Object evaluate(ExecutionContext context)
    {
        return operator.operate(operand.evaluate(context));
    }

    @Override
    public String toString()
    {
        return operator.toString() + operand.toString();
    }
}
