package ru.amalnev.solarium.language.expressions;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;

public interface IExpression
{
    IValue evaluate(ExecutionContext context) throws InterpreterException;
}
