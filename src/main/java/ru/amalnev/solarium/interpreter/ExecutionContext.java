package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.language.statements.CompoundStatement;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

public class ExecutionContext implements ICallStack, IVariableScope, IFunctionRepository, IStackFrame
{
    private final FunctionRepository functionRepository;

    private final CallStack callStack;

    public ExecutionContext()
    {
        functionRepository = new FunctionRepository();
        callStack = new CallStack();
        callStack.setFunctionRepository(functionRepository);
    }

    public CompoundStatement enterFunction(final String functionName, Object... args)
    {
        return callStack.enterFunction(functionName, args);
    }

    public Object exitFunction()
    {
        return callStack.exitFunction();
    }

    public void setReturnValue(final Object value)
    {
        callStack.setReturnValue(value);
    }

    @Override
    public void defineScalar(String name)
    {
        callStack.defineScalar(name);
    }

    @Override
    public void defineArray(String name)
    {
        callStack.defineArray(name);
    }

    @Override
    public Object getValue(String name)
    {
        return callStack.getValue(name);
    }

    @Override
    public Object getValue(String name, Integer index)
    {
        return callStack.getValue(name, index);
    }

    @Override
    public <T> T getValue(String name, Class<T> valueClass)
    {
        return callStack.getValue(name, valueClass);
    }

    @Override
    public <T> T getValue(String name, Integer index, Class<T> valueClass)
    {
        return callStack.getValue(name, index, valueClass);
    }

    @Override
    public void setValue(String name, Object value)
    {
        callStack.setValue(name, value);
    }

    @Override
    public void setValue(String name, Integer index, Object value)
    {
        callStack.setValue(name, index, value);
    }

    @Override
    public void defineFunction(FunctionDefinition functionDefinition)
    {
        functionRepository.defineFunction(functionDefinition);
    }

    @Override
    public FunctionDefinition getFunctionDefinition(String name)
    {
        return functionRepository.getFunctionDefinition(name);
    }

    @Override
    public void enterEnclosedScope()
    {
        callStack.enterEnclosedScope();
    }

    @Override
    public void exitCurrentScope()
    {
        callStack.exitCurrentScope();
    }
}
