package ru.amalnev.solarium.language.operators;

public interface IBinaryOperator<T, R>
{
    R operate(T leftOperand, T rightOperand);
}
