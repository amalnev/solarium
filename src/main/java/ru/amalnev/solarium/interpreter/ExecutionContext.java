package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.language.statements.CodeBlock;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext
{
    private CallStack callStack = new CallStack();

    private Map<String, FunctionDefinition> functionDefinitions = new HashMap<>();

    public LocalVariable defineLocalVariable(final String name)
    {
        return callStack.defineLocalVariable(name);
    }

    public LocalVariable defineLocalVariable(final String name, final Object value)
    {
        return callStack.defineLocalVariable(name, value);
    }

    public void undefineLocalVariable(final String name)
    {
        callStack.undefineLocalVariable(name);
    }

    public LocalVariable getLocalVariable(final String name)
    {
        return callStack.getLocalVariable(name);
    }

    public <T> T getLocalVariableValue(final String name, final Class<T> valueType)
    {
        return (T) callStack.getLocalVariable(name).getValue();
    }

    public void defineFunction(final FunctionDefinition functionDefinition)
    {
        final String functionName = functionDefinition.getFunctionName();
        if (functionDefinitions.get(functionName) != null)
            throw new InterpreterException("Redefinition: " + functionName);

        functionDefinitions.put(functionName, functionDefinition);
    }

    public CodeBlock enterFunction(final String functionName, Object... args)
    {
        final FunctionDefinition functionDefinition = functionDefinitions.get(functionName);
        if (functionDefinition == null)
            throw new InterpreterException(functionName + " is not defined");

        callStack.enterFunction(functionDefinition, args);
        return functionDefinition.getBody();
    }

    public Object exitFunction()
    {
        return callStack.exitFunction();
    }

    public void setReturnValue(final Object value)
    {
        callStack.setReturnValue(value);
    }

    public boolean isVariableDefined(final String name)
    {
        return callStack.isVariableDefined(name);
    }
}
