package ru.amalnev.solarium.language.operators;

public interface IUnaryOperator<T, R>
{
    R operate(T operand);
}
