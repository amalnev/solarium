package ru.amalnev.solarium.language.expressions;

import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.IValue;

public interface IExpression
{
    IValue evaluate(ExecutionContext context);
}
