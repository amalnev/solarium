package ru.amalnev.solarium.language.operators;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;

public interface IUnaryOperator
{
    IValue operate(IValue operand) throws InterpreterException;
}
