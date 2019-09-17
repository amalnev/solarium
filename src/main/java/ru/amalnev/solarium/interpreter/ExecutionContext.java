package ru.amalnev.solarium.interpreter;

import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.RedefinitionException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.IVariableScope;
import ru.amalnev.solarium.interpreter.stack.CallStack;
import ru.amalnev.solarium.interpreter.stack.ICallStack;
import ru.amalnev.solarium.interpreter.stack.IStackFrame;
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

    @Override
    public CompoundStatement enterFunction(final String functionName, IValue... args) throws InterpreterException
    {
        return callStack.enterFunction(functionName, args);
    }

    @Override
    public IValue exitFunction() throws InterpreterException
    {
        return callStack.exitFunction();
    }

    @Override
    public void setReturnValue(final IValue value)
    {
        callStack.setReturnValue(value);
    }

    @Override
    public IValue getReturnValue() throws InterpreterException
    {
        throw new InterpreterException();
    }

    @Override
    public void defineFunction(FunctionDefinition functionDefinition) throws RedefinitionException
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

    @Override
    public String getFunctionName() throws InterpreterException
    {
        throw new InterpreterException();
    }

    @Override
    public void setFunctionName(String functionName) throws InterpreterException
    {
        throw new InterpreterException();
    }

    @Override
    public IValue defineVariable(String name)
    {
        return callStack.defineVariable(name);
    }

    @Override
    public IValue findVariable(String name) throws InterpreterException
    {
        return callStack.findVariable(name);
    }
}
