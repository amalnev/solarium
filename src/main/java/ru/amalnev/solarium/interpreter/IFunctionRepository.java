package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.language.statements.FunctionDefinition;

public interface IFunctionRepository
{
    void defineFunction(final FunctionDefinition functionDefinition);

    FunctionDefinition getFunctionDefinition(String name);
}
