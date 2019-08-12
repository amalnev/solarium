package ru.amalnev.solarium.language.operators;

public class Or implements IBinaryOperator<Boolean, Boolean>
{
    @Override
    public Boolean operate(Boolean leftOperand, Boolean rightOperand)
    {
        return leftOperand || rightOperand;
    }

    @Override
    public String toString()
    {
        return "|";
    }
}
