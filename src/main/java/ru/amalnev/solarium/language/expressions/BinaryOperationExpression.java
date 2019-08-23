package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.RValue;
import ru.amalnev.solarium.language.operators.IBinaryOperator;

@Getter
@Setter
@AllArgsConstructor
public class BinaryOperationExpression implements IExpression
{
    private IExpression leftOperand;

    private IBinaryOperator operator;

    private IExpression rightOperand;

    @Override
    public RValue evaluate(ExecutionContext context)
    {
        final Object leftValue = leftOperand.evaluate(context).getValue();
        final Object rightValue = rightOperand.evaluate(context).getValue();

        return new RValue(operator.operate(leftValue, rightValue));
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(leftOperand.toString());
        builder.append(" ");
        builder.append(operator.toString());
        builder.append(" ");
        builder.append(rightOperand.toString());

        return builder.toString();
    }
}
