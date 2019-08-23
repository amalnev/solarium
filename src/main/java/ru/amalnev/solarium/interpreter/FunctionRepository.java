package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.language.statements.FunctionDefinition;

import java.util.HashMap;
import java.util.Map;

public class FunctionRepository implements IFunctionRepository
{
    private Map<String, FunctionDefinition> functionDefinitions = new HashMap<>();

    @Override
    public void defineFunction(final FunctionDefinition functionDefinition)
    {
        final String functionName = functionDefinition.getFunctionName();
        if (functionDefinitions.get(functionName) != null)
            throw new RedefinitionException(functionName);

        functionDefinitions.put(functionName, functionDefinition);
    }

    @Override
    public FunctionDefinition getFunctionDefinition(String name)
    {
        return functionDefinitions.get(name);
    }
}
