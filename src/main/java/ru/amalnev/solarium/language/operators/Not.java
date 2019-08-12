package ru.amalnev.solarium.language.operators;

public class Not implements IUnaryOperator<Boolean, Boolean>
{
    @Override
    public Boolean operate(Boolean operand)
    {
        return !operand;
    }
}
