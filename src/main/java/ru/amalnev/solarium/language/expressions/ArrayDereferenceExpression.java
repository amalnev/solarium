package ru.amalnev.solarium.language.expressions;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.TypeMismatchException;
import ru.amalnev.solarium.interpreter.memory.IValue;

@Getter
@Setter
public class ArrayDereferenceExpression implements IExpression
{
    private IExpression arrayExpression;

    private IExpression indexExpression;

    @Override
    public IValue evaluate(ExecutionContext context) throws InterpreterException
    {
        final IValue arrayExpressionValue = arrayExpression.evaluate(context);
        final IValue indexExpressionValue = indexExpression.evaluate(context);

        try
        {
            final Integer index = (Integer) indexExpressionValue.getScalarValue();
            return arrayExpressionValue.getArrayElement(index);
        }
        catch (ClassCastException e)
        {
            throw new TypeMismatchException(indexExpression.toString(),
                                            "integer",
                                            indexExpressionValue.getScalarValue().getClass().getSimpleName());
        }
    }
}
