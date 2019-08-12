package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.language.operators.IBinaryOperator;

@Getter
@Setter
public class BinaryOperationExpression implements IExpression
{
    private IExpression leftOperand;

    private IExpression rightOperand;

    private IBinaryOperator operator;

    @Override
    public Object evaluate(ExecutionContext context)
    {
        final Object leftValue = leftOperand.evaluate(context);
        final Object rightValue = rightOperand.evaluate(context);

        return operator.operate(leftValue, rightValue);
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
