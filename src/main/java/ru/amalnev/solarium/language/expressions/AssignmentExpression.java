package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;

@Getter
@Setter
@AllArgsConstructor
public class AssignmentExpression implements IExpression
{
    private IExpression leftOperand;

    private IExpression rightOperand;

    @Override
    public IValue evaluate(ExecutionContext context) throws InterpreterException
    {
        final IValue leftOperandValue = leftOperand.evaluate(context);
        leftOperandValue.copy(rightOperand.evaluate(context));

        return leftOperandValue;
    }

    @Override
    public String toString()
    {
        return leftOperand.toString() + " = " + rightOperand.toString();
    }
}
