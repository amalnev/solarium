package ru.amalnev.solarium.language.operators;

public class UnaryMinus extends UnaryOperatorImpl<Integer, Integer>
{
    public UnaryMinus()
    {
        super(Integer.class, Integer.class);
    }

    @Override
    public String toString()
    {
        return "-";
    }

    @Override
    protected Integer calculateResult(Integer operandValue)
    {
        return -1 * operandValue;
    }
}
