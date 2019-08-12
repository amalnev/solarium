package ru.amalnev.solarium.language.operators;

public class UnaryMinus implements IUnaryOperator<Integer, Integer>
{
    @Override
    public Integer operate(Integer operand)
    {
        return -1 * operand;
    }

    @Override
    public String toString()
    {
        return "-";
    }
}
