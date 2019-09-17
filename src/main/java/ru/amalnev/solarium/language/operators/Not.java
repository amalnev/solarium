package ru.amalnev.solarium.language.operators;

public class Not extends UnaryOperatorImpl<Boolean, Boolean>
{
    public Not()
    {
        super(Boolean.class, Boolean.class);
    }

    @Override
    protected Boolean calculateResult(Boolean operandValue)
    {
        return !operandValue;
    }
}
