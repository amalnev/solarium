package ru.amalnev.solarium.language.operators;

import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;

public class Eq implements IBinaryOperator
{
    @Override
    public String toString()
    {
        return "==";
    }

    @Override
    public IValue operate(IValue leftOperand, IValue rightOperand)
    {
        return new Value(leftOperand.equals(rightOperand));
    }
}
