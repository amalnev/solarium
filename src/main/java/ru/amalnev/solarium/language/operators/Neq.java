package ru.amalnev.solarium.language.operators;

public class Neq  implements IBinaryOperator<Object, Boolean>
{
    @Override
    public Boolean operate(Object leftOperand, Object rightOperand)
    {
        final Eq eq = new Eq();
        return !eq.operate(leftOperand, rightOperand);
    }

    @Override
    public String toString()
    {
        return "!=";
    }
}
