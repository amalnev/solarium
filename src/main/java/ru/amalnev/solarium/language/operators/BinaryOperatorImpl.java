package ru.amalnev.solarium.language.operators;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.TypeMismatchException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;

public abstract class BinaryOperatorImpl<T, R> extends BasicOperatorImpl<T, R> implements IBinaryOperator
{
    public BinaryOperatorImpl(Class<T> operandClass, Class<R> resultClass)
    {
        super(operandClass, resultClass);
    }

    @Override
    public IValue operate(IValue leftOperand, IValue rightOperand) throws InterpreterException
    {
        if (!operandClass.isAssignableFrom(leftOperand.getScalarValue().getClass()) ||
            !operandClass.isAssignableFrom(rightOperand.getScalarValue().getClass()))
        {
            throw new TypeMismatchException();
        }

        final T leftOperandValue = (T) leftOperand.getScalarValue();
        final T rightOperandValue = (T) rightOperand.getScalarValue();
        return new Value(calculateResult(leftOperandValue, rightOperandValue));
    }

    protected abstract R calculateResult(T leftOperandValue, T rightOperandValue);
}
