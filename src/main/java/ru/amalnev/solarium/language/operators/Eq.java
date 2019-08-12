package ru.amalnev.solarium.language.operators;

public class Eq implements IBinaryOperator<Object, Boolean>
{
    @Override
    public Boolean operate(Object leftOperand, Object rightOperand)
    {
        if (leftOperand != null) return leftOperand.equals(rightOperand);
        return false;
    }

    @Override
    public String toString()
    {
        return "==";
    }
}
