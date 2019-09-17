package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
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
    public IValue evaluate(ExecutionContext context) throws InterpreterException
    {
        /*final Value value = new Value();
        value.copy(operator.operate(leftOperand.evaluate(context), rightOperand.evaluate(context)));
        return value;*/
        return operator.operate(leftOperand.evaluate(context), rightOperand.evaluate(context));
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
