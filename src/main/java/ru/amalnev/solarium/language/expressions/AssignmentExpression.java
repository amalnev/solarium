package ru.amalnev.solarium.language.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.*;

@Getter
@Setter
@AllArgsConstructor
public class AssignmentExpression implements IExpression
{
    private IExpression leftOperand;

    private IExpression rightOperand;

    @Override
    public IValue evaluate(ExecutionContext context)
    {
        final IValue leftOperandValue = leftOperand.evaluate(context);
        if(! (leftOperandValue instanceof ILValue)) throw new LValueRequiredException(leftOperand.toString());
        final Object rightOperandValue = rightOperand.evaluate(context).getValue();
        final LValue leftOperandLValue = (LValue) leftOperandValue;

        try
        {
            leftOperandLValue.setValue(rightOperandValue);
        }
        catch (VariableNotDefinedException e)
        {
            context.defineScalar(leftOperandLValue.getVariableName());
            leftOperandLValue.setValue(rightOperandValue);
        }

        return leftOperandLValue;
    }

    @Override
    public String toString()
    {
        return leftOperand.toString() + " = " + rightOperand.toString();
    }
}
