package ru.amalnev.solarium.language.operators;

public class Ge extends BinaryOperatorImpl<Integer, Boolean>
{
    public Ge()
    {
        super(Integer.class, Boolean.class);
    }

    @Override
    public String toString()
    {
        return ">=";
    }

    @Override
    protected Boolean calculateResult(Integer leftOperandValue, Integer rightOperandValue)
    {
        return leftOperandValue >= rightOperandValue;
    }
}
