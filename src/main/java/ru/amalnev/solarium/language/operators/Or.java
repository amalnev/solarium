package ru.amalnev.solarium.language.operators;

public class Or extends BinaryOperatorImpl<Boolean, Boolean>
{
    public Or()
    {
        super(Boolean.class, Boolean.class);
    }

    @Override
    public String toString()
    {
        return "||";
    }

    @Override
    protected Boolean calculateResult(Boolean leftOperandValue, Boolean rightOperandValue)
    {
        return leftOperandValue || rightOperandValue;
    }
}
