package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.RValue;
import ru.amalnev.solarium.language.operators.IUnaryOperator;

@Getter
@Setter
@AllArgsConstructor
public class UnaryOperationExpression implements IExpression
{
    private IUnaryOperator operator;

    private IExpression operand;

    @Override
    public RValue evaluate(ExecutionContext context)
    {
        return new RValue(operator.operate(operand.evaluate(context).getValue()));
    }

    @Override
    public String toString()
    {
        return operator.toString() + operand.toString();
    }
}
