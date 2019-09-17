package ru.amalnev.solarium.language.operators;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.TypeMismatchException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;

public abstract class UnaryOperatorImpl<T, R> extends BasicOperatorImpl<T, R> implements IUnaryOperator
{
    public UnaryOperatorImpl(Class<T> operandClass, Class<R> resultClass)
    {
        super(operandClass, resultClass);
    }

    @Override
    public IValue operate(IValue operand) throws InterpreterException
    {
        if (!operandClass.isAssignableFrom(operand.getScalarValue().getClass()))
            throw new TypeMismatchException();

        final T operandValue = (T) operand.getScalarValue();
        return new Value(calculateResult(operandValue));
    }

    protected abstract R calculateResult(T operandValue);
}
