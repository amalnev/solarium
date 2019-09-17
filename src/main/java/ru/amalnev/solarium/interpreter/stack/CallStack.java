package ru.amalnev.solarium.interpreter.stack;

import lombok.Getter;
import lombok.Setter;
import ru.amalnev.solarium.interpreter.IFunctionRepository;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.errors.UnknownIdentifier;
import ru.amalnev.solarium.interpreter.errors.WrongNumberOfArgsException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.IVariableScope;
import ru.amalnev.solarium.language.statements.CompoundStatement;
import ru.amalnev.solarium.language.statements.FunctionDefinition;

import java.util.Stack;

public class CallStack implements IVariableScope, ICallStack, IStackFrame
{
    private Stack<StackFrame> frames = new Stack<>();

    @Getter
    @Setter
    private IFunctionRepository functionRepository;

    private StackFrame topFrame()
    {
        return frames.peek();
    }

    @Override
    public CompoundStatement enterFunction(String functionName, IValue... args) throws InterpreterException
    {
        final FunctionDefinition functionDefinition = functionRepository.getFunctionDefinition(functionName);
        if (functionDefinition == null) throw new UnknownIdentifier(functionName);
        if (args.length != functionDefinition.getArgumentNames().size())
            throw new WrongNumberOfArgsException(functionDefinition.getFunctionName(),
                                                 functionDefinition.getArgumentNames().size(),
                                                 args.length);

        final StackFrame stackFrame = new StackFrame();
        stackFrame.setFunctionName(functionDefinition.getFunctionName());
        for (int i = 0; i < args.length; i++)
        {
            final String argumentName = functionDefinition.getArgumentNames().get(i);
            final IValue argumentValue = args[i];

            IValue variable = stackFrame.defineVariable(argumentName);
            variable.copy(argumentValue);
        }

        frames.push(stackFrame);
        return functionDefinition.getBody();
    }

    @Override
    public IValue exitFunction()
    {
        return frames.pop().getReturnValue();
    }

    @Override
    public void setReturnValue(final IValue value)
    {
        topFrame().setReturnValue(value);
    }

    @Override
    public IValue getReturnValue()
    {
        return topFrame().getReturnValue();
    }

    @Override
    public void enterEnclosedScope()
    {
        topFrame().enterEnclosedScope();
    }

    @Override
    public void exitCurrentScope()
    {
        topFrame().exitCurrentScope();
    }

    @Override
    public String getFunctionName()
    {
        return topFrame().getFunctionName();
    }

    @Override
    public void setFunctionName(String functionName)
    {
        topFrame().setFunctionName(functionName);
    }

    @Override
    public IValue defineVariable(String name)
    {
        return topFrame().defineVariable(name);
    }

    @Override
    public IValue findVariable(String name) throws InterpreterException
    {
        return topFrame().findVariable(name);
    }
}
