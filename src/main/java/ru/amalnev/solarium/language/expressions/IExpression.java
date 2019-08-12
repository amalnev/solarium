package ru.amalnev.solarium.language.expressions;

import ru.amalnev.solarium.interpreter.ExecutionContext;

public interface IExpression
{
    Object evaluate(ExecutionContext context);
}
