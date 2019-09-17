package ru.amalnev.solarium.language.operators;

public class Modulo extends BinaryOperatorImpl<Integer, Integer>
{
    public Modulo()
    {
        super(Integer.class, Integer.class);
    }

    @Override
    public String toString()
    {
        return "%";
    }

    @Override
    protected Integer calculateResult(Integer leftOperandValue, Integer rightOperandValue)
    {
        return leftOperandValue % rightOperandValue;
    }
}
