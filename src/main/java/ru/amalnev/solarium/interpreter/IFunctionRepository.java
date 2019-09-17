package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.interpreter.errors.RedefinitionException;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

public interface IFunctionRepository
{
    void defineFunction(final FunctionDefinition functionDefinition) throws RedefinitionException;

    FunctionDefinition getFunctionDefinition(String name);
}
