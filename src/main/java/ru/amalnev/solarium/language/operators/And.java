package ru.amalnev.solarium.language.operators;

public class And extends BinaryOperatorImpl<Boolean, Boolean>
{
    public And()
    {
        super(Boolean.class, Boolean.class);
    }

    @Override
    protected Boolean calculateResult(Boolean leftOperandValue, Boolean rightOperandValue)
    {
        return leftOperandValue && rightOperandValue;
    }

    @Override
    public String toString()
    {
        return "&&";
    }
}
