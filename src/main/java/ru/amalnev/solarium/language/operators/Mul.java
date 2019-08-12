package ru.amalnev.solarium.language.operators;

public class Mul implements IBinaryOperator<Integer, Integer>
{
    @Override
    public Integer operate(Integer leftOperand, Integer rightOperand)
    {
        return leftOperand * rightOperand;
    }

    @Override
    public String toString()
    {
        return "*";
    }
}
