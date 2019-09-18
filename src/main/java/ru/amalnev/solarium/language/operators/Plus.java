package ru.amalnev.solarium.language.operators;

import ru.amalnev.solarium.interpreter.errors.TypeMismatchException;

public class Plus extends BinaryOperatorImpl<Object, Object>
{
    public Plus()
    {
        super(Object.class, Object.class);
    }

    @Override
    public String toString()
    {
        return "+";
    }

    @Override
    protected Object calculateResult(Object leftOperandValue, Object rightOperandValue) throws TypeMismatchException
    {
        if (leftOperandValue.getClass().equals(Integer.class))
        { //integer arithmetic is requested.
            if (rightOperandValue.getClass().equals(Integer.class))
                return (Integer) leftOperandValue + (Integer) rightOperandValue;
            try
            {
                if (rightOperandValue.getClass().equals(String.class))
                    return (Integer) leftOperandValue + Integer.valueOf((String) rightOperandValue);
            }
            catch (NumberFormatException e)
            {
                throw new TypeMismatchException();
            }
        }
        else if (leftOperandValue.getClass().equals(String.class))
        { //string concatenation is requested
            return (String) leftOperandValue + rightOperandValue.toString();
        }

        throw new TypeMismatchException();
    }
}
