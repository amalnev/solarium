package ru.amalnev.solarium.language.operators;

public class Ge implements IBinaryOperator<Integer, Boolean>
{
    @Override
    public Boolean operate(Integer leftOperand, Integer rightOperand)
    {
        return leftOperand >= rightOperand;
    }

    @Override
    public String toString()
    {
        return ">=";
    }
}
