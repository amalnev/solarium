package ru.amalnev.solarium.language.operators;

public class Lt extends BinaryOperatorImpl<Integer, Boolean>
{
    public Lt()
    {
        super(Integer.class, Boolean.class);
    }

    @Override
    public String toString()
    {
        return "<";
    }

    @Override
    protected Boolean calculateResult(Integer leftOperandValue, Integer rightOperandValue)
    {
        return leftOperandValue < rightOperandValue;
    }
}
