package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.language.operators.IUnaryOperator;

@Getter
@Setter
@AllArgsConstructor
public class UnaryOperationExpression implements IExpression
{
    private IUnaryOperator operator;

    private IExpression operand;

    @Override
    public IValue evaluate(ExecutionContext context) throws InterpreterException
    {
        //return new Value(operator.operate(operand.evaluate(context)));
        return operator.operate(operand.evaluate(context));
    }

    @Override
    public String toString()
    {
        return operator.toString() + operand.toString();
    }
}
