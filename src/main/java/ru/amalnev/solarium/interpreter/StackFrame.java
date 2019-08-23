package ru.amalnev.solarium.interpreter;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StackFrame implements IVariableScope, IStackFrame
{
    @Getter
    @Setter
    private String functionName;

    private Scope currentScope = new Scope();

    @Getter
    @Setter
    private Object returnValue;

    @Override
    public void enterEnclosedScope()
    {
        final Scope childScope = new Scope();
        childScope.setParentScope(currentScope);
        currentScope = childScope;
    }

    @Override
    public void exitCurrentScope()
    {
        currentScope = currentScope.getParentScope();
    }

    public static StackFrame makeFrameForFunctionCall(final FunctionDefinition functionDefinition,
                                                      Object... args)
    {
        final StackFrame stackFrame = new StackFrame();
        stackFrame.setFunctionName(functionDefinition.getFunctionName());
        if (args.length != functionDefinition.getArgumentNames().size())
            throw new InterpreterException(functionDefinition.getFunctionName() + ": wrong number of args");

        for (int i = 0; i < args.length; i++)
        {
            final String argumentName = functionDefinition.getArgumentNames().get(i);
            final Object argumentValue = args[i];

            stackFrame.defineScalar(argumentName);
            stackFrame.setValue(argumentName, argumentValue);
        }

        return stackFrame;
    }

    @Override
    public void defineScalar(String name)
    {
        currentScope.defineScalar(name);
    }

    @Override
    public void defineArray(String name)
    {
        currentScope.defineArray(name);
    }

    @Override
    public Object getValue(String name)
    {
        return currentScope.getValue(name);
    }

    @Override
    public Object getValue(String name, Integer index)
    {
        return currentScope.getValue(name, index);
    }

    @Override
    public <T> T getValue(String name, Class<T> valueClass)
    {
        return currentScope.getValue(name, valueClass);
    }

    @Override
    public <T> T getValue(String name, Integer index, Class<T> valueClass)
    {
        return currentScope.getValue(name, index, valueClass);
    }

    @Override
    public void setValue(String name, Object value)
    {
        currentScope.setValue(name, value);
    }

    @Override
    public void setValue(String name, Integer index, Object value)
    {
        currentScope.setValue(name, index, value);
    }
}
